package uz.perevods.perevod.component.httpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.perevods.perevod.entitiy.authorization.Role;
import uz.perevods.perevod.entitiy.authorization.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class GetterSessionData {

    public SessionDataValue onlyGetSessionData(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Set<Role> roles = user.getRoles();
        List<Role> roleList = new ArrayList<>(roles);

        SessionDataValue sessionDataValue = new SessionDataValue();
        sessionDataValue.setUserId((String) request.getSession().getAttribute("userId"));
        sessionDataValue.setUserIdS((String) request.getSession().getAttribute("userIdS"));
        sessionDataValue.setUserName((String) request.getSession().getAttribute("userName"));
        sessionDataValue.setUserRole((Integer) request.getSession().getAttribute("userRole"));
        sessionDataValue.setUserRoleName((String) request.getSession().getAttribute("userRoleName"));
        sessionDataValue.setUserLocation((String) request.getSession().getAttribute("userLocation"));
        sessionDataValue.setUserLocationName((String) request.getSession().getAttribute("userLocationName"));
        sessionDataValue.setUserPost((String) request.getSession().getAttribute("userPost"));
        sessionDataValue.setUserPostName((String) request.getSession().getAttribute("userPostName"));
        sessionDataValue.setRoles(roleList);
        sessionDataValue.setLanguage((String) request.getSession().getAttribute("lang"));
        sessionDataValue.setCardType((String) request.getSession().getAttribute("cardType"));
        return sessionDataValue;
    }
}
