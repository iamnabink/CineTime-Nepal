package com.whoamie.cinetime_nepal.common.activities;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whoamie.cinetime_nepal.BuildConfig;
import com.whoamie.cinetime_nepal.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SettingActivity extends AppCompatActivity {
    LinearLayout termsOfUseLayout, notificationLayout, shareAppLayout, aboutAppLayout, privacyLayout, feedbackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
    }

    private void initViews() {
        termsOfUseLayout = findViewById(R.id.terms_of_use_layout);
        notificationLayout = findViewById(R.id.notification_layout);
        aboutAppLayout = findViewById(R.id.about_app_layout);
        feedbackLayout = findViewById(R.id.feedback_layout);
        privacyLayout = findViewById(R.id.privacy_layout);
        shareAppLayout = findViewById(R.id.shareapp_layout);
        termsOfUseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://cinetimenepal.whoamie.com/terms&condition")));
            }
        });
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNotificationSettings();
//                Intent intent = new Intent();
//                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
//                String packageName=getApplicationContext().getPackageName();
//                List<PackageInfo> apps = getPackageManager().getInstalledPackages(0);
//                for(PackageInfo packageInfo:apps){
//                    if(packageInfo.packageName.equals(packageName)){
//                        intent.putExtra("android.provider.extra.APP_PACKAGE", packageName);
//                        startActivity(intent);
//                    }
//                }
            }
        });
        aboutAppLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://cinetimenepal.whoamie.com/about-app")));
            }
        });
        privacyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://cinetimenepal.whoamie.com/privacy-policy")));
            }
        });
        feedbackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,FeedBackActivity.class));
            }
        });


        shareAppLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(android.content.Intent.ACTION_SEND); //open share via intent
//                i.setType("text/plain");
//                i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject test");
//                i.putExtra(android.content.Intent.EXTRA_TEXT, "extra text that you want to put");
//                startActivity(Intent.createChooser(i,"Share via"));
                shareApplication();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void goToNotificationSettings() {

        String packageName = getApplicationContext().getPackageName();

        try {
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {

                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);

            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {

                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra("android.provider.extra.APP_PACKAGE", packageName);

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                intent.putExtra("app_package", packageName);
                intent.putExtra("app_uid", getApplicationContext().getApplicationInfo().uid);

            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {

                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + packageName));

            } else {
                return;
            }

            startActivity(intent);

        } catch (Exception e) {
            // log goes here

        }

    }

    private void shareApplication() {
        ApplicationInfo app = getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;

        Intent intent = new Intent(Intent.ACTION_SEND);

        // MIME of .apk is "application/vnd.android.package-archive".
        // but Bluetooth does not accept this. Let's use "*/*" instead.
        intent.setType("*/*");

        // Append file and send Intent
        File originalApk = new File(filePath);

        try {
            //Make new directory in new location=
            File tempFile = new File(getExternalCacheDir() + "/ExtractedApk");
            //If directory doesn't exists create new
            if (!tempFile.isDirectory())
                if (!tempFile.mkdirs())
                    return;
            //Get application's name and convert to lowercase
            tempFile = new File(tempFile.getPath() + "/" + getString(app.labelRes).replace(" ", "").toLowerCase() + ".apk");
            //If file doesn't exists create new
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return;
                }
            }
            //Copy file to new location
            InputStream in = new FileInputStream(originalApk);
            OutputStream out = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
            //Open share dialog
//          intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", tempFile);
//          intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            intent.putExtra(Intent.EXTRA_STREAM, photoURI);
            startActivity(Intent.createChooser(intent, "Share app via"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
