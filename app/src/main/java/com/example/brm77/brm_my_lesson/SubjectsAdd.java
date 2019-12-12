package com.example.brm77.brm_my_lesson;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.example.brm77.brm_my_lesson.data.LessonContract;
import com.example.brm77.brm_my_lesson.data.LessonHelper;
import com.example.brm77.brm_my_lesson.data.Subjects;

import static com.example.brm77.brm_my_lesson.R.id.edit_subject_name;

public class SubjectsAdd extends AppCompatActivity {

    private EditText mNameEditText;
    private LessonHelper mDBHelper;
    private long ID_SUB = 0;
    private long newRowId;
    private MenuItem delButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_theme_edit, menu);
        delButton = menu.findItem(R.id.action_delete);

        Bundle extras = getIntent().getExtras();
        if (extras != null){ID_SUB = extras.getLong("ID",0);}

        if (ID_SUB == 0){delButton.setVisible(false);}

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_add);

        mNameEditText = (EditText)findViewById(edit_subject_name);
        mDBHelper = new LessonHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){ID_SUB = extras.getLong("ID",0) ;}

        if (ID_SUB > 0){loadDataBaseInfo();}

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();


    }

    private void loadDataBaseInfo(){

        //Создадим и откроем для чтения базу данных
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        //Запрос
        Cursor SubCursor = db.rawQuery("Select * from " + LessonContract.SubEntry.TABLE_NAME_Subjects + " where _ID = ?", new String[]{String.valueOf(ID_SUB)});

        SubCursor.moveToFirst();

        if (SubCursor.getCount() > 0){
            int nameColumnIndex = SubCursor.getColumnIndex(LessonContract.SubEntry.COLUMN_NAME);
            mNameEditText.setText(SubCursor.getString(nameColumnIndex));}
        else {finish();}

        SubCursor.close();
    }

    public void onSaveSubject(MenuItem item){
        Subjects subject;

        if (ID_SUB == 0){subject = new Subjects(mNameEditText.getText().toString());}
        else {subject = new Subjects((int)ID_SUB,mNameEditText.getText().toString());}

        mDBHelper.addSubject(subject);
        finish();

    }

    public void onDeleteSubject(MenuItem item){
        final int resDelete = deleteSubject((int)ID_SUB);
        if (resDelete > 0)
        {
//            Toast toast = Toast.makeText(this,"По данному предмету есть лекции!",Toast.LENGTH_LONG);
//            toast.show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Внимание!")
                    .setMessage("По данному предмету создано " +resDelete+ " лекция(ий)")
                    .setPositiveButton("Удалить лекции", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteLesson();
                            deleteSubject((int)ID_SUB);
                            finish();
                        }
                    })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        else
        {
            Toast toast = Toast.makeText(this,"Успешно!",Toast.LENGTH_LONG);
            toast.show();
            finish();
        }



    }

    public int deleteSubject(int id_subject){
        final int resDelete = mDBHelper.deleteSubject((int)ID_SUB);
        return resDelete;
    }
    public void deleteLesson(){
        final boolean resDelete = mDBHelper.deleteLessons((int)ID_SUB);
        if (resDelete){
            Toast toast = Toast.makeText(this,"Лекции удалены!",Toast.LENGTH_LONG);
            toast.show();
        }
    }

}

