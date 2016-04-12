package com.cuginimotorsports.cs683_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.welcome);

        //Method used to switch to the EnterInvoice.java class.
        Button switchToInvoiceButton = (Button)findViewById(R.id.enterInvoiceScreenButton);
        final Intent intentForInvoiceView =
                new Intent(this, EnterInvoice.class);
        switchToInvoiceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intentForInvoiceView);
            }
        });

        //Method used to switch to the CalendarHome.java class
        Button switchToCalendarButton = (Button)findViewById(R.id.enterCalendarScreenButton);
        final Intent intentForCalendarView =
                new Intent(this, CalendarHome.class);
        switchToCalendarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intentForCalendarView);
            }
        });

    }

    /*public void saveInfo(View view) {
        SharedPreferences sharedPref = getSharedPreferences(Text_Size, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", usernameInput.getText().toString());
        editor.apply();

        Toast.makeText(this, "Saved Username!", Toast.LENGTH_LONG).show();
    }*/

}
