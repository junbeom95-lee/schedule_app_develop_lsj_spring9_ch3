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
    private String email;               //이메일
    @Column(nullable = false, length = 50)
    private String username;            //유저명
    @Column(nullable = false, length = 50)
    private String password;            //비밀번호


    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * 유저 수정 하기 위한 메서드
     * @param email 수정할 유저명
     * @param username 수정할 이메일
     * @param password 수정할 비밀번호
     */
    public void update(String email, String username, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
