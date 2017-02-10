package com.mybuckstory.web.spring;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.ProgressListener;

import twitter4j.internal.logging.Logger;

public class FileUploadProgress implements ProgressListener {
	
    public static final String SESSION_KEY = "ProgressListener";
    private static final Logger logger = Logger.getLogger(FileUploadProgress.class);
	private long bytesRead = 0;
    private long contentLength = 0;
    private boolean multipartFinished = false;

    public FileUploadProgress(HttpServletRequest request) {
        request.getSession().setAttribute(SESSION_KEY, this);
    }

    @Override
    public void update(long bytesRead, long contentLength, int items) {
    	logger.debug("Updating - bytes read: " + bytesRead + "; content length: " + contentLength + "; items: " + items);
        this.bytesRead = bytesRead;
        this.contentLength = contentLength;
        logger.debug("Percent complete: " + getPercentDone());
    }

    public void setMultipartFinished() {
    	logger.debug("Multipart isfinished");
        this.multipartFinished = true;
    }

    public boolean isFinished() {
        return multipartFinished;
    }

    public int getPercentDone() {
        if (contentLength == -1) {
            // ensure we never reach 100% but show progress
            return (int) Math.abs(bytesRead * 100.0 / (bytesRead + 10000));
        }
        return (int) Math.abs(bytesRead * 100.0 / contentLength);
    }
}