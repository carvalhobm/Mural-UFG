package br.ufg.inf.muralufg.utils.gcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import br.ufg.inf.muralufg.activity.InboxActivity;
import br.ufg.inf.muralufg.utils.mail.SendEmailTask;

public class RegisterGCM {
    private static final String TAG = "RegisterGCM";

    private static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static String senderID = "439472309664";

    public static String getRegistrationId(Context context) {

        final SharedPreferences prefs = getGcmPreferences(context);

        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            return "";
        }

        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            return "";
        }
        return registrationId;
    }

    private static SharedPreferences getGcmPreferences(Context context) {
        return context.getSharedPreferences((InboxActivity.class.getSimpleName()), Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static void registerInBackground(final Context context) {


        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (InboxActivity.getGCM() == null) {
                        InboxActivity.setGCM(GoogleCloudMessaging.getInstance(context));
                    }
                    InboxActivity.setregid(InboxActivity.getGCM().register(senderID));
                    msg = "Device registered, registration ID=" + InboxActivity.getregid();
                    sendRegistrationIdToBackend(InboxActivity.getregid());
                    storeRegistrationId(context, InboxActivity.getregid());
                } catch (IOException ex) {
                    Log.e(TAG, ex.getMessage());
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }
        }.execute(null, null, null);
    }

    private static void sendRegistrationIdToBackend(final String emailBody) {
        new SendEmailTask().execute(emailBody);
    }

    private static void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }
}
