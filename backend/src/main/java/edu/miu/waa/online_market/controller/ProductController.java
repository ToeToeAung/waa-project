package edu.miu.waa.online_market.controller;

import edu.miu.waa.online_market.entity.*;
import edu.miu.waa.online_market.entity.dto.ProductDto;
import edu.miu.waa.online_market.service.CategoryService;
import edu.miu.waa.online_market.service.ProductService;
import edu.miu.waa.online_market.service.ReviewService;
import edu.miu.waa.online_market.service.UserService;
import edu.miu.waa.online_market.service.impl.CategoryServiceImpl;
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
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, ReviewService reviewService, UserService userService, CategoryService categoryService) {
        this.productService = productService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.categoryService = categoryService;
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
    public void createProduct(@RequestBody ProductDto product, @RequestParam Long categoryId) {
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        if(user.getRole().equals(Role.SELLER) && user.getSellerStatus().equals(SellerStatus.APPROVED)){
            if(null == categoryService.findById(categoryId)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
            }
            productService.createProduct(product, categoryId, user.getId());
        }
        else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only sellers can create products");
        }
    }

    @PutMapping()
    public void updateProduct(@RequestParam Long id, @RequestParam int quantity) {
        String username = CurrentUser.getCurrentUser();
        Product product = productService.getProduct(id);
        if(product != null && product.getUser().getUsername().equals(username)){
            productService.updateProduct(id, quantity);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Product");
        }
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable Long id) {
        String username = CurrentUser.getCurrentUser();
        Product product = productService.getProduct(id);
        if(product != null && product.getUser().getUsername().equals(username)){
            productService.deleteProduct(id);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Product");
        }
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
