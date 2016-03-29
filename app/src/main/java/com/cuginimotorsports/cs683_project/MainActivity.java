package com.cuginimotorsports.cs683_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Menu;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button ViewCalendarButton;
    private Button EditCalendarButton;

    //Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //Default View on opening App
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        //button to view the calendar from the main screen.
        ViewCalendarButton = (Button) findViewById(R.id.view_calendar_button);

        ViewCalendarButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setContentView(R.layout.calendar);

            }
        });

        //button to create calendar event from main screen
        EditCalendarButton = (Button) findViewById(R.id.add_event_button);

        EditCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View arg0) {
                addCalendarEvent();
            }
        });

    }

    //pulls built in android calendar event editor to add events to calendar
    public void addCalendarEvent() {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        startActivity(intent);
    }

}
