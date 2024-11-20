package edu.miu.waa.online_market.service.impl;

import edu.miu.waa.online_market.entity.Cart;
import edu.miu.waa.online_market.entity.Role;
import edu.miu.waa.online_market.entity.SellerStatus;
import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.entity.dto.UserDto;
import edu.miu.waa.online_market.repo.UserRepo;
import edu.miu.waa.online_market.service.CartService;
import edu.miu.waa.online_market.service.UserService;
import edu.miu.waa.online_market.service.LoggerService;
import edu.miu.waa.online_market.util.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.miu.waa.online_market.util.CurrentUser;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final CartService cartService;
    private final LoggerService loggerService;
    private final ListMapper listMapper;
    @Autowired
    public UserServiceImpl(UserRepo userRepo, CartService cartService,LoggerService loggerService,
                           ListMapper listMapper) {
        this.userRepo = userRepo;
        this.cartService = cartService;
        this.loggerService = loggerService;
        this.listMapper = listMapper;
    }

    public void createUser(User user) {
        String user1= CurrentUser.getCurrentUser();
        loggerService.logOperation("Current User is "+ user1);
        user.setSellerStatus(SellerStatus.NONE);
        if(user.getRole() == Role.BUYER){
            Cart cart = new Cart(user);
            cartService.CreateCart(cart);
        }else if(user.getRole() == Role.SELLER){
            user.setSellerStatus(SellerStatus.PENDING);
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

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> findSellersWithPendingStatus(){
        return userRepo.findBySellerStatus(SellerStatus.PENDING);
    }

    @Override
    public void approveSeller(Long id){
        User user = findById(id);
        user.setSellerStatus(SellerStatus.APPROVED);
        userRepo.save(user);
    }
}
