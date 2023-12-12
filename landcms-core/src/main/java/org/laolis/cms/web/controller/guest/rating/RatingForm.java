package org.laolis.cms.web.controller.guest.rating;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.laolis.cms.domain.BlogLanguage;
import org.laolis.cms.domain.User;
import org.laolis.cms.model.RatingCreateRequest;
import org.laolis.cms.model.RatingUpdateRequest;

public class RatingForm implements Serializable {
	@NotNull
	private Long postId;
	@NotNull
	private int ratingStar;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public int getRatingStar() {
		return ratingStar;
	}

	public void setRatingStar(int ratingStar) {
		this.ratingStar = ratingStar;
	}

	public RatingCreateRequest toRatingCreateRequest(BlogLanguage blogLanguage, User author) {
		RatingCreateRequest request = new RatingCreateRequest();
		request.setBlogLanguage(blogLanguage);
		request.setPostId(getPostId());
		request.setAuthorId(author.getId());
		request.setRatingStar(getRatingStar());
		return request;
	}

	public RatingUpdateRequest toRatingUpdateRequest(long id) {
		RatingUpdateRequest request = new RatingUpdateRequest();
		request.setId(id);
		request.setRatingStar(getRatingStar());
		return request;
	}
}
