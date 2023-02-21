package org.techtown.callog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DiaryFragment extends Fragment {

    private View view;
    private TextView dateTextView;
    private String day;


    public static DiaryFragment newInstance() {
        return new DiaryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calendar_diary, container, false);
        dateTextView = view.findViewById(R.id.dateTextView);


        if (getArguments() != null)
        {
            day = getArguments().getString("date"); // 프래그먼트1에서 받아온 값 넣기
            dateTextView.setText(day);
        }


        return view;
    }
}