package fpl.poly.nhom9.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ActivityLoginPhone extends AppCompatActivity {

    private EditText phone, otp;
    private Button btnloginotp, btngetotp;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String smsVerify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        otp=findViewById(R.id.edotp);
        phone=findViewById(R.id.edphone);
        btngetotp=findViewById(R.id.btngetotp);
        btnloginotp=findViewById(R.id.btnloginotp);
        mAuth=FirebaseAuth.getInstance();
        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                otp.setText(phoneAuthCredential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(ActivityLoginPhone.this, "Đã quá số lợng", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                smsVerify=s;
            }
        };
        btngetotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber=phone.getText().toString().trim();
                if(phoneNumber.isEmpty()){
                    Toast.makeText(ActivityLoginPhone.this, "Nhập số điện thoại", Toast.LENGTH_SHORT).show();
                }else if(!phoneNumber.matches("[0-9]+")){
                    Toast.makeText(ActivityLoginPhone.this, "SỐ điện thoại chỉ có số", Toast.LENGTH_SHORT).show();
                }else {
                    getOTP(phoneNumber);
                }
            }
        });
        btnloginotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uotp=otp.getText().toString();
                if(uotp.isEmpty()){
                    Toast.makeText(ActivityLoginPhone.this, "Nhập otp", Toast.LENGTH_SHORT).show();
                    return;
                }
                SmsVerify(uotp);
            }
        });


    }
    private void getOTP(String phoneNumber){
        PhoneAuthOptions options=
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84"+phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
    private void SmsVerify(String code){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(smsVerify,code);

    }
    private void signWith(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ActivityLoginPhone.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(ActivityLoginPhone.this,Out.class);
                            startActivity(intent);
                            FirebaseUser user=task.getResult().getUser();
                        }else{
                            otp.setError("OTP sai");
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){

                            }
                        }
                    }
                });
    }
}