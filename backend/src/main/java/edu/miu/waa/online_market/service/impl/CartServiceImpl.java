package edu.miu.waa.online_market.service.impl;

import edu.miu.waa.online_market.entity.Cart;
import edu.miu.waa.online_market.repo.CartRepo;
import edu.miu.waa.online_market.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private CartRepo cartRepo;
    @Autowired
    public CartServiceImpl(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

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
}
