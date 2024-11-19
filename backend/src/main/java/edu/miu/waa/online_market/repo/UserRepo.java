package edu.miu.waa.online_market.repo;
import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.entity.dto.UserDto;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Repository
public interface UserRepo extends JpaRepository<User,Long>  {

    public User save(UserDto p);
    public void deleteById(long id);
    public User getById(long id);
    public List<User> findAll();
    public User findByUsername(String username);
}
