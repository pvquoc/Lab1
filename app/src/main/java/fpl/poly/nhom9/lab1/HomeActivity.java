package fpl.poly.nhom9.lab1;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    FirebaseFirestore db;
    private RecyclerView rc;
    private CTAdapter ctAdapter;
    private List<CTModel> list;
    FloatingActionButton btnadd;
    Button btn_them,btn_huy;

    TextInputEditText txt_name,txt_country,txt_population;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        db = FirebaseFirestore.getInstance();
        rc=findViewById(R.id.recyclerview);
        rc.setLayoutManager(new LinearLayoutManager(this));
        btnadd=findViewById(R.id.btnadd);

        btnadd.setOnClickListener(v ->{
            View view= LayoutInflater.from(this).inflate(R.layout.item_add,null);
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setView(view);
            AlertDialog alertDialog=builder.create();
            btn_them=view.findViewById(R.id.btn_them);
            btn_huy=view.findViewById(R.id.btn_huy);
            txt_name=view.findViewById(R.id.edcity);
            txt_country=view.findViewById(R.id.edcountry);
            txt_population=view.findViewById(R.id.edpopilation);
            txt_name=view.findViewById(R.id.edcity);
            btn_them.setOnClickListener(v1->{
                String citiName=txt_name.getText().toString();
                String citiCountry=txt_country.getText().toString();
                String citiPopulation=txt_population.getText().toString();
                if (!citiName.isEmpty() && !citiCountry.isEmpty() && !citiPopulation.isEmpty()){
                    CollectionReference cities=db.collection("cities");
                    CTModel newVity =new CTModel(citiName,citiCountry,Integer.parseInt(citiPopulation));
                    cities.add(newVity).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                alertDialog.dismiss();
                                docDuLieu();
                                Toast.makeText(HomeActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            }else {
                                Log.d(TAG,"Error adding document",task.getException());
                            }
                        }
                    });
                }else {
                    Toast.makeText(this, "Nhập đâ đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();


        });
        list=new ArrayList<>();
        ctAdapter=new CTAdapter(list);
        rc.setAdapter(ctAdapter);

        giDuLieu();

        docDuLieu();
        findViewById(R.id.btnlogiut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ChonLoginActivity.class));
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
                            list.clear();
                            for (QueryDocumentSnapshot documen : task.getResult()){
                                CTModel ctData=documen.toObject(CTModel.class);
                                list.add(ctData);
                            }
                            ctAdapter.notifyDataSetChanged();
                        }else {
                            Log.d(TAG,"Error getting documents: ",task.getException());
                        }
                    }
                });
    }
}