package edu.miu.waa.online_market.service;

import edu.miu.waa.online_market.entity.CartItem;

public interface CartItemService {
    void addCartItem(CartItem cartItem, Long productId, Long cartId);
}
