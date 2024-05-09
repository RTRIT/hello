package com.example.google_authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.google_authentication.Helper.AppConfig;
import com.example.google_authentication.Helper.StringHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;
import com.example.google_authentication.Helper.Ip;
public class SignUpActivity extends AppCompatActivity {
    EditText firstname,lastname,email,username,password;
    Button signupbtn;
    TextView click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.passwd);

        signupbtn = findViewById(R.id.signupbtn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processForm();
                return;
            }
        });

        click = (TextView) findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class );
                startActivity(intent);
                finish();
            }
        });

//        click = findViewById(R.id.click);
//        click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//            }
//        });
    }

    public void processForm(){
        if(!validateFirstname() || !validateLastname() || !validateEmail() || !validateUsername() || !validatePassword()){
            return;
        }
        registerUserIntoDB();

    }

    public void registerUserIntoDB(){
        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
        AppConfig appConfig = AppConfig.getInstance();
        String ip = appConfig.getServerAddress();
        String url = "http://"+ip+":9080/user/register";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("success")){
                    firstname.setText(null);
                    lastname.setText(null);
                    email.setText(null);
                    username.setText(null);
                    password.setText(null);
                    Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                }
                //Nếu register thành công thì vào if và set các giá trị về null để đảm bảo về an toàn

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                 volleyError.printStackTrace();
                Toast.makeText(SignUpActivity.this, "Registration Un-Successful", Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", firstname.getText().toString());
                params.put("last_name", lastname.getText().toString());
                params.put("email", email.getText().toString());
                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }

        };// End Of String Request Object.
//        JSONObject signUpData = new JSONObject();
//        try {
//            signUpData.put("first_name", firstname.getText().toString());
//            signUpData.put("last_name", lastname.getText().toString());
//            signUpData.put("email", email.getText().toString());
//            signUpData.put("username", username.getText().toString());
//            signUpData.put("password", password.getText().toString());
//        }catch(JSONException e){
//            e.printStackTrace();
//        }
//
//
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, signUpData, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                Toast.makeText(SignUpActivity.this, "Register successfully!", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                volleyError.printStackTrace();
//                System.out.println(volleyError.getMessage());
//
//            }
//        });
        queue.add(stringRequest);
    }

    public boolean validateFirstname(){
        String fname = firstname.getText().toString();
        if(fname.isEmpty()){
            firstname.setError("Please complete this field");
            return false;
        }
        firstname.setError(null);
        return true;
    }
    //End of firstname Validate

    public boolean validateLastname(){
        String lname = lastname.getText().toString();
        if(lname.isEmpty()){
            lastname.setError("Please complete this field");
            return false;
        }
        lastname.setError(null);
        return true;
    }
    //End of firstname Validate

    public boolean validateEmail(){
        String emailv = email.getText().toString();
        if(emailv.isEmpty()){
            email.setError("Please complete this field");
            return false;
        }
        if(!StringHelper.regexEmailValidationPattern(emailv)){
            email.setError("Please enter valid email");
            return false;
        }
        email.setError(null);
        return true;
    }
    //End of Email Validate

    public boolean validateUsername(){
        String usrname = username.getText().toString();
        if(usrname.isEmpty()){
            username.setError("Please complete this field");
            return false;
        }
        username.setError(null);
        return true;
    }
    //End of Username Validate

    public boolean validatePassword(){
        String passwd = password.getText().toString();
        if(passwd.isEmpty()){
            password.setError("Please complete this field");
            return false;
        }
        password.setError(null);
        return true;
    }
    //End of Password Validate


}