package edu.miu.waa.online_market.service;
import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.entity.dto.UserDto;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
public interface UserService {
    void save(UserDto p);
    void delete(long id);
    User getById(long id);
    public List<User> findAll();
    // void update(long id, PostDto p);

  }
