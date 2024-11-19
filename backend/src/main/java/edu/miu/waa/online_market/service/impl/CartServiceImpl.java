package edu.miu.waa.online_market.service.impl;

import edu.miu.waa.online_market.entity.Cart;
import edu.miu.waa.online_market.repo.CartRepo;
import edu.miu.waa.online_market.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private CartRepo cartRepo;
    @Autowired
    public CartServiceImpl(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }
    @Override
    public void CreateCart(Cart cart) {
        cartRepo.save(cart);
    }
    @Override
    public Cart getCart(Long id){
        return cartRepo.findById(id).orElse(null);
    }
}
