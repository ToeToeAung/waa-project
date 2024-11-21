package edu.miu.waa.online_market.controller;

import edu.miu.waa.online_market.entity.Product;
import edu.miu.waa.online_market.entity.Review;
import edu.miu.waa.online_market.entity.Role;
import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.entity.dto.ProductDto;
import edu.miu.waa.online_market.service.LoggerService;
import edu.miu.waa.online_market.service.ProductService;
import edu.miu.waa.online_market.service.ReviewService;
import edu.miu.waa.online_market.service.UserService;
import edu.miu.waa.online_market.util.CurrentUser;
import edu.miu.waa.online_market.util.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = {"*"})
//@PreAuthorize("#username == authentication.name")
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final LoggerService loggerService;
    @Autowired
    public ProductController(ProductService productService, ReviewService reviewService, UserService userService,LoggerService loggerService) {
        this.productService = productService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.loggerService = loggerService;
    }

    @GetMapping("/buyer/filter")
    public ResponseEntity<Page<Product>> filterProducts(
            @RequestParam(required = false, defaultValue = "0") Long categoryId,
            @RequestParam(required = false, defaultValue = "0") Float ratingGt,
            @RequestParam(required = false, defaultValue = "0") Float ratingLt,
            @RequestParam(required = false, defaultValue = "0") Float priceGt,
            @RequestParam(required = false, defaultValue = "0") Float priceLt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        loggerService.logOperation("Buyer filter "+user.getUsername() +"User Role "+ user.getRole() + " Role.BUYER? "+Role.BUYER);
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> products;
        if(user.getRole().equals(Role.BUYER)){
           products = productService.findByFilters(categoryId, ratingGt, ratingLt, priceGt, priceLt, pageable);
            return ResponseEntity.ok(products);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/seller")
    public List<Product> getProductsBySeller() {
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        System.out.println("User Role "+ user.getRole() + " Role.BUYER "+Role.SELLER);
        loggerService.logOperation("User Role "+ user.getRole() + " Role.SELLER "+Role.SELLER);
        if(user.getRole().equals(Role.SELLER)){
            return productService.getProductsBySellerId(user.getId());
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }
    @PostMapping
    public void createProduct(@RequestBody Product product) {
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        loggerService.logOperation("User Role "+ user.getRole() + " Role.SELLER "+Role.SELLER);
        productService.createProduct(product);
    }

    @PutMapping()
    public void updateProduct(@RequestParam Long id, @RequestParam int quantity) {
        productService.updateProduct(id, quantity);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PostMapping("{id}/reviews")
    public void createReview(@PathVariable Long id, @RequestBody Review review) {
        reviewService.create(review, id);
    }

    @DeleteMapping("/reviews")
    public void deleteReview(@RequestParam Long productId, @RequestParam Long reviewId) {
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        if(user.getRole().equals(Role.ADMIN)){
            reviewService.delete(productId, reviewId);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/reviews/{id}")
    public List<Review> getReviews(@PathVariable Long id) {
        return productService.findReviewsByProduct(id);
    }

}
