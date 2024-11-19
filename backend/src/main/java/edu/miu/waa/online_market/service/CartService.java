package edu.miu.waa.online_market.service;

import edu.miu.waa.online_market.entity.Cart;

public interface CartService {
    void CreateCart(Cart cart);
    Cart getCart(Long id);
}
