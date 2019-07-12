package com.htetaunglin.basicfirestore;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Source;
import com.htetaunglin.basicfirestore.adapter.ContactAdapter;
import com.htetaunglin.basicfirestore.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton FAButton;

    private static final String collection = "ContactCollection";
    private ArrayList<Contact> contacts;
    private ContactAdapter adapter;

    private static final CollectionReference crf = FirebaseFirestore.getInstance().collection(collection);

    static String contactID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recycler);
        FAButton = findViewById(R.id.floatingActionButton);

        recyclerDecoration();

        recyclerDataBind();


    }

    private void recyclerDataBind() {

        crf.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null)
                e.printStackTrace();

            if (queryDocumentSnapshots != null) {
                contacts.clear();
                for (QueryDocumentSnapshot qds : queryDocumentSnapshots) {
                    Contact contact = qds.toObject(Contact.class);

                    contacts.add(contact);
                    adapter.notifyDataSetChanged();
                }
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

    public static void showInsertForm(View v) {

        showInsertForm(v, new Contact());

    }


    public static void showInsertForm(View v, Contact contact) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

        View dialog_view = LayoutInflater.from(v.getContext()).inflate(R.layout.input_dialog, null, false);

        EditText contactName = dialog_view.findViewById(R.id.input_name);
        EditText contactPhone = dialog_view.findViewById(R.id.input_phno);


        if (contact.getId() == null)
            contactID = String.valueOf(System.currentTimeMillis());
        else {
            contactID = contact.getId();
            contactName.setText(contact.getName());
            contactPhone.setText(contact.getPhone());
        }

        alertDialog.setView(dialog_view);

        alertDialog
                .setPositiveButton("Submit", (dialogInterface, i) ->
                        crf.document(contactID)
                                .set(new Contact(contactID, contactName.getText().toString(), contactPhone.getText().toString()))
                                .addOnSuccessListener(runnable -> Toast.makeText(v.getContext(), "Success Save", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(runnable -> Toast.makeText(v.getContext(), "Success Save", Toast.LENGTH_SHORT).show()))
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        alertDialog.create().show();
    }

}
