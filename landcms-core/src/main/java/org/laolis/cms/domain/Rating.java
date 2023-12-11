package org.laolis.cms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;

@Entity
@Table(name = "rating")
@DynamicInsert
@DynamicUpdate
public class Rating extends DomainObject<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Field(name = "ratingId")
	@SortableField(forField = "ratingId")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "post_id")
	@IndexedEmbedded(includeEmbeddedObjectId = true)
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "author_id")
	@IndexedEmbedded(includeEmbeddedObjectId = true)
	private User author;

	@Column(name = "rating_star", nullable = false)
	private int ratingStar;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public int getRatingStar() {
		return ratingStar;
	}

	public void setRatingStar(int ratingStar) {
		this.ratingStar = ratingStar;
	}

	@Override
	public String print() {
		return this.getClass().getName() + " " + getId();
	}

}
