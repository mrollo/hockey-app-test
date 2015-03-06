package com.example.matthewrollo.hockeyapptest;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.CrashManagerListener;
import net.hockeyapp.android.UpdateManager;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button crashMeButton = (Button) findViewById(R.id.crash_me_button);
        crashMeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new RuntimeException("This should crash the app and give a report in HockeyApp");
            }
        });

        try {
            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionNumber = pinfo.versionCode;
            String versionName = pinfo.versionName;
            TextView appVersionTextView = (TextView) findViewById(R.id.appVersionTextView);
            appVersionTextView.setText(versionNumber + " - " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForCrashes();
        checkForUpdates();
    }

    private void checkForCrashes() {
        CrashManager.register(this, "73d5739cce511e9246e8d8e4f50f2969", new CrashManagerListener() {
            @Override
            public boolean shouldAutoUploadCrashes() {
                // don't prompt user to upload crash data
                return true;
            }
        });
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this, "73d5739cce511e9246e8d8e4f50f2969");
    }
}
