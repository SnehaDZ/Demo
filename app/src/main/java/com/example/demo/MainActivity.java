package com.example.demo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ConnectionDetector cd;
    Button btn_submit;
    public String url = "https://localjacv2.jmfl.com/localapp/api/Contacts/GetContacts";
    public ProgressDialog pDialog;
    APIService mAPIService;
    EditText edit_contact;
    RecyclerView recycler_contacts;
    TextView txt_emptymsg;
    List<String> list = new ArrayList<String>();
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edit_contact = findViewById(R.id.edit_contact);
        recycler_contacts = findViewById(R.id.recycler_contacts);
        txt_emptymsg = findViewById(R.id.txt_emptymsg);
        progressbar = findViewById(R.id.progressbar);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_contacts.setLayoutManager(mLayoutManager);

        mAPIService = ApiUtils.getAPIService();
        cd = new ConnectionDetector(MainActivity.this);

        edit_contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = edit_contact.getText().toString();
                if (cd.isConnectingToInternet()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name", name);
                    getContact(jsonObject);
                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    public void dismissProgress() {
        progressbar.setVisibility(View.GONE);
    }


    public void getContact(JsonObject name) {
        showProgress();
        mAPIService.GetContacts(name).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                dismissProgress();
                if (response.isSuccessful()) {
                    Log.i("hit", "post submitted to API." + response.body().toString());
                    JsonArray jsonarraray = new JsonArray();
                    jsonarraray.addAll(response.body().getAsJsonArray());
                    list.clear();
                    for (int i = 0; i < jsonarraray.size(); i++) {
                        list.add(jsonarraray.get(i).toString());
                    }

                    if (list.size() == 0) {
                        txt_emptymsg.setVisibility(View.VISIBLE);
                        recycler_contacts.setVisibility(View.GONE);
                    } else {
                        txt_emptymsg.setVisibility(View.GONE);
                        recycler_contacts.setVisibility(View.VISIBLE);
                        ContactAdapter adapter = new ContactAdapter(MainActivity.this, list);
                        adapter.notifyDataSetChanged();
                        recycler_contacts.setAdapter(adapter);
                    }
                } else {

                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                    Log.i("hit", "post submitted to API Wrong.");
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                dismissProgress();
                Log.i("hit", t.toString());
            }
        });
    }
}