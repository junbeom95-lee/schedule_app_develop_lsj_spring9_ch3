package com.schedule.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity { //일정 Entity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            //일정 고유 ID
    @Column(nullable = false, length = 50)
    private String username;    //작성 유저명
    @Column(nullable = false, length = 100)
    private String title;       //일정 제목
    private String content;     //일정 내용

    public Schedule(String username, String title, String content) {
        this.username = username;
        this.title = title;
        this.content = content;
    }

    /**
     * 일정 수정 메서드
     * @param title 수정할 일정 제목
     * @param content 수정할 일정 내용
     */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
