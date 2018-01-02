package com.tubes.e_burjo.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class input_fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ListView lv;
    ProgressDialog pDialog;
    private EditText et_nama,et_alamat,et_longitude,et_latitude;
    AlertDialog alertDialog;
    SwipeRefreshLayout swipeLayout;
    private DatabaseHelper databaseHelper;
    private data datas;
    CustomAdapter adapter;
    Button simpan;
    EditText search;
    public static input_fragment newInstance() {
        return new input_fragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.input_fragment,container,false);
        et_nama = (EditText) view.findViewById(R.id.et_Nama);
        et_alamat = (EditText) view.findViewById(R.id.et_Alamat);
        et_longitude = (EditText) view.findViewById(R.id.et_Longitude);
        et_latitude = (EditText) view.findViewById(R.id.et_Latitude);
        simpan = (Button) view.findViewById(R.id.btn_simpan);


        final TextInputLayout lay_nama = (TextInputLayout) view.findViewById(R.id.input_layout_Nama);
        final TextInputLayout lay_alamat = (TextInputLayout) view.findViewById(R.id.input_layout_Alamat);
        final TextInputLayout lay_longitude = (TextInputLayout) view.findViewById(R.id.input_layout_Longitude);
        final TextInputLayout lay_latitude = (TextInputLayout) view.findViewById(R.id.input_layout_Latitude);


        et_nama.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_nama.getText().toString())){
                    lay_nama.setError("Masukkan Nama Warung");
                }else {
                    lay_nama.setError(null);
                }
            }
        });


        et_alamat.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_alamat.getText().toString())){
                    lay_alamat.setError("Masukkan Nama");
                }else {
                    lay_alamat.setError(null);
                }
            }
        });

        et_longitude.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_longitude.getText().toString())){
                    lay_longitude.setError("Masukkan Longitude");
                }else {
                    lay_longitude.setError(null);
                }
            }
        });

        et_latitude.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_latitude.getText().toString())){
                    lay_latitude.setError("Masukkan Latitude");
                }else {
                    lay_latitude.setError(null);
                }
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nama = et_nama.getText().toString();
                String alamat = et_alamat.getText().toString();
                String longitude = et_longitude.getText().toString();
                String latitude = et_latitude.getText().toString();


                new getlist().execute(nama,alamat,longitude,latitude);

            }
        });

        return view;

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
            String nama = params[0];
            String alamat = params[1];
            String longit = params[2];
            String latit = params[3];
            String result = null;
            try {
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType, "nama="+nama+"&alamat="+alamat+"&long="+longit+"&lat="+latit);
                Request request = new Request.Builder()
                        .url("http://api.e-dokter.xyz/input.php")
                        .post(body)
                        .addHeader("content-type", "application/x-www-form-urlencoded")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "7409b15d-e968-02b3-7a2f-e5b057eacda1")
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
            JSONObject jObj = null;
            String reason="";
            try
            {
                jObj = new JSONObject(result);
                JSONArray array_data= jObj.getJSONArray("input");
                JSONObject explrObject = array_data.getJSONObject(0);
                reason = explrObject.getString("reason");

                alertDialog.setMessage(reason);
                alertDialog.show();
            }
            catch (JSONException e)
            {
                Log.e("JSON exception", e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
