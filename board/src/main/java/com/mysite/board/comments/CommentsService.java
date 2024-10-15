package com.mysite.board.comments;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mysite.board.posts.Posts;
import com.mysite.board.user.WebUser;

import lombok.RequiredArgsConstructor;
import java.util.Optional;
import com.mysite.board.DataNotFoundException;

@RequiredArgsConstructor
@Service
public class CommentsService {
	private final CommentsRepository commentsRepository;
	
	public void create(Posts posts, String content, WebUser author) {
		Comments comments = new Comments();
		comments.setContent(content);
		comments.setCreateDate(LocalDateTime.now());
		comments.setPosts(posts);
		comments.setAuthor(author);
		this.commentsRepository.save(comments);
	}
	
	public Comments getComments(Integer id) {
        Optional<Comments> answer = this.commentsRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Comments comments, String content) {
        comments.setContent(content);
        comments.setModifyDate(LocalDateTime.now());
        this.commentsRepository.save(comments);
    }
    
    public void delete(Comments comments) {
    	this.commentsRepository.delete(comments);
    }
}
