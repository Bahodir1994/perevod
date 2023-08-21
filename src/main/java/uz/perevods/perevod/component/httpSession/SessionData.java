package uz.perevods.perevod.component.httpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.perevods.perevod.entitiy.authorization.Role;
import uz.perevods.perevod.entitiy.authorization.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SessionData {
    public SessionDataValue getSessionData(HttpSession session, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Set<Role> roles = user.getRoles();
        List<Role> roleList = new ArrayList<>(roles);
        roleList.sort((o1, o2) -> o1.getCode().compareTo(o2.getCode()));

        if (session.getAttribute("userRole") == null) {
            if (user.getRole() == null) {
                session.setAttribute("role", "0");
                session.setAttribute("userRole", "0");
                session.setAttribute("userId", "0");
                session.setAttribute("userIdS", user.getId());
                session.setAttribute("userName", "0");
                session.setAttribute("userLocation", "0");
                session.setAttribute("userPost", "0");
                session.setAttribute("userLocationName", "0");
                session.setAttribute("lang", "uz_UZ");
            } else {
                session.setAttribute("role", user.getRole());
                session.setAttribute("userRole", user.getRole());
                session.setAttribute("userId", user.getUserid());
                session.setAttribute("userIdS", user.getId());
                session.setAttribute("userName", user.getFullname());
                session.setAttribute("userLocation", user.getLocation());
                session.setAttribute("userLocationName", user.getLocationname());
                session.setAttribute("userPost", user.getPost());
                session.setAttribute("lang", "uz_UZ");
            }
        }

        Integer roleI = (Integer) session.getAttribute("role");
        String roleN = "";
        for (Role role : roleList) {
            if (role.getCode().equals(roleI)) {
                roleN = role.getName();
                break;
            }
        }
        session.setAttribute("userRoleName", roleN);
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
