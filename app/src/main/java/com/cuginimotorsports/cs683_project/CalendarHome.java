 package com.cuginimotorsports.cs683_project;

    import android.Manifest;
    import android.app.Activity;
    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.database.Cursor;
    import android.net.Uri;
    import android.os.Bundle;
    import android.provider.CalendarContract;
    import android.support.v4.app.ActivityCompat;
    import android.support.v7.app.AppCompatActivity;
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.ListView;
    import android.widget.TextView;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Date;

 //Activity for the Reminders List view.

 /* The majority of methods in this class were taken from a previously created class - invoiceList.
 I also adopted a method to pull the events from all calendars on a mobile device and read those
 calendars for all events.
  */
 public class CalendarHome extends AppCompatActivity {

     private ListView listView;
     ReminderAdapter reminderAdapter;
     private ArrayList<ReminderListHelper> Reminders = new ArrayList<>();

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.calendar_home);

         listView = (ListView) findViewById(R.id.calendar_home_reminderList);
         registerForContextMenu(listView);
         refreshData();

     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.calendar_menu, menu);
         return true;
     }

     public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
             case R.id.addBillReminder:

                 //Created an alert dialog to give users tips on creating a useful reminder that
                 //includes a description which they can view from this app.
                 AlertDialog.Builder addReminder = new AlertDialog.Builder(this);
                 addReminder.setTitle("Reminder Tips");
                 addReminder.setMessage("When adding a reminder please enter details into the" +
                         "description field. This enables you to see what the reminder does and " +
                         "how often the reminder occurs from within the app." +
                         "\n\nEX: This Event occurs on the 24th of every month.");

                /*I chose PositiveButton over NeutralButton because I liked the OK on the bottom
                right better than the left. Upon clicking "OK" the addCalendarEvent method is
                invoked.*/
                 addReminder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         addCalendarEvent();
                     }
                 });

                 //displays the alert dialog laid out above.
                 AlertDialog alertDialog = addReminder.create();
                 alertDialog.show();
                 return true;

             //invokes the method described. Takes to built in Android calendar event add screen.
             case R.id.ViewCalendar:
                 viewCalendarEvent();
                 return true;

             //takes user back to the invoice list view.
             case R.id.calendarToInvoices:
                 final Intent intentToInvoices = new Intent(this, invoiceList.class);
                 startActivity(intentToInvoices);
                 return true;

             //takes user back to the welcome screen.
             case R.id.calendarToHome:
                 final Intent intentToHome = new Intent(this, MainActivity.class);
                 startActivity(intentToHome);
                 return true;

             default:
                 return super.onOptionsItemSelected(item);
         }
     }

     private void refreshData() {

         Reminders.clear();
         /* This string method was found on:
         https://www.grokkingandroid.com/androids-calendarContract-provider/ in the Reading,
         updating, and deleting events section. The method has been modified
         to pull the desired event properties that I wanted for my app.*/
         String[] event =
                 new String[] {
                         CalendarContract.Events._ID,
                         CalendarContract.Events.DTSTART,
                         CalendarContract.Events.DTEND,
                         CalendarContract.Events.DESCRIPTION,
                         CalendarContract.Events.TITLE,
                 };

         //permission checker for calendar.
         if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
             return;
         }
         /*The simple date format was taken from
         http://stackoverflow.com/questions/12722613/get-calendar-events-for-ics-android-version-4-for-a-particular-date
         it was the answer given by lijo john on Dec 11, 2012 @ 12:37.*/
         SimpleDateFormat simple = new SimpleDateFormat();

         /*Added in the Deleted not equal to 1 because the list view wasn't updating since there is
         a delay from when I tell it to delete and when it actually deleted. Events are marked as
         deleted = 1 when they are waiting for the calendar to delete the event*/
         Cursor eventCursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI,event,CalendarContract.Events.DELETED + "!=1",null,CalendarContract.Events.DTSTART);
         if (eventCursor.moveToFirst()) {
             while (!eventCursor.isAfterLast()) {
                 String eventId = eventCursor.getString(0); //pulls event id
                 Date dateStartHard = new Date(eventCursor.getLong(1)); //pulls start date
                 Date dateEndHard = new Date(eventCursor.getLong(2)); //pulls end date
                 String description = eventCursor.getString(3); // pulls description
                 String title = eventCursor.getString(4); // pulls title

                 //These convert the Long Date methods above to readable date formats.
                 String dateStart = simple.format(dateStartHard);
                 String dateEnd = simple.format(dateEndHard);

                 //Every Event found will be stored in the newReminders helper.
                 //Once all events details are added, this event is added to the Reminders
                 //array.
                 ReminderListHelper newReminders = new ReminderListHelper();
                 newReminders.setEventId(eventId);
                 newReminders.setDateStart(dateStart);
                 newReminders.setDateEnd(dateEnd);
                 newReminders.setReminderTitle(title);
                 newReminders.setDescription(description);

                 //add newReminders to the array.
                 Reminders.add(newReminders);

                 //move to the next event found.
                 eventCursor.moveToNext();
             }
         }
         eventCursor.close();

         /*Adapter created to pull data into the list view using reminderAdapter and the format of
         calendar_list_row. It puts the Reminders created by the adapter into the list rows which
         are then put into the listView.*/
         reminderAdapter = new ReminderAdapter(CalendarHome.this,R.layout.calendar_list_row, Reminders);
         listView.setAdapter(reminderAdapter);
         reminderAdapter.notifyDataSetChanged();
     }

     //Adapter class created for the arrayList.
     public class ReminderAdapter extends ArrayAdapter<ReminderListHelper> {
         Activity activity;
         int layoutResource;
         ArrayList<ReminderListHelper> mData = new ArrayList<>();
         public ReminderAdapter(Activity act, int resource, ArrayList<ReminderListHelper> data) {
             super(act, resource, data);
             activity = act;
             layoutResource = resource;
             mData = data;
             notifyDataSetChanged();
         }

         @Override
         public int getCount() {
             return mData.size();
         }

         @Override
         public ReminderListHelper getItem(int position) {
             return mData.get(position);
         }

         @Override
         public int getPosition(ReminderListHelper item) {
             return super.getPosition(item);
         }

         @Override
         public long getItemId(int position) {
             return super.getItemId(position);
         }

         @Override
         public View getView(int position, View convertView, ViewGroup parent) {
             View row = convertView;
             ViewHolder holder = null;

             if (row == null || (row.getTag()) == null) {
                 LayoutInflater inflater = LayoutInflater.from(activity);

                 row = inflater.inflate(layoutResource, null);
                 holder = new ViewHolder();

                 holder.dateStart = (TextView) row.findViewById(R.id.rowDateStart);
                 holder.dateEnd = (TextView) row.findViewById(R.id.rowDateEnd);
                 holder.title = (TextView) row.findViewById(R.id.rowReminderTitle);

                 row.setTag(holder);
             } else {
                 holder = (ViewHolder) row.getTag();
             }
             holder.myReminder = getItem(position);

             holder.dateStart.setText(holder.myReminder.getDateStart());
             holder.dateEnd.setText(holder.myReminder.getDateEnd());
             holder.title.setText(holder.myReminder.getReminderTitle());


             //Creates the onClick method for the list view to change to the individual reminder
             //details which also shows more details of the event selected.
             final ViewHolder finalHolder = holder;
             holder.title.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     String eventId = finalHolder.myReminder.getEventId();
                     String dateStart = finalHolder.myReminder.getDateStart();
                     String dateEnd = finalHolder.myReminder.getDateEnd();
                     String title = finalHolder.myReminder.getReminderTitle();
                     String description = finalHolder.myReminder.getDescription();


                     Intent i = new Intent(CalendarHome.this, ReminderDetails.class);
                     i.putExtra("eventId", eventId);
                     i.putExtra("dateStart", dateStart);
                     i.putExtra("dateEnd", dateEnd);
                     i.putExtra("title", title);
                     i.putExtra("description", description);

                     startActivity(i);
                 }
             });

             return row;
         }
         class ViewHolder{

             ReminderListHelper myReminder;
             TextView dateStart;
             TextView dateEnd;
             TextView title;
         }
     }

     //pulls up screen to add a calendar event to built in android calendar.
     public void addCalendarEvent() {
         Intent intent = new Intent(Intent.ACTION_EDIT);
         intent.setType("vnd.android.cursor.item/event");
         startActivity(intent);
     }

     //pulls up screen to view the built in android calendar.
     public void viewCalendarEvent() {
         //Based this code off of http://www.grokkingandroid.com/intents-of-androids-calendar-app/
         //When the below code didn't work, found builder.appendPath("time"); here: http://stackoverflow.com/questions/11607250/how-to-get-calendar-page-in-my-android-application
         Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
         builder.appendPath("time");
         Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
         startActivity(intent);
     }

 }