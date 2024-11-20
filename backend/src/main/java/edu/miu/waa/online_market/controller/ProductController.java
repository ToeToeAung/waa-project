package edu.miu.waa.online_market.controller;

import edu.miu.waa.online_market.entity.Product;
import edu.miu.waa.online_market.entity.Review;
import edu.miu.waa.online_market.entity.Role;
import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.entity.dto.ProductDto;
import edu.miu.waa.online_market.service.ProductService;
import edu.miu.waa.online_market.service.ReviewService;
import edu.miu.waa.online_market.service.UserService;
import edu.miu.waa.online_market.util.CurrentUser;
import edu.miu.waa.online_market.util.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = {"*"})
@PreAuthorize("#username == authentication.name")
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;
    private final UserService userService;

    @Autowired
    public ProductController(ProductService productService, ReviewService reviewService, UserService userService) {
        this.productService = productService;
        this.reviewService = reviewService;
        this.userService = userService;
    }
    @GetMapping()
    public List<ProductDto> listProducts() {
        return productService.listProducts();
    }

    @GetMapping("{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }
    @PostMapping
    public void createProduct(@RequestBody ProductDto product, @RequestParam Long categoryId, @RequestParam Long userId) {
        productService.createProduct(product, categoryId, userId);
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
