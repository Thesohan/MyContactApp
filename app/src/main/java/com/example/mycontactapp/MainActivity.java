package com.example.mycontactapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frameLayout);


        if (!hasAllPermissions())
            askForPermissions();
        else{
            openFragment();
        }
    }

    private void openFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, new ContactsFragment(), "addonstack").
                addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>1) {
            getSupportFragmentManager().popBackStack();
            }
        else{

            finish();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if
                (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFragment();
                }

                else {

                    showAlertMessage();

                }

                return;
            }
        }
    }

    private void showAlertMessage() {

        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
        builder.setMessage("Please allow the permission to use this app")
                .setIcon(R.drawable.ic_warning_black_24dp)
                .setTitle("Permission")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        askForPermissions();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();

    }

    private void askForPermissions()
    {
        String permissions[]={Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS};
        if(Build.VERSION.SDK_INT>=23)
            requestPermissions(permissions,1);
    }

    private boolean hasAllPermissions()
    {
        if(Build.VERSION.SDK_INT>=23)
            return ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_CONTACTS)==PackageManager.PERMISSION_GRANTED;
        return true;
    }
}

