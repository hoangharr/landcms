package org.laolis.cms.web.controller.admin.rating;

import java.util.List;

import org.laolis.cms.domain.Rating;
import org.laolis.cms.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/{language}/ratings/index")
public class RatingIndexController {
	private final RatingService ratingService;

	@Autowired
	public RatingIndexController(RatingService ratingService) {
		this.ratingService = ratingService;
	}

	@RequestMapping
	public String listRatings(@PathVariable String language, Model model) {
		List<Rating> ratings = ratingService.getAllRatings();
		model.addAttribute("ratings", ratings);
		return "rating/index";
	}
}
