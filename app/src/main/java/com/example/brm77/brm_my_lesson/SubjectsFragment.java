package com.example.brm77.brm_my_lesson;

//import android.app.ListFragment;
import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.*;
import android.widget.ListView;
import com.example.brm77.brm_my_lesson.data.LessonContract;
import com.example.brm77.brm_my_lesson.data.LessonHelper;
import com.example.brm77.brm_my_lesson.data.MyCursorAdapter;

public class SubjectsFragment extends ListFragment {

    private LessonHelper mDBHelper;
    private MyCursorAdapter MyAdapter;
    final String LOG_TAG = "myLogs";
    ListView lvTheme;
    SimpleCursorAdapter scAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDBHelper = new LessonHelper(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subjects,container,false);

        // Inflate the layout for this fragment
        return view;


    }

    @Override
    public void onStart() {
        super.onStart();
        displayDataBaseInfo();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_subjects,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(v.getContext(), SubjectsAdd.class);
        intent.putExtra("ID",id);
        startActivity(intent);
    }

    private void displayDataBaseInfo(){

        //Создадим и откроем для чтения базу данных
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        //Запрос
        Cursor cursor = db.rawQuery("Select * from " + LessonContract.SubEntry.TABLE_NAME_Subjects, null);

        ListView listViewSubjects = (ListView)getActivity().findViewById(android.R.id.list);
        MyAdapter = new MyCursorAdapter(getActivity(),cursor,0);

        listViewSubjects.setAdapter(MyAdapter);
    }



}
