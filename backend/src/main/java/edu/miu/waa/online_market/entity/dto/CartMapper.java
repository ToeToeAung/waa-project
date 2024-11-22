package edu.miu.waa.online_market.entity.dto;
import edu.miu.waa.online_market.entity.Cart;
import edu.miu.waa.online_market.entity.CartItem;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class CartMapper {
    public CartDto toDTO(Cart cart) {
        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        dto.setCartItems(cart.getCartItems().stream()
                .map(this::toItemDo)
                .collect(Collectors.toList()));
        return dto;
    }

    private CartItemDto toItemDo(CartItem item) {
        CartItemDto dto = new CartItemDto();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setProductId(item.getProduct().getId());
        return dto;
    }
}
