package com.example.brm77.brm_my_lesson;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.brm77.brm_my_lesson.data.LessonContract;
import com.example.brm77.brm_my_lesson.data.LessonHelper;
import com.example.brm77.brm_my_lesson.data.Lessons;

public class LessonsAdd extends AppCompatActivity implements DialogFragmentLessonEdit_Subjects.NoticeDialogListener {

    private EditText mSubEditText;
    private LessonHelper mDBHelper;
    private long ID_Lesson = 0;
    public Integer ID_Subject = 0;
    public String  nameSubject;
    private long newRowId;
    private MenuItem delButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lesson_edit, menu);
        delButton = menu.findItem(R.id.action_delete);

        Bundle extras = getIntent().getExtras();
        if (extras != null){ID_Lesson = extras.getInt("ID_Lesson",0);}

        if (ID_Lesson == 0){delButton.setVisible(false);}

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_add);

        mSubEditText = (EditText)findViewById(R.id.edit_lesson_subject);
        mDBHelper = new LessonHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){ID_Lesson = extras.getInt("ID_Lesson",0) ;}

        if (ID_Lesson > 0){loadDataBaseInfo();}
    }

    private void loadDataBaseInfo(){

        //Создадим и откроем для чтения базу данных
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        //Запрос
        Cursor LessonsCursor = db.rawQuery("Select " +
                "lessons._id ," +
                " lessons.subject_id," +
                " subjects._id ," +
                " subjects.name" +
                " from lessons " +
                    "LEFT JOIN subjects ON lessons.subject_id = subjects._id " +
                "where lessons._id = ?", new String[]{String.valueOf(ID_Lesson)});

        LessonsCursor.moveToFirst();

        if (LessonsCursor.getCount() > 0){
            String SubjectName = LessonsCursor.getString(LessonsCursor.getColumnIndex(LessonContract.SubEntry.COLUMN_NAME));
            mSubEditText.setText(SubjectName);}
        else {finish();}

        LessonsCursor.close();
    }

    public void onSaveLesson(MenuItem item){
        Lessons lesson = new Lessons(ID_Subject,nameSubject);
        mDBHelper.addLesson(lesson);
        finish();

    }

    public void onDeleteLesson(MenuItem item){
        mDBHelper.deleteLesson((int)ID_Lesson);
        finish();
    }

    public void onChoiceSubject(View view) {

        DialogFragmentLessonEdit_Subjects dialogFragmentLessonEdit_subjects = new DialogFragmentLessonEdit_Subjects();
        FragmentManager fragmentManager = getFragmentManager();
        dialogFragmentLessonEdit_subjects.show(fragmentManager,"dialog");
    }

    public void onDialogPositiveClick(DialogFragment dialog,String Subject,long subject_id){
        ID_Subject = (int)subject_id;
        nameSubject = Subject;
        mSubEditText.setText(Subject);
    }



    public void onDialogNegativeClick(DialogFragment dialog){
        Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
    }


}

