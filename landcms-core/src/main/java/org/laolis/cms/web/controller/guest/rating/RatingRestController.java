package org.laolis.cms.web.controller.guest.rating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.laolis.cms.domain.BlogLanguage;
import org.laolis.cms.domain.Post;
import org.laolis.cms.domain.Rating;
import org.laolis.cms.model.RatingCreateRequest;
import org.laolis.cms.model.RatingDeleteRequest;
import org.laolis.cms.model.RatingUpdateRequest;
import org.laolis.cms.service.PostService;
import org.laolis.cms.service.RatingService;
import org.laolis.cms.support.AuthorizedUser;
import org.laolis.cms.web.support.DomainObjectDeletedModel;
import org.laolis.cms.web.support.RestValidationErrorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
public class RatingRestController {

	@Autowired
	private RatingService ratingService;

	@Autowired
	private PostService postService;

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	private static Logger logger = LoggerFactory.getLogger(RatingRestController.class);

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RestValidationErrorModel bindException(BindException e) {
		logger.debug("BindException", e);
		return RestValidationErrorModel.fromBindingResult(e.getBindingResult(), messageSourceAccessor);
	}

// show all user's rated posts at user's profile (consider adding in future)
	@GetMapping
	public String listRatings(Model model) {
		List<Post> posts = postService.getAllPosts();
		model.addAttribute("posts", posts);

		Map<Long, Integer> userRatings = new HashMap<>();
		for (Post post : posts) {
			Integer userRating = ratingService.getUserRatingForPost(post.getId());
			userRatings.put(post.getId(), userRating);
		}
		model.addAttribute("userRatings", userRatings);

		return "/ratings";
	}

	@PostMapping("/rate")
	public String ratePost(@ModelAttribute RatingCreateRequest ratingCreateRequest, AuthorizedUser createdBy) {
		Rating userRating = ratingService.saveUserRating(ratingCreateRequest, createdBy);

		if (userRating != null) {
			postService.updateAverageRating(userRating.getPost().getId());
		}

		return "redirect:/ratings";
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public RatingSavedModel create(@Validated RatingForm form, BindingResult result, BlogLanguage blogLanguage,
			AuthorizedUser authorizedUser) throws BindException {
		if (result.hasErrors()) {
			throw new BindException(result);
		}

		RatingCreateRequest request = form.toRatingCreateRequest(blogLanguage, authorizedUser);
		Rating rating = ratingService.saveUserRating(request, authorizedUser);
		return new RatingSavedModel(rating);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public RatingSavedModel update(@PathVariable long id, @Validated RatingForm form, BindingResult result,
			AuthorizedUser authorizedUser) throws BindException {
		if (result.hasFieldErrors("content")) {
			throw new BindException(result);
		}
		RatingUpdateRequest request = form.toRatingUpdateRequest(id);
		Rating rating = ratingService.updateRating(request, authorizedUser);
		return new RatingSavedModel(rating);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public DomainObjectDeletedModel<Long> delete(@PathVariable long id, AuthorizedUser authorizedUser) {
		RatingDeleteRequest request = new RatingDeleteRequest();
		request.setId(id);
		Rating rating = ratingService.deleteRating(request, authorizedUser);
		return new DomainObjectDeletedModel<>(rating);
	}
}
