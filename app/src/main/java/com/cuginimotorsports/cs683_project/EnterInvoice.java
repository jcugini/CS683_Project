package com.cuginimotorsports.cs683_project;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterInvoice extends AppCompatActivity {

    //private methods used to define each edit text entry.
    private EditText companyName;
    private EditText paidDate;
    private EditText invoiceNumber;
    private EditText amountPaid;
    private Button addInvoice;
    private InvoiceDBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_invoice);

        //calls the InvoiceDBHelper java class to enter an invoice into the database.
        dbh = new InvoiceDBHelper(EnterInvoice.this);

        //gathers the data from the edit text entries and puts them into the private variables
        //declared above.
        companyName = (EditText) findViewById(R.id.companyEditText);
        paidDate = (EditText) findViewById(R.id.paidDateEditText);
        invoiceNumber = (EditText) findViewById(R.id.invoiceNumberEditText);
        amountPaid = (EditText) findViewById(R.id.amountPaidEditText);
        addInvoice = (Button) findViewById(R.id.addInvoiceButton);

        //saves the above information to the databse by clicking the "add invoice" button
        addInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                saveToDB();
            }
        });


    }

    //creates the Toast String implemented below to show that the invoice was added
    private void toast(String aToast) {
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

        toast("Successfully Added Invoice");
    }

    //method used on button "Show All Invoices" used to switch to java class invoiceList. This
    //displays the listView created in invoice_list.
    public void SwitchToAllInvoiceView(View view){
        Intent intent = new Intent(getApplicationContext(),
                invoiceList.class);
        startActivity(intent);
        finish();
    }
}
