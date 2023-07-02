package com.ronny202102241.mahasiswalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

public class AddMahasiswaActivity extends AppCompatActivity {

    private Button _saveButton;
    private EditText _alamatEditText, _namaEditText, _nimEditText, _tempatLahirEditText;
    private EditText _tahunMasukEditText;
    private DatePicker _tanggalLahirDatePicker;
    private Spinner _jenisKelaminSpinner, _jpSpinner, _statusNikahSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mahasiswa);

        initInputs();
        initSaveButton();
    }

    private void initSaveButton(){
        _saveButton = findViewById(R.id.saveButton);
        _saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String alamat = _alamatEditText.getText().toString();
                String jenisKelamin = _jenisKelaminSpinner.getSelectedItem().toString();
                String jp = _jpSpinner.getSelectedItem().toString();
                String nama = _namaEditText.getText().toString();
                String nim = _nimEditText.getText().toString();
                String statusNikah = _statusNikahSpinner.getSelectedItem().toString();
                String tahunMasuk = String.valueOf(_tahunMasukEditText.getText());
                String tanggalLahir = String.valueOf(_tanggalLahirDatePicker.getYear()) + String.valueOf(_tanggalLahirDatePicker.getMonth()) + String.valueOf(_tanggalLahirDatePicker.getDayOfMonth());
                String tempatLahir = _tempatLahirEditText.getText().toString();

                try{
                    alamat = URLEncoder.encode(alamat, "utf-8");
                    jenisKelamin = URLEncoder.encode(jenisKelamin, "utf-8");
                    nama = URLEncoder.encode(nama, "utf-8");
                    nim = URLEncoder.encode(nim, "utf-8");
                    statusNikah = URLEncoder.encode(statusNikah, "utf-8");
                    tanggalLahir = URLEncoder.encode(tanggalLahir, "utf-8");
                    tempatLahir = URLEncoder.encode(tempatLahir, "utf-8");

                }
                catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                String url = "https://stmikpontianak.net/011100862/tambahMahasiswa.php"+"?nim=" + nim + "&nama=" + nama + "&jenisKelamin=" + jenisKelamin
                        + "&tempatLahir=" + tempatLahir + "&tanggalLahir=" + tanggalLahir + "&alamat=" + alamat + "&jp=" + jp + "&statusPernikahan="  + statusNikah
                        + "&tahunMasuk=" + tahunMasuk;
                Log.d("*tw*", url);
                AsyncHttpClient ahc = new AsyncHttpClient();
                ahc.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d("*tw*", new String(responseBody));
                        new AlertDialog.Builder(AddMahasiswaActivity.this).setTitle("Berhasil").setMessage("Record Berhasil Disimpan").show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void initInputs(){
        _alamatEditText = findViewById(R.id.alamatEditText);
        _jenisKelaminSpinner = findViewById(R.id.jenisKelaminSpinner);
        _jpSpinner = findViewById(R.id.jpSpinner);
        _namaEditText = findViewById(R.id.namaEditText);
        _nimEditText = findViewById(R.id.nimEditText);
        _statusNikahSpinner = findViewById(R.id.statisNikahSpinner);
        _tahunMasukEditText = findViewById(R.id.tahunMasukEditText);
        _tanggalLahirDatePicker = findViewById(R.id.tanggalLahirDatePicker);
        _tempatLahirEditText = findViewById(R.id.tempatLahirEditText);

    }
}