package com.mybuckstory.web.command;


public class ProfileSettings {	
	
	private boolean showBirthday;
	private boolean makePublic;
	private boolean disableAllEmailNotifications;
	
	public boolean isShowBirthday() {
		return showBirthday;
	}
	public void setShowBirthday(boolean showBirthday) {
		this.showBirthday = showBirthday;
	}
	public boolean isMakePublic() {
		return makePublic;
	}
	public void setMakePublic(boolean makePublic) {
		this.makePublic = makePublic;
	}
	public boolean isDisableAllEmailNotifications() {
		return disableAllEmailNotifications;
	}
	public void setDisableAllEmailNotifications(boolean disableAllEmailNotifications) {
		this.disableAllEmailNotifications = disableAllEmailNotifications;
	}	
	
}
