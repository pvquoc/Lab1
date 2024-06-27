package fpl.poly.nhom9.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangNhap extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputEditText emailEditText, passwordEditText;
    private Button loginButton;
    TextView loginquenmk,logindk;
    private static final String TAG = "DangNhapActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        mAuth = FirebaseAuth.getInstance();



        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        loginquenmk=findViewById(R.id.loginquenmk);
        logindk=findViewById(R.id.logindk);

        Intent intent=getIntent();
        if (intent != null){
            Bundle ex=intent.getExtras();
            if (ex!=null){
                emailEditText.setText(ex.getString("email"));
                passwordEditText.setText(ex.getString("password"));
            }
        }
        logindk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhap.this, DangKi.class));
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(DangNhap.this, "Email và mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(DangNhap.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(DangNhap.this, "Đăng Nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DangNhap.this, Out.class));

                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(DangNhap.this, "Đăng Nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        loginquenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = emailEditText.getText().toString().trim();

                if (emailAddress.isEmpty()) {
                    Toast.makeText(DangNhap.this, "Email không được bỏ trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DangNhap.this, "Kiểm tra email để đặt lại mật khẩu", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DangNhap.this, "Yêu cầu đặt lại mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
