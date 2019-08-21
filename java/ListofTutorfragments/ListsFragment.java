package com.example.gagan.dailyserices.ListofTutorfragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gagan.dailyserices.Adapters.Tutoradapter;
import com.example.gagan.dailyserices.Beans.ResultBean;
import com.example.gagan.dailyserices.Beans.RetreiveresultBean;
import com.example.gagan.dailyserices.Beans.RetreivetutorBean;
import com.example.gagan.dailyserices.Beans.Tutorbean;
import com.example.gagan.dailyserices.ListofTutorActivity;
import com.example.gagan.dailyserices.MainActivity;
import com.example.gagan.dailyserices.R;
import com.example.gagan.dailyserices.RegisterUserActivity;
import com.example.gagan.dailyserices.SeeTutorActivity;
import com.example.gagan.dailyserices.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListsFragment extends Fragment {


    private ArrayList<RetreivetutorBean> tutorlist;
    RetreivetutorBean tutorbean;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    ListView listview;

    Tutoradapter adapter;
    public ListsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_lists, container, false);

        tutorbean = new RetreivetutorBean();
        listview = (ListView)view.findViewById(R.id.mylistview);
        requestQueue = Volley.newRequestQueue(getActivity());
        Toast.makeText(getActivity(),"1",Toast.LENGTH_LONG).show();
        if(Util.spinneron==0&&Util.cityon==0) {
            readtutor();
        }
        else if(Util.spinneron==0&&Util.cityon==1){
            readtutorcity();
        }
        else if(Util.spinneron==1&&Util.cityon==0){
            readtutoroccupation();
        }
        else if(Util.spinneron==1&&Util.cityon==1){
            readtutorboth();
        }
      return view;
    }

    private void readtutorboth() {
        stringRequest = new StringRequest(Request.Method.POST, "http://tablepay.esy.es/retrievebyboth.php",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {

                    try {
                        JSONObject jsonObject = new JSONObject(s);

                        Gson gson = new Gson();
                        JSONArray jsonArray = jsonObject.getJSONArray("serviceprovider");

                          /*  for(int i=0;i<=jsonArray.length();i++){
                                JSONObject jsonobj=jsonArray.getJSONObject(i);

                                Toast.makeText(getActivity(),jsonobj.getString("Name")+jsonobj.getString("Email"),Toast.LENGTH_LONG).show();
                            }*/
                        Type listType = new TypeToken<List<RetreivetutorBean>>() {
                        }.getType();
                        tutorlist = (ArrayList<RetreivetutorBean>) gson.fromJson(jsonArray.toString(), listType);
                        adapter = new Tutoradapter(getActivity(), R.layout.tutor, tutorlist);
                        //     Toast.makeText(MainActivity.this, "1 ", Toast.LENGTH_LONG).show();
                        listview.setAdapter(adapter);
                        //    Toast.makeText(MainActivity.this, "2 ", Toast.LENGTH_LONG).show();
                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                RetreivetutorBean p = tutorlist.get(position);
                                RetreiveresultBean.Email=p.getEmail();
                                retrieve();


                            }
                        });
                    }catch (Exception e){
                  //      Toast.makeText(getActivity(),"Some Exception",Toast.LENGTH_LONG).show();
                    }finally {

                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

               //     Toast.makeText(getActivity(),"VolleyError "+volleyError, Toast.LENGTH_LONG).show();
                    Log.i("Volley", volleyError.toString());
                    Log.i("Volley",volleyError.getMessage());
                }
            }
    )

    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> map = new HashMap<>();

            map.put("occupation", ResultBean.Occupation);
            map.put("city", ResultBean.City);

            return map;
        }
    };
        // Now the request shall be executed
        requestQueue.add(stringRequest);


    }

    private void readtutoroccupation() {
        stringRequest = new StringRequest(Request.Method.POST, "http://tablepay.esy.es/retrievebyoccupation.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            Gson gson = new Gson();
                            JSONArray jsonArray = jsonObject.getJSONArray("serviceprovider");

                          /*  for(int i=0;i<=jsonArray.length();i++){
                                JSONObject jsonobj=jsonArray.getJSONObject(i);

                                Toast.makeText(getActivity(),jsonobj.getString("Name")+jsonobj.getString("Email"),Toast.LENGTH_LONG).show();
                            }*/
                            Type listType = new TypeToken<List<RetreivetutorBean>>() {
                            }.getType();
                            tutorlist = (ArrayList<RetreivetutorBean>) gson.fromJson(jsonArray.toString(), listType);
                            adapter = new Tutoradapter(getActivity(), R.layout.tutor, tutorlist);

                            listview.setAdapter(adapter);


                            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    RetreivetutorBean p = tutorlist.get(position);
                                    RetreiveresultBean.Email=p.getEmail();
                                    retrieve();


                                }
                            });
                        }catch (Exception e){
                      //      Toast.makeText(getActivity(),"Some Exception",Toast.LENGTH_LONG).show();
                        }finally {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                     //   Toast.makeText(getActivity(),"VolleyError "+volleyError, Toast.LENGTH_LONG).show();
                        Log.i("Volley", volleyError.toString());
                        Log.i("Volley",volleyError.getMessage());
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                map.put("occupation", ResultBean.Occupation);


                return map;
            }
        };
        // Now the request shall be executed
        requestQueue.add(stringRequest);

    }

    private void readtutorcity() {
        stringRequest = new StringRequest(Request.Method.POST, "http://tablepay.esy.es/retrievebycity.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            Gson gson = new Gson();
                            JSONArray jsonArray = jsonObject.getJSONArray("serviceprovider");

                          /*  for(int i=0;i<=jsonArray.length();i++){
                                JSONObject jsonobj=jsonArray.getJSONObject(i);

                                Toast.makeText(getActivity(),jsonobj.getString("Name")+jsonobj.getString("Email"),Toast.LENGTH_LONG).show();
                            }*/
                            Type listType = new TypeToken<List<RetreivetutorBean>>() {
                            }.getType();
                            tutorlist = (ArrayList<RetreivetutorBean>) gson.fromJson(jsonArray.toString(), listType);
                            adapter = new Tutoradapter(getActivity(), R.layout.tutor, tutorlist);
                            //     Toast.makeText(MainActivity.this, "1 ", Toast.LENGTH_LONG).show();
                            listview.setAdapter(adapter);
                            //    Toast.makeText(MainActivity.this, "2 ", Toast.LENGTH_LONG).show();
                            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    RetreivetutorBean p = tutorlist.get(position);
                                    RetreiveresultBean.Email=p.getEmail();
                                    retrieve();


                                }
                            });
                        }catch (Exception e){
                       //     Toast.makeText(getActivity(),"Some Exception",Toast.LENGTH_LONG).show();
                        }finally {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                   //     Toast.makeText(getActivity(),"VolleyError "+volleyError, Toast.LENGTH_LONG).show();
                        Log.i("Volley", volleyError.toString());
                        Log.i("Volley",volleyError.getMessage());
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                map.put("city", ResultBean.City);

                return map;
            }
        };
        // Now the request shall be executed
        requestQueue.add(stringRequest);

    }

    private void readtutor() {
     //   Toast.makeText(getActivity(),"2",Toast.LENGTH_LONG).show();


        stringRequest = new StringRequest(com.android.volley.Request.Method.GET,"http://tablepay.esy.es/retrievetutor.php",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            Gson gson = new Gson();
                            JSONArray jsonArray = jsonObject.getJSONArray("serviceprovider");

                          /*  for(int i=0;i<=jsonArray.length();i++){
                                JSONObject jsonobj=jsonArray.getJSONObject(i);

                                Toast.makeText(getActivity(),jsonobj.getString("Name")+jsonobj.getString("Email"),Toast.LENGTH_LONG).show();
                            }*/
              Type listType = new TypeToken<List<RetreivetutorBean>>() {
                }.getType();
                tutorlist = (ArrayList<RetreivetutorBean>) gson.fromJson(jsonArray.toString(), listType);
                            adapter = new Tutoradapter(getActivity(), R.layout.tutor, tutorlist);
                            //     Toast.makeText(MainActivity.this, "1 ", Toast.LENGTH_LONG).show();
                            listview.setAdapter(adapter);
                            //    Toast.makeText(MainActivity.this, "2 ", Toast.LENGTH_LONG).show();
                            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    RetreivetutorBean p=tutorlist.get(position);
                                    RetreiveresultBean.Email=p.getEmail();
                                    retrieve();


                                }
                            }) ;
                            //    Toast.makeText(MainActivity.this, " 1 ", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {

                          //  Toast.makeText(getActivity(), "Some JSON Parsing Error", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                // failure
                new com.android.volley.Response.ErrorListener() {
                    //  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    //    Toast.makeText(getActivity(), "Some Volley Error", Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(stringRequest);

}

    private void retrieve() {
        stringRequest = new StringRequest(com.android.volley.Request.Method.POST, "http://tablepay.esy.es/retrieveinfo.php",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            Util.tutorornot = 1;
                            //  Toast.makeText(MainActivity.this, "tutor or not " + Util.tutorornot + " text " + txtRegister.getText(), Toast.LENGTH_LONG).show();
                            RetreiveresultBean.Name = jsonObject.getString("Name");
                            RetreiveresultBean.Password = jsonObject.getString("Password");
                            RetreiveresultBean.City = jsonObject.getString("City");
                            RetreiveresultBean.Mobile = jsonObject.getString("Mobile");
                            RetreiveresultBean.Hometutor = jsonObject.getString("Hometutor");
                            RetreiveresultBean.Charges = jsonObject.getString("Charges");
                            RetreiveresultBean.Qualification = jsonObject.getString("Qualification");
                            RetreiveresultBean.Occupation = jsonObject.getString("Occupation");
                            RetreiveresultBean.Experience = jsonObject.getString("Experience");
                            RetreiveresultBean.Timing1 = jsonObject.getString("Timing1");
                            RetreiveresultBean.Timing2 = jsonObject.getString("Timing2");
                            //     Toast.makeText(MainActivity.this, "tutor or not " + Util.tutorornot + " text " + txtRegister.getText(), Toast.LENGTH_LONG).show();

                            try {
                                RetreiveresultBean.Address = jsonObject.getString("Address");
                                RetreiveresultBean.Latitude = jsonObject.getString("Latitude");
                                RetreiveresultBean.Longitude = jsonObject.getString("Longitude");
                            } catch (Exception e) {

                            }
                            startActivity(new Intent(getActivity(),SeeTutorActivity.class));
                            Toast.makeText(getActivity(), RetreiveresultBean.Timing1 + "" + RetreiveresultBean.Address + "" + RetreiveresultBean.Timing2
                                    + "" + Tutorbean.Experience + "" + Tutorbean.Hometutor + " " + Tutorbean.City + " " + Tutorbean.Mobile + "       " + Util.nodialog, Toast.LENGTH_LONG).show();


                            //    Toast.makeText(MainActivity.this, Tutorbean.Email+""+Tutorbean.Name+" "+Tutorbean.City+" "+ Tutorbean.Mobile, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {

                         //   Toast.makeText(getActivity(), "Some JSON Parsing Error", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                // failure
                new com.android.volley.Response.ErrorListener() {
                    //  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Some Volley Error", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("email", RetreiveresultBean.Email);

                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

            }
