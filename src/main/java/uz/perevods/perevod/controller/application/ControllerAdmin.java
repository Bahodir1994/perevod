package uz.perevods.perevod.controller.application;


import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.perevods.perevod.entitiy.authorization.Roles;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.repository.authorization.UserRepository;
import uz.perevods.perevod.security.secureData.SecuredUserData;
import uz.perevods.perevod.service.application.AppService1;
import uz.perevods.perevod.service.application.JobCheckerService;
import uz.perevods.perevod.service.helperClass.CashRegister;
import uz.perevods.perevod.service.helperClass.MessageCLassDto;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/route_v3")
@RequiredArgsConstructor
public class ControllerAdmin {
    private final SecuredUserData securedUserData;
    private final AppService1 appService1;
    private final JobCheckerService jobCheckerService;
    private final UserRepository userRepository;

    /**todo-->*Admin dashboard */
    @GetMapping("/data_v1/admin_panel")
    @Secured("ROLE_admin")
    public ModelAndView getData1(@AuthenticationPrincipal UserDetails userDetails){
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

        try {
            MessageCLassDto messageCLassDto = appService1.changeTotalMoney(cashRegister);
            return new ResponseEntity<>(messageCLassDto, HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/data_v3/chash_status_by_loc")
    @Secured("ROLE_admin")
    public ResponseEntity<Object> getData2(@RequestParam("locationId") String locationId, @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(jobCheckerService.getJobData2(locationId), HttpStatus.OK);
    }

    @GetMapping("/data_v4/change_chash_persons_location")
    @Secured("ROLE_admin")
    public ResponseEntity<Object> setData2(@AuthenticationPrincipal UserDetails userDetails) {
        userRepository.updateLocationCodesAndNames();
        List<Users> usersList = userRepository.findAll();
        for (Users users : usersList) {
            users.setRoles(null);
        }

        // Build the HTML list using StringBuilder
        StringBuilder list = new StringBuilder();
        list.append("<div class=\"w-100\"><ul class=\"list-group listGroup\">");

        for (Users data : usersList) {
            list.append("<li class=\"list-group-item\">")
                    .append(data.getFullName())
                    .append(": ")
                    .append(data.getLocationName())
                    .append(" (")
                    .append(data.getUpdTime())
                    .append(")</li>");
        }

        list.append("</ul></div>");

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("list", list.toString()); // Convert StringBuilder to String
        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }
}
