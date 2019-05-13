package com.example.mycontactapp;

import android.database.Cursor;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContactDetailsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>{

    CustomCursorAdapter cursorAdapter;
    ListView contactsList;

    private static final String[] PROJECTION =
            {
                    ContactsContract.Data.PHOTO_THUMBNAIL_URI,
                    ContactsContract.Data.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone._ID,
                    ContactsContract.CommonDataKinds.Email.ADDRESS,
                    ContactsContract.CommonDataKinds.Email.TYPE,
                    ContactsContract.CommonDataKinds.Email.LABEL,
                    ContactsContract.CommonDataKinds.Email.ADDRESS
            };
    /*
     * Defines a string that specifies a sort order of MIME type
         private static final String SORT_ORDER = ContactsContract.CommonDataKinds.Phone._ID + " ASC ";
*/
        // Defines a constant that identifies the loader

private static final int DETAILS_QUERY_ID = 0;


    // Defines the selection clause
    private static final String SELECTION = ContactsContract.Data.LOOKUP_KEY + " = ?";
    // Defines the array to hold the search criteria
    private String[] selectionArgs = { "" };
//    private
    /*
     * Defines a variable to contain the selection value. Once you
     * have the Cursor from the Contacts table, and you've selected
     * the desired row, move the row's LOOKUP_KEY value into this
     * variable.
     */

    String lookupKey;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Initializes the loader framework

        contactsList =
                (ListView) getActivity().findViewById(R.id.list);

        lookupKey=getArguments().getString("LOOKUPKEY");
        Log.d("lookupkey",lookupKey);
        getLoaderManager().initLoader(DETAILS_QUERY_ID, null, this);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        // Choose the proper action

        selectionArgs[0] = lookupKey;

        return new CursorLoader(
                getActivity(),
                ContactsContract.Data.CONTENT_URI,
                PROJECTION,
                SELECTION,
                selectionArgs,
                null
        );

    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                Log.d("column",""+cursor.getColumnCount());
                Log.d("row",""+cursor.getCount());
                for(int i=0;i<cursor.getColumnCount();i++)
                Log.d("name",""+cursor.getColumnName(i));
                try{
                    cursor.moveToNext();
                    Log.d("deeeee",cursor.getString(cursor.getColumnIndex("display_name")));
                }
                catch (Exception e){

                    Log.d("except",e.toString());
                }
//Log.d("cname",""+cursor.getString(cursor.getColumnIndexOrThrow("display_name")));

    }

//                  Log.d("demo",
//                        cursor.getColumnName())
//                Log.d("demo",cursor.getColumnName(12)+" ,"+cursor.getColumnName(4));
//        }
//    }
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contacts_list_view,container, false);

    }

    public static ContactDetailsFragment newInstance(String lookupKey) {

        Bundle args = new Bundle();
        args.putString("LOOKUPKEY",lookupKey);
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

