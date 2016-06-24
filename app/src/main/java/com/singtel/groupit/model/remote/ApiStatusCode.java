package com.singtel.groupit.model.remote;

public interface ApiStatusCode {
	public static final int STATE_STARTED = 1;
	public static final int STATE_INTERNET_CONNECTED = 2;
	public static final int STATE_INTERNET_NOT_CONNECTED = 3;
	public static final int STATE_CHECKED_VERSION = 4;
	public static final int STATE_CHECKED_VERSION_ERROR = 5;

	public static final int ERROR_CV_INVALID_VERSION = 601;
//	public static final int ERROR_CV_UNSUPPORTED_DEVICE = 603;
	public static final int ERROR_CV_UPGRADE_REQUIRED = 1001; // TODO check
	public static final int ERROR_CV_UPGRADE_AVAILABLE = 1002; // TODO check in
																// status msg
//	public static final int ERROR_CV_INVALID_CLIENT_TYPE = 600;
//	public static final int ERROR_CV_INVALID_DEVICE_MODEL = 602;

	public static final int STATE_FIRST_START = 9;
	public static final int STATE_FIRST_START_ERROR = 90;
	public static final int STATE_REGULAR_START = 10;

	public static final int STATE_REGISTERED_DEVICE = 11;
	public static final int STATE_REGISTERED_DEVICE_ERROR = 110;
	public static final int ERROR_RD_INVALID_DEVICE_SECRET = 600;
	public static final int ERROR_RD_INVALID_DEVICE_ID = 601;
	public static final int ERROR_RD_INVALID_DEVICE_MODEL = 602;

	public static final int STATE_DOWNLOADED_ACCOUNT = 12;
	public static final int STATE_DOWNLOADED_ACCOUNT_ERROR = 120;
	public static final int ERROR_DA_INVALID_PROFILE_DETAIL_MODE = 600;

	public static final int ERROR_SP_INVALID_ACCOUNT = 600;
	public static final int ERROR_INTERNET_NOT_AVAILABLE = 8000;

	public static final int STATE_DOWNLOADED_INITIAL_CONTENTS = 14;
	public static final int STATE_DOWNLOADED_INITIAL_CONTENTS_ERROR = 140;

	public static final int STATE_INSERTED_CONTENTS = 15;
	public static final int STATE_INSERTED_CONTENTS_ERROR = 150;
	
	public static final int STATE_LOADED_ARTICLES = 16;
	public static final int STATE_PARTIALY_LOADED_ARTICLES = 160;

	public static final int STATE_TERMINATE = -1;
	
	public static final int ERROR_STATE_TERMINATE = -1;
	public static final int ERROR_STATE_ = -1;
    public static final int STATE_NO_CONTENT = 204; //when there is no update
    //app full refresh timeout
    public static final int STATE_TIMEOUT_ERROR = 170;
	
	// FROM SERVER
	public static final int STATUS_OK = 200;
	public static final int STATUS_CREATED = 201;
	public static final int STATUS_DEVICE_ALREADY_REGISTERED = 603;
	
	
	
	public static final int STATUS_INVALID_ACCOUNT = 600;
	//invalid account error from server for email signin, will share the same error message
	/**when user enter incorrect password*/
	public static final int STATUS_INVALID_PROFILE = 601;
	/**when user enter incorrect email*/
	public static final int STATUS_USER_ACCOUNT_NOT_EXIST = 614;
	
	public static final int STATUS_USER_ACCOUNT_NOT_VERIFIED = 611;
	public static final int STATUS_USER_ACCOUNT_IS_LOCKED = 613;
	public static final int STATUS_INVALID_SOCIAL_NETWORK_PLATFORM = 603;
	public static final int STATUS_INVALID_USAGE = 604;
	public static final int STATUS_DUPLICATE_SOCIAL_CATEGORY = 605;
	public static final int STATUS_INVALID_SOCIAL_CATEGORY_TOKEN = 606;
	public static final int STATUS_USER_ACCOUNT_ALREADY_REGISTERED = 602;
	public static final int STATUS_SOCIAL_PROFILE_NOT_SAME_AS_SOCIAL_CATEGORY = 607;
	public static final int STATUS_MULTIPLE_SOCIAL_CATEGORY = 609;
	public static final int STATUS_DEFAULT_ERROR = -1;
	
}
