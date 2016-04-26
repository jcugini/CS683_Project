package com.cuginimotorsports.cs683_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userNameEdit;
    public static SharedPreferences userName;

    //Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Takes User to Reminders List View.
            case R.id.menu_main_ViewCalendarEvents:
                final Intent intentForNewCalendar = new Intent(this, CalendarHome.class);
                startActivity(intentForNewCalendar);
                return true;

            //Takes User to Invoices List View.
            case R.id.menu_main_ViewInvoices:
                final Intent intentForInvoices = new Intent(this, invoiceList.class);
                startActivity(intentForInvoices);
                return true;

            //Takes User to login Screen to update username.
            case R.id.menu_main_UpdateUserName:
                startLogin();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Method called on for login screen. This saves the users input from the EditText field
    //and puts it into a shared preference file.
    public void saveInfo() {
        userName = getSharedPreferences("userName", MODE_PRIVATE);
        userNameEdit = (EditText) findViewById(R.id.login_userNameEdit);
        SharedPreferences.Editor editor = userName.edit();
        editor.clear();
        editor.putString("userName", userNameEdit.getText().toString());
        editor.apply();

        //Toast pops up to inform the user that the username has been saved.
        Toast.makeText(this, "Saved Username!", Toast.LENGTH_LONG).show();
    }

    //This is the view that is invoked after the user already has a shared preference for their
    //username.
    public void startNormal(){
        String toast = userName.getString("userName", null);
        setContentView(R.layout.welcome);

        //Toast method to display a welcome message with the users name upon opening the app.
        Toast.makeText(this, "Welcome Back " + toast + "!", Toast.LENGTH_LONG).show();

        //button created to enter the application (starts with listView of invoices).
        Button switchToAppStart = (Button) findViewById(R.id.welcome_enterApp);
        final Intent intentForAppStart =
                new Intent(this, invoiceList.class);
        switchToAppStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intentForAppStart);

            }
        });
    }

    //This is the the View that is invoked the first time the user opens the application.
    public void startLogin() {
        setContentView(R.layout.login);

        //Button to save shared preferences for the username entered.
        Button saveName = (Button) findViewById(R.id.login_saveName);
        final Intent intentForSaveName =
                new Intent(this, MainActivity.class);
        saveName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveInfo();

                //After saving the username - restart this activity - the view changes and
                //username is then toasted based on method described earlier in startNormal.
                startActivity(intentForSaveName);
            }
        });
    }

    /*On Create Method for the Main Activity. I created if-else statements to redirect the user
    to a page to enter their username on first time use. Once a username is detected, the user will
    be directed to the normal screen for all future app starts.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userName = getSharedPreferences("userName", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        if (userName.getString("userName", null) != null) {
            startNormal();
        }else {
            startLogin();
        }
    }

}
