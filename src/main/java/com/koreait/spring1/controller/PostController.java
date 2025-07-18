package com.koreait.spring1.controller;

import com.koreait.spring1.dto.PostListDTO;
import com.koreait.spring1.entity.Post;
import com.koreait.spring1.service.PostService;
import com.koreait.spring1.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtUtil jwtUtil;

    // 🔒 토큰에서 userid 추출 (유효성 검사 포함)
    private String extractUserid(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) return null;

        return jwtUtil.getUseridFromToken(token);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post,
                                        @RequestHeader(value = "Authorization", required = false) String authHeader) {
        String userid = extractUserid(authHeader);
        if (userid == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        postService.createPost(userid, post);
        return ResponseEntity.ok("게시글 등록 완료");
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable int id) {
        PostListDTO post = postService.getPostById(id);

        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable int id,
                                        @RequestBody Post postData,
                                        @RequestHeader(value = "Authorization", required = false) String authHeader) {
        String userid = extractUserid(authHeader);
        if (userid == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        boolean result = postService.updatePost(userid, id, postData);
        if (!result) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정 권한 없음");

        return ResponseEntity.ok("수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id,
                                        @RequestHeader(value = "Authorization", required = false) String authHeader) {
        String userid = extractUserid(authHeader);
        if (userid == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        boolean result = postService.deletePost(userid, id);
        if (!result) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한 없음");

        return ResponseEntity.ok("삭제 완료");
    }
}
