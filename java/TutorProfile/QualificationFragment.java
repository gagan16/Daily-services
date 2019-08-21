package com.example.gagan.dailyserices.TutorProfile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gagan.dailyserices.Beans.Tutorbean;
import com.example.gagan.dailyserices.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QualificationFragment extends Fragment {

    TextView txtqualification,txtexperience;

    public QualificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_qualification, container, false);

        txtexperience=(TextView)view.findViewById(R.id.textViewexperience);
        txtexperience.setText(Tutorbean.Experience);
        txtqualification=(TextView)view.findViewById(R.id.textViewqualification);
        txtqualification.setText(Tutorbean.Qualification);

        return view;
    }

}
