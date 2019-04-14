package pl.edu.wat.wcy.pz.database.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.wat.wcy.pz.database.dto.UserDto;

@FeignClient(name = "authorization-service")
public interface AuthorizationServiceClient {

    @PostMapping("/users")
    void creteUser(UserDto userDto);

}
