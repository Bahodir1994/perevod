package uz.perevods.perevod.controller.application;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.security.secureData.SecuredUserData;
import uz.perevods.perevod.service.application.AppService1;
import uz.perevods.perevod.service.helperClass.CashRegister;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/route_v3")
@RequiredArgsConstructor
public class ControllerAdmin {
    private final SecuredUserData securedUserData;
    private final AppService1 appService1;

    /**todo-->*Admin dashboard */
    @GetMapping("/data_v1/admin_panel")
    @Secured("ROLE_admin")
    public ModelAndView getData4(@AuthenticationPrincipal UserDetails userDetails){
        ModelAndView modelAndView;
        Users users = securedUserData.getSecuredUserParams(userDetails);
        modelAndView = new ModelAndView("adminpage");

        return modelAndView;
    }

    @PostMapping("/data_v2/update_cash")
    @Secured("ROLE_admin")
    public ResponseEntity<Object> setData1(@RequestBody @Valid @Validated CashRegister cashRegister, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails){
        Map<String, String> errors;
        if (bindingResult.hasErrors()) {
            errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        appService1.changeTotalMoney(cashRegister);
        return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.OK);
    }
}
