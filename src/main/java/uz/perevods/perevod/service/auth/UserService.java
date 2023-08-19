package uz.perevods.perevod.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.entitiy.auth.User;
import uz.perevods.perevod.repository.auth.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }
}
