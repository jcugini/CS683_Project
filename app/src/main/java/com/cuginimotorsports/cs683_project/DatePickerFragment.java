package com.cuginimotorsports.cs683_project;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import java.util.Calendar;

/*This fragment Class was taken from the following site:
http://stackoverflow.com/questions/14933330/datePicker-how-to-popup-datePicker-when-click-on-editText
I used the answer provided by Daniel Jonker on Oct 6, 2014.
This Fragment is invoked in the EnterInvoice class in order to get a popup to select a date.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar datePicker = Calendar.getInstance();
        int year = datePicker.get(Calendar.YEAR);
        int month = datePicker.get(Calendar.MONTH);
        int day = datePicker.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

    }
}
