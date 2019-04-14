package pl.edu.wat.wcy.pz.authorization.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.pz.authorization.dto.UserDto;
import pl.edu.wat.wcy.pz.authorization.service.UserService;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @GetMapping("/current")
    public Principal getCurrentUser(Principal principal) {
        return principal;
    }

    @PostMapping("")
    @PreAuthorize("#oauth2.hasScope('server')")
    public void createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
    }
}
