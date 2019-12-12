package com.example.brm77.brm_my_lesson;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ListView;

import com.example.brm77.brm_my_lesson.data.*;
import java.util.ArrayList;
import com.example.brm77.brm_my_lesson.R;


public class LessonsFragment extends Fragment {

    private LessonHelper mDBHelper;
//    private MyCursorAdapter MyAdapter;
    final String LOG_TAG = "myLogs";
    private MyCursorAdapter_RecyclerView MyRVAdapter;
    ListView lvTheme;
    SimpleCursorAdapter scAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDBHelper = new LessonHelper(getActivity());
        setHasOptionsMenu(true);
        setRetainInstance(true);

        ArrayList lessons = mDBHelper.lessonsArrayList();

        MyRVAdapter = new MyCursorAdapter_RecyclerView(lessons);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     RecyclerView recyclerView = new RecyclerView(getActivity());
     recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
     recyclerView.setAdapter(MyRVAdapter);

     return recyclerView;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
     }

    @Override
    public void onResume() {



        ArrayList lessonsArray = mDBHelper.lessonsArrayList();

        if (lessonsArray.size() == 0) {
            if (MyRVAdapter == null) { super.onResume();return;}
            MyRVAdapter.ClearArray();
            MyRVAdapter.notifyDataSetChanged();
           super.onResume();
           return;
        }

        MyRVAdapter.UpdateArray(lessonsArray);
        MyRVAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lessons,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
