package com.example.google_authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {
    TextView name, email, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.test);
        email = findViewById(R.id.test2);
        id = findViewById(R.id.test3);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        if(accessToken!=null && accessToken.isExpired()==false){
//            Toast.makeText(getApplicationContext(),"token",Toast.LENGTH_LONG).show();
//        }
        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                            String fullName = object.getString("name");
                            String email2 = object.getString("email");
                            String id2 = object.getString("id");
                            name.setText(fullName);
                            email.setText(email2);
                            id.setText(id2);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();

    }
}