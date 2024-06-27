package fpl.poly.nhom9.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = FirebaseFirestore.getInstance();

        giDuLieu();

        docDuLieu();

        LottieAnimationView animationView=findViewById(R.id.animationView);
        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(HomeActivity.this, ChonLoginActivity.class);
                startActivity(intent);
            }
        });

    }
    private void giDuLieu(){
        CollectionReference citiel=db.collection("cities");
        Map<String, Object> data1=new HashMap<>();
        data1.put("name","San fansitco");
        data1.put("state","CA");
        data1.put("country","USA");
        data1.put("capital",false);
        data1.put("population",10000001);
        data1.put("regions", Arrays.asList("west_coast","norcal"));
        citiel.document("SF").set(data1);

        Map<String, Object> data2=new HashMap<>();
        data2.put("name","Ha Noi");
        data2.put("state","CA");
        data2.put("country","VN");
        data2.put("capital",false);
        data2.put("population",20000001);
        data2.put("regions", Arrays.asList("west_coast","norcal"));
        citiel.document("LA").set(data2);

        Map<String, Object> data3=new HashMap<>();
        data3.put("name","Tik Tok");
        data3.put("state","CA");
        data3.put("country","VN");
        data3.put("capital",false);
        data3.put("population",30000001);
        data3.put("regions", Arrays.asList("west_coast","norcal"));
        citiel.document("DC").set(data3);

        Map<String, Object> data4=new HashMap<>();
        data4.put("name","Chines");
        data4.put("state","CA");
        data4.put("country","USA");
        data4.put("capital",false);
        data4.put("population",40000001);
        data4.put("regions", Arrays.asList("west_coast","norcal"));
        citiel.document("TOK").set(data4);
    }
    String TAG ="HomeActivity";
    private void docDuLieu(){
        db.collection("cities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documen : task.getResult()){
                                Log.d(TAG,documen.getId()+ "=>" +documen.getData());
                            }
                        }else {
                            Log.d(TAG,"Error getting documents: ",task.getException());
                        }
                    }
                });
    }
}