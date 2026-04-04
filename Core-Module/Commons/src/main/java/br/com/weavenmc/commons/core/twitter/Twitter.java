package br.com.weavenmc.commons.core.twitter;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
/**
 * Copyright (C) WeavenMC. All Rights Reserved.
 *
 * Unauthorized copying, modification, distribution, or use of this file,
 * via any medium, is strictly prohibited without explicit permission
 * from the copyright holder.
 *
 * Distribution and use for educational purposes are permitted.
 * Commercial use, resale, or any form of monetized distribution
 * is strictly prohibited under GNU License terms and applicable
 * copyright laws.
 *
 * Violators may be subject to legal action.
 */

public class Twitter {

	public static boolean tweet(TwitterAccount account, String tweet) throws TwitterException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(account.getConsumerKey())
				.setOAuthConsumerSecret(account.getConsumerSecret())
                .setOAuthAccessToken(account.getAccessToken())
				.setOAuthAccessTokenSecret(account.getAccessSecret());
		twitter4j.Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		Status status = twitter.updateStatus(tweet);
		return status != null;
	}
}
