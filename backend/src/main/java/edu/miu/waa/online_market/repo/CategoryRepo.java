package edu.miu.waa.online_market.repo;

import edu.miu.waa.online_market.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category save(Category category);
    List<Category> findAll();
    Category findByName(String name);
}
