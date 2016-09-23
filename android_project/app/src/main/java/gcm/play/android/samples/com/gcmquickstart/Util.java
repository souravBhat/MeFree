package gcm.play.android.samples.com.gcmquickstart;

import android.content.SharedPreferences;

/**
 * Created by ASUS on 10/11/2015.
 */
public class Util {
    public static final String EXTRA_MESSAGE="message";
    public static final String PROPERTY_REG_ID="registration_id";
    public static final String PROPERTY_APP_VERSION="appVersion";
    public static final String EMAIL="email";
    public static final String USER_NAME="user_name";
    public static final String USER_LOGGEDIN="userLoggedIn";


    public static final int PLAY_SERVICES_RESOLUTION_REQUEST=9000;
    public static final String SENDER_ID="957968065393";
    public static final String base_url="http://124.244.194.249//gcm_demo/";
    public final static String register_url=base_url+"register.php";
    public static final String send_chat_url=base_url+"sendChatmessage.php";
    public static final String getAllUsersUrl=base_url+"searchUser.php";
    public static final String FRIEND_REQUEST=base_url+"friendRequest.php";
    public static final String ACCEPT_FRIEND_REQUEST=base_url+"acceptFriend.php";

    public static final String EVENT_DESCIPTION_URL=base_url+"showDescription.php";/////////add this
    public static final String GET_GOING_LIST_URL=base_url+"showParticipants.php";/////ask braden
    public static final String CREATE_EVENTS_URL=base_url+"newEvent.php";///ask braden
    public static final String INVITE_FRIENDS_TO_EVENTS=base_url+"eventInvite.php";////ask braden
    public static final String ACCEPT_EVENT_INVITE=base_url+"acceptEvent.php";





    //////unique event_ids
    public static final String EVENT_IDS="eventIds";
    public static final String DESCRIPTION_TYPE="descriptiontype";



}
