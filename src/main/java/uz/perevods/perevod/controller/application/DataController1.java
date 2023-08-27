package uz.perevods.perevod.controller.application;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.perevods.perevod.entitiy.application.TotalMoney;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.security.secureData.SecuredUserData;
import uz.perevods.perevod.service.application.AppService1;
import uz.perevods.perevod.service.helperClass.TransactionalMoneyDto;
import uz.perevods.perevod.service.helperClass.ValidationError;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/route_v2")
@RequiredArgsConstructor
public class DataController1 {
    private final SecuredUserData securedUserData;
    private final AppService1 appService1;


    @GetMapping("/dataV1")
    public DataTablesOutput<TransactionalMoney> getData1(DataTablesInput tablesInput, HttpServletRequest request){
        return appService1.getData1(tablesInput, request);
    }

    @GetMapping("/dataV2")
    public ResponseEntity<Object> getData2(@AuthenticationPrincipal UserDetails userDetails){
        Users users = securedUserData.getSecuredUserParams(userDetails);
        TotalMoney totalMoney = appService1.getData2(users);
        return new ResponseEntity<>(totalMoney, HttpStatus.OK);
    }

    /**in money**/
    @PostMapping("/dataV3")
    public ResponseEntity<Object> setData1(@RequestBody @Valid @Validated TransactionalMoneyDto transactionalMoneyDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails
    ) {
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        ValidationError validationError = new ValidationError();
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        Users users = securedUserData.getSecuredUserParams(userDetails);

        appService1.setData1(transactionalMoneyDto, users);
        return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.OK);
    }

    /**out money**/
    @PostMapping("/dataV4")
    public ResponseEntity<Object> setData1(){
        return null;
    }
}
