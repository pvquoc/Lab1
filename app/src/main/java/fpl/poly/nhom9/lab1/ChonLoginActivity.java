package fpl.poly.nhom9.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChonLoginActivity extends AppCompatActivity {
Button btnloginemail,btnloginphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_login);
        btnloginemail=findViewById(R.id.btnloginemail);
        btnloginphone=findViewById(R.id.btnloginphone);
        btnloginemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChonLoginActivity.this, DangNhap.class));
            }
        });
        btnloginphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChonLoginActivity.this,MainActivity.class));
            }
        });
    }
}