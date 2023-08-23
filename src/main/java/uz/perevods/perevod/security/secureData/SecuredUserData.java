package uz.perevods.perevod.security.secureData;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.service.authorization.customs.CustomUserDetailsService;

@Component
@RequiredArgsConstructor
public class SecuredUserData {
    private final CustomUserDetailsService customUserDetailsService;

    public Users getSecuredUserParams(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return customUserDetailsService.getUser(username);
    }
}
