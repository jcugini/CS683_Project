package com.cuginimotorsports.cs683_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    //Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*Method to invoke CalendarHome.java
        This Method switches the Content View to calendar_home
        It also enables the button methods on that view*/
    public void SwitchToCalendarView(View view){
        Intent intent = new Intent(getApplicationContext(),
                CalendarHome.class);
        startActivity(intent);
        finish();
    }

    /*Method to invoke EnterInvoice.java
    This Method switches the Content View to enter_invoice
    It also enables the button methods on that view*/
    public void SwitchToInvoiceView(View view){
        Intent intent = new Intent(getApplicationContext(),
                EnterInvoice.class);
        startActivity(intent);
        finish();
    }
    //Default View on opening App
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

    }

}
