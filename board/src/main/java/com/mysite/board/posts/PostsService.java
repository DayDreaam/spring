package com.mysite.board.posts;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import com.mysite.board.DataNotFoundException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PostsService {
	private final PostsRepository postsRepository;
	
	public List<Posts> getList(){
		return this.postsRepository.findAll();
	}
	
	public Posts getPosts(Integer id) {
		Optional<Posts> posts = this.postsRepository.findById(id);
		if(posts.isPresent()) {
			return posts.get();
		}else {
			throw new DataNotFoundException("posts not found");
		}
	}
	
	public void create(String subject, String content) {
		Posts posts = new Posts();
		posts.setContent(content);
		posts.setSubject(subject);
		posts.setCreateDate(LocalDateTime.now());
		this.postsRepository.save(posts);
	}
	
}
