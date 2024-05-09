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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.google_authentication.Adapter.UserAdapter;
import com.example.google_authentication.Helper.AppConfig;
import com.example.google_authentication.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {
    private List<User> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter mUserAdapter;



    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    AppConfig appConfig = AppConfig.getInstance();
    String ip = appConfig.getServerAddress();




    //
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Cal api to db to get all the user:

        getUserFromDb();


        recyclerView = findViewById(R.id.recyclerUser);
        mUserAdapter = new UserAdapter(this, userList);
        recyclerView.setAdapter(mUserAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setClickable(true);

        //Khi người dùng click vào bất kì item nào trên list đó thì show ra deltail của user
        mUserAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                // You may need to retrieve the clicked user from the list if needed
                String usrname = userList.get(position).getUsername();
                getUserDetail(usrname);
            }

        });




    }



    public void getUserFromDb() {
        String url = "http://"+ip+":9080/home/user";
        RequestQueue queue = Volley.newRequestQueue(UserList.this);
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray res) {
                try {
                    for(int i=0; i<res.length(); i++){
                        JSONObject jsonObject = res.getJSONObject(i);
                        userList.add(new User(jsonObject.getString("username"), R.drawable.cat1));
                        Toast.makeText(getApplicationContext(),jsonObject.getString("username"),Toast.LENGTH_LONG).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(stringRequest);
    }


    public void getUserDetail(String usrname){
            String url="http://"+ip+":9080/home/userDetail";
            RequestQueue queue = Volley.newRequestQueue(UserList.this);

            JSONObject postData = new JSONObject();
            try {
                postData.put("username", usrname);
                //Gửi request với token được set
            }catch (JSONException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
                //StringRequest automatically parses the response as a String,
                // //so you need to manually parse it as a JSON object.
                @Override
                public void onResponse(JSONObject res) {
                    try {
                        Toast.makeText(UserList.this, "GET IN", Toast.LENGTH_LONG).show();

                        // Response tra ve la JSON
                        String first_name = (String) res.getString("first_name");
                        String last_name = (String) res.get("last_name");
                        String email = (String) res.get("email");
                        String password = (String) res.get("password");
                        String username = (String) res.get("username");
                        String role = (String) res.get("role");
                        //Get value from the response

                        Intent intent = new Intent(UserList.this, UserDetail.class);

                        intent.putExtra("firstname", first_name);
                        intent.putExtra("lastname", last_name);
                        intent.putExtra("email", email);
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);
                        startActivity(intent);

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
                    Toast.makeText(UserList.this, "Failing", Toast.LENGTH_LONG).show();
                }
            }
            );
            queue.add(jsonObjectRequest);
    }







    private void initPreferences() {
        sharedPreferences = getSharedPreferences("tokenSet", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
}