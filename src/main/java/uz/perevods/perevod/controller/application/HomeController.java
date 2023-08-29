package uz.perevods.perevod.controller.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.security.secureData.SecuredUserData;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final SecuredUserData securedUserData;

    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal UserDetails userDetails) throws JsonProcessingException {
        Users users = securedUserData.getSecuredUserParams(userDetails);
        ObjectMapper objectMapper = new ObjectMapper();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("user", users);
        modelAndView.addObject("userLocation", objectMapper.writeValueAsString(users.getLocationCode()));
        return modelAndView;
    }
}
