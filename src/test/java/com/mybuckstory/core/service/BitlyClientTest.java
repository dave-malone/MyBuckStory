package com.mybuckstory.core.service;


public class BitlyClientTest extends AbstractSecureContextTest {

	protected BitlyService bitlyService;


	public void testShorten() {
		String fullUrl = "http://www.mybuckstory.com/test/url";
		String shortUrl = bitlyService.shorten(fullUrl);
		assertNotNull(shortUrl);
		assertNotSame(fullUrl, shortUrl);
		logger.debug("Shortened URL: " + shortUrl);
	}

}
