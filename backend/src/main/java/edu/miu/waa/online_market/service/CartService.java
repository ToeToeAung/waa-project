package edu.miu.waa.online_market.service;

import edu.miu.waa.online_market.entity.Cart;
import edu.miu.waa.online_market.entity.dto.request.AddCartItemRequest;

public interface CartService {
    Cart CreateCart(Cart cart);
    Cart findById(Long id);
    void deleteById(long id);
    void deleteCartItemById(long buyerId,long productId);
    Cart addCartItem(AddCartItemRequest req);
    Cart getCart();
    Cart removeCartItem(long id);
}
