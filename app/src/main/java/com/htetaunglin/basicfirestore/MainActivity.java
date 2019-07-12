package com.htetaunglin.basicfirestore;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.htetaunglin.basicfirestore.adapter.ContactAdapter;
import com.htetaunglin.basicfirestore.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private static final String collection = "ContactCollection";
    ArrayList<Contact> contacts;
    ContactAdapter adapter;

    CollectionReference crf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crf = FirebaseFirestore.getInstance().collection(collection);

        recyclerView = findViewById(R.id.recycler);

        recyclerDecoration();

        recyclerDataBind();


    }

    private void recyclerDataBind() {

        crf.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null)
                e.printStackTrace();

            contacts.clear();
            for (QueryDocumentSnapshot qds : queryDocumentSnapshots) {
                Contact contact = qds.toObject(Contact.class);

                contacts.add(contact);
                adapter.notifyDataSetChanged();
            }
        });

    }

    public void recyclerDecoration() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        contacts = new ArrayList<>();
        adapter = new ContactAdapter(this, contacts);
        recyclerView.setAdapter(adapter);

    }


    public void faButtonClick(View v) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        View dialog_view = LayoutInflater.from(this).inflate(R.layout.input_dialog, null, false);

        String contactID = String.valueOf(System.currentTimeMillis());
        EditText contactName = dialog_view.findViewById(R.id.input_name);
        EditText contactPhone = dialog_view.findViewById(R.id.input_phno);

        alertDialog.setView(dialog_view);

        alertDialog
                .setPositiveButton("Submit", (dialogInterface, i) ->
                        crf.document(contactID)
                                .set(new Contact(contactID, contactName.getText().toString(), contactPhone.getText().toString()))
                                .addOnSuccessListener(runnable -> Toast.makeText(this, "Success Save", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(runnable -> Toast.makeText(this, "Success Save", Toast.LENGTH_SHORT).show()))
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        alertDialog.create().show();

    }

}
