package com.example.gagan.dailyserices;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gagan.dailyserices.Adapters.DownloadImageTask;
import com.example.gagan.dailyserices.Beans.RetreiveresultBean;
import com.example.gagan.dailyserices.Beans.RetreivetutorBean;
import com.example.gagan.dailyserices.Beans.Tutorbean;
import com.example.gagan.dailyserices.TutorProfile.ContactInfoFragment;
import com.example.gagan.dailyserices.TutorProfile.GeneralInfoFragment;
import com.example.gagan.dailyserices.TutorProfile.QualificationFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SeeTutorActivity extends AppCompatActivity {

    ImageView imgview;
    TextView txtname,txtoccupation;
    String s;
    String[] split ;
    String name;
    LatLng center;
    private Handler requestHandler;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_tutor);

        txtname=(TextView)findViewById(R.id.textViewname);
        txtname.setText(RetreiveresultBean.Name);
        txtoccupation=(TextView)findViewById(R.id.textViewoccupation);
        txtoccupation.setText(RetreiveresultBean.Occupation);
        imgview=(ImageView)findViewById(R.id.imageView2);
        requestHandler = new Handler();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // requestQueue = Volley.newRequestQueue(this);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
      //  retrieve();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               trydemosendemail();
            }
        });
        s=RetreiveresultBean.Email;
        split = s.split("@");
        name = split[0];
        try{ new DownloadImageTask(imgview).execute("http://tablepay.esy.es/photos/"+name+".png");}
        catch (Exception e){
            imgview.setImageResource(R.drawable.defaultprofilepic);
        }
//        getImage();

    }

    private void trydemosendemail() {

        String to = RetreiveresultBean.Email;
        String subject = "I want to try a demo";
        String message = "Hi, I am "+Tutorbean.Name+". I want to try a demo at your Institute. My contact info is given below. Mobile- "+Tutorbean.Mobile+" Email is "+ Tutorbean.Email;

        Intent email = new Intent(Intent.ACTION_SEND);

        email.setData(Uri.parse("mailto:"));
        email.setType("text/plain");
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
        //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
        //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    private void getImage() {
        s=Tutorbean.Email;
        split = s.split("@");
        name = split[0];
        try{ new DownloadImageTask(imgview).execute("http://tablepay.esy.es/photos/"+name+".png");}
        catch (Exception e){
            imgview.setImageResource(R.drawable.defaultprofilepic);
        }/*

        final String name = Tutorbean.Email;
        class GetImage extends AsyncTask<String,Void,Bitmap> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SeeTutorActivity.this, "Loading...", null,true,true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                loading.dismiss();
                imgview.setImageBitmap(b);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String id = params[0];
                String add = "http://tablepay.esy.es/photos/"+name+".png";
                URL url = null;
                Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }
        }

        GetImage gi = new GetImage();
        gi.execute(name);*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_see_tutor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(SeeTutorActivity.this,LoginActivity.class));finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_see_tutor, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    private void retrieve() {
        stringRequest = new StringRequest(com.android.volley.Request.Method.POST, "http://tablepay.esy.es/retrieveinfo.php",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            try{RetreiveresultBean.Email=jsonObject.getString("Email");
                            RetreiveresultBean.Name=jsonObject.getString("Name");
                            RetreiveresultBean.Mobile=jsonObject.getString("Mobile");
                            RetreiveresultBean.City=jsonObject.getString("City");
                            RetreiveresultBean.Password=jsonObject.getString("Password");}
                            catch (Exception E){
                      //          Toast.makeText(SeeTutorActivity.this,"first", Toast.LENGTH_LONG).show();

                            }
                            try {
                                RetreiveresultBean.Charges = jsonObject.getString("Charges");
                                RetreiveresultBean.Qualification = jsonObject.getString("Qualification");
                                RetreiveresultBean.Occupation = jsonObject.getString("Occupation");
                                RetreiveresultBean.Experience = jsonObject.getString("Experience");
                                RetreiveresultBean.Timing1 = jsonObject.getString("Timing1");
                                RetreiveresultBean.Timing2 = jsonObject.getString("Timing2");
                                RetreiveresultBean.Hometutor = jsonObject.getString("Hometutor");
                            }
                            catch (Exception e){
                               // Toast.makeText(SeeTutorActivity.this,"second", Toast.LENGTH_LONG).show();

                            }
                            try {
                                RetreiveresultBean.Address = jsonObject.getString("Address");
                                RetreiveresultBean.Latitude = jsonObject.getString("Latitude");
                                RetreiveresultBean.Longitude = jsonObject.getString("Longitude");
                            }
                            catch (Exception e){
                              //  Toast.makeText(SeeTutorActivity.this,"third", Toast.LENGTH_LONG).show();

                            }

                            //    Toast.makeText(MainActivity.this, Tutorbean.Email+""+Tutorbean.Name+" "+Tutorbean.City+" "+ Tutorbean.Mobile, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {

                           // Toast.makeText(SeeTutorActivity.this, "Some JSON Parsing Error", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                // failure
                new com.android.volley.Response.ErrorListener() {
                    //  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                      //  Toast.makeText(SeeTutorActivity.this, "Some Volley Error", Toast.LENGTH_LONG).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                map.put("email",Util.TutorSelectedbyuseremail);

                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position){
                case 0:
                    return new GeneralInfoFragment();

                case 1:
                    return new ContactInfoFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "General";
                case 1:

                    return "Contact";
            }
            return null;
        }
    }
}
