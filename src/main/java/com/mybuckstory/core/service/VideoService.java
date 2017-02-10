package com.mybuckstory.core.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mybuckstory.core.event.MemberFeedEvent;
import com.mybuckstory.core.event.VideoEncodingFailedEvent;
import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.MemberFeedItem;
import com.mybuckstory.model.Video;
import com.mybuckstory.model.encodingcom.Format;
import com.mybuckstory.model.encodingcom.Query;
import com.mybuckstory.model.encodingcom.Response;
import com.mybuckstory.model.encodingcom.Result;
import com.mybuckstory.util.JAXBUtil;

@Service
public class VideoService extends GenericMbsService<Video>{

	private static final Logger logger = Logger.getLogger(VideoService.class);
	
	private static final String ENCODING_COM_MANAGE_URL = "http://manage.encoding.com";
	private static final String USER_ID = "9437";
	private static final String API_KEY = "e490075c74b07c59103fd5d5989a2e7b";	
	
	private final ApplicationEventService eventService;
	private final String sftpUrl;
	private final String notifyUrl;

	private final String videoSourceFolder;
	private final String videoDestFolder;
	private final String thumbDestFolder;
	
	public VideoService(){
		this(null, null, null, null, null);
	}
	
	@Autowired
	public VideoService(ApplicationEventService eventService, @Qualifier("videoDao") GenericHibernateDao<Video, Long> dao, @Value("${mbs.sftp.url}") String sftpUrl, @Value("${videos.dir}") String videoFolder, @Value("${mbs.encoding.com.notify.url}") String notifyUrl){
		super(Video.class, dao);
		this.eventService = eventService;
		this.sftpUrl = sftpUrl;
	//  http://whatismyipaddress.com/
		//  http://76.113.165.32:8080/video/encodingNotification
		this.notifyUrl = notifyUrl;
		this.videoSourceFolder = videoFolder + "raw/";
		this.videoDestFolder = videoFolder + "encoded/";
		this.thumbDestFolder = videoFolder + "thumbnail/";
	}

	public static void main(String[] args) throws Exception{	
//		String response = addMedia("FullSwingTip.wmv");
//		System.out.println(response);
//		
//		String destination = SFTP_URL + VIDEO_DEST_FOLDER + "FullSwingTip.wmv";
//		System.out.println(destination);
//		if(destination.indexOf(VIDEO_DEST_FOLDER) != -1){
//			System.out.println("found it!");
//			System.out.println(destination.lastIndexOf("/"));
//			System.out.println(destination.substring(destination.lastIndexOf("/") + 1));
//		}

		
//		String mediaInfo = getMediaInfo("3615091");	
//		System.out.println(mediaInfo);
		
		
		String xml ="<?xml version=\"1.0\"?>\n<response><message>Added</message><MediaID>5212501</MediaID></response>";
		
		Response response = JAXBUtil.fromXML(xml, Response.class);
		System.out.println("response object: " + response);
		System.out.println("MediaID: " + response.getMediaId());
		System.out.println("Message: " + response.getMessage());
		
//		String xml = "<?xml version=\"1.0\"?>\n<result><mediaid>3614581</mediaid><source>sftp://encodingcom:Spring11@standardpar.com:2223/var/www/standardpar.com/static/video/raw/FullSwingTip.wmv</source><status>Finished</status><description></description><format><taskid>12927172</taskid><output>fl9</output><status>Finished</status><destination>sftp://encodingcom:Spring11@standardpar.com:2223/var/www/standardpar.com/static/video/encoded/</destination></format><format><taskid>12927173</taskid><output>thumbnail</output><status>Finished</status><destination>sftp://encodingcom:Spring11@standardpar.com:2223/var/www/standardpar.com/static/video/thumbnail/</destination></format></result>";
		
//		
//		
//		
//		Result result = JAXBUtil.fromXML(xml, Result.class);
//		System.out.println("result object: " + result);
//		String mediaId = result.getMediaId();
//		System.out.println("MediaID: " + mediaId);
//		String status = result.getStatus();
//		System.out.println("Status: " + status);
//		
//		if("Finished".equalsIgnoreCase(status)){
//			System.out.println("finished!!");
//		}
//		String source = result.getSource();
//		
//		String originalFileName = source.substring(source.lastIndexOf("/") + 1);
//		originalFileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
//		System.out.println("original file name: " + originalFileName);		
//		
//		
//		for(Format format : result.getFormats())	{
//			String output = format.getOutput();
//			String taskId = format.getTaskId();
//			
//			if("thumbnail".equalsIgnoreCase(output)){
//				//FullSwingTip_12927173.jpg
//				String previewFileName = originalFileName + "_" + taskId + ".jpg";
//				System.out.println("preview file name: " + previewFileName);
//			}else if("fl9".equalsIgnoreCase(output)){
//				//FullSwingTip_12927172.mp4
//				String encodedFileName = originalFileName + "_" + taskId + ".mp4";
//				System.out.println("encoded file name: " + encodedFileName);
//			}
//		}
	}
	
	
	public String addMedia(final String fileName) throws ProtocolException, IOException, JAXBException {
		logger.debug("addMedia " + fileName);
		
		final Query query = new Query();		
		query.setNotify(this.notifyUrl);
		query.setAction("AddMedia");
		query.setUserId(USER_ID);
		query.setApiKey(API_KEY);
		query.addSource(this.sftpUrl + this.videoSourceFolder + fileName);
		
		final Format video = new Format();
		video.addDestination(this.sftpUrl + this.videoDestFolder);		
		video.setOutput("fl9");
		video.setSize("650x488");
		query.addFormat(video);
		
		final Format thumbnail = new Format();
		thumbnail.addDestination(this.sftpUrl + this.thumbDestFolder);
		thumbnail.setOutput("thumbnail");
		thumbnail.setWidth(650);
		thumbnail.setHeight(488);
		query.addFormat(thumbnail);
		
		return submitQuery(query);
	}

	public String getMediaInfo(final String mediaID) throws ProtocolException, IOException, JAXBException {
		final Query query = new Query();		
		query.setAction("GetMediaInfo");
		query.setUserId(USER_ID);
		query.setApiKey(API_KEY);
		query.setMediaId(mediaID);
		
		return submitQuery(query);		
	}
	
	public String getStatus(final String mediaID) throws ProtocolException, IOException, JAXBException{
		final Query query = new Query();		
		query.setAction("GetStatus");
		query.setUserId(USER_ID);
		query.setApiKey(API_KEY);
		query.setMediaId(mediaID);
		
		return submitQuery(query);
	}

	private String submitQuery(Query query) throws JAXBException, IOException, ProtocolException{
		final String xml = JAXBUtil.toXml(query);
					
		final HttpURLConnection urlConnection = getUrlConnection();
		postXmlRequest(xml, urlConnection, 60000);			
		urlConnection.connect();			

		final String responseMessage = urlConnection.getResponseMessage();
		logger.debug("Response Code:" + urlConnection.getResponseCode());
		logger.debug("Response Message:" + responseMessage);
		
		final String responseXml = getResponseAsString(urlConnection);
		
		logger.debug(responseXml);
		return responseXml;		
	}

	private String getResponseAsString(HttpURLConnection urlConnection) throws IOException {
		final InputStream is = urlConnection.getInputStream();
		final StringBuffer strbuf = new StringBuffer();
		final byte[] buffer = new byte[1024 * 4];
		try {
			int n = 0;
			while (-1 != (n = is.read(buffer))) {
				strbuf.append(new String(buffer, 0, n));
			}			
		} catch (IOException e) {
			logger.error("An error occurred while reading the response from the url connection", e);			
		}finally{
			is.close();
		}
		
		String response = strbuf.toString();
		return response;
	}

	private void postXmlRequest(final String xml, final HttpURLConnection urlConnection, final int connectionTimeoutInMillis) throws IOException {
		final String xmlRequest = "xml=" + URLEncoder.encode(xml, "UTF8");
		urlConnection.setRequestMethod("POST");
		urlConnection.setDoOutput(true);
		urlConnection.setConnectTimeout(connectionTimeoutInMillis);
		urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
		out.write(xmlRequest);
		out.flush();
		out.close();
	}

	private HttpURLConnection getUrlConnection() throws IOException, ProtocolException {
		final URL serverUrl = getEncodingServerUrl();
		logger.debug("Opening Connection to: " + serverUrl);	
		final HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
		return urlConnection;
	}

	private URL getEncodingServerUrl() throws MalformedURLException {
		return new URL(ENCODING_COM_MANAGE_URL);
	}
	
	
	public Video create(MultipartFile mpf) throws IOException{
		if(mpf != null && !mpf.isEmpty() && mpf.getSize() > 0){
			Video video = new Video(mpf.getOriginalFilename(), mpf.getContentType(), mpf.getBytes());		
			create(video);
			return video;
		}
		
		return null;
	}
	
	
    public Long create(Video video){
		logger.debug("saving new video: " + video);
		
		/*
		 * 1. save video to DB
		 * 2. use Video ID, write video to file system using /raw/$ID-$originalFileName
		 * 3. call encoding service to add video to encoding.com job queue, get MediaID from response
		 * 4. set MediaID on video, update video entity in DB  
		 */	
		
		Long id = super.create(video);
		
		if(id != null){
			logger.debug("video entity persisted to DB; saving the video to the file system");
			saveVideoToFS(video);		
			
			try {
				logger.debug("adding media to Encoding.com queue");
				String xml = addMedia(video.getRawFileName());
				logger.debug("encoding.com xml response: " + xml);				
				
				Response response = JAXBUtil.fromXML(xml, Response.class);
				
				String encodingComMediaID = response.getMediaId();
				logger.debug("MediaID: " + encodingComMediaID);
				video.setEncodingComMediaID(encodingComMediaID);
				logger.debug("updating video in the DB with all of the new attributes");
				
				try{
					super.update(video);
					logger.debug("Successfully updated video");
					return video.getId();
				}catch(Exception e){
					logger.error("There was an error updating the video in the db", e);
				}
			} catch (Exception e) {
				logger.error("Failed to encode the video on encoding.com", e);
			}					
		}else{
			logger.error("There was an error persisting the video in the db");
		}
		
		return null;		
    }	
    
    /**
     * <pre>
		Encoding Result XML format
		
		
		If " http(s):// " link is specified in the <notify> part of the <query>, HTTP POST request will be sent to the specified location.
		The POST data will contain one parameter named xml, containing XML of the following format:
		<?xml version="1.0"?>
		<result>
			<mediaid>[MediaID]</mediaid>
			<source>[SourceFile]</source>
			<status>[MediaStatus]</status>
			<description>[ ErrorDescription]</description> <!-- Only in case of Status = Error -->
			<format>
				<output>[OutputFormat]</output>
		
				<destination>[URL]</destination> <!-- Only in case of Status = Finished -->
				<destination_status>[Saved|Error (ErrorDescription)]</destination_status>
				<destination>[URL_2]</destination>
				<destination_status>[Saved|Error (ErrorDescription)]</destination_status>
				...
				<destination>[URL_N]</destination>
				<destination_status>[Saved|Error (ErrorDescription)]</destination_status>
		
				<status>[TaskStatus]</status>
				<description>[ErrorDescription]</description> <!-- Only in case of Status = Error -->
				<suggestion>[ErrorSuggestion]</suggestion> <!-- Only in case of Status = Error -->
			</format>
		</result>
		
		Fields values
	
	
		MediaID - an unique identifier of the media.
		SourceFile - media source file URL
		MediaStatus - could be either Finished or Error
		OutputFormat - format of encoded file, as was requested in the query.
		DestFile, ThumbDest - could be one of the following:
		ftp://[user]:[password]@[server]/[path]/[filename],
		http://[users.bucket].s3.amazonaws.com/[path]/[filename] (the bucket must have WRITE permission for AWS user 1a85ad8fea02b4d948b962948f69972a72da6bed800a7e9ca7d0b43dc61d5869, See http://docs.amazonwebservices.com/AmazonS3/2006-03-01/S3_ACLs.html#S3_ACLs_Grantees for details) , OR
		http://[encoding.bucket].s3.amazonaws.com/[path]/[filename] - if destination was not specified in the query.
		TaskStatus - could be either Finished or Error
		ErrorDescription - the description of the error if status is Error
		ErrorSuggestion - the description of the error if status is Error and we have any suggestion available for this error.
	</pre>
	*/
	public void handleEncodingNotification(String notification) throws Exception{
		logger.debug("Handling encoding notification: " + notification);
		/*
		 * 1. get MediaID from notification
		 * 2. lookup Video by MediaID
		 * 3. If success, set encodedFileName and previewFileName on Video, update video entity in DB
		 * 4. If not success, send email to standardpar admin letting them know something needs to be done
		 */
		
		Result result = JAXBUtil.fromXML(notification, Result.class);
		
		
		
		String mediaId = result.getMediaId();
		String status = result.getStatus();
		 
		logger.debug("MediaID: " + mediaId);
		logger.debug("Status: " + status);
		
		if("Finished".equalsIgnoreCase(status)){
			logger.debug("getting video from db matching MediaID: " + mediaId);
			
			Video video = (Video) getCurrentSession().createCriteria(Video.class)
				.add(Restrictions.eq("encodingComMediaID", mediaId))
				.uniqueResult();

			
			if(video == null){
				eventService.publishAsynch(new VideoEncodingFailedEvent(this, notification, "Encoding.com sent a notification for a MediaID which doesn't match a video in our system: " + mediaId));
				return;
			}
			
			logger.debug("matching video: " + video);

			String source = result.getSource();
		
			String originalFileName = source.substring(source.lastIndexOf("/") + 1);
			originalFileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
			logger.debug("original file name: " + originalFileName);		
			
			for(Format format : result.getFormats())	{
				String output = format.getOutput();
				String taskId = format.getTaskId();
				
				if("thumbnail".equalsIgnoreCase(output)){
					//FullSwingTip_12927173.jpg
					String previewFileName = originalFileName + "_" + taskId + ".jpg";
					video.setPreviewThumbnailFileName(previewFileName);
				}else if("fl9".equalsIgnoreCase(output)){
					//FullSwingTip_12927172.mp4
					String encodedFileName = originalFileName + "_" + taskId + ".mp4";
					video.setEncodedFileName(encodedFileName);
				}
			}
			
			logger.debug("encoded file name: " + video.getEncodedFileName());
			logger.debug("preview thumbnail file name: " + video.getPreviewThumbnailFileName());
			
			try{
				super.update(video);
				logger.debug("successfully updated the video with the encoded file name and the preview thumbnail file name");
				
				try{
					MemberFeedItem feedItem = new MemberFeedItem();
					feedItem.setVideo(video);
					feedItem.setType(MemberFeedItem.Type.VIDEO);
					eventService.publishAsynch(new MemberFeedEvent(this, feedItem));
				}catch(Exception e){
					logger.error("Failed to publish new video feed item event: " + e.getMessage());
				}				
			}catch(Exception e){
				logger.error("failed to update the video with encoded file name and preview thumb file name: ", e);
				eventService.publishAsynch(new VideoEncodingFailedEvent(this, notification, "failed to update the video with encoded file name and preview thumb file name: " + e.getMessage()));
			}
		}else{
			eventService.publishAsynch(new VideoEncodingFailedEvent(this, notification, "Encoding.com sent us a notification that was not in the 'Finished' status"));
		}
		
	}
	
	public void saveVideoToFS(Video video){
		logger.debug("saving video to the file system");
		try{
			File videoDir = new File(this.videoSourceFolder);
			String rawFileName = video.getId() + "-" + video.getOriginalFileName();
			File file = new File(videoDir, rawFileName);
			logger.debug("saving vieo to " + file.getAbsolutePath());
			FileUtils.writeByteArrayToFile(file, video.getBytes());
			video.setRawFileName(rawFileName);
		}catch(Exception e){
			logger.error("an error occurred when attempting to writevideo to file system", e);
		}
	}

	public Video comment(Long id, Comment comment) {
		Video video = getById(id);
		video.getComments().add(comment);
		update(video);
		
		try{
			MemberFeedItem feedItem = new MemberFeedItem();
			feedItem.setVideo(video);
			feedItem.setVideoComment(comment);
			feedItem.setType(MemberFeedItem.Type.VIDEO_COMMENT);
			eventService.publish(new MemberFeedEvent(this, feedItem));
		}catch(Exception e){
			logger.error("Failed to publish video comment feed item event: " + e.getMessage());
		}
		
		return video;
	}

	@Transactional(readOnly=true)
	public Video getFeaturedVideo() {
		Video featured = null;
		
		@SuppressWarnings("unchecked")
		List<Video> videos = getCurrentSession().createCriteria(Video.class)
			.add(Restrictions.isNotNull("encodedFileName"))
			.setFirstResult(0)
			.setMaxResults(1)
			.addOrder(Order.desc("id"))
			.list();
		
		if(!videos.isEmpty()){
			featured = videos.get(0);
		}
		
		return featured;
	}
	
}
