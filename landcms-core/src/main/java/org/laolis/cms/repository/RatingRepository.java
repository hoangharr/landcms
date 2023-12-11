package org.laolis.cms.repository;

import javax.persistence.LockModeType;

import org.laolis.cms.domain.Post;
import org.laolis.cms.domain.Rating;
import org.laolis.cms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public interface RatingRepository extends JpaRepository<Rating, Long> {

	Rating findOneById(Long id);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Rating findOneForUpdateById(Long id);

	Rating findByPostId(Long postId);

	Rating findByAuthorIdAndPostId(Long authorId, Long postId);

	Rating findByAuthorAndPost(User author, Post post);
}
