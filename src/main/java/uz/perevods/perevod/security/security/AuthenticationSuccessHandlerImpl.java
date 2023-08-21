package uz.perevods.perevod.security.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import uz.perevods.perevod.component.httpSession.SessionData;
import uz.perevods.perevod.component.httpSession.SessionDataValue;
import uz.perevods.perevod.entitiy.authorization.ActivUserCount;
import uz.perevods.perevod.repository.authorization.jpaRepository.UserRepository;
import uz.perevods.perevod.service.authorization.jpaServices.LogServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    HttpSession session; //autowiring session

    @Autowired
    UserRepository repository; //autowire the user repo

    @Autowired
    SessionData sessionData;

    @Autowired
    LogServiceImpl logService;


    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // TODO Auto-generated method stub
        SessionDataValue sessionDataValue = sessionData.getSessionData(session, request);
        ActivUserCount activeCount = logService.count();

    }

}
