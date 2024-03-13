package com.example.quanlychitieuapp.activies;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieuapp.R;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText edtuser, editpass;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        edtuser = findViewById(R.id.edituser);
        editpass = findViewById(R.id.editPass);
        btnlogin = findViewById(R.id.btnLogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtuser.getText().toString();
                String password = editpass.getText().toString();
                auth.signInWithEmailAndPassword(name, password)
                        .addOnCompleteListener(LoginActivity.this, task -> {
                            if (task.isSuccessful()) {
                                // Đăng nhập thành công
                                FirebaseUser user = auth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                // Đăng nhập thất bại
                                Exception exception = task.getException();
                                String message;
                                if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                    message = "Sai tên đăng nhập hoặc mật khẩu. Vui lòng thử lại.";
                                } else if (exception instanceof FirebaseNetworkException) {
                                    message = "Lỗi kết nối mạng. Vui lòng kiểm tra kết nối internet và thử lại.";
                                } else {
                                    message = "Đăng nhập thất bại. Vui lòng thử lại.";
                                    Log.w(TAG, "signInWithEmail:failure", exception);
                                }
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


    }
}