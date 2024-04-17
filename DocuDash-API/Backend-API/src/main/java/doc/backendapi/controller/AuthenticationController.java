package doc.backendapi.controller;

import doc.backendapi.DTO.AuthLoginDto;
import doc.backendapi.DTO.CreateUserDto;
import doc.backendapi.DTO.EmailDto;
import doc.backendapi.DTO.UserInfoDto;
import doc.backendapi.config.JwtTokenUtil;
import doc.backendapi.entities.User;
import doc.backendapi.model.JwtResponse;
import doc.backendapi.notification.WSService;
import doc.backendapi.service.AuthService;
import doc.backendapi.service.RefreshService;
import doc.backendapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/")
public class AuthenticationController {

    @Autowired
    private AuthService service;

    @Autowired
    private UserService userService;

    @Autowired
    RefreshService refreshService;

    @Autowired
    private WSService wsService;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<JwtResponse> matching(@Valid @RequestBody AuthLoginDto match) throws Exception {
        return service.Matching(match);
    }

    @GetMapping("/refresh")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity refreshLogin(@Valid HttpServletRequest request) {
        return refreshService.refreshToken(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid CreateUserDto newUser) {
        return userService.save(newUser);
    }

    @PostMapping("/user-info")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoDto getUserByEmail(@RequestBody EmailDto email) {
        return userService.getUserByEmail(email.getEmail());
    }
}