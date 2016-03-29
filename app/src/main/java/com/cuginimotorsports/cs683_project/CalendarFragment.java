package com.cuginimotorsports.cs683_project;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;
import java.util.Calendar;


/*This Class was created to try to pull in the phone calendars into Calendar view. This Class
currently serves no purpose since I couldn't get it to pull the on-board calendar.*/

public class CalendarFragment extends Fragment {

    Calendar cal = Calendar.getInstance();

    public void onCreate(LayoutInflater inflater,
                         ViewGroup container,
                         Bundle savedInstanceState) {;
        View view = inflater.inflate(R.layout.calendar,container,false);

    }

}
