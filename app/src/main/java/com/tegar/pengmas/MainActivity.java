package com.tegar.pengmas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText koordinat1, nama_lengkap, tempat_lahir, tgl_lahir, tem1, tem2, tem3;
    Biodata biodata = new Biodata();
    ProgressBar progressBar;
    String lat, lng;

    public static final int USE_ADDRESS_NAME = 1;
    public static final int USE_ADDRESS_LOCATION = 2;

    int fetchType = USE_ADDRESS_LOCATION;

    private static final String TAG = "MAIN_ACTIVITY_ASYNC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        koordinat1 = (EditText) findViewById(R.id.koordinat1);
        nama_lengkap = (EditText) findViewById(R.id.register_username);
        tempat_lahir = (EditText) findViewById(R.id.tempat_lahir);
        tgl_lahir = (EditText) findViewById(R.id.tanggal_lahir);
        tem1 = (EditText) findViewById(R.id.koordinat_1);
        tem2 = (EditText) findViewById(R.id.koordinat_2);
        tem3 = (EditText) findViewById(R.id.koordinat_3);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void alamat_latlng(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, 1);
    }

    public void tempat1(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, 2);
    }

    public void tempat2(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, 3);
    }

    public void tempat3(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, 4);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Log.d("resultcode", "" + resultCode);
                lat = data.getStringExtra("lat");
                lng = data.getStringExtra("lng");

                biodata.setLat(Double.parseDouble(lat));
                biodata.setLng(Double.parseDouble(lng));

                Toast.makeText(this, "LatLng Alamat Dipilih", Toast.LENGTH_SHORT).show();
//                koordinat1.setText(lat+", "+lng, TextView.BufferType.EDITABLE);
//                new KirimKeAPI().execute();
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                lat = data.getStringExtra("lat");
                lng = data.getStringExtra("lng");

                biodata.setLat_1(Double.parseDouble(lat));
                biodata.setLng_1(Double.parseDouble(lng));
                Toast.makeText(this, "LatLng Tempat 1 Dipilih", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                lat = data.getStringExtra("lat");
                lng = data.getStringExtra("lng");

                biodata.setLat_2(Double.parseDouble(lat));
                biodata.setLng_2(Double.parseDouble(lng));
                Toast.makeText(this, "LatLng Tempat 2 Dipilih", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                lat = data.getStringExtra("lat");
                lng = data.getStringExtra("lng");

                biodata.setLat_3(Double.parseDouble(lat));
                biodata.setLng_3(Double.parseDouble(lng));
                Toast.makeText(this, "LatLng Tempat 2 Dipilih", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                TextView ip = (TextView) findViewById(R.id.ip_address_set);
                ip.setText("");
            }
        }
    }

    public void kirim(View view) {
        biodata.setNama_lengkap(nama_lengkap.getText().toString());
        biodata.setTanggal_lahir(tgl_lahir.getText().toString());
        biodata.setTempat_lahir(tempat_lahir.getText().toString());
        biodata.setAlamat(koordinat1.getText().toString());
        biodata.setTempat1(tem1.getText().toString());
        biodata.setTempat2(tem2.getText().toString());
        biodata.setTempat3(tem3.getText().toString());
        new KirimKeAPI().execute();
    }

    public void settings(View view) {
        Intent in = new Intent(this, SettingsActivity.class);
        startActivityForResult(in, 5);
    }

    private class KirimKeAPI extends AsyncTask<Void, Void, Biodata>{

        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Mengirim", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Biodata doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            RequestBody formbody = new FormBody.Builder()
                    .add("nama_lengkap", biodata.getNama_lengkap())
                    .add("alamat", biodata.getAlamat())
                    .add("tanggal_lahir", biodata.getTanggal_lahir())
                    .add("tempat_1", biodata.getTempat1())
                    .add("lat_1", String.valueOf(biodata.getLat_1()))
                    .add("lng_1", String.valueOf(biodata.getLng_1()))
                    .add("tempat_2", biodata.getTempat2())
                    .add("lat_2", String.valueOf(biodata.getLat_2()))
                    .add("lng_2", String.valueOf(biodata.getLng_2()))
                    .add("tempat_3", biodata.getTempat3())
                    .add("lat_3", String.valueOf(biodata.getLat_3()))
                    .add("lng_3", String.valueOf(biodata.getLng_3()))
                    .add("tempat_lahir", biodata.getTempat_lahir())
                    .add("lat", String.valueOf(biodata.getLat()))
                    .add("lng", String.valueOf(biodata.getLng()))
                    .build();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

            StringBuilder builder = new StringBuilder();

            String link = "http://"+sharedPreferences.getString("ip_address", "192.168.1.26")+"/pengmas/backend/web/api/penduduk/create";
            Request request = new Request.Builder()
                    .url(link)
                    .post(formbody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Biodata biodata) {
            Toast.makeText(MainActivity.this, "Cek DB", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            koordinat1.setText("");
            nama_lengkap.setText("");
            tempat_lahir.setText("");
            tgl_lahir.setText("");
            tem1.setText("");
            tem2.setText("");
            tem3.setText("");
        }
    }
}
