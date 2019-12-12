package com.example.brm77.brm_my_lesson.data;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter{

       public MyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        TextView view = new TextView(context);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String id = cursor.getString(0);
        String name = cursor.getString(1);
        // Get all the values
        // Use it however you need to
        TextView textView = (TextView) view;
        textView.setText(name);
    }
}
