package uz.perevods.perevod.service.authorization.customs;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.repository.authorization.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(userName);
//        List<Set<String>> roles = Arrays.asList(user.getRoles());
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles("USER")
                        .build();

        return userDetails;
    }

    public Users getUser(String userName){
        return userRepository.findByUsername(userName);
    }


}

