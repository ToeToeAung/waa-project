package edu.miu.waa.online_market.controller;

import edu.miu.waa.online_market.entity.*;
import edu.miu.waa.online_market.entity.dto.ProductDto;
import edu.miu.waa.online_market.service.LoggerService;
import edu.miu.waa.online_market.service.ProductService;
import edu.miu.waa.online_market.service.ReviewService;
import edu.miu.waa.online_market.service.UserService;
import edu.miu.waa.online_market.util.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = {"*"})
//@PreAuthorize("#username == authentication.name")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final LoggerService loggerService;

    @GetMapping("/filter")
    public ResponseEntity<Page<Product>> filterProducts(
            @RequestParam(required = false, defaultValue = "0") Long categoryId,
            @RequestParam(required = false, defaultValue = "0") Float ratingGt,
            @RequestParam(required = false, defaultValue = "0") Float ratingLt,
            @RequestParam(required = false, defaultValue = "0") Float priceGt,
            @RequestParam(required = false, defaultValue = "0") Float priceLt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> products;
        products = productService.findByFilters(categoryId, ratingGt, ratingLt, priceGt, priceLt, pageable);
        return ResponseEntity.ok(products);
//        User user = userService.findByUsername(CurrentUser.getCurrentUser());
//        loggerService.logOperation("Buyer filter "+user.getUsername() +"User Role "+ user.getRole() + " Role.BUYER? "+Role.BUYER);
//        Pageable pageable = PageRequest.of(page, pageSize);
//        Page<Product> products;
//        if(user.getRole().equals(Role.BUYER)){
//        products = productService.findByFilters(categoryId, ratingGt, ratingLt, priceGt, priceLt, pageable);
//        return ResponseEntity.ok(products);
//        }else{
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
//        }
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
    public void createProduct(@RequestBody ProductDto product) {
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        loggerService.logOperation("User Role "+ user.getRole() + " Role.SELLER "+Role.SELLER);

        // TODO: move logic to service layer
        if(user.getRole().equals(Role.SELLER) && user.getSellerStatus().equals(SellerStatus.APPROVED)){
//         if(null == categoryService.findById(categoryId)){
//                  throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
//              }
        productService.createProduct(product);
        return;
      }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
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
        productService.sellerDeleteProduct(id);
//        String username = CurrentUser.getCurrentUser();
//        Product product = productService.getProduct(id);
//        if(product != null && product.getUser().getUsername().equals(username)){
//            productService.deleteProduct(id);
//        }else{
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Product");
//        }
    }

    @PostMapping("{id}/reviews")
    public void createReview(@PathVariable Long id, @RequestBody Review review) {
        reviewService.create(review, id);
    }

    @DeleteMapping("/reviews")
    public void deleteReview(@RequestParam Long productId, @RequestParam Long reviewId) {
        reviewService.delete(productId, reviewId);
//        User user = userService.findByUsername(CurrentUser.getCurrentUser());
//        if(user.getRole().equals(Role.ADMIN)){
//            reviewService.delete(productId, reviewId);
//        }else{
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
//        }
    }

    @GetMapping("/reviews/{id}")
    public List<Review> getReviews(@PathVariable Long id) {
        return productService.findReviewsByProduct(id);
    }

}
