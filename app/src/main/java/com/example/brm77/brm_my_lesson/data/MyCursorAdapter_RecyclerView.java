package com.example.brm77.brm_my_lesson.data;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brm77.brm_my_lesson.LessonsAdd;
import com.example.brm77.brm_my_lesson.R;


import java.util.List;



public class MyCursorAdapter_RecyclerView extends RecyclerView.Adapter<MyCursorAdapter_RecyclerView.LessonViewHolder>{

    private List<Lessons> lessonList;

    public MyCursorAdapter_RecyclerView(List<Lessons> lessonList){
        this.lessonList = lessonList;
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item,parent,false);
        final LessonViewHolder lessonViewHolder = new LessonViewHolder(view);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final int position = lessonViewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    int idLesson = lessonList.get(position).getID();
                    Log.d("pos","positoin - "+ position + "id - "+ idLesson);
                    Intent intent = new Intent(v.getContext(), LessonsAdd.class);
                    intent.putExtra("ID_Lesson",idLesson);
                    v.getContext().startActivity(intent);


                }
            }
        });
        return lessonViewHolder;
    }

    @Override
    public void onBindViewHolder(LessonViewHolder holder, final int position) {
        Lessons lesson = lessonList.get(position);
        TextView name = (TextView) holder.itemView.findViewById(R.id.Lesson_name);
        name.setText(lesson.getName());

    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {

       public LessonViewHolder(View itemView){
            super(itemView);

        }

    }

    public void UpdateArray(List<Lessons> lessonList){
        this.lessonList = lessonList;
    }

    public void ClearArray(){
        this.lessonList.clear();
    }




}