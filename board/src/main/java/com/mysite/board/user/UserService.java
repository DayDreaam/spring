package com.mysite.board.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.board.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public WebUser create(String username, String email, String password) {
		WebUser user = new WebUser();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(user);
		return user;
	}
	
	 public WebUser getUser(String username) {
	        Optional<WebUser> webUser = this.userRepository.findByusername(username);
	        if (webUser.isPresent()) {
	            return webUser.get();
	        } else {
	            throw new DataNotFoundException("siteuser not found");
	        }
	    }
}
