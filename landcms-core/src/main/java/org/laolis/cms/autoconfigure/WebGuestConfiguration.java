/*
 * Copyright 2014 Tagbangers, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.laolis.cms.autoconfigure;

import org.laolis.cms.service.BlogService;
import org.laolis.cms.service.PageService;
import org.laolis.cms.web.controller.guest.CategoryController;
import org.laolis.cms.web.controller.guest.FeedController;
import org.laolis.cms.web.controller.guest.IndexController;
import org.laolis.cms.web.controller.guest.SearchController;
import org.laolis.cms.web.controller.guest.TagController;
import org.laolis.cms.web.controller.guest.article.ArticleDescribeController;
import org.laolis.cms.web.controller.guest.article.ArticleIndexController;
import org.laolis.cms.web.controller.guest.comment.CommentRestController;
import org.laolis.cms.web.controller.guest.page.PageDescribeController;
import org.laolis.cms.web.controller.guest.rating.RatingRestController;
import org.laolis.cms.web.controller.guest.user.LoginController;
import org.laolis.cms.web.controller.guest.user.PasswordResetController;
import org.laolis.cms.web.controller.guest.user.PasswordUpdateController;
import org.laolis.cms.web.controller.guest.user.ProfileUpdateController;
import org.laolis.cms.web.controller.guest.user.SignupController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class WebGuestConfiguration extends DelegatingWebMvcConfiguration {

	@Autowired
	private PageDescribeController pageDescribeController;

	@Override
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
		handlerMapping.setOrder(Ordered.LOWEST_PRECEDENCE);
		return handlerMapping;
	}

	@Override
	protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
		RequestMappingHandlerMapping handlerMapping = super.createRequestMappingHandlerMapping();
		handlerMapping.setDefaultHandler(pageDescribeController);
		return handlerMapping;
	}

	@Override
	protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
		return super.createRequestMappingHandlerAdapter();
	}

	@Configuration
	public static class ControllerConfigration {

		@Autowired
		private BlogService blogService;

		@Autowired
		private PageService pageService;

		@Bean
		@ConditionalOnMissingBean
		public PageDescribeController pageDescribeController() {
			return new PageDescribeController(blogService, pageService);
		}

		@Bean
		@ConditionalOnMissingBean
		public ArticleDescribeController articleDescribeController() {
			return new ArticleDescribeController();
		}

		@Bean
		@ConditionalOnMissingBean
		public ArticleIndexController articleIndexController() {
			return new ArticleIndexController();
		}

		@Bean
		@ConditionalOnMissingBean
		public CommentRestController commentRestController() {
			return new CommentRestController();
		}

		@Bean
		@ConditionalOnMissingBean
		public RatingRestController ratingRestController() {
			return new RatingRestController();
		}

		@Bean
		@ConditionalOnMissingBean
		public LoginController loginController() {
			return new LoginController();
		}

		@Bean
		@ConditionalOnMissingBean
		public PasswordResetController passwordResetController() {
			return new PasswordResetController();
		}

		@Bean
		@ConditionalOnMissingBean
		public PasswordUpdateController passwordUpdateController() {
			return new PasswordUpdateController();
		}

		@Bean
		@ConditionalOnMissingBean
		public ProfileUpdateController profileUpdateController() {
			return new ProfileUpdateController();
		}

		@Bean
		@ConditionalOnMissingBean
		public SignupController signupController() {
			return new SignupController();
		}

		@Bean
		@ConditionalOnMissingBean
		public FeedController feedController() {
			return new FeedController();
		}

		@Bean
		@ConditionalOnMissingBean
		public IndexController indexController() {
			return new IndexController();
		}

		@Bean
		@ConditionalOnMissingBean
		public SearchController searchController() {
			return new SearchController();
		}

		@Bean
		@ConditionalOnMissingBean
		public CategoryController categoryController() {
			return new CategoryController();
		}

		@Bean
		@ConditionalOnMissingBean
		public TagController tagController() {
			return new TagController();
		}
	}
}
