package edu.miu.waa.online_market.entity.dto;
import edu.miu.waa.online_market.entity.Address;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private long id;
    private String username;
    private Address address;
}
