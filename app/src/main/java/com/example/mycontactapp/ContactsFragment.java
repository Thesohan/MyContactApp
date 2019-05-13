package com.example.mycontactapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContactsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {


    /*
     * Defines an array that contains column names to move from
     * the Cursor to the ListView.
     */
    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };
    /*
     * Defines an array that contains resource ids for the layout views
     * that get the Cursor column contents. The id is pre-defined in
     * the Android framework, so it is prefaced with "android.R.id"
     */
    private final static int[] TO_IDS = {
            android.R.id.text1
    };
    // Define global mutable variables
    // Define a ListView object
    ListView contactsList;
    // Define variables for the contact the user selects
    // The contact's _ID value
    long contactId;
    // The contact's LOOKUP_KEY
    String contactKey;
    // A content URI for the selected contact
    Uri contactUri;
    // An adapter that binds the result Cursor to the ListView
    private SimpleCursorAdapter cursorAdapter;

    // Empty public constructor, required by the system
    public ContactsFragment() {}

    // A UI Fragment must inflate its View



    // The column index for the _ID column
    private static final int CONTACT_ID_INDEX = 0;
    // The column index for the CONTACT_KEY column
    private static final int CONTACT_KEY_INDEX = 1;

    // Defines the text expression
    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";
    // Defines a variable for the search string
    private String searchString="%"; //to retrieve all contacts
    // Defines the array to hold values that replace the ?
    private String[] selectionArgs = { searchString };
    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION =
            {
                    /*
                     * The detail data row ID. To make a ListView work,
                     * this column is required.
                     */
                    ContactsContract.Data._ID,
                    // The primary display name
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Data.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Data.DISPLAY_NAME,
                    // The contact's _ID, to construct a content URI
                    ContactsContract.Data.CONTACT_ID,
                    // The contact's LOOKUP_KEY, to construct a content URI
                    ContactsContract.Data.LOOKUP_KEY // A permanent link to the contact
            };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.contacts_list_view,container, false);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        /*
         * Makes search string into pattern and
         * stores it in the selection array
         */
        selectionArgs[0] = "%" + searchString + "%";
        // Starts the query
        return new CursorLoader(
                getActivity(),
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION,
                SELECTION,
                selectionArgs,
                null
        );
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Put the result Cursor in the adapter for the ListView
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Delete the reference to the existing Cursor
        cursorAdapter.swapCursor(null);

    }
    @SuppressLint("ResourceType")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Gets the ListView from the View list of the parent activity
        contactsList =
                (ListView) getActivity().findViewById(R.id.list);
        // Gets a CursorAdapter
        cursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                FROM_COLUMNS, TO_IDS,
                0);
        // Sets the adapter for the ListView
        contactsList.setAdapter(cursorAdapter);
        contactsList.setOnItemClickListener(this);

        // Initializes the loader
        getLoaderManager().initLoader(0, null, this);


    }

    @Override
    public void onItemClick(
            AdapterView<?> parent, View item, int position, long rowID) {
        // Get the Cursor
        Cursor cursor = ((SimpleCursorAdapter) parent.getAdapter()).getCursor();
        // Move to the selected contact
        cursor.moveToPosition(position);
        // Get the _ID value
        contactId = cursor.getLong(CONTACT_ID_INDEX);
        // Get the selected LOOKUP KEY
        contactKey = cursor.getString(CONTACT_KEY_INDEX);
        // Create the contact's content Uri
        contactUri = ContactsContract.Contacts.getLookupUri(contactId, contactKey);
        /*
         * You can use contactUri as the content URI for retrieving
         * the details for a contact.
         */

        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout,ContactDetailsFragment.newInstance(contactKey)).commit();

    }
}
