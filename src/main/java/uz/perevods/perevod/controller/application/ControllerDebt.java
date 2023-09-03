package uz.perevods.perevod.controller.application;

import lombok.RequiredArgsConstructor;
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
import org.springframework.web.servlet.ModelAndView;
import uz.perevods.perevod.entitiy.application.TotalMoney;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;
import uz.perevods.perevod.entitiy.application.TransactionalMoneyLog;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.security.secureData.SecuredUserData;
import uz.perevods.perevod.service.application.DebtService;
import uz.perevods.perevod.service.application.TransactionalMoneyLogService;
import uz.perevods.perevod.service.helperClass.DebtUsagingDto;
import uz.perevods.perevod.service.helperClass.MessageCLassDto;
import uz.perevods.perevod.service.helperClass.TransactionalMoneyDebtDto;
import uz.perevods.perevod.service.helperClass.TransactionalMoneyDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/route_v4")
@RequiredArgsConstructor
public class ControllerDebt {
    private final SecuredUserData securedUserData;
    private final DebtService debtService;
    private final TransactionalMoneyLogService transactionalMoneyLogService;

    @GetMapping("/data_v1/debt_page")
    public ModelAndView getData1(@AuthenticationPrincipal UserDetails userDetails){
        return new ModelAndView("debtpage");
    }

    @GetMapping("/data_v2/debt_info")
    public ResponseEntity<Object> getData2(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(defaultValue = "0") Boolean seeAll){
        Users users = securedUserData.getSecuredUserParams(userDetails);
        List<TransactionalMoneyDebtDto> moneyDebtDto = debtService.getData2(users, userDetails, seeAll);
        return new ResponseEntity<>(moneyDebtDto, HttpStatus.OK);
    }

    @GetMapping("/data_v3/debt_table")
    public DataTablesOutput<TransactionalMoney> getData3(
            @Valid DataTablesInput tablesInput,
            @RequestParam Boolean seeAll,
            @RequestParam String seeAllDebtType,
            @RequestParam String costType,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        Users users = securedUserData.getSecuredUserParams(userDetails);
        return debtService.getData3(tablesInput, userDetails, users, seeAll, seeAllDebtType, costType);
    }

    @PostMapping("/data_v4/debt_usaging")
    public ResponseEntity<Object> setData1(@RequestBody @Valid @Validated DebtUsagingDto debtUsagingDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails){
        Map<String, String> errors;
        if (bindingResult.hasErrors()) {
            errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Users users = securedUserData.getSecuredUserParams(userDetails);
        try {
            MessageCLassDto messageCLassDto = debtService.setData1(debtUsagingDto, users);
            return new ResponseEntity<>(messageCLassDto, HttpStatus.OK);
        }catch (Exception error){
                return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/data_v3/debt_table_log")
    public DataTablesOutput<TransactionalMoneyLog> getData4(
            @Valid DataTablesInput tablesInput
    ){
        return transactionalMoneyLogService.getData1(tablesInput);
    }
}
