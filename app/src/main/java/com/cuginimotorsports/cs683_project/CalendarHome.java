 package com.cuginimotorsports.cs683_project;

    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.provider.CalendarContract;
    import android.support.v7.app.AppCompatActivity;
    import android.view.View;
    import android.widget.Button;

 public class CalendarHome extends AppCompatActivity {

     Button ViewCalendarButton;
     Button EditCalendarButton;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.calendar_home);

         //button to add an event to the calendar
         EditCalendarButton = (Button) findViewById(R.id.add_event_button);

         EditCalendarButton.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 addCalendarEvent();
             }
         });

         //button to create calendar event from main screen
         ViewCalendarButton = (Button) findViewById(R.id.view_calendar_button);

         ViewCalendarButton.setOnClickListener(new View.OnClickListener() {
             @Override

             public void onClick(View arg0) {
                 viewCalendarEvent();
             }
         });

     }

     //pulls built in android calendar event editor to add events to calendar
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
 }