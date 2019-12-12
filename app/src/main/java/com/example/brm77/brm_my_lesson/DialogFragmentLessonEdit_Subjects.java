package com.example.brm77.brm_my_lesson;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.brm77.brm_my_lesson.data.LessonContract;
import com.example.brm77.brm_my_lesson.data.LessonHelper;
import com.example.brm77.brm_my_lesson.data.MyCursorAdapter;

public class DialogFragmentLessonEdit_Subjects extends DialogFragment {
    private LessonHelper mDBHelper;
    private MyCursorAdapter MyAdapter;
    public String SubjectName;
    public Long id_subject;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog,String Subject,long id_subject);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener mListner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDBHelper = new LessonHelper(getActivity());

        //Создадим и откроем для чтения базу данных
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        //Запрос
        final Cursor cursor = db.rawQuery("Select * from " + LessonContract.SubEntry.TABLE_NAME_Subjects, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите предмет");
        //Переключатели
        builder.setSingleChoiceItems(cursor, -1,"name" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            cursor.moveToPosition(which);
            SubjectName = cursor.getString(cursor.getColumnIndex(LessonContract.SubEntry.COLUMN_NAME));
            id_subject = cursor.getLong(cursor.getColumnIndex(LessonContract.SubEntry._ID));

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListner.onDialogPositiveClick(DialogFragmentLessonEdit_Subjects.this,SubjectName,id_subject);
            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListner.onDialogNegativeClick(DialogFragmentLessonEdit_Subjects.this);
            }
        });

//        return super.onCreateDialog(savedInstanceState);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListner = (NoticeDialogListener)getActivity();

        }catch (ClassCastException e){
//            throw new ClassCastException(activity.toString()
//                    + " must implement NoticeDialogListener");
        }
    }


}
