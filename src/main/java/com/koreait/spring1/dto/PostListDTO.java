package com.koreait.spring1.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostListDTO {
    private int id;
    private String title;
    private String content;
    private String writerId;         // 작성자 ID (users.userid)
    private LocalDateTime createdAt; // 작성일
}