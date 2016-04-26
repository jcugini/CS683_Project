package com.cuginimotorsports.cs683_project;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EnterInvoice extends Activity {

    //private methods used to define each edit text entry.
    private EditText companyName;
    private EditText paidDate;
    private EditText invoiceNumber;
    private EditText amountPaid;
    Button addInvoice;
    private InvoiceDBHelper dbh;

    /*String Format used to convert the paidDate Fragment to String. Sources:
    http://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
    answer provided by user: Android_coder @ Feb 18, 2013.
     */
    String format = "MM/dd/yyyy";
    SimpleDateFormat f = new SimpleDateFormat(format, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_invoice);

//Created a button to switch to the invoiceList java class view to view all items in the
        //list view.
        Button switchToAllInvoices = (Button)findViewById(R.id.enter_invoice_ViewAllInvoicesButton);
        final Intent intentForAllInvoices =
                new Intent(this, invoiceList.class);
        switchToAllInvoices.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intentForAllInvoices);
            }
        });

        //calls the InvoiceDBHelper java class to enter an invoice into the database.
        dbh = new InvoiceDBHelper(EnterInvoice.this);

        //gathers the data from the edit text entries and puts them into the private variables
        //declared above.
        companyName = (EditText) findViewById(R.id.enter_invoice_CompanyEditText);
        paidDate = (EditText) findViewById(R.id.enter_invoice_PaidDateEditText);
        invoiceNumber = (EditText) findViewById(R.id.enter_invoice_InvoiceNumberEditText);
        amountPaid = (EditText) findViewById(R.id.enter_invoice_AmountPaidEditText);
        addInvoice = (Button) findViewById(R.id.enter_invoice_AddInvoiceButton);

        /*Created an onClickListener for paidDate. After clicking on the editText a dialog box
        with a calendar selector appears where you can choose the date desired. This also
        forces the date to be entered in a specific format which is excellent for user readability
        This method invokes the DataPickerFragment Class.
        This method was taken from a mix of responses from the following site:
        http://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
        I changed a users idea and used an OnClickListener instead of his suggested OnFocusChange.
        */
        paidDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateFrag = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        Calendar datePicker = Calendar.getInstance();
                        datePicker.set(year,month,day);
                        paidDate.setText(f.format(datePicker.getTime()));
                    }
            };
                dateFrag.show(getFragmentManager().beginTransaction(), "datePicker");
        }

    });

//saves the above information to the database by clicking the "add invoice" button
        addInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveToDB();
            }
        });

    }

    //creates the Toast String implemented below to show that the invoice was added
    public void toast(String aToast) {
        Toast.makeText(getApplicationContext(), aToast, Toast.LENGTH_SHORT).show();
    }

    //this is the method used to add invoices to the database in the method above.
    private void saveToDB() {
        //pulls the data from the declared variables and makes them into a string.

            InvoiceHome invoice = new InvoiceHome();
            invoice.setCompanyName(companyName.getText().toString().trim());
            invoice.setPaidDate(paidDate.getText().toString().trim());
            invoice.setInvoiceNumber(invoiceNumber.getText().toString().trim());
            invoice.setAmountPaid(amountPaid.getText().toString().trim());

        //adds the invoices to the data
        dbh.addInvoices(invoice);
        dbh.close();

        companyName.setText("");
        paidDate.setText("");
        invoiceNumber.setText("");
        amountPaid.setText("");

        //This toast method displays the selected methods used in InvoiceDbHelper defined in the
        //getError method.
        toast(dbh.getAddInvoiceError());
    }

}
