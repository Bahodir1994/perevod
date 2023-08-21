package uz.perevods.perevod.controller.authorization;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/check_language_type")
public class LanguageController {

    @PostMapping("/i18/v1")
    public void setLanguage(@RequestParam("langCode") String langCode, HttpServletRequest request, HttpSession session) {
        session.setAttribute("lang", langCode);
    }
}
