package pl.edu.wat.wcy.pz.database.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.wcy.pz.database.dto.UserDto;
import pl.edu.wat.wcy.pz.database.entity.User;
import pl.edu.wat.wcy.pz.database.service.UserService;

@AllArgsConstructor
@RestController
@RequestMapping("/linguafranca/users")
public class UserController {

    private UserService userService;

    @PostMapping("")
    public User createNewUser(@RequestBody UserDto userDto) {
        return userService.createNewUser(userDto);
    }
}
