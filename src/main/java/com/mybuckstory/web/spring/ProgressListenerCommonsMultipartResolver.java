package com.mybuckstory.web.spring;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class ProgressListenerCommonsMultipartResolver extends CommonsMultipartResolver{

	private static ThreadLocal<FileUploadProgress> progressListener = new ThreadLocal<FileUploadProgress>();
	
    @Override
    public void cleanupMultipart(MultipartHttpServletRequest request) {
        progressListener.get().setMultipartFinished();
        super.cleanupMultipart(request);
    }

    @Override
    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
        FileUpload fileUpload = super.newFileUpload(fileItemFactory);
        fileUpload.setProgressListener(progressListener.get());
        return fileUpload;
    }

    @Override
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
        progressListener.set(new FileUploadProgress(request));
        return super.resolveMultipart(request);
    }

	
	
}
