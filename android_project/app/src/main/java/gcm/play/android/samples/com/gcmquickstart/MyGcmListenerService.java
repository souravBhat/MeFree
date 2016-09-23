/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gcm.play.android.samples.com.gcmquickstart;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    private Context context;

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String senderName=data.getString("senderName");
        String type=data.getString("type");

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(data);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     *
     */
    private void sendNotification(Bundle data) {


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        context=getApplicationContext();

        ////get contentvalues and contentresolver
        ContentValues values=new ContentValues();
        ContentResolver resolver=context.getContentResolver();
        /////

        String message = data.getString("message");
        String senderName=data.getString("senderName");
        String type=data.getString("type");
        switch (type)
        {
            case "friendrequest":
                Intent intent = new Intent(this, FriendRequestReceived.class);
                intent.putExtra("message",message);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notificationicon)
                        .setContentTitle("Friend Request!")
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                /////handle database

                values.put(MeFreeProviderContract.FriendRequests.NAME,senderName);
                resolver.insert(MeFreeProviderContract.FriendRequests.CONTENT_URI,values);

                ///create notification

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());


                break;
            case "eventInvite":
                Intent intent2 = new Intent(this, New_Invites.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0 /* Request code */, intent2,
                        PendingIntent.FLAG_ONE_SHOT);

                ////get the other set of data
                String eventid=data.getString("eventid");
                String eventname=data.getString("eventname");
                String eventtime=data.getString("eventtime");
                String eventlocation=data.getString("eventlocation");


                NotificationCompat.Builder notificationBuilder2 = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notificationicon)
                        .setContentTitle("Event Invite")
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent2);

                //insert into database
                values.put(MeFreeProviderContract.Invited.EVENT_ID,eventid);
                values.put(MeFreeProviderContract.Invited.NAME,eventname);
                values.put(MeFreeProviderContract.Invited.LOCATION,eventlocation);
                values.put(MeFreeProviderContract.Invited.DATE,eventtime);
                resolver.insert(MeFreeProviderContract.Invited.CONTENT_URI,values);

                notificationManager.notify(0 /* ID of notification */, notificationBuilder2.build());
                break;
            case "acceptrequest":
                Intent intent3 = new Intent(this, Current_Friends.class);
                intent3.putExtra("message",message);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent3 = PendingIntent.getActivity(this, 0 /* Request code */, intent3,
                        PendingIntent.FLAG_ONE_SHOT);

                NotificationCompat.Builder notificationBuilder3 = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notificationicon)
                        .setContentTitle("Friend Request Accepted!")
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent3);

                /////handle database

                values.put(MeFreeProviderContract.Friends.NAME,senderName);
                resolver.insert(MeFreeProviderContract.Friends.CONTENT_URI, values);

                ///create notification

                notificationManager.notify(0 /* ID of notification */, notificationBuilder3.build());
                break;

        }

    }
}
