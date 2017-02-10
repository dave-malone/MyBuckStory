package com.mybuckstory.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class UriUtil {

	public static final String NEW_PAGE = "new_page";
	
	public static final DateFormat URI_DATE_FORMAT = new SimpleDateFormat("yyyy/MMMM/dd");
	
	private UriUtil(){
		//enforce non-instantiability
	}
	
	
	/**
	 * A utility method to create a 'clean' uri.  Concatenates prefix, path, and filename
	 * (in that order), while making sure uri path seperators are put in place, and duplicate
	 * seperators are avoided/removed.  It is not necessary to wrap any of the arguments
	 * in path seperators - seperators are logically added to create the appropriate path
	 * as promised by the return contract.  
	 * @param prefix
	 * @param path
	 * @param filename
	 * @return /prefix/path/filename
	 */
	public static String createUri(String prefix, String path, String filename){
		
		StringBuffer buffer = new StringBuffer();
		buffer
			.append(wrapPartialPath(prefix))
			.append(wrapPartialPath(path))
			.append(wrapPartialPath(filename));
		return cleanupUri(buffer.toString());
	}
	
	/**
	 * replaces &quot;new_page&quot; with the newFileName param value
	 * @param uri
	 * @param newFileName
	 * @return uri ending with the new filename
	 */
	public static String replaceNewPageIndicator(String uri, String newFileName){
		if(StringUtils.contains(uri, NEW_PAGE)){
			uri = StringUtils.replaceOnce(uri, NEW_PAGE, wrapPartialPath(newFileName));
		}
		return cleanupUri(uri);
	}
	
	/**
	 * Calls wrapPartialPath, then cleanupUri
	 * @param partialPath
	 * @return
	 */
	public static String wrapAndClean(String partialPath){
		return cleanupUri(wrapPartialPath(partialPath));
	}
	
	/**
	 * Wraps the partial path with a '/' in the front and the back
	 * @param partialPath
	 * @return
	 */
	public static String wrapPartialPath(String partialPath){
		if(StringUtils.isBlank(partialPath)){
			partialPath = "/";
		}
		if(!partialPath.startsWith("/")){
			partialPath = "/" + partialPath;
		}
		
		if(!partialPath.endsWith("/")){
			partialPath += "/";
		}
		
		return partialPath;
	}
	
	public static String removeSpecialChars(String title){
		title = StringUtils.trimToEmpty(title);
		String[] words = title.split(" ");
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < words.length; i++){
			String word = words[i];
			word = word.replaceAll("[\\W]", "");	
			if(StringUtils.isNotBlank(word)){
				buffer.append(word);
				if(i + 1 < words.length){
					buffer.append("-");
				}
			}
		}
		return buffer.toString();
	}
	
	
	/**
	 * 
	 * Makes sure that all backslashes (\) are replaced with frontslashes (/), and any double
	 * frontslashes (//) are replaced with single frontslashes.  Finally, replaces any spaces in 
	 * the uri with dashes ( - )
	 * 
	 * @param uri
	 * @return cleaned up uri
	 */
	public static String cleanupUri(String uri){		
		uri = StringUtils.trimToEmpty(uri);
		
		if(uri.indexOf("\\") != -1){
			uri = uri.replaceAll("\\", "/");
		}
		
		while(uri.indexOf("//") != -1){
			uri = uri.replaceAll("//", "/");
		}
		
		if(StringUtils.contains(uri, " ")){
			uri = StringUtils.replace(uri, " ", "-");
		}
		
		return uri;
		
		
	}

	public static String buildPath(String prefix, Date date, String title) {
		StringBuffer buffer = new StringBuffer();		
		buffer.append("/");
		buffer.append(URI_DATE_FORMAT.format(date));
		buffer.append("/");
		
		return createUri(prefix, buffer.toString(), removeSpecialChars(title));
	}
	
	public static String removeTrailingSlash(String uri){
		if(uri.endsWith("/")){
			uri = uri.substring(0, uri.lastIndexOf("/"));			
		}
		
		return uri;
	}
	
}