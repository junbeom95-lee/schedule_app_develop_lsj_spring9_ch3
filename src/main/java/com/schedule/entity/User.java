package com.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{   //유저 Entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    //유저 고유 ID
    @Column(nullable = false, length = 50)
    private String username;            //유저명
    @Column(nullable = false, length = 50)
    private String email;               //이메일

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
