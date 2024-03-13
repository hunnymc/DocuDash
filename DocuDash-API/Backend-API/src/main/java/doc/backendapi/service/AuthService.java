package doc.backendapi.service;

import doc.backendapi.DTO.AuthLoginDto;
import doc.backendapi.config.JwtTokenUtil;
import doc.backendapi.entities.User;
import doc.backendapi.model.JwtResponse;
import doc.backendapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationManager authenticationManager;

    public ResponseEntity<JwtResponse> Matching(AuthLoginDto match) throws Exception {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
        match.setPassword(match.getPassword());
        if (match.getUserEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is empty.");
        }
        List<User> checkPassword = repository.findAll();
        User duplicateEmail = repository.findByEmail(match.getUserEmail()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "A user with the specified email DOES NOT exist"));
        for (User eventUser : checkPassword){
            if (eventUser.getEmail().equals(match.getUserEmail().trim())){
//                if (encoder.matches(match.getPassword(), eventUser.getPassword())){
//                    throw new ResponseStatusException(HttpStatus.OK, "Password OK Matched.");
//                }
                if (!encoder.matches(match.getPassword(), eventUser.getPassword())){
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password NOT Matched");
                }
            }
        }
        if (duplicateEmail.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is empty.");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(match.getUserEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

        //Get role by email
        Optional<User> user = repository.findByEmail(match.getUserEmail());

        return ResponseEntity.ok(new JwtResponse("Login Success",token,refreshToken,user.get().getRole()));
    }
}