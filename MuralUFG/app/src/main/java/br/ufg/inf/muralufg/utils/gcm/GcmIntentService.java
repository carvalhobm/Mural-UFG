/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.ufg.inf.muralufg.utils.gcm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import br.ufg.inf.muralufg.R;
import br.ufg.inf.muralufg.activity.NewsViewActivity;
import br.ufg.inf.muralufg.model.News;
import br.ufg.inf.muralufg.utils.db.DBOpenHelper;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */

public class GcmIntentService extends IntentService {

    public static final String TAG = "GCM Demo";
    private static final String TITLE = "title";
    private static final String NEWS = "news";
    private static final String AUTHOR = "author";
    private static final String AUTHOR_BELONGS = "authorbelongs";
    private static final String DATE_TIME = "datetime";
    private int notificationId = -1;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);
        if (!extras.isEmpty() && extras.get(GcmIntentService.TITLE) != null) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification(extras);
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification(extras);
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                sendNotification(extras);
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(Bundle extras) {
        DBOpenHelper db = new DBOpenHelper(getApplicationContext());

        News news = new News(extras.get(GcmIntentService.TITLE).toString(), extras.get(GcmIntentService.NEWS).toString(), "", extras.get(GcmIntentService.AUTHOR).toString(), Integer.parseInt(extras.get(GcmIntentService.AUTHOR_BELONGS).toString()), extras.get(GcmIntentService.DATE_TIME).toString(), Integer.parseInt(extras.get("relevance").toString()), extras.get("url").toString());
        if (news.getRelevance() == 0 && !db.canDisplayNews(news))
            return;

        notificationId = db.addNews(news);

        if (notificationId == -1)
            return;

        Intent notificationIntent = new Intent(this, NewsViewActivity.class).putExtra("id", notificationId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification;

        if ("1".equals(extras.get("relevance").toString())) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                notification = new Notification.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ufg_notification)
                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.phonebeeping))
                        .setLights(Color.RED, 3000, 2000)
                        .setContentIntent(pendingIntent)
                        .setTicker(extras.get(GcmIntentService.TITLE).toString())
                        .setContentTitle(extras.get(GcmIntentService.NEWS).toString())
                        .setContentText(extras.get(GcmIntentService.AUTHOR).toString())
                        .extend(new Notification.WearableExtender().setBackground(BitmapFactory.decodeResource(getApplication().getResources(), R.mipmap.ic_launcher)))
                        .build();
            } else {
                notification = new Notification.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ufg_notification)
                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.phonebeeping))
                        .setLights(Color.RED, 3000, 2000)
                        .setContentIntent(pendingIntent)
                        .setTicker(extras.get(GcmIntentService.TITLE).toString())
                        .setContentTitle(extras.get(GcmIntentService.NEWS).toString())
                        .setContentText(extras.get(GcmIntentService.AUTHOR).toString())
                        .build();
            }
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                notification = new Notification.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ufg_notification)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setLights(Color.BLUE, 1000, 5000)
                        .setContentIntent(pendingIntent)
                        .setTicker(extras.get(GcmIntentService.TITLE).toString())
                        .setContentTitle(extras.get(GcmIntentService.NEWS).toString())
                        .setContentText(extras.get(GcmIntentService.AUTHOR).toString())
                        .extend(new Notification.WearableExtender().setBackground(BitmapFactory.decodeResource(getApplication().getResources(), R.mipmap.ic_launcher)))
                        .build();
            } else {
                notification = new Notification.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ufg_notification)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setLights(Color.BLUE, 1000, 5000)
                        .setContentIntent(pendingIntent)
                        .setTicker(extras.get(GcmIntentService.TITLE).toString())
                        .setContentTitle(extras.get(GcmIntentService.NEWS).toString())
                        .setContentText(extras.get(GcmIntentService.AUTHOR).toString())
                        .build();
            }
        }

        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(notificationId, notification);
    }
}
