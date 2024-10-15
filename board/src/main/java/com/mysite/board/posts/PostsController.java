package com.mysite.board.posts;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.board.comments.CommentsForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.security.Principal;
import com.mysite.board.user.WebUser;
import com.mysite.board.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/posts")
@RequiredArgsConstructor
@Controller
public class PostsController {
	
	private final PostsService postsService;
	private final UserService userService;
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<Posts> paging = this.postsService.getList(page);
		model.addAttribute("paging", paging);

		return "posts_list";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, CommentsForm commentsForm) {
		Posts posts = this.postsService.getPosts(id);
		model.addAttribute("posts",posts);
		
		return "posts_detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String postCreate(PostsForm postsForm) {
		return "posts_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String postsCreate(@Valid PostsForm postsForm, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "posts_form";
		}
		WebUser webUser = this.userService.getUser(principal.getName());
		this.postsService.create(postsForm.getSubject(),postsForm.getContent(), webUser);
		return "redirect:/posts/list";
	}
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(PostsForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Posts posts = this.postsService.getPosts(id);
        if(!posts.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(posts.getSubject());
        questionForm.setContent(posts.getContent());
        return "posts_form";
    }
	
	@PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid PostsForm postsForm, BindingResult bindingResult, 
            Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "posts_form";
        }
        Posts question = this.postsService.getPosts(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.postsService.modify(question, postsForm.getSubject(), postsForm.getContent());
        return String.format("redirect:/posts/detail/%s", id);
    }
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Posts posts = this.postsService.getPosts(id);
        if (!posts.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.postsService.delete(posts);
        return "redirect:/";
    }
}
