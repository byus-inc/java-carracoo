package org.carracoo.facebook.models;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/30/13
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Permission      {
	/**
	 * Provides access to the "About Me" section of the profile in the about property
	 */
	USER_ABOUT_ME               (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the "About Me" section of the profile in the about property
	 */
	FRIENDS_ABOUT_ME            (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the user's list of activities as the activities connection
	 */
	USER_ACTIVITIES             (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the user's list of activities as the activities connection
	 */
	FRIENDS_ACTIVITIES          (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the birthday with year as the birthday property. Note that your app may determine if a user is "old enough" to use an app by obtaining the age_range public profile property
	 */
	USER_BIRTHDAY               (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the birthday with year as the birthday property. Note that your app may determine if a user is "old enough" to use an app by obtaining the age_range public profile property
	 */
	FRIENDS_BIRTHDAY            (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides read access to the authorized user's check-ins or a friend's check-ins that the user can see. This permission is superseded by user_status for new applications as of March, 2012.
	 */
	USER_CHECKINS               (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides read access to the authorized user's check-ins or a friend's check-ins that the user can see. This permission is superseded by user_status for new applications as of March, 2012.
	 */
	FRIENDS_CHECKINS            (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to education history as the education property
	 */
	USER_EDUCATION_HISTORY      (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to education history as the education property
	 */
	FRIENDS_EDUCATION_HISTORY   (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the list of events the user is attending as the events connection
	 */
	USER_EVENTS                 (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the list of events the user is attending as the events connection
	 */
	FRIENDS_EVENTS              (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the list of groups the user is a member of as the groups connection
	 */
	USER_GROUPS                 (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the list of groups the user is a member of as the groups connection
	 */
	FRIENDS_GROUPS              (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the user's hometown in the hometown property
	 */
	USER_HOMETOWN               (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the user's hometown in the hometown property
	 */
	FRIENDS_HOMETOWN            (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the user's list of interests as the interests connection
	 */
	USER_INTERESTS              (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the user's list of interests as the interests connection
	 */
	FRIENDS_INTERESTS           (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the list of all of the pages the user has liked as the likes connection
	 */
	USER_LIKES                  (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the list of all of the pages the user has liked as the likes connection
	 */
	FRIENDS_LIKES               (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the user's current city as the location property
	 */
	USER_LOCATION               (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the user's current city as the location property
	 */
	FRIENDS_LOCATION            (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the user's notes as the notes connection
	 */
	USER_NOTES                  (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the user's notes as the notes connection
	 */
	FRIENDS_NOTES               (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the photos the user has uploaded, and photos the user has been tagged in
	 */
	USER_PHOTOS                 (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the photos the user has uploaded, and photos the user has been tagged in
	 */
	FRIENDS_PHOTOS              (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the questions the user or friend has asked
	 */
	USER_QUESTIONS              (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the questions the user or friend has asked
	 */
	FRIENDS_QUESTIONS           (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the user's family and personal relationships and relationship status
	 */
	USER_RELATIONSHIPS          (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the user's family and personal relationships and relationship status
	 */
	FRIENDS_RELATIONSHIPS       (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the user's relationship preferences
	 */
	USER_RELATIONSHIP_DETAILS   (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the user's relationship preferences
	 */
	FRIENDS_RELATIONSHIP_DETAILS(Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the user's religious and political affiliations
	 */
	USER_RELIGION_POLITICS      (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the user's religious and political affiliations
	 */
	FRIENDS_RELIGION_POLITICS   (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the user's status messages and checkins. Please see the documentation for the location_post table for information on how this permission may affect retrieval of information about the locations associated with posts.
	 */
	USER_STATUS                 (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the user's status messages and checkins. Please see the documentation for the location_post table for information on how this permission may affect retrieval of information about the locations associated with posts.
	 */
	FRIENDS_STATUS              (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the user's subscribers and subscribees
	 */
	USER_SUBSCRIPTIONS          (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the user's subscribers and subscribees
	 */
	FRIENDS_SUBSCRIPTIONS       (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the videos the user has uploaded, and videos the user has been tagged in
	 */
	USER_VIDEOS                 (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the videos the user has uploaded, and videos the user has been tagged in
	 */
	FRIENDS_VIDEOS              (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to the user's web site URL
	 */
	USER_WEBSITE                (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to the user's web site URL
	 */
	FRIENDS_WEBSITE             (Type.INFO,     Target.FRIEND,  Action.READ),
	/**
	 * Provides access to work history as the work property
	 */
	USER_WORK_HISTORY           (Type.INFO,     Target.USER,    Action.READ),
	/**
	 * Provides access to work history as the work property
	 */
	FRIENDS_WORK_HISTORY        (Type.INFO,     Target.FRIEND,  Action.READ),

	// OPEN GRAPH
	/**
	 * Allows you to retrieve the actions published by all applications using the built-in music.listens action.
	 */
	USER_ACTIONS_MUSIC          (Type.GRAPH,    Target.USER,    Action.READ),
	/**
	 * Allows you to retrieve the actions published by all applications using the built-in music.listens action.
	 */
	FRIENDS_ACTIONS_MUSIC       (Type.GRAPH,    Target.FRIEND,  Action.READ),
	/**
	 * Allows you to retrieve the actions published by all applications using the built-in news.reads action.
	 */
	USER_ACTIONS_NEWS           (Type.GRAPH,    Target.USER,    Action.READ),
	/**
	 * Allows you to retrieve the actions published by all applications using the built-in news.reads action.
	 */
	FRIENDS_ACTIONS_NEWS        (Type.GRAPH,    Target.FRIEND,  Action.READ),
	/**
	 * Allows you to retrieve the actions published by all applications using the built-in video.watches action.
	 */
	USER_ACTIONS_VIDEO          (Type.GRAPH,    Target.USER,    Action.READ),
	/**
	 * Allows you to retrieve the actions published by all applications using the built-in video.watches action.
	 */
	FRIENDS_ACTIONS_VIDEO       (Type.GRAPH,    Target.FRIEND,  Action.READ),
	/**
	 * Allows you retrieve the actions published by another application as specified by the app namespace. For example, to request the ability to retrieve the actions published by an app which has the namespace awesomeapp, prompt the user for the user_actions:awesomeapp and/or friends_actions:awesomeapp permissions.
	 */
	USER_ACTIONS_APP            (Type.GRAPH,    Target.USER,    Action.READ),
	/**
	 * Allows you retrieve the actions published by another application as specified by the app namespace. For example, to request the ability to retrieve the actions published by an app which has the namespace awesomeapp, prompt the user for the user_actions:awesomeapp and/or friends_actions:awesomeapp permissions.
	 */
	FRIENDS_ACTIONS_APP         (Type.GRAPH,    Target.FRIEND,  Action.READ),
	/**
	 * Allows you post and retrieve game achievement activity.
	 */
	USER_GAMES_ACTIVITY         (Type.GRAPH,    Target.USER,    Action.READ),
	/**
	 * Allows you post and retrieve game achievement activity.
	 */
	FRIENDS_GAMES_ACTIVITY      (Type.GRAPH,    Target.FRIEND,  Action.READ),

	/**
	 * Provides access to the user's primary email address in the email
	 * property. Do not spam users. Your use of email must comply both with
	 * Facebook policies and with the CAN-SPAM Act.
	 */
	EMAIL                       (Type.EMAIL,    Target.USER,    Action.READ),
	/**
	 * Enables your application to retrieve access_tokens for
	 * Pages and Applications that the user administrates.
	 * The access tokens can be queried by calling /<user_id>/accounts
	 * via the Graph API. See here for generating long-lived Page access
	 * tokens that do not expire after 60 days.
	 */
	MANAGE_PAGES                (Type.PAGE,     Target.USER,    Action.READ),

	/**
	 * Provides access to any friend lists the user created. All user's friends are provided as part of basic data, this extended permission grants access to the lists of friends a user has created, and should only be requested if your application utilizes lists of friends.
	 */
	READ_FRIENDLISTS            (Type.EXTENDED, Target.USER,    Action.READ),
	/**
	 * Provides read access to the Insights data for pages, applications, and domains the user owns.
	 */
	READ_INSIGHTS               (Type.EXTENDED, Target.USER,    Action.READ),
	/**
	 * Provides the ability to read from a user's Facebook Inbox.
	 */
	READ_MAILBOX                (Type.EXTENDED, Target.USER,    Action.READ),
	/**
	 * Provides read access to the user's friend requests
	 */
	READ_REQUESTS               (Type.EXTENDED, Target.USER,    Action.READ),
	/**
	 * Provides access to all the posts in the user's News Feed and enables your application to perform searches against the user's News Feed
	 */
	READ_STREAM                 (Type.EXTENDED, Target.USER,    Action.READ),
	/**
	 * Provides applications that integrate with Facebook Chat the ability to log in users.
	 */
	XMPP_LOGIN                  (Type.EXTENDED, Target.USER,    Action.READ),
	/**
	 * Provides access to the user's online/offline presence
	 */
	USER_ONLINE_PRESENCE        (Type.EXTENDED, Target.USER,    Action.READ),
	/**
	 * Provides access to the user's friend's online/offline presence
	 */
	FRIENDS_ONLINE_PRESENCE     (Type.EXTENDED, Target.USER,    Action.READ),

	/**
	 * Provides the ability to manage ads and call the Facebook Ads API on behalf of a user.
	 */
	ADS_MANAGEMENT              (Type.EXTENDED, Target.USER,    Action.PUBLISH),
	/**
	 * Enables your application to create and modify events on the user's behalf
	 */
	CREATE_EVENT                (Type.EXTENDED, Target.USER,    Action.PUBLISH),
	/**
	 * Enables your app to create and edit the user's friend lists.
	 */
	MANAGE_FRIENDLISTS          (Type.EXTENDED, Target.USER,    Action.PUBLISH),
	/**
	 * Enables your app to read notifications and mark them as read. Intended usage: This permission should be used to let users read and act on their notifications; it should not be used to for the purposes of modeling user behavior or data mining. Apps that misuse this permission may be banned from requesting it.
	 */
	MANAGE_NOTIFICATIONS        (Type.EXTENDED, Target.USER,    Action.PUBLISH),
	/**
	 * Enables your app to post content, comments, and likes to a user's stream and to the streams of the user's friends. This requires extra permissions from a person using your app. However, please note that Facebook recommends a user-initiated sharing model. Please read the Platform Policies to ensure you understand how to properly use this permission. Note, you do not need to request the publish_actions permission in order to use the Feed Dialog, the Requests Dialog or the Send Dialog. Facebook used to have a permission called publish_stream, publish_actions replaces it.
	 */
	PUBLISH_ACTIONS             (Type.EXTENDED, Target.USER,    Action.PUBLISH),
	/**
	 * Enables your application to RSVP to events on the user's behalf
	 */
	RSVP_EVENT                  (Type.EXTENDED, Target.USER,    Action.PUBLISH);



	final
	private Type type;
	public  Type type(){
		return type;
	};

	final
	private Target target;
	public  Target target(){
		return target;
	};

	final
	private Action action;
	public  Action action(){
		return action;
	};

	public  String string(String app){
		switch (this){
			case USER_ACTIONS_MUSIC     : return "user_actions.music";
			case USER_ACTIONS_NEWS      : return "user_actions.news";
			case USER_ACTIONS_VIDEO     : return "user_actions.video";
			case USER_ACTIONS_APP       : return "user_actions:"+app;
			case FRIENDS_ACTIONS_MUSIC  : return "friends_actions.music";
			case FRIENDS_ACTIONS_NEWS   : return "friends_actions.news";
			case FRIENDS_ACTIONS_VIDEO  : return "friends_actions.video";
			case FRIENDS_ACTIONS_APP    : return "friends_actions:"+app;
			default                     : return this.name().toLowerCase();
		}
	};

	Permission(Type type, Target target, Action action){
		this.type   = type;
		this.target = target;
		this.action = action;
	}

	public static enum Type {
		INFO,EXTENDED,GRAPH,PAGE,EMAIL
	}

	public static enum Target {
		USER,FRIEND
	}

	public static enum Action {
		READ,PUBLISH
	}

	public static String parse(Permission[] permissions){
		return parse(permissions,null);
	}

	public static String parse(Permission[] permissions, String app){
		String str = "";
		for(Permission permission:permissions){
			str += ","+permission.string(app);
		}
		return str.substring(1);
	}
}
