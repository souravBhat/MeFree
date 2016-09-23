package gcm.play.android.samples.com.gcmquickstart;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by ASUS on 20/11/2015.
 */
public abstract class MeFreeProviderContract {
    public static final String AUTHORITY = "gcm.play.android.samples.com.gcmquickstart" ;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    public static final class Friends
    {
        public static final String _ID = "_id";
        public static final String NAME = "name";
        public static final String TABLE_NAME="friends";
        public static final String [] PROJECTION_ALL={_ID, NAME};

        public static final String CONTENT_TYPE= ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.sourav.contentproviderex.friends";
        public static final String CONTENT_ITEM_TYPE=ContentResolver.CURSOR_ITEM_BASE_TYPE+
                "/vnd.com.sourav.contentproviderex.friends";

        public final static Uri CONTENT_URI= Uri.withAppendedPath(MeFreeProviderContract.CONTENT_URI, TABLE_NAME);

        public static final String SORT_ORDER_DEFAULT=NAME+" ASC";

    }

    public static final class Invited
    {
        public static final String _ID = "_id";
        public static final String EVENT_ID="eventid";
        public static final String NAME = "name";
        public static final String LOCATION="location";
        public static final String DATE="date";
        public static final String TABLE_NAME="invited";
        public static final String [] PROJECTION_ALL={_ID,EVENT_ID, NAME,LOCATION,DATE};

        public static final String CONTENT_TYPE= ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.sourav.contentproviderex.invited";
        public static final String CONTENT_ITEM_TYPE=ContentResolver.CURSOR_ITEM_BASE_TYPE+
                "/vnd.com.sourav.contentproviderex.invited";

        public final static Uri CONTENT_URI= Uri.withAppendedPath(MeFreeProviderContract.CONTENT_URI, TABLE_NAME);

        public static final String SORT_ORDER_DEFAULT=NAME+" ASC";

    }

    public static final class Going
    {
        public static final String _ID = "_id";
        public static final String EVENT_ID="eventid";
        public static final String NAME = "name";
        public static final String LOCATION="location";
        public static final String DATE="date";
        public static final String HOST="host";
        public static final String TABLE_NAME="going";
        public static final String [] PROJECTION_ALL={_ID,EVENT_ID, NAME,LOCATION,DATE,HOST};

        public static final String CONTENT_TYPE= ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.sourav.contentproviderex.going";
        public static final String CONTENT_ITEM_TYPE=ContentResolver.CURSOR_ITEM_BASE_TYPE+
                "/vnd.com.sourav.contentproviderex.going";

        public final static Uri CONTENT_URI= Uri.withAppendedPath(MeFreeProviderContract.CONTENT_URI, TABLE_NAME);

        public static final String SORT_ORDER_DEFAULT=NAME+" ASC";

    }

    public static final class AllFriends
    {
        public static final String _ID = "_id";
        public static final String NAME = "name";
        public static final String TABLE_NAME="allFriends";
        public static final String [] PROJECTION_ALL={_ID, NAME};

        public static final String CONTENT_TYPE= ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.sourav.contentproviderex.allFriends";
        public static final String CONTENT_ITEM_TYPE=ContentResolver.CURSOR_ITEM_BASE_TYPE+
                "/vnd.com.sourav.contentproviderex.allFriends";

        public final static Uri CONTENT_URI= Uri.withAppendedPath(MeFreeProviderContract.CONTENT_URI, TABLE_NAME);

        public static final String SORT_ORDER_DEFAULT=NAME+" ASC";

    }

    public static final class FriendRequests
    {
        public static final String _ID = "_id";
        public static final String NAME = "name";
        public static final String TABLE_NAME="friendRequests";
        public static final String [] PROJECTION_ALL={_ID, NAME};

        public static final String CONTENT_TYPE= ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.sourav.contentproviderex.friendrequest";
        public static final String CONTENT_ITEM_TYPE=ContentResolver.CURSOR_ITEM_BASE_TYPE+
                "/vnd.com.sourav.contentproviderex.friendRequest";

        public final static Uri CONTENT_URI= Uri.withAppendedPath(MeFreeProviderContract.CONTENT_URI, TABLE_NAME);

        public static final String SORT_ORDER_DEFAULT=NAME+" ASC";

    }
}

