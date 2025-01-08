package com.project.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String login;

    private String password;

    private BigInteger capacity;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
