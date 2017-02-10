package com.mybuckstory.util;

import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;

public class PreviewGenerator {

	private static final Logger logger = Logger.getLogger(PreviewGenerator.class);
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	private PreviewGenerator(){}
	
	public static String generatePreview(String content, int length) throws ParserException{
		Parser parser = Parser.createParser(content, null);
		TextExtractingVisitor visitor = new TextExtractingVisitor(); 
		
		parser.visitAllNodesWith(visitor);
		 
		String textInHtmlContent = visitor.getExtractedText();
		logger.debug("Text in content: " + textInHtmlContent);
		if(textInHtmlContent.length() > length){
			textInHtmlContent = textInHtmlContent.substring(0, length);			
		}
		logger.debug("Contains line breaks? " + (textInHtmlContent.indexOf(LINE_SEPARATOR) != -1));
		textInHtmlContent = textInHtmlContent.replaceAll(LINE_SEPARATOR, "<br />");
		
		while(textInHtmlContent.indexOf("<br /><br />") != -1){
			textInHtmlContent = textInHtmlContent.replaceAll("<br /><br />", "<br />");
		}
		
		return textInHtmlContent;
	}
	
}
