package com.mysite.board.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Integer>{
	Posts findBySubject(String subject);
}
