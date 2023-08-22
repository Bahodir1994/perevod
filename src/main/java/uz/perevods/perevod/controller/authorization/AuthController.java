//package uz.perevods.perevod.controller.authorization;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//import uz.perevods.perevod.component.httpSession.GetterSessionData;
//import uz.perevods.perevod.component.httpSession.SessionData;
//import uz.perevods.perevod.component.httpSession.SessionDataValue;
//import uz.perevods.perevod.component.utils.Utils;
//import uz.perevods.perevod.entitiy.ActivUserCount;
//import uz.perevods.perevod.entitiy.User;
//import uz.perevods.perevod.service.authorization.jpaServices.LogServiceImpl;
//import uz.perevods.perevod.service.authorization.jpaServices.UserService;
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/enter_app")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final UserService userService;
//    private final SessionData sessionData;
//    private final GetterSessionData getterSessionData;
//    private final LogServiceImpl logService;
//
//
//    @GetMapping("/sign_in")
//    public ModelAndView signin(HttpSession session, HttpServletRequest request) throws SocketException, UnknownHostException, JsonProcessingException {
//        return new ModelAndView("auth/login");
//    }
//
//    @PostMapping("/sign_in")
//    public String signin(@RequestParam("username") String username,
//                         @RequestParam("password") String password,
//                         Model model) {
//        User user = userService.getUserByUsername(username);
//        if (user != null && user.getPassword().equals(password)) {
//            // Authentication successful
//            return "redirect:/";
//        } else {
//            model.addAttribute("error", "Invalid username or password");
//            return "signin";
//        }
//    }
//
//    @GetMapping("/error_404")
//    public ModelAndView E404Form() {
//        ModelAndView mav = new ModelAndView("/auth/error404");
//        return mav;
//    }
//
//    @PostMapping("/sign_out")
//    public ResponseEntity<String> signout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate(); // Invalidate the session to log out the user
//        }
//
//        String contextPath = request.getContextPath();
//        String redirectUrl = contextPath + "/enter_app/sign_in";
//        return ResponseEntity.status(302).header("Location", redirectUrl).build();
//    }
//
//    @GetMapping("/getSessionData")
//    public ResponseEntity<Object> response(HttpSession session, HttpServletRequest request) throws JsonProcessingException, SocketException, UnknownHostException {
//        SessionDataValue sessionDataValue = sessionData.getSessionData(session, request);
//        SessionDataValue sessionGetterDataValue = getterSessionData.onlyGetSessionData(request);
//        ObjectMapper mapper = new ObjectMapper();
//
//        String lang = Utils.nullClear((String) session.getAttribute("lang"));
//        logService.create(session, request, session.getId(), 0);
//
//        ActivUserCount activeCount = logService.count();
//        String urlSession = (String) request.getSession().getAttribute("URL_PAGE");
//
//        Map<String, Object> objectMap = new HashMap<>();
//        objectMap.put("urlSS", urlSession);
//        objectMap.put("sessionDataValue", sessionDataValue);
//        objectMap.put("sessionGetterDataValue", sessionGetterDataValue);
//        objectMap.put("activeCount", activeCount);
//        return new ResponseEntity<>(objectMap, HttpStatus.OK);
//    }
//}
