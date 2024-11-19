package edu.miu.waa.online_market.service.impl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
//import edu.miu.waa.online_market.annotations.ExecutionTime;
import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.entity.dto.UserDto;
import edu.miu.waa.online_market.repo.UserRepo;
import edu.miu.waa.online_market.service.LoggerService;
import edu.miu.waa.online_market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepo userRepo;


    @Autowired
    private final LoggerService loggerService;

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public void save(UserDto dto){
        User p = new User();
        p.setUsername(dto.getName());
       // p.setPosts(dto.getPost());
        loggerService.logOperation("Saved User with title: " + dto.getName());
        userRepo.save(p);
    }

    @Override
    public void delete(long id) {
        userRepo.deleteById(id);
        loggerService.logOperation("Deleted User with ID: " + id);
    }

    @Override
    public User getById(long id) {
        loggerService.logOperation("Retrieved User with ID: " + id);
        return userRepo.findById(id).get();
    }

    @Override
    public List<User> findAll() {
        Iterable<User> iterable = userRepo.findAll();
        loggerService.logOperation("Retrieved all users");
        List<User> list = Streamable.of(iterable).toList();
        return list;
    }

}
