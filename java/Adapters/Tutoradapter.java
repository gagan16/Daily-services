 package com.example.gagan.dailyserices.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.RippleDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.gagan.dailyserices.Beans.RetreivetutorBean;
import com.example.gagan.dailyserices.Beans.Tutorbean;
import com.example.gagan.dailyserices.R;
import com.example.gagan.dailyserices.TutorProfileActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

 /**
  * Created by gagan on 12/3/17.
  */

 public class Tutoradapter extends ArrayAdapter<RetreivetutorBean> {

     Context cxt;

     int res;

     ArrayList<RetreivetutorBean> tutorList;



     public Tutoradapter(Context context, int resource, ArrayList<RetreivetutorBean> objects) {
         super(context, resource, objects);

         cxt = context;
         res = resource;
         tutorList = objects;

     }


     @Override
     public View getView(int position, View convertView, ViewGroup parent) {

         View view = LayoutInflater.from(cxt).inflate(R.layout.tutor, parent, false);
         String name; String s; ImageView ivIcon=null;
         String[] split ;
         TextView txtName,txtoccupation,txttiming,txtexperience,txtcharges;
         ivIcon = (ImageView) view.findViewById(R.id.imageViewtutor);
         txtName = (TextView) view.findViewById(R.id.textViewtutorname);
         txtoccupation = (TextView) view.findViewById(R.id.textViewoccupation);
         txttiming = (TextView) view.findViewById(R.id.textViewtiming);
         txtexperience = (TextView) view.findViewById(R.id.textViewexperience);
         txtcharges = (TextView) view.findViewById(R.id.textViewcharges);

         RetreivetutorBean p = tutorList.get(position);
         p.toString();
         s=p.getEmail();
         split = s.split("@");
         name = split[0];
         txtName.setText(p.getName());
         txtoccupation.setText(p.getOccupation());
         txttiming.setText("Available from "+p.getTiming1()+" to "+p.getTiming2());
         txtexperience.setText("Experience  "+p.getExperience());
         txtcharges.setText("Charges-"+p.getCharges());
      //   Toast.makeText(getContext(),name,Toast.LENGTH_LONG).show();
         //
       try{ new DownloadImageTask(ivIcon).execute("http://tablepay.esy.es/photos/"+name+".png");}
        catch (Exception e){
                   ivIcon.setImageResource(R.drawable.defaultprofilepic);
        }

         return view;
     }
     public void clearData() {
         // clear the data
         tutorList.clear();
     }

 }




