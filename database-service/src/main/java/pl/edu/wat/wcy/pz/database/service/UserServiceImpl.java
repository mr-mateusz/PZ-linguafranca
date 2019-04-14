package pl.edu.wat.wcy.pz.database.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.pz.database.client.AuthorizationServiceClient;
import pl.edu.wat.wcy.pz.database.dto.UserDto;
import pl.edu.wat.wcy.pz.database.entity.User;
import pl.edu.wat.wcy.pz.database.repository.UserRepository;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AuthorizationServiceClient authorizationServiceClient;

    @Override
    public User createNewUser(UserDto userDto) {

        if (userRepository.findByUsername(userDto.getEmail()).isPresent())
            throw new IllegalArgumentException("This email is already taken.");

        if (authorizationServiceClient == null) {
            System.out.println("mfnsjidnfsdaomfskld");
        }
        authorizationServiceClient.creteUser(userDto);

        User user = new User();
        user.setUsername(userDto.getEmail());
        userRepository.save(user);

        return user;
    }
}
