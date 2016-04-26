package com.cuginimotorsports.cs683_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


//This class was formatted based off the InvoiceHome java class. It contains the get and set
//methods to add data to the string array for calendar events.
public class ReminderListHelper extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String EventId;
    public String DateStart;
    public String DateEnd;
    public String Description;
    public String Title;

    public String getEventId() {return EventId;}
    public void setEventId(String EventId) {this.EventId = EventId;}

    public String getDateStart() {return DateStart;}
    public void setDateStart(String DateStart) {this.DateStart = DateStart;}

    public String getDateEnd() {return DateEnd;}
    public void setDateEnd(String DateEnd) {this.DateEnd = DateEnd;}

    public String getReminderTitle () {return Title;}
    public void setReminderTitle(String Title) {this.Title = Title;}

    public String getDescription () {return Description;}
    public void setDescription(String Description) {this.Description = Description;}

}
