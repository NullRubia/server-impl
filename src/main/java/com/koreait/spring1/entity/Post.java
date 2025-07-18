package com.koreait.spring1.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    private int id;
    private String title;
    private String content;
    private int writerId;
    private LocalDateTime createdAt;
}