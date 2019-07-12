package com.htetaunglin.basicfirestore;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.htetaunglin.basicfirestore.model.Contact;
import com.htetaunglin.basicfirestore.model.ContactDao;
import com.htetaunglin.basicfirestore.model.ContactDaoImpl;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton FAButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        FAButton = findViewById(R.id.floatingActionButton);

    }


    public void faButtonClick(View v) {

        ContactDao dao = ContactDaoImpl.getInstance();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        View dialog_view = LayoutInflater.from(this).inflate(R.layout.input_dialog, null, false);
        EditText ed_name = dialog_view.findViewById(R.id.input_name);
        EditText ed_phone = dialog_view.findViewById(R.id.input_phno);

        alertDialog.setView(dialog_view);
        alertDialog.setPositiveButton("Submit", (dialogInterface, i) -> dao.save(new Contact(ed_name.getText().toString(), ed_phone.getText().toString())))
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        alertDialog.create().show();

    }

}
