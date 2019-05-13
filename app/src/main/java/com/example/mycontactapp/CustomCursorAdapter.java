package com.example.mycontactapp;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomCursorAdapter extends CursorAdapter {
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public CustomCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = mLayoutInflater.inflate(R.layout.items_row, parent, false);
        return v;
    }

    /**
     * @author will
     *
     * @param   v
     *          The view in which the elements we set up here will be displayed.
     *
     * @param   context
     *          The running context where this ListView adapter will be active.
     *
     * @param   c
     *          The Cursor containing the query results we will display.
     */

    @Override
    public void bindView(View v, Context context, Cursor c) {
        String title = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        Log.d("title",title);
//        String date = c.getString(c.getColumnIndexOrThrow(ItemDbAdapter .KEY_DATE));
//        String imagePath = c.getString(c.getColumnIndexOrThrow(ItemDbAdapter.KEY_IMG));
//        int deletion = c.getInt(c.getColumnIndexOrThrow(ItemDbAdapter.KEY_DELETION));
//        int priority = c.getInt(c.getColumnIndexOrThrow(ItemDbAdapter.KEY_PRIORITY));
//
//        /**
//         * Next set the title of the entry.
//         */
//
//        TextView title_text = (TextView) v.findViewById(R.id.item_text);
//        if (title_text != null) {
//            title_text.setText(title);
//        }
//
//        /**
//         * Set Date
//         */
//
//        TextView date_text = (TextView) v.findViewById(R.id.item_date);
//        if (date_text != null) {
////            date_text.setText(date);
//        }
//
//        /**
//         * Decide if we should display the paper clip icon denoting image attachment
//         */
//
//        ImageView item_image = (ImageView) v.findViewById(R.id.item_attachment);
//        item_image.setVisibility(ImageView.INVISIBLE);
////        if (imagePath != null && imagePath.length() != 0 && item_image != null) {
//            item_image.setVisibility(ImageView.VISIBLE);
//        }
//
//        /**
//         * Decide if we should display the deletion indicator
//         */
////        ImageView del_image = (ImageView) v.findViewById(R.id.item_deletion);
////        del_image.setVisibility(ImageView.INVISIBLE);
////        if (deletion == 1) {
////            del_image.setVisibility(ImageView.VISIBLE);
////        }
    }
}
