package pl.edu.wat.wcy.pz.authorization.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/current")
    public Principal getCurrentUser(Principal principal) {
        return principal;
    }
}
