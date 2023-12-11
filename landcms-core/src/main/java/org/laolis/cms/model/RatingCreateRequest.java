package org.laolis.cms.model;

import java.io.Serializable;

import org.laolis.cms.domain.BlogLanguage;

public class RatingCreateRequest implements Serializable {

	private BlogLanguage blogLanguage;
	private Long postId;
	private Long authorId;
	private int ratingStar;

	public BlogLanguage getBlogLanguage() {
		return blogLanguage;
	}

	public void setBlogLanguage(BlogLanguage blogLanguage) {
		this.blogLanguage = blogLanguage;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public int getRatingStar() {
		return ratingStar;
	}

	public void setRatingStar(int ratingStar) {
		this.ratingStar = ratingStar;
	}

}
