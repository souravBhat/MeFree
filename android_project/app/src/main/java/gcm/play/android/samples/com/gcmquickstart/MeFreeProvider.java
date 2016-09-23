package gcm.play.android.samples.com.gcmquickstart;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

public class MeFreeProvider extends ContentProvider {

    SQLiteDatabase database;
    MyHelper myHelper;
    private static String DATABASE_NAME="MeFree";
    private static int DATABASE_VERSION=1;
    private static String CREATE_FRIENDS="CREATE TABLE IF NOT EXISTS "+MeFreeProviderContract.Friends.TABLE_NAME+" ("+MeFreeProviderContract.Friends._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
    MeFreeProviderContract.Friends.NAME+" TEXT NOT NULL);";
    private static String CREATE_INVITED="CREATE TABLE IF NOT EXISTS "+MeFreeProviderContract.Invited.TABLE_NAME+" ("+MeFreeProviderContract.Invited._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            MeFreeProviderContract.Invited.EVENT_ID+" TEXT NOT NULL, "+MeFreeProviderContract.Invited.NAME+" TEXT NOT NULL, "+
            MeFreeProviderContract.Invited.LOCATION+" TEXT NOT NULL, "+MeFreeProviderContract.Invited.DATE+" TEXT NOT NULL);";

    private static final String CREATE_GOING="CREATE TABLE IF NOT EXISTS "+MeFreeProviderContract.Going.TABLE_NAME+" ("+MeFreeProviderContract.Invited._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            MeFreeProviderContract.Going.EVENT_ID+" TEXT NOT NULL, "+MeFreeProviderContract.Going.NAME+" TEXT NOT NULL, "+
            MeFreeProviderContract.Going.LOCATION+" TEXT NOT NULL, "+MeFreeProviderContract.Going.DATE+" TEXT NOT NULL, "
            +MeFreeProviderContract.Going.HOST+" TEXT NOT NULL);";

    private static final String CREATE_ALL_FRIENDS="CREATE TABLE IF NOT EXISTS "+MeFreeProviderContract.AllFriends.TABLE_NAME+
            " ("+MeFreeProviderContract.AllFriends._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ MeFreeProviderContract.AllFriends.NAME+" TEXT NOT NULL);";


    private static final String CREATE_FRIEND_REQUESTS="CREATE TABLE IF NOT EXISTS "+MeFreeProviderContract.FriendRequests.TABLE_NAME+
            " ("+MeFreeProviderContract.FriendRequests._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ MeFreeProviderContract.FriendRequests.NAME+" TEXT NOT NULL);";

    private static final String DROPTABLE_FRIENDS="DROP TABLE "+MeFreeProviderContract.Friends.TABLE_NAME+" IF EXISTS";
    private static final String DROPTABLE_INVITED="DROP TABLE "+MeFreeProviderContract.Invited.TABLE_NAME+" IF EXISTS";
    private static final String DROPTABLE_GOING="DROP TABLE "+MeFreeProviderContract.Going.TABLE_NAME+" IF EXISTS";
    private static final String DROPTABLE_ALL_FRIENDS="DROP TABLE "+MeFreeProviderContract.AllFriends.TABLE_NAME+" IF EXISTS";
    private static final String DROPTABLE_FRIEND_REQUESTS="DROP TABLE "+MeFreeProviderContract.FriendRequests.TABLE_NAME+" IF EXISTS";
    /////URIMATHCHER

    private static final int FRIENDS=1;
    private static final int FRIENDS_ID=2;
    private static final int INVITED=3;
    private static final int INVITED_ID=4;
    private static final int GOING=5;
    private static final int GOING_ID=6;
    private static final int ALL_FRIENDS=7;
    private static final int ALL_FRIENDS_ID=8;
    private static final int FRIEND_REQUESTS=9;
    private static final int FRIEND_REQUESTS_NAME=10;


    private static final UriMatcher urimatcher;
    static{
        urimatcher=new UriMatcher(UriMatcher.NO_MATCH);
        urimatcher.addURI(MeFreeProviderContract.AUTHORITY,MeFreeProviderContract.Friends.TABLE_NAME,FRIENDS);
        urimatcher.addURI(MeFreeProviderContract.AUTHORITY,MeFreeProviderContract.Friends.TABLE_NAME+"/#",FRIENDS_ID);
        urimatcher.addURI(MeFreeProviderContract.AUTHORITY,MeFreeProviderContract.Invited.TABLE_NAME,INVITED);
        urimatcher.addURI(MeFreeProviderContract.AUTHORITY,MeFreeProviderContract.Invited.TABLE_NAME+"/#",INVITED_ID);
        urimatcher.addURI(MeFreeProviderContract.AUTHORITY,MeFreeProviderContract.Going.TABLE_NAME,GOING);
        urimatcher.addURI(MeFreeProviderContract.AUTHORITY,MeFreeProviderContract.Going.TABLE_NAME+"/#",GOING_ID);
        urimatcher.addURI(MeFreeProviderContract.AUTHORITY,MeFreeProviderContract.AllFriends.TABLE_NAME,ALL_FRIENDS);
        urimatcher.addURI(MeFreeProviderContract.AUTHORITY,MeFreeProviderContract.AllFriends.TABLE_NAME+"/#",ALL_FRIENDS_ID);
        urimatcher.addURI(MeFreeProviderContract.AUTHORITY,MeFreeProviderContract.FriendRequests.TABLE_NAME,FRIEND_REQUESTS);
        urimatcher.addURI(MeFreeProviderContract.AUTHORITY,MeFreeProviderContract.FriendRequests.TABLE_NAME+"/*",FRIEND_REQUESTS);


    }

    public class MyHelper extends SQLiteOpenHelper{

        public MyHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_FRIENDS);
            sqLiteDatabase.execSQL(CREATE_GOING);
            sqLiteDatabase.execSQL(CREATE_INVITED);
            sqLiteDatabase.execSQL(CREATE_ALL_FRIENDS);
            sqLiteDatabase.execSQL(CREATE_FRIEND_REQUESTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROPTABLE_FRIENDS);
            sqLiteDatabase.execSQL(DROPTABLE_GOING);
            sqLiteDatabase.execSQL(DROPTABLE_INVITED);
            sqLiteDatabase.execSQL(DROPTABLE_ALL_FRIENDS);
            sqLiteDatabase.execSQL(DROPTABLE_FRIEND_REQUESTS);

            onCreate(sqLiteDatabase);


        }
    }



    public void addAllUsers(ArrayList<String> userList, boolean createAnew)
    {
        if(createAnew)
        {
            deleteAll();
        }

        String sql="INSERT INTO "+MeFreeProviderContract.AllFriends.TABLE_NAME+" VALUES(?);";
        SQLiteStatement statement=database.compileStatement(sql);
        database.beginTransaction();
        for(int i=0;i<userList.size();i++)
        {
            statement.bindString(2,userList.get(i));
            statement.execute();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    private void deleteAll() {
        database.delete(MeFreeProviderContract.AllFriends.TABLE_NAME, null, null);
    }

    public ArrayList<String> getAllUsers()
    {
        ArrayList<String> userList=new ArrayList<String>();
        Cursor cursor=database.query(MeFreeProviderContract.AllFriends.TABLE_NAME,MeFreeProviderContract.AllFriends.PROJECTION_ALL,null,null,null,null,null);
        if(cursor!=null)
        {
            int index=cursor.getColumnIndex(MeFreeProviderContract.AllFriends.NAME);
            while(cursor.moveToNext())
            {
                userList.add(cursor.getString(index));
            }
        }
        return userList;
    }

    public ArrayList<String> getAllFriendRequests()
    {
        ArrayList<String> userList=new ArrayList<String>();
        Cursor cursor=database.query(MeFreeProviderContract.FriendRequests.TABLE_NAME,MeFreeProviderContract.FriendRequests.PROJECTION_ALL,null,null,null,null,null);
        if(cursor!=null)
        {
            int index=cursor.getColumnIndex(MeFreeProviderContract.AllFriends.NAME);
            while(cursor.moveToNext())
            {
                userList.add(cursor.getString(index));
            }
        }
        return userList;

    }

    public MeFreeProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int number=0;
        database=myHelper.getWritableDatabase();
        String where=null;
        switch (urimatcher.match(uri))
        {
            case FRIENDS:
                number=database.delete(MeFreeProviderContract.Friends.TABLE_NAME,selection,selectionArgs);
                break;
            case FRIENDS_ID:
                where="";
                where=MeFreeProviderContract.Friends._ID+"="+uri.getLastPathSegment().toString();
                selection+=" AND "+where;
                number=database.delete(MeFreeProviderContract.Friends.TABLE_NAME,selection,selectionArgs);
                break;

            case INVITED:
                number=database.delete(MeFreeProviderContract.Invited.TABLE_NAME,selection,selectionArgs);
                break;
            case INVITED_ID:
                where="";
                where=MeFreeProviderContract.Invited._ID+"="+uri.getLastPathSegment().toString();
                selection+=" AND "+where;
                number=database.delete(MeFreeProviderContract.Invited.TABLE_NAME,selection,selectionArgs);
                break;
            case GOING:
                number=database.delete(MeFreeProviderContract.Going.TABLE_NAME,selection,selectionArgs);
                break;
            case GOING_ID:
                where="";
                where=MeFreeProviderContract.Going._ID+"="+uri.getLastPathSegment().toString();
                selection+=" AND "+where;
                number=database.delete(MeFreeProviderContract.Going.TABLE_NAME,selection,selectionArgs);
                break;
            case FRIEND_REQUESTS:
                number=database.delete(MeFreeProviderContract.FriendRequests.TABLE_NAME,selection,selectionArgs);
                break;

            case FRIEND_REQUESTS_NAME:
                where=MeFreeProviderContract.FriendRequests.NAME+"="+uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection))
                {
                    selection=where;
                }
                else
                {
                    selection+=" AND "+MeFreeProviderContract.FriendRequests.NAME+"="+where;
                }
                number=database.delete(MeFreeProviderContract.FriendRequests.TABLE_NAME,selection,selectionArgs);

                break;

        }

        return number;
    }

    @Override
    public String getType(Uri uri) {
        switch (urimatcher.match(uri))
        {
            case FRIENDS:
                return MeFreeProviderContract.Friends.CONTENT_TYPE;
            case FRIENDS_ID:
                return MeFreeProviderContract.Friends.CONTENT_ITEM_TYPE;
            case INVITED:
                return MeFreeProviderContract.Invited.CONTENT_TYPE;
            case INVITED_ID:
                return MeFreeProviderContract.Invited.CONTENT_ITEM_TYPE;
            case GOING:
                return MeFreeProviderContract.Going.CONTENT_TYPE;
            case GOING_ID:
                return MeFreeProviderContract.Going.CONTENT_ITEM_TYPE;
            case FRIEND_REQUESTS:
                return  MeFreeProviderContract.FriendRequests.CONTENT_TYPE;
            case FRIEND_REQUESTS_NAME:
                return MeFreeProviderContract.FriendRequests.CONTENT_ITEM_TYPE;
            default:
                return null;

        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        database=myHelper.getWritableDatabase();
        long rowID=0;
        switch (urimatcher.match(uri))
        {
            case FRIENDS:
                rowID=database.insertWithOnConflict(MeFreeProviderContract.Friends.TABLE_NAME,null, values,SQLiteDatabase.CONFLICT_REPLACE);
                if(rowID>0)
                {
                    uri= ContentUris.withAppendedId(uri, rowID);
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                break;
            case INVITED:
                rowID=database.insertWithOnConflict(MeFreeProviderContract.Invited.TABLE_NAME,null, values,SQLiteDatabase.CONFLICT_REPLACE);
                if(rowID>0)
                {
                    uri= ContentUris.withAppendedId(uri, rowID);
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                break;
            case GOING:
                rowID=database.insertWithOnConflict(MeFreeProviderContract.Going.TABLE_NAME,null, values,SQLiteDatabase.CONFLICT_REPLACE);
                if(rowID>0)
                {
                    uri= ContentUris.withAppendedId(uri, rowID);
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                break;
            case FRIEND_REQUESTS:
                rowID=database.insertWithOnConflict(MeFreeProviderContract.FriendRequests.TABLE_NAME,null, values,SQLiteDatabase.CONFLICT_REPLACE);
                if(rowID>0)
                {
                    uri= ContentUris.withAppendedId(uri, rowID);
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                break;

            case FRIEND_REQUESTS_NAME:
                rowID=database.insertWithOnConflict(MeFreeProviderContract.FriendRequests.TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_REPLACE);
                if(rowID>0)
                {
                    uri= ContentUris.withAppendedId(uri, rowID);
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                break;
            default:
                Log.i("SOURAV","URI NOT FOUND TO INSERT");


        }
        return uri;

    }

    @Override
    public boolean onCreate() {
        Context context=getContext();
        myHelper=new MyHelper(context);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        database=myHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder=new SQLiteQueryBuilder();
        Cursor cursor;
        switch (urimatcher.match(uri))
        {
            case FRIENDS:
                queryBuilder.setTables(MeFreeProviderContract.Friends.TABLE_NAME);
                if(TextUtils.isEmpty(sortOrder))
                    sortOrder=MeFreeProviderContract.Friends.SORT_ORDER_DEFAULT;
                break;
            case INVITED:
                queryBuilder.setTables(MeFreeProviderContract.Invited.TABLE_NAME);
                if(TextUtils.isEmpty(sortOrder))
                    sortOrder=MeFreeProviderContract.Invited.SORT_ORDER_DEFAULT;
                break;
            case GOING:
                queryBuilder.setTables(MeFreeProviderContract.Going.TABLE_NAME);
                if(TextUtils.isEmpty(sortOrder))
                    sortOrder=MeFreeProviderContract.Going.SORT_ORDER_DEFAULT;
                break;
            case FRIEND_REQUESTS:
                queryBuilder.setTables(MeFreeProviderContract.FriendRequests.TABLE_NAME);
                if(TextUtils.isEmpty(sortOrder))
                    sortOrder=MeFreeProviderContract.FriendRequests.SORT_ORDER_DEFAULT;
                break;

            default:
                Log.i("SOURAV","uri not supported");
        }
        cursor=queryBuilder.query(database,projection,selection,selectionArgs,null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;


    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int number=0;
        database=myHelper.getWritableDatabase();
        switch (urimatcher.match(uri))
        {
            case FRIENDS:
                number=database.update(MeFreeProviderContract.Friends.TABLE_NAME,values,selection, selectionArgs);
                break;
            case INVITED:
                number=database.update(MeFreeProviderContract.Invited.TABLE_NAME,values,selection, selectionArgs);
                break;
            case GOING:
                number=database.update(MeFreeProviderContract.Going.TABLE_NAME,values,selection, selectionArgs);
                break;
            case FRIEND_REQUESTS:
                number=database.update(MeFreeProviderContract.FriendRequests.TABLE_NAME,values,selection, selectionArgs);
                break;

            default:
                Log.i("SOURAV", "uri not supported in update");
        }
        return number;
    }
}
