package com.cuginimotorsports.cs683_project;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/*This class was created for the handling of the click events on the invoiceList.java items.
After clicking on an invoice in the listView, you're redirected to this Class which pulls in the
view laid out in invoice_detail_view.xml. The view provides a better layout for end users than the
messy list view.
 */
public class InvoiceDetailView extends AppCompatActivity{
    private TextView company, datePaid, paid, invoiceNumber;
    String invoiceId;
    private InvoiceDBHelper dbh;
    private int invoiceIdInt;
    String format = "MM/dd/yyyy";
    SimpleDateFormat f = new SimpleDateFormat(format, Locale.US);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.invoice_detail_menu, menu);
        return true;
    }

    //Created a menu item "Delete" which allows a user to delete the selected invoice.
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteInvoiceMenu:
                //Created an alert Dialog to warn the user they are going to delete the invoice.
                AlertDialog.Builder invoiceDelete = new AlertDialog.Builder(this);
                invoiceDelete.setTitle("Delete Invoice?");
                invoiceDelete.setMessage("Are you sure you want to delete this invoice?");

                /*On clicking "yes" the user will invoke the deleteInvoice method in
                the invoiceDBHelper.class. They also see a confirmation Toast and be redirected
                to the invoice list view.*/
                invoiceDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbh = new InvoiceDBHelper(InvoiceDetailView.this);
                        dbh.deleteInvoice(invoiceIdInt);
                        toast("Invoice Deleted");
                        startActivity(new Intent(InvoiceDetailView.this, invoiceList.class));

                    }
                });

                //On Clicking "No" the user will simply be told the invoice was not deleted and
                //will be redirected back to the updated list view which should be the same.
                invoiceDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toast("Your Invoice was not Deleted.");
                        startActivity(new Intent(InvoiceDetailView.this, invoiceList.class));
                    }
                });

                //displays the alert dialog laid out above.
                AlertDialog alertDialog = invoiceDelete.create();
                alertDialog.show();
                return true;

            case R.id.update_invoice:
                updateDB();
                final Intent intentForRefreshPage =
                        new Intent(this, invoiceList.class);
                startActivity(intentForRefreshPage);
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_detail_view);

        dbh = new InvoiceDBHelper(InvoiceDetailView.this);

        company = (TextView) findViewById(R.id.detailCompanyName);
        datePaid = (TextView) findViewById(R.id.detailPaidDate);
        paid = (TextView) findViewById(R.id.detailAmountPaid);
        invoiceNumber = (TextView) findViewById(R.id.detailInvoiceNumber);

        //Cycles through the TextViews created above and pulls the text into a String format.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            company.setText(extras.getString("company"));
            datePaid.setText(extras.getString("datePaid"));
            paid.setText(extras.getString("paid"));
            invoiceNumber.setText(extras.getString("id"));
            invoiceId = extras.getString("id");

            //used to convert invoice id into int so I can update and/or delete the invoice by ID.
            invoiceIdInt = Integer.parseInt(invoiceId);
        }

        //Copied this method from the EnterInvoice Class since they both use the same fragment.
        datePaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateFrag = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        Calendar datePicker = Calendar.getInstance();
                        datePicker.set(year,month,day);
                        datePaid.setText(f.format(datePicker.getTime()));
                    }
                };
                dateFrag.show(getFragmentManager().beginTransaction(), "datePicker");
            }

        });
    }

    public void toast(String aToast) {
        Toast.makeText(getApplicationContext(), aToast, Toast.LENGTH_SHORT).show();
    }

    /*This Class is called to Update the invoice. After getting ot this view, the text is pulled in
    to the variables laid out above. This text is then sent over to the InvoiceHOme class which has
    get and set methods to store the data so that the invoiceDBHelper class can pull the get methods
    and get data from the methods that set the data below.*/
    private void updateDB (){

        InvoiceHome invoice = new InvoiceHome();
        invoice.setCompanyName(company.getText().toString().trim());
        invoice.setPaidDate(datePaid.getText().toString().trim());
        invoice.setInvoiceNumber(invoiceNumber.getText().toString().trim());
        invoice.setAmountPaid(paid.getText().toString().trim());

        //adds the invoices to the data
        dbh.updateInvoice(invoice);
        dbh.close();
    }
}
