package com.mysite.board.comments;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.board.posts.Posts;
import com.mysite.board.posts.PostsService;
import com.mysite.board.user.UserService;
import com.mysite.board.user.WebUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/comments")
@RequiredArgsConstructor
@Controller
public class CommentsController {
	
	private final PostsService postsService;
	private final CommentsService commentsService;
	private final UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createComments(Model model, @PathVariable("id") Integer id, @Valid CommentsForm commentsForm, BindingResult bindingResult, Principal principal) {
		Posts posts = this.postsService.getPosts(id);
		WebUser webUser = this.userService.getUser(principal.getName());
		if(bindingResult.hasErrors()) {
			model.addAttribute("posts",posts);
			return "posts_detail";
		}
		this.commentsService.create(posts, commentsForm.getContent(), webUser);
		return String.format("redirect:/posts/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(CommentsForm answerForm, @PathVariable("id") Integer id, Principal principal) {
        Comments comments = this.commentsService.getComments(id);
        if (!comments.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(comments.getContent());
        return "comments_form";
    }
	
	@PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid CommentsForm commentsForm, BindingResult bindingResult,
            @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "comments_form";
        }
        Comments comments = this.commentsService.getComments(id);
        if (!comments.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.commentsService.modify(comments, commentsForm.getContent());
        return String.format("redirect:/posts/detail/%s", comments.getPosts().getId());
    }
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
        Comments comments = this.commentsService.getComments(id);
        if (!comments.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.commentsService.delete(comments);
        return String.format("redirect:/posts/detail/%s", comments.getPosts().getId());
    }

}
