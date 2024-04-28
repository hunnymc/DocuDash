package doc.backendapi.controller;

import doc.backendapi.DTO.UserInfoDto;
import doc.backendapi.hadlers.CustomHttpException;
import doc.backendapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserInfoDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/check-token")
    public ResponseEntity<String> checkToken() {
        return ResponseEntity.ok("Token is valid");
    }

    @GetMapping("/user-info/{userID}")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoDto getUserById(@PathVariable Integer userID) {
        return userService.getUserById(userID);
    }

    @PatchMapping("/{userId}")
    public CustomHttpException editUser(@PathVariable Integer userId, @RequestBody UserInfoDto user) {
        return userService.editUser(userId, user);
    }
}