package edu.miu.waa.online_market.entity;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private Role role;
    private SellerStatus sellerStatus;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public User() {

    }

}