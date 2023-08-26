package uz.perevods.perevod.security.secureData;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.service.authorization.customs.CustomUserDetailsService;

@Component
@RequiredArgsConstructor
public class SecuredUserData {
    private final CustomUserDetailsService customUserDetailsService;

    public Users getSecuredUserParams(UserDetails userDetails){
        String username = userDetails.getUsername();
        return customUserDetailsService.getUser(username);
    }
}
