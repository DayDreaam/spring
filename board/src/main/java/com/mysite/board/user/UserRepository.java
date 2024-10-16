package com.mysite.board.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<WebUser, Long>{
	Optional<WebUser> findByusername(String username);
}
