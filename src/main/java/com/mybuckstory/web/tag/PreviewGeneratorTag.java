package com.mybuckstory.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;

import com.mybuckstory.util.PreviewGenerator;

public class PreviewGeneratorTag extends SimpleTagSupport{

	private static final Logger logger = Logger.getLogger(PreviewGeneratorTag.class);
	
	private String content;
	private int length;
	private String continueReadingUrl;
	

	private String getContinueReadingText(){
		String continueReadingText = "...";
		if(continueReadingUrl != null){
			continueReadingText += "<a href=\"" + continueReadingUrl + "\">Continue Reading</a>";
		}
		return continueReadingText;
	}


	public void setContent(String content){
		this.content = content;
	}

	public void setLength(int length){
		this.length = length;
	}

	public void setContinueReadingUrl(String continueReadingUrl){
		this.continueReadingUrl = continueReadingUrl;
	}

	@Override
	public void doTag() throws JspException, IOException{
		logger.debug(content);
		logger.debug("Desired length: " + length);
		logger.debug("Continue reading url: " + continueReadingUrl);
		try{		
			String textInHtmlContent = PreviewGenerator.generatePreview(content, length);
			
			if(textInHtmlContent.length() > length){				
				textInHtmlContent += getContinueReadingText();
			}
			
			getJspContext().getOut().write(textInHtmlContent);
		}catch(ParserException e){
			logger.warn(e, e);			
		}
	}
	
}
