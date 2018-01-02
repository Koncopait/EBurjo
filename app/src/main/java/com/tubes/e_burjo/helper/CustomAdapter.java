package com.tubes.e_burjo.helper;

/**
 * Created by user on 21/07/2017.
 */
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


//import com.shidiqarifs.edokter.DetailActivity;
//import com.shidiqarifs.edokter.Pasien_DetailActivity;
import com.tubes.e_burjo.DetailActivity;
import com.tubes.e_burjo.R;
import com.tubes.e_burjo.model.data;
//import com.shidiqarifs.edokter.Recyle;
//import com.shidiqarifs.edokter.Splash_Activity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by Oclemy on 7/15/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class CustomAdapter  extends BaseAdapter{

    Context c;
    ArrayList<data> datas;
    ArrayList<data> search_data=null;
    public CustomAdapter(Context c, ArrayList<data> list_data) {
        this.c = c;
        this.datas = list_data;
        this.search_data = new ArrayList<data>();
        this.search_data.addAll(datas);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null)
        {
            view=LayoutInflater.from(c).inflate(R.layout.model,viewGroup,false);

        }
        CardView cd = (CardView) view.findViewById(R.id.cd);
        TextView nameTxt= (TextView) view.findViewById(R.id.nameTxt);
        TextView keluhanTxt= (TextView) view.findViewById(R.id.penyakit);
        data data1= (data) this.getItem(i);

        final String id_warung=data1.getId();
        final String nama_warung=data1.getNama();
        final String alamat_warung= data1.getAlamat();
        final String latitude_warung = data1.getLatitude();
        final String longitude_warung = data1.getLongitude();


        nameTxt.setText(nama_warung);
        keluhanTxt.setText(alamat_warung);


        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OPEN DETAIL ACTIVITY
                openDetailActivity(id_warung,nama_warung,alamat_warung,longitude_warung,latitude_warung);

            }
        });

        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OPEN DETAIL ACTIVITY
                openDetailActivity(Fullname,diag);

            }
        });*/

        if (i % 2 == 0) {
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            view.setBackgroundColor(Color.parseColor("#f2efef"));
        }
        return view;
    }
    ////open activity
    private void openDetailActivity(String...details)
    {
        Intent i= new Intent(c,DetailActivity.class);
        i.putExtra("ID_WARUNG",details[0]);
        i.putExtra("NAMA_WARUNG",details[1]);
        i.putExtra("ALAMAT_WARUNG",details[2]);
        i.putExtra("LATITUDE_WARUNG",details[3]);
        i.putExtra("LONGITUDE_WARUNG",details[4]);
        c.startActivity(i);

    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        datas.clear();
        if (charText.length() == 0) {
            datas.addAll(search_data);
        }
        else
        {
            for (data wp : search_data) {
                if (wp.getNama().toLowerCase(Locale.getDefault()).contains(charText)) {
                    datas.add(wp);
                }else if (wp.getAlamat().toLowerCase(Locale.getDefault()).contains(charText)) {
                    datas.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
