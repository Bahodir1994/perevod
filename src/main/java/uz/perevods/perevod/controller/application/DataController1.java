package uz.perevods.perevod.controller.application;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import uz.perevods.perevod.entitiy.application.TotalMoney;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.repository.application.TransactionalMoneyRepository;
import uz.perevods.perevod.security.secureData.SecuredUserData;
import uz.perevods.perevod.service.application.AppService1;
import uz.perevods.perevod.service.helperClass.ValidationError;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/dataV3")
    public ResponseEntity<Object> setData1(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody TransactionalMoneyDto transactionalMoneyDto, BindingResult bindingResult){
        ValidationError validationError = new ValidationError();
        if (bindingResult.hasErrors()){
            for (FieldError error : bindingResult.getFieldErrors()) {
                validationError.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
        }

        Users users = securedUserData.getSecuredUserParams(userDetails);

        appService1.setData1(transactionalMoneyDto, users);
        return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.OK);
    }
}
