package pl.edu.wat.wcy.pz.authorization.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.pz.authorization.dto.UserDto;
import pl.edu.wat.wcy.pz.authorization.entity.RoleName;
import pl.edu.wat.wcy.pz.authorization.entity.User;
import pl.edu.wat.wcy.pz.authorization.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDto userDto) {
        if (userRepository.findById(userDto.getEmail()).isPresent())
            throw new IllegalArgumentException("Email: " + userDto.getEmail() + " is already taken");

        User user = new User(
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getFirstName(),
                userDto.getSecondName(),
                new HashSet<>(Collections.singletonList(RoleName.ROLE_USER))
        );

        userRepository.save(user);
        logger.info("User with email: " + user.getUsername() + " created");
    }
}
