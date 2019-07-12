package com.htetaunglin.basicfirestore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.htetaunglin.basicfirestore.MainActivity;
import com.htetaunglin.basicfirestore.R;
import com.htetaunglin.basicfirestore.model.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Context context;
    private List<Contact> contactList;

    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ContactViewHolder(LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactViewHolder contactViewHolder, int i) {

        contactViewHolder.tv_first_letter.setText(String.valueOf(contactList.get(i).getName().charAt(0)).toUpperCase());
        contactViewHolder.tv_name.setText(contactList.get(i).getName());
        contactViewHolder.tv_phone.setText(contactList.get(i).getPhone());
        contactViewHolder.menu.setOnClickListener(v -> showPopup(v, contactList.get(i)));

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void showPopup(View v, Contact contact) {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(menuItem -> {

            switch (menuItem.getItemId()) {

                case R.id.menu_edit:
                    MainActivity.showInsertForm(v, contact);
                    break;
                case R.id.menu_delete:
                    deleteAlertDialog(contact);
                    break;

            }
            return true;
        });
        popup.show();
    }

    private void deleteAlertDialog(Contact contact) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Delete");
        alertDialog.setMessage("Are you want to delete?");
        alertDialog.setPositiveButton("Confirm", (dialogInterface, i) -> {
            CollectionReference crf = FirebaseFirestore.getInstance().collection("ContactCollection");
            crf.document(contact.getId()).delete();
        }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialog.create().show();
    }


    class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView tv_first_letter;
        TextView tv_name;
        TextView tv_phone;
        ImageView menu;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_first_letter = itemView.findViewById(R.id.first_letter);
            tv_name = itemView.findViewById(R.id.name);
            tv_phone = itemView.findViewById(R.id.phoneno);
            menu = itemView.findViewById(R.id.menu);
        }
    }
}
