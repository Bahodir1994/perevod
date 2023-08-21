package uz.perevods.perevod.service.authorization.jpaServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.component.utils.Utils;
import uz.perevods.perevod.repository.authorization.jpaRepository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        UserDetails userDetails = userRepository.findByUsername(Utils.RepMus(userName.trim()));
        return userDetails;
    }
}
