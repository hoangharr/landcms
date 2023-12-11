package org.laolis.cms.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.laolis.cms.domain.Post;
import org.laolis.cms.domain.Rating;
import org.laolis.cms.domain.User;
import org.laolis.cms.exception.ServiceException;
import org.laolis.cms.model.RatingCreateRequest;
import org.laolis.cms.model.RatingDeleteRequest;
import org.laolis.cms.model.RatingUpdateRequest;
import org.laolis.cms.repository.PostRepository;
import org.laolis.cms.repository.RatingRepository;
import org.laolis.cms.repository.UserRepository;
import org.laolis.cms.support.AuthorizedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class RatingService {
	@Resource
	private RatingRepository ratingRepository;
	@Resource
	private PostRepository postRepository;
	@Resource
	private UserRepository userRepository;

	private static Logger logger = LoggerFactory.getLogger(RatingService.class);

	public Integer getUserRatingForPost(Long postId) {
		Rating rating = ratingRepository.findByPostId(postId);

		return (rating != null) ? rating.getRatingStar() : null;
	}

	public Rating saveUserRating(RatingCreateRequest request, AuthorizedUser createdBy) {
		if (request == null || request.getPostId() == null) {
			throw new ServiceException("Invalid request: Missing postId");
		}

		Post post = postRepository.findOneById(request.getPostId());
		if (post == null) {
			throw new ServiceException("Post not found");
		}

		User author = userRepository.findOneById(request.getAuthorId());

		if (author == null || request.getAuthorId() == null) {
			throw new ServiceException("User not found");
		}

		Rating existingRating = ratingRepository.findByAuthorAndPost(author, post);

		LocalDateTime now = LocalDateTime.now();
		Rating rating;

		if (existingRating != null) {
			rating = existingRating;
			rating.setRatingStar(request.getRatingStar());
			rating.setUpdatedAt(now);
			rating.setUpdatedBy(createdBy.toString());
		} else {
			rating = new Rating();
			rating.setPost(post);
			rating.setAuthor(author);
			rating.setRatingStar(request.getRatingStar());
			rating.setCreatedAt(now);
			rating.setCreatedBy(createdBy.toString());
			rating.setUpdatedAt(now);
			rating.setUpdatedBy(createdBy.toString());
		}
		return ratingRepository.saveAndFlush(rating);
	}

	public Rating updateRating(RatingUpdateRequest request, AuthorizedUser updatedBy) {
		Rating rating = ratingRepository.findOneById(request.getId());
		if (rating == null) {
			throw new ServiceException();
		}
		if (!updatedBy.getRoles().contains(User.Role.ADMIN)
				&& !ObjectUtils.nullSafeEquals(rating.getAuthor(), updatedBy)) {
			throw new ServiceException();
		}

		LocalDateTime now = LocalDateTime.now();
		rating.setRatingStar(request.getRatingStar());
		rating.setUpdatedAt(now);
		rating.setUpdatedBy(updatedBy.toString());
		return ratingRepository.saveAndFlush(rating);
	}

	public Rating deleteRating(RatingDeleteRequest request, AuthorizedUser deletedBy) {
		Rating rating = ratingRepository.findOneById(request.getId());
		if (rating == null) {
			throw new ServiceException();
		}
		if (!deletedBy.getRoles().contains(User.Role.ADMIN)
				&& !ObjectUtils.nullSafeEquals(rating.getAuthor(), deletedBy)) {
			throw new ServiceException();
		}
		ratingRepository.delete(rating);
		return rating;
	}

	public List<Rating> getAllRatings() {
		return ratingRepository.findAll();
	}
}
