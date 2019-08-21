package com.example.gagan.dailyserices.TutorProfile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gagan.dailyserices.Beans.RetreiveresultBean;
import com.example.gagan.dailyserices.Beans.RetreivetutorBean;
import com.example.gagan.dailyserices.Beans.Tutorbean;
import com.example.gagan.dailyserices.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralInfoFragment extends Fragment {
    TextView txttiming,txtcharges,txthometutor,txtdescription,txtexperience,txtqualification;


    public GeneralInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_general_info, container, false);
        txtcharges=(TextView)view.findViewById(R.id.textViewCharges);
        txtcharges.setText(RetreiveresultBean.Charges);
        txttiming=(TextView)view.findViewById(R.id.textViewTiming);
        txttiming.setText(RetreiveresultBean.Timing1+" to "+RetreiveresultBean.Timing2);
        txthometutor=(TextView)view.findViewById(R.id.textViewhometutor);
        txthometutor.setText(RetreiveresultBean.Hometutor);
        txtdescription=(TextView)view.findViewById(R.id.textViewdescription);
        txtdescription.setText(RetreiveresultBean.Desciption);
        txtexperience=(TextView)view.findViewById(R.id.textViewExperience);
        txtexperience.setText(RetreiveresultBean.Experience);
        txtqualification=(TextView)view.findViewById(R.id.textViewQualification);
        txtqualification.setText(RetreiveresultBean.Qualification);

        return view;
    }

}
