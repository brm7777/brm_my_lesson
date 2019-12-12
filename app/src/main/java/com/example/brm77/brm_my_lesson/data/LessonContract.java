package com.example.brm77.brm_my_lesson.data;

import android.provider.BaseColumns;

public final class LessonContract {
    private LessonContract(){

    };

    public static final class SubEntry implements BaseColumns{
        public final static String TABLE_NAME_Subjects = "subjects";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
    }

    public static final class LessonsEntry implements BaseColumns{
        public final static String TABLE_NAME_Lessons = "lessons";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_Subjects_ID = "subject_id";
    }

}
