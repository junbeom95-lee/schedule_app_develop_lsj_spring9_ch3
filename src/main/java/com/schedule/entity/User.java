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
    //TODO 비밀번호 추가
    //TODO 생성자에도 추가
    //TODO 비밀번호도 update 메서드에 추가 예정

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    /**
     * 유저 수정 하기 위한 메서드
     * @param username 수정할 유저명
     * @param email 수정할 이메일
     */
    public void update(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
