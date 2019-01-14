package pl.java.tetris.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.java.tetris.dto.UserDto;
import pl.java.tetris.entities.User;
import pl.java.tetris.repositories.UserRepository;
import pl.java.tetris.service.UserService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<UserDto> findAllDtoUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> user.toUserDto()).collect(Collectors.toList());
    }


}
