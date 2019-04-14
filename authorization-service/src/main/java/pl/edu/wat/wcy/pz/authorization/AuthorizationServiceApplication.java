package pl.edu.wat.wcy.pz.authorization;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import pl.edu.wat.wcy.pz.authorization.entity.RoleName;
import pl.edu.wat.wcy.pz.authorization.entity.User;
import pl.edu.wat.wcy.pz.authorization.repository.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServiceApplication.class, args);
    }

    @Bean
    ApplicationRunner init(UserRepository userRepository) {
        return args -> {
            userRepository.deleteAll();

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            //Example data
            User user = new User();
            user.setEmail("mateusz");
            user.setPassword(bCryptPasswordEncoder.encode("mateusz"));
            user.setFirstName("FirstName");
            user.setSecondName("SecondName");
            user.setRoles(new HashSet<>(Arrays.asList(RoleName.ROLE_USER, RoleName.ROLE_ADMIN)));
            userRepository.save(user);


            User newUser = new User();
            user.setEmail("user");
            user.setPassword(bCryptPasswordEncoder.encode("user"));
            user.setFirstName("FirstName");
            user.setSecondName("SecondName");
            user.setRoles(new HashSet<>(Collections.singletonList(RoleName.ROLE_USER)));
            userRepository.save(user);
        };
    }
}
