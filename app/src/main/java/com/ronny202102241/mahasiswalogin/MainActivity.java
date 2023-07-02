package com.ronny202102241.mahasiswalogin;

import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.SearchView;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import com.google.android.material.floatingactionbutton.FloatingActionButton;
    import com.google.gson.Gson;
    import com.google.gson.reflect.TypeToken;
    import com.loopj.android.http.AsyncHttpClient;
    import com.loopj.android.http.AsyncHttpResponseHandler;

    import java.util.ArrayList;
    import java.util.Iterator;
    import java.util.List;

    import cz.msebera.android.httpclient.Header;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button _loginButton;
    private EditText _idEditText;
    private EditText _passwordEditText;
    private Intent _menuIntent;
    private String _id;
    private String _password;
    private String _url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _idEditText = (EditText) findViewById(R.id.idEditText);
        _loginButton = (Button) findViewById(R.id.loginButton);
        _passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _id = _idEditText.getText().toString();
                _password = _passwordEditText.getText().toString();
                _url = "https://stmikpontianak.net/011100862/login.php?id=" + _id + "&password=" + _password;

                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.get(_url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String hasil = new String(responseBody);

                        if (!hasil.equals("[{\"idCount\":\"1\"}]")){
                            Toast.makeText(MainActivity.this, "ID dan password anda tidak cocok", Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(MainActivity.this, "Selamat Datang " + _id.substring(0, 1).toUpperCase() + _id.substring(1) , Toast.LENGTH_SHORT).show();

                        _menuIntent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(_menuIntent);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}