package com.mybuckstory.web.dwr;

import javax.servlet.http.HttpSession;

import com.mybuckstory.web.spring.FileUploadProgress;

public class FileUploadProgressClient extends AbstractMbsClient{
	
	public FileUploadProgress getProgress() {
        HttpSession session = getWebContext().getSession();
        FileUploadProgress fileUploadProgress = (FileUploadProgress) session.getAttribute(FileUploadProgress.SESSION_KEY);
        
        if(fileUploadProgress != null && fileUploadProgress.isFinished()) {
            session.removeAttribute(FileUploadProgress.SESSION_KEY);
        }
        return fileUploadProgress;
    }

}
