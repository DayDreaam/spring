package com.mysite.board.comments;

import com.mysite.board.posts.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentsService {
	private final CommentsRepository commentsRepository;
	
	public void create(Posts posts, String content) {
		Comments comments = new Comments();
		comments.setContent(content);
		comments.setCreateDate(LocalDateTime.now());
		comments.setPosts(posts);
		this.commentsRepository.save(comments);
	}
}
