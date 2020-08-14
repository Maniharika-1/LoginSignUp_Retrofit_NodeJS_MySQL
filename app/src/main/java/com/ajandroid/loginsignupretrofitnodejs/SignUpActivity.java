package com.ajandroid.loginsignupretrofitnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    Button signUpButton;
    EditText userName, email, password;
    Retrofit retrofit;
    RetrofitInterface retrofitInterface;
    String baseUrl = "http://192.168.225.213:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButton = findViewById(R.id.signUpButton);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.passwordS);
        email = findViewById(R.id.emailS);

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
            }
        });
    }

    private void handleSignUp() {
        HashMap<String, String > map = new HashMap<>();

        map.put("username", userName.getText().toString());
        map.put("email", email.getText().toString());
        map.put("password", password.getText().toString());

        Call<Void> call = retrofitInterface.executeSignUp(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(SignUpActivity.this,"Signed Up Successfully", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    Toast.makeText(SignUpActivity.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}