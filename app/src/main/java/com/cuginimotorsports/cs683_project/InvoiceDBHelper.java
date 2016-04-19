package com.cuginimotorsports.cs683_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class InvoiceDBHelper extends SQLiteOpenHelper {

    //array list to pull all the invoice data together and add them to the invoice.
    private final ArrayList<InvoiceHome> allInvoices = new ArrayList<>();

    //All my final strings for the database objects involved in creating the sqite database.
    public static final String Invoice_Table = "Invoices";
    public static final String Company_Name = "company";
    public static final String Paid_Date = "datePaid";
    public static final String Primary_Key = "id";
    public static final String Amount_Paid = "paid";
    public static final int Database_Version = 1;
    public static final String Database_Name = "PaidInvoices.db";

    //The table pulls is called "Invoices" and it's primary Key is id.
    //The table has columns: company, invoiceNumber, datepaid, and paid.
    private static final String Table_Specs =
            "Create Table " + Invoice_Table + "(" + Primary_Key + " INTEGER PRIMARY KEY, " +
                    Company_Name + " STRING, " + Paid_Date + " DATE, " + Amount_Paid + " NUMBER) ";

    //declares that the database exists named "PaidInvoices.db" on database version "1"
    public InvoiceDBHelper(Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    //Creates a table with the table specs implemented in "Table_specs"
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Table_Specs);
    }

    //This method drops the table if it exists so that errors aren't thrown if a column is added
    //to an already existing database.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + Invoice_Table);
        onCreate(db);
    }


    //Adds data values to the sqlite database using the get methods laid out in the InvoiceHome.java
    //class. The values are then inserted into the database table.
    public String addInvoiceError;
    public String getAddInvoiceError() {
        return addInvoiceError;
    }
    public void setAddInvoiceError(String addInvoiceError) {this.addInvoiceError = addInvoiceError;}

    public void addInvoices(InvoiceHome invoice) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(Company_Name, invoice.getCompanyName());
            values.put(Paid_Date, invoice.getPaidDate());
            values.put(Amount_Paid, invoice.getAmountPaid());
            values.put(Primary_Key, invoice.getInvoiceNumber());

            /*Updated this section to insertOrThrow to display an error message if the invoice
            number already exists in the table. The default value is "Added Invoice Successfully".
            If the invoiceNumber exists already, then the catch error will display in the toast.
             */
            db.insertOrThrow(Invoice_Table, null, values);
            setAddInvoiceError("Successfully Added Invoice Number " + invoice.getInvoiceNumber());
        } catch (Exception Error) {
            setAddInvoiceError("Invoice Number " + invoice.getInvoiceNumber() + " Already Exists!");
        } finally {
            db.close();
        }
    }

    public void deleteInvoice(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Invoice_Table, Primary_Key + " = ? ",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<InvoiceHome> getAllInvoices() {

        SQLiteDatabase db = this.getReadableDatabase();

        //Adds data each invoice to the database until all new entries are recorded.
        Cursor cursor = db.query(Invoice_Table, new String[]{Primary_Key, Company_Name,
                        Primary_Key, Paid_Date, Amount_Paid,}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                InvoiceHome invoices = new InvoiceHome();
                invoices.setCompanyName(cursor.getString(cursor.getColumnIndex(Company_Name)));
                invoices.setInvoiceNumber(cursor.getString(cursor.getColumnIndex(Primary_Key)));
                invoices.setPaidDate(cursor.getString(cursor.getColumnIndex(Paid_Date)));
                invoices.setAmountPaid(cursor.getString(cursor.getColumnIndex(Amount_Paid)));

                allInvoices.add(invoices);

            } while (cursor.moveToNext());
        }

        return allInvoices;
    }

}