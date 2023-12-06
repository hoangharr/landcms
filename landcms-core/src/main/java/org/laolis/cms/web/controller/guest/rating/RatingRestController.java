package org.laolis.cms.web.controller.guest.rating;

import org.laolis.cms.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class RatingRestController {

	@Autowired
	private RatingService ratingService;
}
