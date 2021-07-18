package com.example.expensetracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText expense, amount, date;
    Button insert, update, delete, view;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expense = findViewById(R.id.etexpense);
        amount = findViewById(R.id.etamount);
        date = findViewById(R.id.etdate);
        insert = findViewById(R.id.btnadd);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnshow);
        DB = new DBHelper(this);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setIcon(R.mipmap.expensetrackericon2);
        actionBar.setTitle(" ExpenseTracker");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expensetxt = expense.getText().toString();
                String amounttxt = amount.getText().toString();
                String datetxt = date.getText().toString();

                Boolean checkinsertdata = DB.insertuserdata(expensetxt, amounttxt, datetxt);
                if(checkinsertdata==true)
                    Toast.makeText(MainActivity.this, "New Expense Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Expense Not Inserted", Toast.LENGTH_SHORT).show();
            }        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expensetxt = expense.getText().toString();
                String amounttxt = amount.getText().toString();
                String datetxt = date.getText().toString();

                Boolean checkupdatedata = DB.updateuserdata(expensetxt, amounttxt, datetxt);
                if(checkupdatedata==true)
                    Toast.makeText(MainActivity.this, "Expense Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Expense Not Updated", Toast.LENGTH_SHORT).show();
            }        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expensetxt = expense.getText().toString();
                Boolean checkudeletedata = DB.deletedata(expensetxt);
                if(checkudeletedata==true)
                    Toast.makeText(MainActivity.this, "Expense Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Expense Not Deleted", Toast.LENGTH_SHORT).show();
            }        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(MainActivity.this, "No Expenses Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Expensetype :"+res.getString(0)+"\n");
                    buffer.append("Amount :"+res.getString(1)+"\n");
                    buffer.append("Date:"+res.getString(2)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Expenses List");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });
    }}
