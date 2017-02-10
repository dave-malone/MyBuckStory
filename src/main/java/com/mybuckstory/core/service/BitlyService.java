package com.mybuckstory.core.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mybuckstory.model.bitly.Bitly;
import com.mybuckstory.util.JAXBUtil;

@Service
public class BitlyService {
	
	private static final Logger logger = Logger.getLogger(BitlyService.class);
	private static final String PARAM_VERSION = "version";
	private static final String PARAM_FORMAT = "format";
	private static final String PARAM_LOGIN = "login";
	private static final String PARAM_API_KEY = "apiKey";
	private static final String PARAM_LONG_URL = "longUrl";	
	private static final String version = "2.0.1";
	private static final String format = "xml";
	private static final String shortenApiUrl = "http://api.bit.ly/shorten";
	

	private final String login;
	private final String apiKey;	
	
	public BitlyService(){
		this(null, null);
	}
	
	@Autowired
	public BitlyService(@Value("${bitly.username}") String login, @Value("${bitly.api.key}") String apiKey){
		this.login = login;
		this.apiKey = apiKey;		
	}
	
	public String shorten(String urlToShorten){
		String shortenedUrl = "";
		InputStream is = null;
		try {
			StringBuffer buffer = new StringBuffer(shortenApiUrl)
				.append("?").append(PARAM_VERSION).append("=").append(version)
				.append("&").append(PARAM_API_KEY).append("=").append(apiKey)
				.append("&").append(PARAM_LOGIN).append("=").append(login)
				.append("&").append(PARAM_LONG_URL).append("=").append(urlToShorten);
			
			if(StringUtils.isNotBlank(format)){
				buffer.append("&").append(PARAM_FORMAT).append("=").append(format);
			}
			
			String url = buffer.toString();
			logger.debug("Calling bit.ly shorten API URL: " + url);
			
			URL bitlyUrl = new URL(url);
			
			is = bitlyUrl.openStream();
			
			Bitly bitly = (Bitly)JAXBUtil.parseXML(new BufferedReader(new InputStreamReader(is)), Bitly.class);
			
			shortenedUrl = bitly.getResults().get(0).getShortUrl();			
		} catch (MalformedURLException e) {
			logger.error("Bad bit.ly URL", e);
		}catch(IOException e){
			logger.error("IOException when trying to call openStream bit.ly URL", e);
		}catch(JAXBException e){
			logger.error("JAXB error when reading bit.ly shorten response", e);
		}finally{
			IOUtils.closeQuietly(is);
		}
		
		return shortenedUrl;
	}
	
}
