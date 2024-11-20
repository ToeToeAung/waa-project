package edu.miu.waa.online_market.controller;

import edu.miu.waa.online_market.entity.Address;
import edu.miu.waa.online_market.entity.Category;
import edu.miu.waa.online_market.entity.Role;
import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.service.CategoryService;
import edu.miu.waa.online_market.service.UserService;
import edu.miu.waa.online_market.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = {"*"})
@PreAuthorize("#username == authentication.name")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;
    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }
    @GetMapping()
    public List<Category> findAll(){
        return categoryService.findAll();
    }
    @PostMapping()
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        if(user.getRole().equals(Role.ADMIN)){
            if(categoryService.findByName(category.getName()) != null){
                Map<String, Object> error = new HashMap<>();
                error.put("error", Collections.singletonMap("CategoryName", "duplicate"));
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            categoryService.createCategory(category);
            return ResponseEntity.ok().build();
        }
        else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
