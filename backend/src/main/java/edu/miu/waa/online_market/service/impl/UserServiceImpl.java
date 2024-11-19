package edu.miu.waa.online_market.service.impl;

import edu.miu.waa.online_market.entity.Cart;
import edu.miu.waa.online_market.entity.Role;
import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.repo.UserRepo;
import edu.miu.waa.online_market.service.CartService;
import edu.miu.waa.online_market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final CartService cartService;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, CartService cartService) {
        this.userRepo = userRepo;
        this.cartService = cartService;
    }

    public void createUser(User user) {
        if(user.getRole() == Role.BUYER){
            Cart cart = new Cart(user);
            cartService.CreateCart(cart);
        }
        userRepo.save(user);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }
}
