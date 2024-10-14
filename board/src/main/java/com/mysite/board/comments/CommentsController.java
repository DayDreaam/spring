package com.mysite.board.comments;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.board.posts.Posts;
import com.mysite.board.posts.PostsService;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@RequestMapping("/comments")
@RequiredArgsConstructor
@Controller
public class CommentsController {
	
	private final PostsService postsService;
	private final CommentsService commentsService;
	
	@PostMapping("/create/{id}")
	public String createComments(Model model, @PathVariable("id") Integer id, @Valid CommentsForm commentsForm, BindingResult bindingResult) {
		Posts posts = this.postsService.getPosts(id);
		if(bindingResult.hasErrors()) {
			model.addAttribute("posts",posts);
			return "posts_detail";
		}
		this.commentsService.create(posts, commentsForm.getContent());
		return String.format("redirect:/posts/detail/%s", id);
	}

}
