package org.laolis.cms.web.controller.guest.rating;

import org.laolis.cms.domain.Rating;
import org.laolis.cms.web.support.DomainObjectSavedModel;

public class RatingSavedModel extends DomainObjectSavedModel<Long> {

	private int ratingStar;

	public RatingSavedModel(Rating rating) {
		super(rating);
		ratingStar = rating.getRatingStar();
	}

	public int getRatingStar() {
		return ratingStar;
	}

}
