package edu.miu.waa.online_market.controller;

import edu.miu.waa.online_market.entity.Cart;
import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.entity.dto.request.AddCartItemRequest;
import edu.miu.waa.online_market.repo.UserRepo;
import edu.miu.waa.online_market.service.CartService;
import edu.miu.waa.online_market.service.LoggerService;
import edu.miu.waa.online_market.util.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = {"*"})
//@PreAuthorize("#username == authentication.name")
public class CartController {
    private final CartService cartService;
    private final UserRepo userRepo;
    private final LoggerService loggerService;
    public CartController(CartService cartService, UserRepo userRepo, LoggerService loggerService) {
        this.cartService = cartService;
        this.userRepo = userRepo;
        this.loggerService = loggerService;
    }

    @PostMapping()
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart savedCart = cartService.CreateCart(cart);
        return ResponseEntity.ok(savedCart);
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return cartService.findById(id);
    }

    @PutMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        cartService.deleteById(id);
    }

    @PutMapping("/products/{productId}")
    public void deleteCartItemById(@PathVariable Long productId) {
        User user=userRepo.findByUsername(CurrentUser.getCurrentUser());
        loggerService.logOperation("deleteCartItemById " + user.getUsername());
        cartService.deleteCartItemById(user.getId(),productId);
    }


    @GetMapping()
    public Cart getCart() {
        return cartService.getCart();
    }

    @PostMapping("/items")
    public Cart addCartItem(@RequestBody AddCartItemRequest req) {
        return cartService.addCartItem(req);
    }

    @DeleteMapping("/items/{id}")
    public Cart removeCartItem(@PathVariable long id) {
        return cartService.removeCartItem(id);
    }
}
