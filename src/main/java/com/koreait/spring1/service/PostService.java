package com.koreait.spring1.service;

import com.koreait.spring1.dto.PostListDTO;
import com.koreait.spring1.entity.Post;
import com.koreait.spring1.entity.User;
import com.koreait.spring1.mapper.PostMapper;
import com.koreait.spring1.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final UserMapper userMapper;

    public void createPost(String userid, Post post) {
        User user = userMapper.findByUserid(userid);
        post.setWriterId(user.getId());
        postMapper.insertPost(post);
    }

    public List<PostListDTO> getAllPosts() {
        return postMapper.getAllPosts(); // resultType PostListDTO
    }

    public PostListDTO getPostById(int id) {
        return postMapper.getPostById(id);
    }

    public boolean updatePost(String userid, int postId, Post postData) {
        PostListDTO origin = postMapper.getPostById(postId);
        if (origin == null) return false;

        User user = userMapper.findByUserid(userid);
        if (user == null || !user.getUserid().equals(origin.getWriterId())) return false;

        postData.setId(postId);
        postMapper.updatePost(postData);
        return true;
    }

    public boolean deletePost(String userid, int postId) {
        PostListDTO post = postMapper.getPostById(postId);
        if (post == null) return false;

        User user = userMapper.findByUserid(userid);
        if (user == null || !user.getUserid().equals(post.getWriterId())) return false;

        postMapper.deletePost(postId);
        return true;
    }
}
