package com.mysite.board.posts;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import com.mysite.board.DataNotFoundException;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import com.mysite.board.user.WebUser;

@RequiredArgsConstructor
@Service
public class PostsService {
	private final PostsRepository postsRepository;
	
	// 페이징 이전 함수
//	public List<Posts> getList(){
//		return this.postsRepository.findAll();
//	}
	
	public Posts getPosts(Integer id) {
		Optional<Posts> posts = this.postsRepository.findById(id);
		if(posts.isPresent()) {
			return posts.get();
		}else {
			throw new DataNotFoundException("posts not found");
		}
	}
	
	public void create(String subject, String content, WebUser webUser) {
		Posts posts = new Posts();
		posts.setContent(content);
		posts.setSubject(subject);
		posts.setCreateDate(LocalDateTime.now());
		posts.setAuthor(webUser);
		this.postsRepository.save(posts);
	}
	
	public Page<Posts> getList(int page){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.postsRepository.findAll(pageable);
	}
	
	public void modify(Posts posts, String subject, String content) {
        posts.setSubject(subject);
        posts.setContent(content);
        posts.setModifyDate(LocalDateTime.now());
        this.postsRepository.save(posts);
    }
	
	public void delete(Posts posts) {
        this.postsRepository.delete(posts);
    }
}
