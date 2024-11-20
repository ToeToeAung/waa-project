package edu.miu.waa.online_market.controller;
import edu.miu.waa.online_market.entity.Role;
import edu.miu.waa.online_market.entity.dto.UserDto;
import edu.miu.waa.online_market.service.LoggerService;
import edu.miu.waa.online_market.entity.Address;
import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.service.UserService;
import edu.miu.waa.online_market.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = {"*"})
@PreAuthorize("#username == authentication.name")
public class UserController {
    private final UserService userService;
    private final LoggerService loggerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, LoggerService loggerService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.loggerService = loggerService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<?> save(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", Collections.singletonMap("username", "duplicate"));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
        if(user.getRole().equals(Role.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Address address = new Address(user.getAddress().getStreet(), user.getAddress().getCity(),
                user.getAddress().getState(), user.getAddress().getZip());

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        User newUser = new User(user.getUsername(), encodedPassword, user.getRole(), address);
        userService.createUser(newUser);
        return ResponseEntity.ok().build();
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/register")
//    public void save(@RequestBody User user) {
//        if(null != userService.findByUsername(user.getUsername())){
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
//        }
//
//        Address address = new Address(user.getAddress().getStreet(), user.getAddress().getCity(),
//                user.getAddress().getState(), user.getAddress().getZip());
//
//        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
//        User newUser = new User(user.getUsername(), encodedPassword, user.getRole(), address);
//        userService.createUser(newUser);
//    }

    @GetMapping()
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/me")
    public User findOne() {
        String name = CurrentUser.getCurrentUser();
        loggerService.logOperation("@me:user name is " + name);
        return userService.findByUsername(name);

    }

    @GetMapping("/sellers")
    public List<User> findSellersWithPendingStatus() {
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        if(user.getRole().equals(Role.ADMIN)){
            return userService.findSellersWithPendingStatus();
        }
        else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/sellers/{id}")
    public void approveSeller(@PathVariable Long id) {
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        if(user.getRole().equals(Role.ADMIN)){
            userService.approveSeller(id);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
