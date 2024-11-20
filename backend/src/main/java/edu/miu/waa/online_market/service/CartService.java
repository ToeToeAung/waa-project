package edu.miu.waa.online_market.service;

import edu.miu.waa.online_market.entity.Cart;

public interface CartService {
    Cart CreateCart(Cart cart);
    Cart findById(Long id);
    void deleteById(long id);
    void deleteCartItemById(long buyerId,long productId);
}
