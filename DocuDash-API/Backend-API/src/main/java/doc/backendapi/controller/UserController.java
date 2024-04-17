package doc.backendapi.controller;

import doc.backendapi.DTO.AuthLoginDto;
import doc.backendapi.DTO.UserInfoDto;
import doc.backendapi.config.JwtTokenUtil;
import doc.backendapi.model.JwtResponse;
import doc.backendapi.notification.WSService;
import doc.backendapi.service.AuthService;
import doc.backendapi.service.RefreshService;
import doc.backendapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
}