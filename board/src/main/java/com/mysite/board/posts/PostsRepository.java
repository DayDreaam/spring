package com.mysite.board.posts;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostsRepository extends JpaRepository<Posts, Integer>{
	Posts findBySubject(String subject);
	Page<Posts> findAll(Pageable pageable);
}
