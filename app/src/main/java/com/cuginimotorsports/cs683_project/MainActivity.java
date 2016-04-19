package com.cuginimotorsports.cs683_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addBillReminder:
                addCalendarEvent();
                return true;
            case R.id.ViewCalendar:
                viewCalendarEvent();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    //Default View on opening App
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        Button switchToAppStart = (Button)findViewById(R.id.enterApp);
        final Intent intentForAppStart =
                new Intent(this, invoiceList.class);
        switchToAppStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intentForAppStart);
            }
        });
    }

    public void viewCalendarEvent() {
        //Based this code off of http://www.grokkingandroid.com/intents-of-androids-calendar-app/
        //When the below code didn't work, found builder.appendPath("time"); here: http://stackoverflow.com/questions/11607250/how-to-get-calendar-page-in-my-android-application
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
        startActivity(intent);
    }

    public void addCalendarEvent() {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        startActivity(intent);
    }

    /*public void saveInfo(View view) {
        SharedPreferences sharedPref = getSharedPreferences(Text_Size, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", usernameInput.getText().toString());
        editor.apply();

        Toast.makeText(this, "Saved Username!", Toast.LENGTH_LONG).show();
    }*/

}
