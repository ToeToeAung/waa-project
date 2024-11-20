package edu.miu.waa.online_market.service;


import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.entity.dto.UserDto;

import java.util.List;

public interface UserService {
    void createUser(User user);
    List<User> findAll();
    User findById(Long id);
    User findByUsername(String username);
    List<UserDto> findSellersWithPendingStatus();
    void approveSeller(Long userId);
}
