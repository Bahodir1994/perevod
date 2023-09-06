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
import uz.perevods.perevod.entitiy.application.TransactionalMoneyLog;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.security.secureData.SecuredUserData;
import uz.perevods.perevod.service.application.AppService1;
import uz.perevods.perevod.service.application.TransactionalMoneyLogService;
import uz.perevods.perevod.service.helperClass.*;

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
    private final TransactionalMoneyLogService transactionalMoneyLogService;


    @GetMapping("/dataV1")
    public DataTablesOutput<TransactionalMoney> getData1(DataTablesInput tablesInput, @AuthenticationPrincipal UserDetails userDetails){
        Users users = securedUserData.getSecuredUserParams(userDetails);
        return appService1.getData1(tablesInput, users);
    }

    @GetMapping("/dataV2")
    public ResponseEntity<Object> getData2(@AuthenticationPrincipal UserDetails userDetails){
        Users users = securedUserData.getSecuredUserParams(userDetails);
        TotalMoney totalMoney = appService1.getData2(users);
        return new ResponseEntity<>(totalMoney, HttpStatus.OK);
    }
/*******************************************************************************************/
    /*todo--> in money >>*/
    @PostMapping("/dataV3")
    public ResponseEntity<Object> postData1(@RequestBody @Valid @Validated TransactionalMoneyDto transactionalMoneyDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        Map<String, String> errors;
        if (bindingResult.hasErrors()) {
            errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Users users = securedUserData.getSecuredUserParams(userDetails);

        appService1.setData1In(transactionalMoneyDto, users);
        return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.OK);
    }

    @PutMapping("/dataV3")
    public ResponseEntity<Object> updateData1(@RequestBody @Valid @Validated TransactionalMoneyDto transactionalMoneyDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        Map<String, String> errors;
        if (bindingResult.hasErrors()) {
            errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Users users = securedUserData.getSecuredUserParams(userDetails);

        MessageCLassDto messageCLassDto = appService1.setData1InChange("put", transactionalMoneyDto, users);
        return new ResponseEntity<>(messageCLassDto, HttpStatus.OK);
    }

    @DeleteMapping("/dataV3")
    public ResponseEntity<Object> setData1(@RequestBody @Valid @Validated TransactionalMoneyDto transactionalMoneyDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        Map<String, String> errors;
        if (bindingResult.hasErrors()) {
            errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Users users = securedUserData.getSecuredUserParams(userDetails);

        MessageCLassDto messageCLassDto = appService1.setData1InChange("delete", transactionalMoneyDto, users);
        return new ResponseEntity<>(messageCLassDto, HttpStatus.OK);
    }
    /*todo--> in money <<*/
/*******************************************************************************************/
    /*todo--> out money >>*/
    @PostMapping("/dataV4")
    public ResponseEntity<Object> setData1(@RequestBody @Valid @Validated OutUsagingDto outUsagingDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails){
        Map<String, String> errors;
        if (bindingResult.hasErrors()) {
            errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Users users = securedUserData.getSecuredUserParams(userDetails);
        try {
            MessageCLassDto messageCLassDto = appService1.setData1Out(outUsagingDto, users);
            return new ResponseEntity<>(messageCLassDto, HttpStatus.OK);
        }catch (Exception error) {
            return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*todo--> out money <<**/
/*******************************************************************************************/
    /*todo--> out money log >>*/
    @GetMapping("/dataV5")
    public DataTablesOutput<TransactionalMoneyLog> getData4(@Valid DataTablesInput tablesInput){
        return transactionalMoneyLogService.getData2(tablesInput);
    }
    /*todo--> out money log <<*/
}
