package com.example.mycontactapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactDetailsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>{


    /*
      Defines a string that specifies a sort order of MIME type
     Defines a constant that identifies the loader


    // Defines the selection clause
    // Defines the array to hold the search criteria

     * Defines a variable to contain the selection value. Once you
     * have the Cursor from the Contacts table, and you've selected
     * the desired row, move the row's LOOKUP_KEY value into this
     * variable.
     */

    private ImageView imageView;
    private ListView detailsList;
    private ArrayList<String> detailArrayList;
    private ArrayAdapter detailsListAdapter;
    private  String lookupKey;
    private static final int DETAILS_QUERY_ID = 0;
    private String[] selectionArgs = { "" };
    private static final String SELECTION = ContactsContract.Data.LOOKUP_KEY + " = ?";
    private static final String SORT_ORDER = ContactsContract.CommonDataKinds.Phone._ID + " ASC ";

    private static final String[] PROJECTION =
            {
                    ContactsContract.CommonDataKinds.Email._ID,
                    ContactsContract.CommonDataKinds.Email.ADDRESS,
                    ContactsContract.CommonDataKinds.Email.TYPE,
                    ContactsContract.CommonDataKinds.Email.LABEL,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI,
                    ContactsContract.Data.CONTACT_STATUS
            };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Initializes the loader framework

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
                SORT_ORDER
        );

    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        Log.d("column", "" + cursor.getColumnCount());
        Log.d("row", "" + cursor.getCount());


        boolean firstTime=true;
        while(cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                try {
                    if(firstTime) {
                        firstTime=false;
                        imageView.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndex(PROJECTION[5]))));
                    }

                    String detail=cursor.getColumnName(i) + ":    " + cursor.getString(cursor.getColumnIndex(PROJECTION[i]));
                    if(!detailArrayList.contains(detail))
                        detailArrayList.add(detail);
                    detailsListAdapter.notifyDataSetChanged();
                    Log.d("heloo", cursor.getColumnName(i) + "  " + cursor.getString(cursor.getColumnIndex(PROJECTION[i])));
                } catch (Exception e) {
                    Log.d("heloo", e.getMessage());
                }
            }
        }
//        }

    }
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.contact_details,container, false);
        detailsList=view.findViewById(R.id.detailsList);
        imageView=view.findViewById(R.id.image);
        detailArrayList=new ArrayList<>();
        detailsListAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,detailArrayList);
        detailsList.setAdapter(detailsListAdapter);
        return view;
    }

    public static ContactDetailsFragment newInstance(String lookupKey) {

        Bundle args = new Bundle();
        args.putString("LOOKUPKEY",lookupKey);
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

}

