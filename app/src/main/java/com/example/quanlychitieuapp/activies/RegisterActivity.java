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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText edtuser, edtname, editpass, edtcfpass;
    Button btnlogin;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        edtuser = findViewById(R.id.edittendangnhap);
        edtname = findViewById(R.id.edithovaten);
        editpass = findViewById(R.id.editmatkhau);
        edtcfpass = findViewById(R.id.editnlmatkhau);
        btnlogin = findViewById(R.id.btnRegister);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtuser.getText().toString();
                String name = edtname.getText().toString();
                String password = editpass.getText().toString();
                String cfpassword = edtcfpass.getText().toString();

                registerUser(username, name, password, cfpassword);

            }
        });
    }

    void registerUser(String username, String name, String password, String cfpassword) {
        // Kiểm tra mật khẩu và nhập lại mật khẩu có đúng không
        if(username.length() == 0 || name.length() == 0 || password.length() == 0 || cfpassword.length() == 0){
            Toast.makeText(RegisterActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        else if (password.equals(cfpassword)) {
            auth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(RegisterActivity.this, task -> {
                        if (task.isSuccessful()) {
                            // Đăng ký thành công
                            FirebaseUser user = auth.getCurrentUser();
                            String uid = user.getUid(); // lấy uid của user login

                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/" + uid);
                            userRef.child("name").setValue(name);
                            userRef.child("birthday").setValue(""); // Set default value for birthday

                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            // Đăng ký thất bại
                            try {
                                throw task.getException(); // Bắt ngoại lệ để kiểm tra chi tiết
                            } catch (FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(RegisterActivity.this, "Mật khẩu không đủ mạnh", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthUserCollisionException e) {
                                Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                // Xử lý các lỗi khác
                                Log.w(TAG, "Đăng ký thất bại", e);
                                Toast.makeText(RegisterActivity.this, "Đăng ký thất bại, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(RegisterActivity.this, "Mật khẩu chưa khớp", Toast.LENGTH_SHORT).show();
        }
    }

}