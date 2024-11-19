package edu.miu.waa.online_market.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Role role;
    private SellerStatus sellerStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;


    public User(String username, String password, Role role, Address address) {
        this.username = username;
        this.password = password;
        this.role = role;
        if(role != Role.SELLER){
            this.sellerStatus = SellerStatus.NONE;
        }else{
            this.sellerStatus = SellerStatus.PENDING;
        }
        this.address = address;
    }
}
