package uz.perevods.perevod.controller.authorization;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.perevods.perevod.entitiy.authorization.Role;
import uz.perevods.perevod.entitiy.authorization.User;
import uz.perevods.perevod.entitiy.authorization.UserRoleResponse;
import uz.perevods.perevod.security.security.SpringSecurityAuditorAware;
import uz.perevods.perevod.service.authorization.jpaServices.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/checkRole")
public class RoleController {
    private final String roleT = "/checkRole";

    private final UserServiceImpl userService;

    public RoleController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping(value = roleT, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public UserRoleResponse editLang(@RequestParam Integer role, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserRoleResponse userRoleResponse = new UserRoleResponse();
        session.setAttribute("role", role);
        session.setAttribute("userRole", role);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authUser = (User) authentication.getPrincipal();
        Optional<User> user = Optional.ofNullable(userService.getUserById(authUser.getId()));
        User userN = new User();
        if (user.isPresent()) {
            user.get().setRole(role);
            userN = userService.save(user.get());
        }
        List<Role> roleList = new ArrayList<>(userN.getRoles());
        roleList.sort((o1, o2) -> o1.getCode().compareTo(o2.getCode()));
        String roleN = "";
        for (Role roles : roleList) {
            if (roles.getCode().equals(userN.getRole())) {
                roleN = roles.getName();
                break;
            }
        }
        userRoleResponse.setFullName(userN.getFullname());
        userRoleResponse.setRoleName(roleN);
        userRoleResponse.setRole(userN.getRole());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User finalUserN = userN;
        Authentication authentication2 = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return finalUserN.getAuthorities();
            }

            @Override
            public Object getCredentials() {
                return finalUserN;
            }

            @Override
            public Object getDetails() {
                return finalUserN;
            }

            @Override
            public Object getPrincipal() {
                return finalUserN;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {
            }

            @Override
            public String getName() {
                return finalUserN.getUsername();
            }
        };
        securityContext.setAuthentication(authentication2);
        SpringSecurityAuditorAware springSecurityAuditorAware = new SpringSecurityAuditorAware();
        springSecurityAuditorAware.getCurrentAuditor();
        return userRoleResponse;
    }
}
