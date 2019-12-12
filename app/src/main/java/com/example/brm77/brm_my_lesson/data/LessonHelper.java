package com.example.brm77.brm_my_lesson.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static com.example.brm77.brm_my_lesson.data.LessonContract.SubEntry.TABLE_NAME_Subjects;
import static com.example.brm77.brm_my_lesson.data.LessonContract.LessonsEntry.TABLE_NAME_Lessons;

public class LessonHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = LessonHelper.class.getSimpleName();

    //Имя Файла базы данных
    private static final String DATABASE_NAME = "lectures.db";

    //Версия базы данных
    private static  final  int DATABASE_VERSION = 10;

    //Конструктор
    public LessonHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //--//

    @Override
    public void onCreate(SQLiteDatabase sqLitDeatabase) {
        String SQL_CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_NAME_Subjects + " ("
                + LessonContract.SubEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LessonContract.SubEntry.COLUMN_NAME + " TEXT NOT NULL);";

        String SQL_CREATE_LESSONS_TABLE = "CREATE TABLE " + TABLE_NAME_Lessons + " ("
                + LessonContract.LessonsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LessonContract.LessonsEntry.COLUMN_Subjects_ID + " INTEGER, FOREIGN KEY("+ LessonContract.LessonsEntry.COLUMN_Subjects_ID +") REFERENCES "+ TABLE_NAME_Subjects +"("+LessonContract.SubEntry._ID+") );";

        sqLitDeatabase.execSQL(SQL_CREATE_SUBJECTS_TABLE);
        sqLitDeatabase.execSQL(SQL_CREATE_LESSONS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Запишем в журнал
        Log.w("SQLite", "Обновляемся с версии " + i + " на версию " + i1);

        //Удаляем старую таблицу и создаем новую
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_Subjects + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_Lessons + ";");

        //Создаем новую таблицу
        onCreate(sqLiteDatabase);
    }

    public ArrayList<Lessons>lessonsArrayList(){

        //Создадим и откроем для чтения базу данных
        SQLiteDatabase db = this.getReadableDatabase();

        //Запрос
        Cursor cursor = db.rawQuery("Select " +
                "lessons._id ," +
                "IFNULL(subjects._id,'0')," +
                "IFNULL(subjects.name,'0')" +
                " from lessons " +
                "LEFT JOIN subjects ON lessons.subject_id = subjects._id",null);

        cursor.moveToFirst();

        ArrayList<Lessons> lessons = new ArrayList<Lessons>();

        while (!cursor.isAfterLast()) {
            int id = Integer.parseInt(cursor.getString(0));
            int id_subject = Integer.parseInt(cursor.getString(1));
            String name = cursor.getString(2);
            lessons.add(new Lessons(id,id_subject,name));
            cursor.moveToNext();
        }

        cursor.close();

        return lessons;
    }

    public void addLesson(Lessons Lesson){

        Integer idLesson  = Lesson.getID();
        Integer idSubject = Lesson.getID_Subject();

        ContentValues values = new ContentValues();
        if (idLesson != 0){values.put(LessonContract.LessonsEntry._ID,idLesson);}
        values.put(LessonContract.LessonsEntry.COLUMN_Subjects_ID,idSubject);

        SQLiteDatabase db = this.getWritableDatabase();

        if (idLesson > 0 ){db.update(TABLE_NAME_Lessons, values,LessonContract.LessonsEntry._ID + "=" + String.valueOf(Lesson.getID()),null);}
        else {db.insert(TABLE_NAME_Lessons, null, values);}

    }

    public boolean deleteLessons(int id_subject){

        //Создадим и откроем для чтения базу данных
        SQLiteDatabase db = this.getReadableDatabase();

        //Запрос
        Cursor cursor = db.rawQuery("Select " +
                "lessons._id ," +
                "lessons.subject_id " +
                " from lessons " +
                "Where lessons.subject_id = "+id_subject,null);

        while (cursor.moveToNext()){
            int id_lesson = Integer.parseInt(cursor.getString(0));
            deleteLesson(id_lesson);
        }

        cursor.close();

        return true;
    }

    public void deleteLesson(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_Lessons, LessonContract.LessonsEntry._ID + " = ?", new String[] { String.valueOf(id)});
    }

    public void addSubject(Subjects Subject){
        Integer idSubject = Subject.getId();
        String nameSubject = Subject.getName();


        ContentValues values = new ContentValues();
        if (idSubject > 0){values.put(LessonContract.SubEntry._ID,idSubject);}
        values.put(LessonContract.SubEntry.COLUMN_NAME,nameSubject);

        SQLiteDatabase db = this.getWritableDatabase();

        if (idSubject > 0) {db.update(TABLE_NAME_Subjects,values,LessonContract.SubEntry._ID + "=" + String.valueOf(Subject.getId()),null );}
        else {db.insert(TABLE_NAME_Subjects,null,values);}

    }

    public int deleteSubject(int id){

        int resCheck = protectSubject(id);

        if (resCheck > 0){return resCheck;}

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_Subjects,LessonContract.SubEntry._ID +" = ?", new String[]{String.valueOf(id)});

        return resCheck;
    }

    public int protectSubject(int id){

        //Создадим и откроем для чтения базу данных
        SQLiteDatabase db = this.getReadableDatabase();

        //Запрос
        Cursor cursor = db.rawQuery("Select " +
                "lessons._id ," +
                "lessons.subject_id " +
                " from lessons " +
                "Where lessons.subject_id = "+id,null);

        cursor.moveToFirst();
        int sumLesson = cursor.getCount();

        return sumLesson;

    }



}


