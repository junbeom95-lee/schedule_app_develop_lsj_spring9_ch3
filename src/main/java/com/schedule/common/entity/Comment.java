package com.schedule.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            //댓글 고유 ID

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;          //유저

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;  //일정

    @Column(nullable = false)
    private String content;     //댓글 내용

    public Comment(User user, Schedule schedule, String content) {
        this.user = user;
        this.schedule = schedule;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
