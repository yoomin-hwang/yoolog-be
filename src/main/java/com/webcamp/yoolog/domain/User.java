package com.webcamp.yoolog.domain;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter @Setter
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;

    private String email;
    private String nickname;
    private String password;
    private String profileImageUrl;
}