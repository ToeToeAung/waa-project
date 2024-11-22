package edu.miu.waa.online_market.repo;

import edu.miu.waa.online_market.entity.Cart;
import edu.miu.waa.online_market.entity.Order;
import edu.miu.waa.online_market.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart save(Cart cart);
    Cart findById(long id);
    @Query("SELECT c FROM Cart c JOIN c.cartItems d WHERE c.user.id = :buyerId")
    List<Cart> getCartItemsByBuyerId(@Param("buyerId") long buyerId);
    void deleteById(long id);

    @Modifying
    @Query("DELETE FROM CartItem d WHERE d.cart.user.id = :buyerId AND d.product.id = :productId")
    void deleteCartItemByBuyerIdAndProductId(@Param("buyerId") long buyerId, @Param("productId") long productId);

    @Modifying
    @Query("DELETE FROM CartItem d WHERE d.id = :cartItemId")
    void deleteCartItemById(@Param("cartItemId") long cartItemId);

    @Query("select c from Cart c where c.user.id = :userId")
    Optional<Cart> findByUserId(long userId);
}