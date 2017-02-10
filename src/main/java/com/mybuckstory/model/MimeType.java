package com.mybuckstory.model;

import org.apache.commons.lang.StringUtils;

import com.mybuckstory.model.util.StringValuedEnum;

public enum MimeType implements StringValuedEnum{

	DEFAULT_BINARY (new String[]{"application/octet-stream"}, new String[]{""}, false),
	HTML    (new String[]{"text/html"}, new String[]{"html"}, false),	
	JPEG    (new String[]{"image/jpeg", "image/pjpeg"}, new String[]{"jpg", "jpe", "jpeg"}, true),
    GIF     (new String[]{"image/gif"}, new String[]{"gif"}, true),
    PNG     (new String[]{"image/gif", "image/x-png", "image/png"},new String[]{"png"}, true),
    ICON    (new String[]{"image/x-icon"}, new String[]{"ico"}, true),
    SVG		(new String[]{"image/svg+xml"}, new String[]{"svg"}, true),
    TIFF	(new String[]{"image/tiff"}, new String[]{"tiff", "tif"}, true),
    BMP		(new String[]{"image/bmp"}, new String[]{"bmp"}, true),
    EXCEL   (new String[]{"application/vnd.ms-excel"}, new String[]{"xls"}, false),
    WORD    (new String[]{"application/msword"}, new String[]{"doc"}, false),
    POWERPOINT (new String[]{"application/vnd.ms-powerpoint"}, new String[]{"ppt", "pps"}, false),
    MS_PROJECT (new String[]{"application/vnd.ms-project"}, new String[]{"mpp"}, false),
    ZIP     (new String[]{"application/zip"}, new String[]{"zip"}, false),
    TAR     (new String[]{"application/x-tar"}, new String[]{"tar"}, false),
    FLASH   (new String[]{"application/x-shockwave-flash"}, new String[]{"swf"}, false),
    MP3     (new String[]{"audio/mpeg"}, new String[]{"mp3"}, false),
    WAV     (new String[]{"audio/x-wav"}, new String[]{"wav"}, false),
    M3U     (new String[]{"audio/x-mpegurl"}, new String[]{"m3u"}, false),
	PDF		(new String[]{"application/pdf"}, new String[]{"pdf"}, false),
	MPEG    (new String[]{"video/mpeg"}, new String[]{"mpeg", "mp2", "mpa", "mpe", "mpg"}, false),
	QUICKTIME (new String[]{"video/quicktime"}, new String[]{"mov", "qt"}, false),
	AVI 	(new String[]{"video/x-msvideo"}, new String[]{"avi"}, false);
	

    private final String[] contentTypes;
    private final String[] fileExts;
    private final boolean isImage;
    
    MimeType(String[] contentTypes, String[] fileExts, boolean isImage) {
        this.contentTypes = contentTypes;
        this.fileExts = fileExts;
        this.isImage = isImage;
    }
	
    public static MimeType getByContentType(String contentType){
    	for(MimeType mimeType : MimeType.values()){
			for(String ct : mimeType.contentTypes){
				if(ct.equalsIgnoreCase(contentType)){
					return mimeType;
				}
			}    			
    	}
    	
    	return null;
    }
    
    public static MimeType getByFileExt(String fileExt){
    	fileExt = StringUtils.replace(fileExt, ".", "");
    	for(MimeType mimeType : MimeType.values()){
    		for(String fe: mimeType.fileExts){
	    		if(fe.equalsIgnoreCase(fileExt)){
	    			return mimeType;
	    		}
    		}
    	}
    	return null;
    }

	

	public String getValue(){
		return getContentTypes()[0];
	}

	public boolean isImage() {
		return isImage;
	}

	public String[] getContentTypes() {
		return contentTypes;
	}

	public String[] getFileExts() {
		return fileExts;
	}
    
    
}
