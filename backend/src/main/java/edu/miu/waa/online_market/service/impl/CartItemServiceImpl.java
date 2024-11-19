package edu.miu.waa.online_market.service.impl;

import edu.miu.waa.online_market.entity.Cart;
import edu.miu.waa.online_market.entity.CartItem;
import edu.miu.waa.online_market.entity.Product;
import edu.miu.waa.online_market.repo.CartItemRepo;
import edu.miu.waa.online_market.service.CartItemService;
import edu.miu.waa.online_market.service.CartService;
import edu.miu.waa.online_market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepo cartItemRepo;
    private final ProductService productService;
    private final CartService cartService;
    @Autowired
    public CartItemServiceImpl(CartItemRepo cartItemRepo, ProductService productService, CartService cartService) {
        this.cartItemRepo = cartItemRepo;
        this.productService = productService;
        this.cartService = cartService;
    }
    @Override
    public void addCartItem(CartItem cartItem, Long productId, Long cartId) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProduct(productId);
        CartItem newCartItem = new CartItem(cartItem.getQuantity(), product);
        cart.getCartItems().add(cartItem);
        cartItemRepo.save(newCartItem);
    }
}
