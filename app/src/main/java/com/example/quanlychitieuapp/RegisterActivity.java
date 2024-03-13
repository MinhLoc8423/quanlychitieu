package com.example.quanlychitieuapp;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    EditText edtuser, editpass;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        edtuser = findViewById(R.id.edittendangnhap);
        editpass = findViewById(R.id.editmatkhau);
        btnlogin = findViewById(R.id.btnRegister);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtuser.getText().toString();
                String password = editpass.getText().toString();

                auth.createUserWithEmailAndPassword(name, password)
                        .addOnCompleteListener(RegisterActivity.this, task -> {
                            if (task.isSuccessful()) {
                                // Đăng ký thành công
                                FirebaseUser user = auth.getCurrentUser();
                                // Thực hiện các hành động sau khi đăng ký
                                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else {
                                // Đăng ký thất bại
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                // Hiển thị thông báo lỗi cho người dùng
                                Toast.makeText(RegisterActivity.this, "Đăng ký that bai", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}