package edu.miu.waa.online_market.controller;

import edu.miu.waa.online_market.entity.Address;
import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = {"*"})
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void save(@RequestBody User user) {
        Address address = new Address(user.getAddress().getStreet(), user.getAddress().getCity(),
                user.getAddress().getState(), user.getAddress().getZip());
        User newUser = new User(user.getUsername(), user.getPassword(), user.getRole(), address);
        userService.createUser(newUser);
    }

    @GetMapping()
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/me")
    public User findOne(@RequestParam Long id) {
        return userService.findById(id);
    }


}
