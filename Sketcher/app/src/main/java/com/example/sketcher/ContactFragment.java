
package com.example.sketcher;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ContactFragment extends Fragment {
    List<String> contacts;
    Uri fileURI;

    /**
     * This method is called when the View is created. It will call the Content Provider to
     * take the contacts, adding them to an ArrayAdapter to view on ListView, and set listener
     * to the items on the ListView to make an intent to send email.
     * @param inflater  The LayoutInflater of the Fragment.
     * @param container The View containing the View of the Fragment.
     * @param savedInstanceState    The saved instance of the Fragment.
     * @return  The View of the Fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        Bundle args = getArguments();
        fileURI = args.getParcelable("fileURI");
        contacts = new ArrayList<String>();
        addContact(contacts);
        String[] contactArray = contacts.toArray(new String[contacts.size()]);

        ListView contactListView = (ListView) rootView.findViewById(R.id.contact_view);

        ArrayAdapter<String> arrayAdapter = new
                ArrayAdapter<String>(getActivity(), R.layout.contact_row,
                R.id.contact_row_view, contactArray);

        contactListView.setAdapter(arrayAdapter);
        sendEmail(contactListView);
        return rootView;
    }

    /**
     * This method sets listener to items on the ListView, and when one of them is clicked, it will
     * send an intent to send email with the drawing to the specified address.
     * @param lv    The ListView containing the contacts.
     */
    public void sendEmail(ListView lv) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String info = (String) parent.getAdapter().getItem(position);
                String contactId = getIdFromRow(info);
                String email = getEmail(contactId);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("vnd.android.cursor.dir/email");
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {email});

                intent.putExtra(android.content.Intent.EXTRA_STREAM, fileURI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                getActivity().startActivity(intent);
            }
        });
    }

    /**
     * This method is used to add contacts from the ContentProvider.
     * @param contacts  The List of String to add contacts into.
     */
    private void addContact(List<String> contacts) {
        Cursor cursor = getActivity().getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while
        (cursor.moveToNext()) {
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contacts.add(name + " :: " + contactId);
        }
        cursor.close();
    }

    /**
     * This method is used to get contactId from the specified row in the ArrayAdapter
     * @param row   The row String to get the contactId.
     * @return  The String representation of the contactId.
     */
    private String getIdFromRow(String row) {
        String retVal;

        int k = 0;
        for (int i = row.length() - 1; i >= 0; i--) {
            if (row.charAt(i) == ':') {
                k = i;
                break;
            }
        }
        String idString = row.substring(k + 2);

        //retVal = Integer.parseInt(idString);

        return idString;
    }

    /**
     * This method gets email from the specified contactId.
     * @param contactId The contactId to query for email.
     * @return  The String representation of the email.
     */
    private String getEmail(String contactId) {
        String retVal = "";
        Cursor emails = getActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID +
                        " = "
                        + contactId, null, null);
        if (emails.moveToNext()) {
            String email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
            retVal = email;
        }
        emails.close();

        return retVal;
    }
}