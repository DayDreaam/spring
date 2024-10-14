package com.mysite.board.posts;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import com.mysite.board.comments.CommentsForm;


@RequestMapping("/posts")
@RequiredArgsConstructor
@Controller
public class PostsController {
	
	private final PostsService postsService;
	
	@GetMapping("/list")
	public String list(Model model) {
		// 서비스를 이용해 DTO 구현
		List<Posts> postsList = this.postsService.getList();
		
		model.addAttribute("postsList", postsList);
		
		return "posts_list";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, CommentsForm commentsForm) {
		Posts posts = this.postsService.getPosts(id);
		model.addAttribute("posts",posts);
		
		return "posts_detail";
	}
	
	@GetMapping("/create")
	public String postCreate(PostsForm postsForm) {
		return "posts_form";
	}
	
	@PostMapping("/create")
	public String postsCreate(@Valid PostsForm postsForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "posts_form";
		}
		this.postsService.create(postsForm.getSubject(),postsForm.getContent());
		return "redirect:/posts/list";
	}
}
