package com.tubes.e_burjo.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.tubes.e_burjo.model.data;
import com.tubes.e_burjo.R;
import com.tubes.e_burjo.helper.CustomAdapter;
import com.tubes.e_burjo.helper.DatabaseHelper;
import com.tubes.e_burjo.model.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by shidiqarifs on 16/11/2017.
 */

public class home_fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ListView lv;
    ProgressDialog pDialog;

    AlertDialog alertDialog;
    SwipeRefreshLayout swipeLayout;
    private DatabaseHelper databaseHelper;
    private data datas;
    CustomAdapter adapter;
    EditText search;
    public static home_fragment newInstance() {
        return new home_fragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.home_fragment,container,false);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        databaseHelper = new DatabaseHelper(getActivity());
        lv = (ListView) rootView.findViewById(R.id.lv);
        ArrayList<data> Array = databaseHelper.getAllUser();
        adapter = new CustomAdapter(getContext(),Array);
        search = (EditText) rootView.findViewById(R.id.search);
        lv.setAdapter(adapter);
        swipeLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new getlist().execute();
                        swipeLayout.setRefreshing(false);
                    }
                }
        );


        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub



            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });


        return rootView;

    }

    @Override
    public void onRefresh() {

    }


    private class getlist extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        AlertDialog alertDialog;

        @Override
        public String doInBackground(String... params) {
            String result = null;
            try {
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType, "");
                Request request = new Request.Builder()
                        .url("http://api.e-dokter.xyz/get_warung.php")
                        .post(body)
                        .addHeader("content-type", "application/x-www-form-urlencoded")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "1c6eae4f-7824-4ab1-a343-e17ee54fa161")
                        .build();

                Response response = client.newCall(request).execute();
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Status");
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Wait a Moment...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            databaseHelper.deleteUser();
            JSONObject jObj = null;
            try
            {
                jObj = new JSONObject(result);
                JSONArray array_data= jObj.getJSONArray("Data");
                for (int i=0;i<array_data.length();i++)
                {
                    datas = new data();
                    JSONObject explrObject = array_data.getJSONObject(i);
                    String id_warung,nama_warung,alamat_warung,longitude_warung,latitude_warung;
                    id_warung = explrObject.getString("id_warung");
                    nama_warung = explrObject.getString("nama_warung");
                    alamat_warung = explrObject.getString("alamat_warung");
                    longitude_warung = explrObject.getString("longitude_warung");
                    latitude_warung = explrObject.getString("latitude_warung");
                    datas.setId(id_warung);
                    datas.setNama(nama_warung);
                    datas.setAlamat(alamat_warung);
                    datas.setLongitude(longitude_warung);
                    datas.setLatitude(latitude_warung);
                    databaseHelper.addUser(datas);
                }

                ArrayList<data> Array = databaseHelper.getAllUser();
                adapter = new CustomAdapter(getContext(),Array);
                lv.setAdapter(adapter);
            }
            catch (JSONException e)
            {
                Log.e("JSON exception", e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
