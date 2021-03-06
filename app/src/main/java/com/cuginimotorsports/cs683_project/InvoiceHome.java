package com.cuginimotorsports.cs683_project;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class InvoiceHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //This java class contains the get methods used in the InvoiceDBHelper java class
    //that are used to pull data from the user EditText lines in the enter_invoice layout.
    public String CompanyName;
    public String PaidDate;
    public String AmountPaid;
    public String InvoiceNumber;

    public String getCompanyName() {return CompanyName;}
    public void setCompanyName(String CompanyName) {this.CompanyName = CompanyName;}

    public String getPaidDate() {
        return PaidDate;
    }
    public void setPaidDate(String PaidDate) {this.PaidDate = PaidDate;}

    public String getAmountPaid() {return AmountPaid;}
    public void setAmountPaid(String AmountPaid) {this.AmountPaid = AmountPaid;}

    public String getInvoiceNumber () {
        return InvoiceNumber;
    }
    public void setInvoiceNumber(String InvoiceNumber) {this.InvoiceNumber = InvoiceNumber;}


}

