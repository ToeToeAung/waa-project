package edu.miu.waa.online_market.service.impl;

import edu.miu.waa.online_market.entity.*;
import edu.miu.waa.online_market.entity.dto.request.AddCartItemRequest;
import edu.miu.waa.online_market.repo.CartItemRepo;
import edu.miu.waa.online_market.repo.CartRepo;
import edu.miu.waa.online_market.repo.ProductRepo;
import edu.miu.waa.online_market.service.CartService;
import edu.miu.waa.online_market.service.UserService;
import edu.miu.waa.online_market.util.CurrentUser;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepo cartRepo;
    private final UserService userService;
    private final ProductRepo productRepo;
    private final CartItemRepo cartItemRepo;


    @Override
    public Cart CreateCart(Cart cart) {
        return cartRepo.save(cart);

    }
    @Override
    public Cart findById(Long id){
        return cartRepo.findById(id).orElse(null);
    }
    @Override
    public void deleteById(long id){
        cartRepo.deleteById(id);
    }
    @Transactional
    public void deleteCartItemById(long buyerId,long productId){
        cartRepo.deleteCartItemByBuyerIdAndProductId(buyerId,productId);
    }

    @Override
    public Cart addCartItem(AddCartItemRequest req) {
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        if (user == null || !user.getRole().equals(Role.BUYER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Cart cart = cartRepo.findByUserId(user.getId()).orElse(null);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(req.getProductId()))
                .findAny()
                .orElse(null);
        if (cartItem == null) {
            cartItem = new CartItem();
            cart.addCartItem(cartItem);
        }
        Product product = productRepo.findById(req.getProductId()).get();
        cartItem.setProduct(product);
        cartItem.setQuantity(req.getQuantity());
        cartItem.setCart(cart);
        cartRepo.save(cart);
        return cart;
    }

    @Override
    public Cart getCart() {
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        if (user == null || !user.getRole().equals(Role.BUYER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return cartRepo.findByUserId(user.getId()).orElseGet(() -> getEmptyCart(user));
    }

    @Override
    public Cart removeCartItem(long id) {
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        if (user == null || !user.getRole().equals(Role.BUYER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        cartItemRepo.deleteById(id);
        return cartRepo.findByUserId(user.getId()).orElseGet(() -> getEmptyCart(user));
    }

    private Cart getEmptyCart(User user) {
        Cart emptyCart = new Cart();
        emptyCart.setUser(user);
        emptyCart.setCartItems(new ArrayList<>());
        return emptyCart;
    }
}
