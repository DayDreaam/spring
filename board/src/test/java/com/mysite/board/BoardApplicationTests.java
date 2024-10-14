package com.mysite.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.board.comments.Comments;
import com.mysite.board.comments.CommentsRepository;
import com.mysite.board.posts.Posts;
import com.mysite.board.posts.PostsRepository;


@SpringBootTest
class BoardApplicationTests {

    @Autowired
    private PostsRepository postsRepository;
    
    @Autowired
    private CommentsRepository commentsRepository;

    @Test
    void testJpa() {        
    	Optional<Posts> op = this.postsRepository.findById(2);
    	assertTrue(op.isPresent());
    	Posts p = op.get();
    	Comments c = new Comments();
    	c.setContent("네 자동으로 생성됩니다.");
    	c.setPosts(p);
    	c.setCreateDate(LocalDateTime.now());
    	this.commentsRepository.save(c);
    }
}