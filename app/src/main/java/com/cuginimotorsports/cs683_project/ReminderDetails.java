package com.cuginimotorsports.cs683_project;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

//This Class displays the reminder_detail_view.
public class ReminderDetails extends AppCompatActivity{

    TextView dateStart, dateEnd, title, description;
    String reminderString;
    private int reminderInt;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_detail_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteReminderItem:
                //Created an alert Dialog to warn the user they are going to delete the invoice.
                AlertDialog.Builder reminderDelete = new AlertDialog.Builder(this);
                reminderDelete.setTitle("Delete Reminder?");
                reminderDelete.setMessage("Are you sure you want to delete this reminder from your" +
                        " Calendar?");

                /*On clicking "yes" the user will invoke the deleteInvoice method in
                the invoiceDBHelper.class. They also see a confirmation Toast and be redirected
                to the invoice list view.*/
                reminderDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteEvent(reminderInt);
                        toast("Reminder Deleted");
                        startActivity(new Intent(ReminderDetails.this, CalendarHome.class));

                    }
                });

                //On Clicking "No" the user will simply be told the invoice was not deleted and
                //will be redirected back to the updated list view which should be the same.
                reminderDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toast("Your Reminder was not Deleted.");
                        startActivity(new Intent(ReminderDetails.this, CalendarHome.class));
                    }
                });

                //displays the alert dialog laid out above.
                AlertDialog alertDialog = reminderDelete.create();
                alertDialog.show();

                return true;

            case R.id.editReminderItem:
                updateCalendarEvent();
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_detail_view);

        dateStart = (TextView) findViewById(R.id.rDetailDateStart);
        dateEnd = (TextView) findViewById(R.id.rDetailDateEnd);
        title = (TextView) findViewById(R.id.rDetailTitle);
        description = (TextView) findViewById(R.id.rDetailDescription);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dateStart.setText(extras.getString("dateStart"));
            dateEnd.setText(extras.getString("dateEnd"));
            title.setText(extras.getString("title"));
            description.setText(extras.getString("description"));
            reminderString = extras.getString("eventId");
            reminderInt = Integer.parseInt(reminderString);
        }
    }

    public void toast(String aToast) {
        Toast.makeText(getApplicationContext(), aToast, Toast.LENGTH_SHORT).show();
    }

    private void deleteEvent(int eventId) {
        Uri CALENDAR_URI = Uri.parse("content://com.android.calendar/events");
        Uri uri = ContentUris.withAppendedId(CALENDAR_URI, eventId);
        getContentResolver().delete(uri, null, null);
    }

    //http://stackoverflow.com/questions/13072275/using-an-intent-to-edit-calendar-event-doesn't-work
    public void updateCalendarEvent() {
        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, reminderInt);
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(uri);
        startActivity(intent);
    }
}
