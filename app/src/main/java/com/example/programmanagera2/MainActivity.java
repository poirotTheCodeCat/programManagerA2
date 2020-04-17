/*
File: Main Activity
Programmers: John Stanly, Aaron Perry, Sasha Malesevic, Manthan Rami, Daniel Grew
Date Last Modified: 2020-03-12
Description: This class holds the main activity that started the application. There is only one activity
in this project. This activity holds the on click functionality for the about dialog in the overflow
menu. Fragments are used to change views.
 */
package com.example.programmanagera2;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private int storagePermission=1;
    private static int session=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        askForPermission();//ask for permission
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;

        //show back button
        ab.setDisplayHomeAsUpEnabled(true);

        Log.v("info", "Application started");
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
        if (id == R.id.action_about) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(R.string.about_app_dialog)
                    .setNegativeButton(R.string.okay, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //user closed the dialog
                        }
                    });
            dialog.show();
            return true;
        }
        if (id == android.R.id.home)
        {
            //return to home page
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void askForPermission()
    {
        PermissionListener permissionListener=new PermissionListener() {
            @Override
            public void onPermissionGranted() { Toast.makeText(MainActivity.this,"Permission Granted!",Toast.LENGTH_SHORT).show(); }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) { Toast.makeText(MainActivity.this,"Permission Denied!",Toast.LENGTH_SHORT).show(); }
        };
        TedPermission.with(MainActivity.this).setPermissionListener(permissionListener).setPermissions(Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_EXTERNAL_STORAGE).check();
    }
}
