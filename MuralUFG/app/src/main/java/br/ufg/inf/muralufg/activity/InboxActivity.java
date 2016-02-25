package br.ufg.inf.muralufg.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import br.ufg.inf.muralufg.fragment.InboxFragment;
import br.ufg.inf.muralufg.fragment.NavigationDrawerFragment;
import br.ufg.inf.muralufg.R;
import br.ufg.inf.muralufg.utils.gcm.RegisterGCM;
import br.ufg.inf.muralufg.utils.mail.SendEmailTask;


public class InboxActivity extends AbstractBaseActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private Context context;
    private static GoogleCloudMessaging gcm;
    private static String regid;
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        if (checkPlayServices()) {
            setGCM(GoogleCloudMessaging.getInstance(context));
            setregid(RegisterGCM.getRegistrationId(context));

            if (getregid().isEmpty()) {
                RegisterGCM.registerInBackground(context);
            }
        }
    }

    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_news);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment objFragment = null;

        if (position == 0)
            objFragment = new InboxFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, objFragment)
                .commit();
    }

    private boolean checkPlayServices() {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    public static GoogleCloudMessaging getGCM() {
        return gcm;
    }

    public static void setGCM(GoogleCloudMessaging gcm) {
        InboxActivity.gcm = gcm;
    }

    public static String getregid() {
        return regid;
    }

    public static void setregid(String regid) {
        InboxActivity.regid = regid;
    }
}
