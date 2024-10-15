package com.mysite.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.board.posts.PostsService;


@SpringBootTest
class BoardApplicationTests {

    @Autowired
    private PostsService postsService;
    @Test
    void testJpa() {        
    	for(int i =1; i<= 300; i++) {
    		String subject = String.format("페이징 구현용 게시물 [%03d]", i);
    		String content = "내용없음";
    		this.postsService.create(subject, content, null);
    	}
    }
}