package edu.miu.waa.online_market.repo;

import edu.miu.waa.online_market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User save(User user);

    List<User> findAll();
    //User findById(Long id);
}
