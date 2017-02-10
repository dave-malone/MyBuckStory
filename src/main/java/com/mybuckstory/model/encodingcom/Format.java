package com.mybuckstory.model.encodingcom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Format {	
	
	@XmlElement(name="metadata")
	private MetaData metadata;
	@XmlElement
	private Logo logo;
	
	@XmlElement
	private String output;
	@XmlElement(name="video_codec")
	private String videoCodec;
	@XmlElement(name="audio_codec")
	private String audioCodec;
	
	@XmlElement
	private String bitrate;
	@XmlElement(name="audio_sample_rate")
	private String audioSampleRate;
	@XmlElement(name="audio_volume")
	private String audioVolume;
	@XmlElement
	private String size;
	@XmlElement(name="crop_left")
	private String cropLeft;
	@XmlElement(name="crop_top")
	private String cropTop;
	@XmlElement(name="crop_right")
	private String cropRight;
	@XmlElement(name="crop_bottom")
	private String cropBottom;
	@XmlElement(name="keep_aspect_ration")
	private String keepAspectRatio;
	@XmlElement(name="add_meta")
	private String addMeta;
	@XmlElement
	private String hint;
	@XmlElement(name="rc_init_occupance")
	private String rcInitOccupancy;
	@XmlElement
	private String minrate;
	@XmlElement
	private String maxrate;
	@XmlElement
	private String bufsize;
	@XmlElement
	private String keyframe;
	@XmlElement
	private String start;
	@XmlElement
	private String duration;
	@XmlElement
	private String profile;
	@XmlElement
	private String turbo;
	@XmlElement
	private String rotate;
	
	@XmlElement
	private Integer height;
	@XmlElement
	private Integer width;
	
	@XmlElement
	private String id;
	@XmlElement
	private String status;
	@XmlElement
	private String suggestion;
	@XmlElement
	private String description;
	@XmlElement
	private Date created;
	@XmlElement
	private Date started;
	@XmlElement
	private Date finished;
	@XmlElement(name="s3_destination")
	private String s3Destination;
	@XmlElement(name="cf_destination")
	private String cfDestination;
	
	@XmlElement(name="taskid")
	private String taskId;
	
	@XmlElement(name = "destination_status", required = false, nillable=true, type=String.class)
	private ArrayList<String> destinationStatuses = new ArrayList<String>();
	
	@XmlElement(name = "destination", required = false, nillable=true, type=String.class)
	private ArrayList<String> destinations = new ArrayList<String>();
	
	public Format(){}

	public List<String> getDestinations(){
		return destinations;
	}

	public MetaData getMetadata(){
		return metadata;
	}

	public Logo getLogo(){
		return logo;
	}

	public String getOutput(){
		return output;
	}

	public String getVideoCodec(){
		return videoCodec;
	}

	public String getAudioCodec(){
		return audioCodec;
	}

	public String getBitrate(){
		return bitrate;
	}

	public String getAudioSampleRate(){
		return audioSampleRate;
	}

	public String getAudioVolume(){
		return audioVolume;
	}

	public String getSize(){
		return size;
	}

	public String getCropLeft(){
		return cropLeft;
	}

	public String getCropTop(){
		return cropTop;
	}

	public String getCropRight(){
		return cropRight;
	}

	public String getCropBottom(){
		return cropBottom;
	}

	public String getKeepAspectRatio(){
		return keepAspectRatio;
	}

	public String getAddMeta(){
		return addMeta;
	}

	public String getHint(){
		return hint;
	}

	public String getRcInitOccupancy(){
		return rcInitOccupancy;
	}

	public String getMinrate(){
		return minrate;
	}

	public String getMaxrate(){
		return maxrate;
	}

	public String getBufsize(){
		return bufsize;
	}

	public String getKeyframe(){
		return keyframe;
	}

	public String getStart(){
		return start;
	}

	public String getDuration(){
		return duration;
	}

	public String getProfile(){
		return profile;
	}

	public String getTurbo(){
		return turbo;
	}

	public String getRotate(){
		return rotate;
	}

	public String getId(){
		return id;
	}

	public String getStatus(){
		return status;
	}

	public String getSuggestion(){
		return suggestion;
	}

	public String getDescription(){
		return description;
	}

	public Date getCreated(){
		return created;
	}

	public Date getStarted(){
		return started;
	}

	public Date getFinished(){
		return finished;
	}

	public String getS3Destination(){
		return s3Destination;
	}

	public String getCfDestination(){
		return cfDestination;
	}

	public List<String> getDestinationStatuses(){
		return destinationStatuses;
	}

	public void setDestinations(List<String> destinations){
		this.destinations.addAll(destinations);
	}

	public void setMetadata(MetaData metadata){
		this.metadata = metadata;
	}

	public void setLogo(Logo logo){
		this.logo = logo;
	}

	public void setOutput(String output){
		this.output = output;
	}

	public void setVideoCodec(String videoCodec){
		this.videoCodec = videoCodec;
	}

	public void setAudioCodec(String audioCodec){
		this.audioCodec = audioCodec;
	}

	public void setBitrate(String bitrate){
		this.bitrate = bitrate;
	}

	public void setAudioSampleRate(String audioSampleRate){
		this.audioSampleRate = audioSampleRate;
	}

	public void setAudioVolume(String audioVolume){
		this.audioVolume = audioVolume;
	}

	/**
	 * WxH, where W and N are any even integers.
	 * @param size
	 */
	public void setSize(String size){
		this.size = size;
	}

	public void setCropLeft(String cropLeft){
		this.cropLeft = cropLeft;
	}

	public void setCropTop(String cropTop){
		this.cropTop = cropTop;
	}

	public void setCropRight(String cropRight){
		this.cropRight = cropRight;
	}

	public void setCropBottom(String cropBottom){
		this.cropBottom = cropBottom;
	}

	public void setKeepAspectRatio(String keepAspectRatio){
		this.keepAspectRatio = keepAspectRatio;
	}

	public void setAddMeta(String addMeta){
		this.addMeta = addMeta;
	}

	public void setHint(String hint){
		this.hint = hint;
	}

	public void setRcInitOccupancy(String rcInitOccupancy){
		this.rcInitOccupancy = rcInitOccupancy;
	}

	public void setMinrate(String minrate){
		this.minrate = minrate;
	}

	public void setMaxrate(String maxrate){
		this.maxrate = maxrate;
	}

	public void setBufsize(String bufsize){
		this.bufsize = bufsize;
	}

	public void setKeyframe(String keyframe){
		this.keyframe = keyframe;
	}

	public void setStart(String start){
		this.start = start;
	}

	public void setDuration(String duration){
		this.duration = duration;
	}

	public void setProfile(String profile){
		this.profile = profile;
	}

	public void setTurbo(String turbo){
		this.turbo = turbo;
	}

	public void setRotate(String rotate){
		this.rotate = rotate;
	}

	public void setId(String id){
		this.id = id;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public void setSuggestion(String suggestion){
		this.suggestion = suggestion;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public void setCreated(Date created){
		this.created = created;
	}

	public void setStarted(Date started){
		this.started = started;
	}

	public void setFinished(Date finished){
		this.finished = finished;
	}

	public void setS3Destination(String s3Destination){
		this.s3Destination = s3Destination;
	}

	public void setCfDestination(String cfDestination){
		this.cfDestination = cfDestination;
	}

	public void setDestinationStatuses(List<String> destinationStatuses){
		this.destinationStatuses.addAll(destinationStatuses);
	}
	
	public boolean addDestination(String destination){
		return this.destinations.add(destination);
	}

	public void setWidth(Integer width) {
		this.width = width;		
	}
	
	public void setHeight(Integer height){
		this.height = height;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getWidth() {
		return width;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}	
		
}
