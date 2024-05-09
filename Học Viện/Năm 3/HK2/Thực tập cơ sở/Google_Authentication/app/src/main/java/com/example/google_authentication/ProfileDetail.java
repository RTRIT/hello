package com.example.google_authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.google_authentication.Helper.AppConfig;
import com.example.google_authentication.Helper.MySharePreference;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileDetail extends AppCompatActivity {
    TextView tv_first_name, tv_last_name, tv_email;
    Button sign_out_btn, adminButton;

    MySharePreference mySharePreference;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Hook Text View Objects:
        tv_first_name = findViewById(R.id.first_name);
        tv_last_name = findViewById(R.id.last_name);
        tv_email = findViewById(R.id.email);
//        // Get Intent Extra Values:
//        String first_name = getIntent().getStringExtra("first_name");
//        String last_name = getIntent().getStringExtra("last_name");
//        String email = getIntent().getStringExtra("email");
//
//        // Set Text View Profile Values:
//        tv_first_name.setText(first_name);
//        tv_last_name.setText(last_name);
//        tv_email.setText(email);
        loadUser();
        // Load user information to interface


        // Hook Sign Out, admin Button:
        sign_out_btn = findViewById(R.id.sign_out_btn);
        adminButton = findViewById(R.id.adminButton);

        // Set On Click Listener:
        sign_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUserOut();
            }
        });
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAdmin = new Intent(ProfileDetail.this, UserList.class);
                startActivity(goToAdmin);
            }
        });
    }

    //Load user detail with login form request
    public void loadUser(){

        AppConfig appConfig = AppConfig.getInstance();
        String ip = appConfig.getServerAddress();
        String url="http://"+ip+":9080/home/userID";
        RequestQueue queue = Volley.newRequestQueue(ProfileDetail.this);
        JSONObject getData = new JSONObject();
        try {
            initPreferences();
//            mySharePreference = mySharePreference.getInstance(ProfileDetail.this);
            String savedToken = sharedPreferences.getString("token", null);
//            String savedToken2 = mySharePreference.getToken();
            getData.put("token", savedToken);

            //Gửi request với token được set
        }catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, getData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                try {
                    String first_name = (String) res.get("first_naadminme");
                    String last_name = (String) res.get("last_name");
                    String email = (String) res.get("email");
                    String role = (String) res.get("role");
                    //Get value from the response

                    if (role.equals("admin")) {
                        adminButton.setVisibility(View.VISIBLE); // Show admin button
                    } else {
                        adminButton.setVisibility(View.GONE); // Hide admin button
                    }
                    //Show button if user is admin


                    tv_first_name.setText(first_name);
                    tv_last_name.setText(last_name);
                    tv_email.setText(email);
                    //Set for text view


                }catch (JSONException e){
                    e.printStackTrace();
                    System.out.println("Failling time");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());
                Toast.makeText(ProfileDetail.this, "Failing", Toast.LENGTH_LONG).show();
            }
        }
        );
        queue.add(jsonObjectRequest);
    }

    public void listUser(){

    }

    public void signUserOut(){
        // Set Text View Profile Values:
        tv_first_name.setText(null);
        tv_last_name.setText(null);
        tv_email.setText(null);

        // Return User Back To Home:
        Intent goToHome = new Intent(ProfileDetail.this, MainActivity.class);
        startActivity(goToHome);
        finish();

    }
    private void initPreferences() {
        sharedPreferences = getSharedPreferences("tokenSet", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
}