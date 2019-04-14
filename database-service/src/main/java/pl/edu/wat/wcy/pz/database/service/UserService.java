package pl.edu.wat.wcy.pz.database.service;

import pl.edu.wat.wcy.pz.database.dto.UserDto;
import pl.edu.wat.wcy.pz.database.entity.User;

public interface UserService {
    User createNewUser(UserDto userDto);
}
