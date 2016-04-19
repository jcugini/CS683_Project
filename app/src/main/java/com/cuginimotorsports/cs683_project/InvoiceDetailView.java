package com.cuginimotorsports.cs683_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*This class was created for the handling of the click events on the invoiceList.java items.
After clicking on an invoice in the listView, you're redirected to this Class which pulls in the
view laid out in invoice_detail_view.xml. The view provides a better layout for end users than the
messy list view.
 */
public class InvoiceDetailView extends AppCompatActivity{
    private TextView company, datePaid, paid, invoiceNumber;
    private String invoiceId;
    private InvoiceDBHelper dbh;
    private Button removeInvoice;
    private int invoiceIdInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_detail_view);

        dbh = new InvoiceDBHelper(InvoiceDetailView.this);
        removeInvoice = (Button) findViewById(R.id.DeleteButton);

        removeInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbh.deleteInvoice(invoiceIdInt);

                toast("Invoice Deleted");

                startActivity(new Intent(InvoiceDetailView.this, invoiceList.class));
            }
        });

        company = (TextView) findViewById(R.id.detailCompanyName);
        datePaid = (TextView) findViewById(R.id.detailPaidDate);
        paid = (TextView) findViewById(R.id.detailAmountPaid);
        invoiceNumber = (TextView) findViewById(R.id.detailInvoiceNumber);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            company.setText(extras.getString("company"));
            datePaid.setText(extras.getString("datePaid"));
            paid.setText(extras.getString("paid"));
            invoiceNumber.setText(extras.getString("id"));
            invoiceId = extras.getString("id");
            invoiceIdInt = Integer.parseInt(invoiceId);
        }
    }

    public void toast(String aToast) {
        Toast.makeText(getApplicationContext(), aToast, Toast.LENGTH_SHORT).show();
    }
}
