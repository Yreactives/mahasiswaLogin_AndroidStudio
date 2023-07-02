package com.ronny202102241.mahasiswalogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class MahasiswaActivity extends AppCompatActivity {

    private FloatingActionButton _addButton;
    private RecyclerView _recyclerview1;
    private SwipeRefreshLayout swipeRefreshLayout;


    private SearchView _searchView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);


        _recyclerview1 = (RecyclerView) findViewById(R.id.recyclerView1);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        _searchView1 = findViewById(R.id.searchView1);
        initAddButton();
        loadRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRecyclerView();

                Toast.makeText(MahasiswaActivity.this, "Refreshed Successfully", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        _searchView1.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });
        _searchView1.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                return false;
            }
        });


        _searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchRecyclerView(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecyclerView(newText);
                return false;
            }
        });
    }

    private void initAddButton(){
        _addButton = findViewById(R.id.addButton);
        _addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddMahasiswaActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadRecyclerView(){
        AsyncHttpClient ahc = new AsyncHttpClient();
        String url = "https://stmikpontianak.net/011100862/tampilMahasiswa.php";
        ahc.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson g = new Gson();
                List<MahasiswaModel> mahasiswaModelList = g.fromJson(new String(responseBody), new TypeToken<List<MahasiswaModel>>(){}.getType());

                RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
                _recyclerview1.setLayoutManager(lm);

                MahasiswaAdapter ma = new MahasiswaAdapter(mahasiswaModelList);
                _recyclerview1.setAdapter(ma);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void searchRecyclerView(String param1){

        AsyncHttpClient ahc = new AsyncHttpClient();
        String url = "https://stmikpontianak.net/011100862/tampilMahasiswa.php";
        List<MahasiswaModel> _mahasiswaModelList = new ArrayList<MahasiswaModel>() {
        };
        ahc.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson g = new Gson();
                List<MahasiswaModel> mahasiswaModelList = g.fromJson(new String(responseBody), new TypeToken<List<MahasiswaModel>>() {
                }.getType());
                Iterator<MahasiswaModel> iterator = mahasiswaModelList.iterator();
                while (iterator.hasNext()) {
                    MahasiswaModel element = iterator.next();
                    if (element.getNama().toLowerCase().startsWith(param1.toLowerCase()) || element.getNIM().startsWith(param1)) {
                        _mahasiswaModelList.add(element);
                    }
                }
                mahasiswaModelList = _mahasiswaModelList;

                RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
                _recyclerview1.setLayoutManager(lm);

                MahasiswaAdapter ma = new MahasiswaAdapter(_mahasiswaModelList);
                _recyclerview1.setAdapter(ma);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
}