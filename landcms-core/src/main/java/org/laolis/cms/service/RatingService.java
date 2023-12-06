package org.laolis.cms.service;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.laolis.cms.domain.Comment;
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

	public Rating createRating(RatingCreateRequest request, AuthorizedUser createdBy) {
		Post post = postRepository.findOneByIdAndLanguage(request.getPostId(), request.getBlogLanguage().getLanguage());
		if (post == null) {
			throw new ServiceException("Post was not found [" + request.getPostId() + "]");
		}

		User user = userRepository.findOneById(request.getUserId());

		LocalDateTime now = LocalDateTime.now();
		Comment comment = new Comment();
		Rating rating = new Rating();
		rating.setPost(post);
		rating.setUser(user);

		comment.setCreatedAt(now);
		comment.setCreatedBy(createdBy.toString());
		comment.setUpdatedAt(now);
		comment.setUpdatedBy(createdBy.toString());

		return ratingRepository.saveAndFlush(rating);
	}

	public Rating updateRating(RatingUpdateRequest request, AuthorizedUser updatedBy) {
		Rating rating = ratingRepository.findOneById(request.getId());
		if (rating == null) {
			throw new ServiceException();
		}
		if (!updatedBy.getRoles().contains(User.Role.ADMIN)
				&& !ObjectUtils.nullSafeEquals(rating.getUser(), updatedBy)) {
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
				&& !ObjectUtils.nullSafeEquals(rating.getUser(), deletedBy)) {
			throw new ServiceException();
		}
		ratingRepository.delete(rating);
		return rating;
	}
}
