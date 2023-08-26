package uz.perevods.perevod.controller.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.security.secureData.SecuredUserData;
import uz.perevods.perevod.service.application.JobCheckerService;
import uz.perevods.perevod.service.helperClass.MessageCLassDto;

@RestController
@RequestMapping("/route_v1")
@RequiredArgsConstructor
public class ControllerDv1 {
    private final SecuredUserData securedUserData;
    private final JobCheckerService jobCheckerService;

    @GetMapping("/data_v1/job_status")
    public ModelAndView getData1(@AuthenticationPrincipal UserDetails userDetails){
        ModelAndView modelAndView;
        Users users = securedUserData.getSecuredUserParams(userDetails);
        if (jobCheckerService.getJobStatus(users)){
            modelAndView = new ModelAndView("jobpage");
        }else {
            modelAndView = new ModelAndView("jobstartpage");
        }

        return modelAndView;
    }

    @GetMapping("/data_v2/job_page_data")
    public ResponseEntity<Object> getData2(@AuthenticationPrincipal UserDetails userDetails){
        Users users = securedUserData.getSecuredUserParams(userDetails);
        return new ResponseEntity<>(jobCheckerService.getJobData1(users), HttpStatus.OK);
    }


    @GetMapping("/data_v3/job_page_starting")
    public ResponseEntity<Object> getData3(@AuthenticationPrincipal UserDetails userDetails){
        Users users = securedUserData.getSecuredUserParams(userDetails);
        MessageCLassDto messageCLassDto = jobCheckerService.setTotalMoney(users);
        return new ResponseEntity<>(messageCLassDto, HttpStatus.OK);
    }
}
