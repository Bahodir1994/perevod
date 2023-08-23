package uz.perevods.perevod.controller.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.security.secureData.SecuredUserData;
import uz.perevods.perevod.service.application.JobCheckerService;

@RestController
@RequestMapping("/route_v1")
@RequiredArgsConstructor
public class ControllerDv1 {
    private final SecuredUserData securedUserData;
    private final JobCheckerService jobCheckerService;

    @GetMapping("/data_v1/job_status")
    public ModelAndView home(){
        ModelAndView modelAndView;
        Users users = securedUserData.getSecuredUserParams();
        if (jobCheckerService.getJobStatus(users)){
            modelAndView = new ModelAndView("jobpage");
        }else {
            modelAndView = new ModelAndView("jobstartpage");
        }

        return modelAndView;
    }
}
