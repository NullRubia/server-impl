package com.koreait.spring1.mapper;

import com.koreait.spring1.dto.PostListDTO;
import com.koreait.spring1.entity.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    void insertPost(Post post);
    List<PostListDTO> getAllPosts();
    PostListDTO getPostById(int id);
    void updatePost(Post post);
    void deletePost(int id);
}