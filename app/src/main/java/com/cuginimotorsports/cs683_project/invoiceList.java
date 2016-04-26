package com.cuginimotorsports.cs683_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/*This Class does the majority of the work involved involved in updating the database and adjusting
the list view on invoice_list. This class was based off a lesson learned on Udemy.com. I used
methods introduced in "The Complete Android & Java Deveoper" by Fahd Sheraz, Web Developer and
Teacher. I still need to make the appropriate references in this class but a lot of the methods in
this class were taken from this lesson.
 */
public class invoiceList extends AppCompatActivity{

    private InvoiceDBHelper dbh;
    private ArrayList<InvoiceHome> dbInvoices = new ArrayList<>();
    private InvoiceAdapter invoiceAdapter;
    private ListView listView;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Created animation that starts the activity by pushing in from the left and out to the right
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.invoice_list);

        listView = (ListView) findViewById(R.id.invoiceList);
        registerForContextMenu(listView);
        refreshData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.invoice_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Add_Invoice:
                final Intent intentForSwitchEnterInvoice =
                        new Intent(this, EnterInvoice.class);
                startActivity(intentForSwitchEnterInvoice);
                return true;

            case R.id.invoiceToHome:
                final Intent intentToHome = new Intent(this, MainActivity.class);
                startActivity(intentToHome);
                return true;

            case R.id.invoiceShowReminders:
                final Intent intentToReminders = new Intent(this, CalendarHome.class);
                startActivity(intentToReminders);
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    private void refreshData() {
        dbInvoices.clear();
        dbh = new InvoiceDBHelper(getApplicationContext());

        ArrayList<InvoiceHome> invoicesFromDb = dbh.getAllInvoices();

        for (int i = 0; i < invoicesFromDb.size(); i++) {
            String companyName = invoicesFromDb.get(i).getCompanyName();
            String paidDate = invoicesFromDb.get(i).getPaidDate();
            String amountPaid = invoicesFromDb.get(i).getAmountPaid();
            String invoiceNumber = invoicesFromDb.get(i).getInvoiceNumber();

            InvoiceHome myInvoices = new InvoiceHome();
            myInvoices.setCompanyName(companyName);
            myInvoices.setPaidDate(paidDate);
            myInvoices.setAmountPaid(amountPaid);
            myInvoices.setInvoiceNumber(invoiceNumber);

            dbInvoices.add(myInvoices);
        }

        dbh.close();

        invoiceAdapter = new InvoiceAdapter(invoiceList.this,R.layout.invoice_list_row, dbInvoices);
        listView.setAdapter(invoiceAdapter);
        invoiceAdapter.notifyDataSetChanged();
    }

    public class InvoiceAdapter extends ArrayAdapter<InvoiceHome> {
        Activity activity;
        int layoutResource;
        ArrayList<InvoiceHome> mData = new ArrayList<>();

        public InvoiceAdapter(Activity act, int resource, ArrayList<InvoiceHome> data) {
            super(act, resource, data);
            activity = act;
            layoutResource = resource;
            mData = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public InvoiceHome getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getPosition(InvoiceHome item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;
            ViewHolder holder = null;

            if (row==null || (row.getTag()) == null){
                LayoutInflater inflater = LayoutInflater.from(activity);

                row = inflater.inflate(layoutResource, null);
                holder = new ViewHolder();

                holder.company = (TextView) row.findViewById(R.id.rowCompanyName);
                holder.paid = (TextView) row.findViewById(R.id.rowAmountPaid);
                holder.datePaid = (TextView) row.findViewById(R.id.rowPaidDate);
                holder.invoiceNumber = (TextView) row.findViewById(R.id.rowInvoiceNumber);

                row.setTag(holder);
            }else{
                holder = (ViewHolder) row.getTag();
            }

            holder.myInvoice = getItem(position);

            holder.company.setText(holder.myInvoice.getCompanyName());
            holder.datePaid.setText(holder.myInvoice.getPaidDate());
            holder.paid.setText(holder.myInvoice.getAmountPaid());
            holder.invoiceNumber.setText(holder.myInvoice.getInvoiceNumber());

            //Adding for Click Event in listView item. On clicking the company name, view changes
            //to invoice details for better view and delete option.
            final ViewHolder finalHolder = holder;
            holder.company.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String companyText = finalHolder.myInvoice.getCompanyName().toString();
                    String datePaidText = finalHolder.myInvoice.getPaidDate().toString();
                    String paidText = finalHolder.myInvoice.getAmountPaid().toString();
                    String invoiceNumberText = finalHolder.myInvoice.getInvoiceNumber().toString();


                    Intent i = new Intent(invoiceList.this, InvoiceDetailView.class);
                    i.putExtra("company", companyText);
                    i.putExtra("datePaid", datePaidText);
                    i.putExtra("paid", paidText);
                    i.putExtra("id", invoiceNumberText);

                    startActivity(i);
                }
            });

            return row;
        }

        class ViewHolder{

            InvoiceHome myInvoice;
            TextView company;
            TextView datePaid;
            TextView paid;
            TextView invoiceNumber;
        }
    }
}
