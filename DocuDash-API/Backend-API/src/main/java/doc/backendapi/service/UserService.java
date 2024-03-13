package doc.backendapi.service;

import doc.backendapi.DTO.CreateUserDto;
import doc.backendapi.DTO.UserInfoDto;
import doc.backendapi.entities.User;
import doc.backendapi.repositories.UserRepository;
import doc.backendapi.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private AuthenticationManager authenticationManager;

    // get all users
    public List<UserInfoDto> getAllUsers() {
        List<User> users = repository.findAll();
        return listMapper.mapList(users, UserInfoDto.class, modelMapper);
    }


    public UserInfoDto getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return modelMapper.map(user, UserInfoDto.class);
    }

    public UserInfoDto getUserById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        User user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return modelMapper.map(user, UserInfoDto.class);
    }

//    public ResponseEntity<Optional<User>> editUser(Integer id, EditUserDto user){
//        Optional<User> existUser = repository.findById(id);
//        if(!existUser.isEmpty()){
//            User editUser = modelMapper.map(user, User.class);
//            editUser.setId(id);
//            editUser.setUserName(user.getUserName());
//            editUser.setUserEmail(user.getUserEmail());
//            editUser.setRole(user.getRole());
//            repository.saveAndFlush(editUser);
//            return new ResponseEntity<>(existUser, HttpStatus.OK);
//        }else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
//    }

    public User save(CreateUserDto newUser) {
//        if (newUser.getRole() == null) {
//            newUser.setRole("User");
//        }

        if (!newUser.getRole().equals("ADMIN") && !newUser.getRole().equals("USER") && !newUser.getRole().equals("MANAGER")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "please enter the true role.");
        }

        if (newUser.getUsername().isEmpty() || newUser.getEmail().isEmpty() || newUser.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please fill in all fields.");
        }
//        if (newUser.getUsername().length() < 3 || newUser.getUsername().length() > 20) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must be between 3 and 20 characters.");
//        }
//        if (newUser.getPassword().length() < 8 || newUser.getPassword().length() > 20) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be between 8 and 20 characters.");
//        }
//        if (newUser.getEmail().length() < 5 || newUser.getEmail().length() > 50) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email must be between 5 and 50 characters.");
//        }
        if (repository.existsByEmail(newUser.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists.");
        }
        if (repository.existsByUsername(newUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists.");
        }
        newUser.setUsername(newUser.getUsername().trim());
        newUser.setEmail(newUser.getEmail().trim());
        System.out.println("raw password: " + newUser.getPassword());
        newUser.setPassword(encryptPasswordWithArgon2(newUser.getPassword()));
        System.out.println("new password: " + newUser.getPassword());
        User u = modelMapper.map(newUser, User.class);
        return repository.saveAndFlush(u);
    }

//    public User addUser(UserDTO user){
//        user.setName(user.getName().trim());
//        User newUser = modelMapper.map(user,User.class);
////        System.out.println(newUser);
//        return repository.saveAndFlush(newUser);
//    }


//    @Override
//    public UserDto create(CreateUserDto newUser) {
////        public User save(NewUserDTO newUser) {
//            User u = modelMapper.map(newUser, User.class);
//            return repository.saveAndFlush(u);
//        }
//    }


//    public boolean auth(AuthLoginDto dto) {
//        User user = repository.findByUserEmail(dto.getUserEmail()).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, "A user with the specified email DOES NOT exit"));
//
//        if (!comparePassword(user.getPassword(), dto.getPassword())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password NOT Matched");
//        }
//        return true;
//    }

    public String encryptPasswordWithArgon2(String password) {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
        return encoder.encode(password);
    }

    private boolean comparePassword(String storePassword, String requestPassword) {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
        return encoder.matches(requestPassword, storePassword);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), new ArrayList<>());
    }

//    private User mapUser(User original, UpdateUserDto update) {
//        if (update.getName() != null) original.setName(update.getName());
//        if (update.getEmail() != null) original.setEmail(update.getEmail());
//        if (update.getRole() != null) original.setRole(update.getRole());
//        return original;
//    }


}
