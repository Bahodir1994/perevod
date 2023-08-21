package uz.perevods.perevod.controller.authorization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uz.perevods.perevod.component.httpSession.GetterSessionData;
import uz.perevods.perevod.component.httpSession.SessionData;
import uz.perevods.perevod.component.httpSession.SessionDataValue;
import uz.perevods.perevod.component.utils.Utils;
import uz.perevods.perevod.entitiy.authorization.ActivUserCount;
import uz.perevods.perevod.entitiy.authorization.Role;
import uz.perevods.perevod.entitiy.authorization.User;
import uz.perevods.perevod.service.authorization.jpaServices.LogServiceImpl;
import uz.perevods.perevod.service.authorization.jpaServices.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final LogServiceImpl logService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final String AUTH_SIGN_IN = "/user/auth/auth-signin";
    private final String AUTH_SIGN_UP = "/user/auth/auth-signup";
    private final String AUTH_SIGN_CREATE = "/user/auth/auth-create";
    private final String HOME = "/user/index";
    private final String E404 = "/user/auth/E404";
    private final String SIGNIN = "/user/auth/authIn";
    private final String INDEX = "/";
    private final String NULLING = "/nulling";
    private final SessionData sessionData;
    private final GetterSessionData getterSessionData;

    public static String randomCode() {
        UUID uuid = UUID.randomUUID();
        long lo = uuid.getLeastSignificantBits();
        long hi = uuid.getMostSignificantBits();
        lo = (lo >> (64 - 31)) ^ lo;
        hi = (hi >> (64 - 31)) ^ hi;
        String s = String.format("%010d", Math.abs(hi) + Math.abs(lo));
        return s.substring(s.length() - 14);
    }

    @GetMapping(INDEX)
    public ModelAndView index(HttpSession session, HttpServletRequest request) throws SocketException, UnknownHostException, JsonProcessingException {
        SessionDataValue sessionDataValue = sessionData.getSessionData(session, request);
        SessionDataValue sessionGetterDataValue = getterSessionData.onlyGetSessionData(request);
        ObjectMapper mapper = new ObjectMapper();

        String lang = Utils.nullClear((String) session.getAttribute("lang"));
        logService.create(session, request, session.getId(), 0);

        ActivUserCount activeCount = logService.count();
        String urlSession = (String) request.getSession().getAttribute("URL_PAGE");
        ModelAndView mav = new ModelAndView("/index2");

        mav.addObject("urlSS", urlSession);
        mav.addObject("sessionDataValue", sessionDataValue);
        mav.addObject("sessionGetterDataValue", mapper.writeValueAsString(sessionGetterDataValue));
        mav.addObject("activeCount", activeCount);
        return mav;
    }

    @GetMapping(NULLING)
    public ModelAndView indexNulling(HttpSession session, HttpServletRequest request) throws SocketException, UnknownHostException {
        request.getSession().setAttribute("URL_PAGE", null);
        ModelAndView mav = new ModelAndView("/resources/pages/StarterPage/homes");
        mav.addObject("urlSS", null);
        return mav;
    }

    @GetMapping(AUTH_SIGN_IN)
    public ModelAndView signInForm(HttpSession session) {
        ModelAndView mav = new ModelAndView("/auth/auth-signin");
        mav.addObject("auth-signin", new User());
        session.setAttribute("LOGIN_TYPE", 0);
        return mav;
    }

    @GetMapping(AUTH_SIGN_UP)
    public ModelAndView authSignUp(HttpSession session) {
        ModelAndView mav = new ModelAndView("/auth/auth-signup");
        mav.addObject("auth-signup", new User());
        return mav;
    }

    @PostMapping(AUTH_SIGN_CREATE)
    public ModelAndView create(@Valid @ModelAttribute User user, BindingResult br) throws IOException {
        ModelAndView mav = new ModelAndView("/auth/auth-signin");
        User userExist = userService.getUserByUsername(user.getUsername());
        if (userExist == null) {
            if (!br.hasErrors()) {
                Set<Role> roles = new HashSet<>();
                roles.add(new Role("95_amu", "Администратор касса", 1));
                user.setNotification(0);
                user.setRoles(roles);
                user.setInsUser(randomCode());
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userService.create(user);
            }
        } else {
            userService.update(userExist.getId(), user);
        }
        return mav;
    }

    @GetMapping(HOME)
    public ModelAndView home(HttpSession session) {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @GetMapping(E404)
    public ModelAndView E404Form() {
        ModelAndView mav = new ModelAndView("/auth/E404");
        return mav;
    }

    @GetMapping(SIGNIN)
    public ModelAndView authInForm(HttpSession session) {
        ModelAndView mav = new ModelAndView("/auth/authIn");
        mav.addObject("auth-sign-up", new User());
        String lang = Utils.nullClear((String) session.getAttribute("lang"));
        if (lang.equals("")) lang = "OZ";
        if (lang.equals("EN")) lang = "GB";
        return mav;
    }

    @RequestMapping("/resources/pages/StarterPage/homes")
    public ModelAndView homePage(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/resources/pages/StarterPage/homes");
        modelAndView.addObject("setting", "ma'lumot");
        return modelAndView;
    }

    @RequestMapping("/resources/pages/StarterPage/unique")
    public ModelAndView uniquePage(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/resources/pages/StarterPage/unique");
        modelAndView.addObject("setting", "ma'lumot");
        return modelAndView;
    }
}
