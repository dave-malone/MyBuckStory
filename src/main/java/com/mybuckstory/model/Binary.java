package com.mybuckstory.model;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

public abstract class Binary extends AbstractAuditable{

	private byte[] content;	
	protected MimeType mimeType;
	protected MultipartFile file;
	
	public byte[] getContent() {
		return this.content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}

	
	public byte[] getBytes() {
		return content;
	}
	
	public void readFrom(File file) throws IOException {
		FileInputStream fis = null;
		
		try{
			fis = new FileInputStream(file);
			setContent(IOUtils.toByteArray(fis));
		}finally{
			if(fis != null){
				IOUtils.closeQuietly(fis);
			}
		}
	}
	
	public void writeTo(File file){
		OutputStream os = null;
		try{
			if(this.content != null){
				os = new FileOutputStream(file);
				os.write(getBytes());		
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					//close quietly
				}
			}
		}
	}
	
	public void writeTo(OutputStream os){
		if(this.content != null){
			BufferedOutputStream bos = (os instanceof BufferedOutputStream ? (BufferedOutputStream)os : new BufferedOutputStream(os));
			try{
				bos.write(getBytes());
			}catch(Exception e){
				throw new RuntimeException(e);
			}finally{
				if(bos != null){
					try {
						bos.close();
					} catch (IOException e) {
						//close quietly
					}
				}
			}
		}
		
	}
	
	public InputStream getInputStream(){
		if(this.content != null){
			return new ByteArrayInputStream(this.content);
		}
		
		return null;		
	}
	public MimeType getMimeType() {
		return mimeType;
	}
	public void setMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public boolean containsNewlyUploadedImage() throws IOException{
		return (getFile() != null && getFile().getBytes() != null && getFile().getBytes().length > 0);		
	}
	

}
