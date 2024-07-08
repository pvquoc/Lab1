package fpl.poly.nhom9.lab1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CTAdapter extends RecyclerView.Adapter<CTAdapter.ViewHoder> {
    private List<CTModel> list;
    private FirebaseFirestore db;

    public CTAdapter(List<CTModel> list) {
        this.list = list;
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city,parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        CTModel city=list.get(position);
        String citiname=city.getName();
        String country =city.getCountry();
        String popilato=city.getPopulation() + "";

        holder.textView.setText("City: "+ citiname);
        holder.tv2.setText("Country: "+country);
        holder.tv3.setText("Population: "+ popilato);

        holder.delete.setOnClickListener(v -> deleteCity(city, position, holder.itemView));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private void deleteCity(CTModel city, int position, View itemView) {
        db.collection("cities")
                .whereEqualTo("name", city.getName())
                .whereEqualTo("country", city.getCountry())
                .whereEqualTo("population", city.getPopulation())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful () && !task.getResult().isEmpty()) {
                        task.getResult().getDocuments().get(0).getReference().delete()
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        list.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, list.size());
                                        Toast.makeText(itemView.getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(itemView.getContext(), "Lô", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(itemView.getContext(), "City not found", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    class ViewHoder extends RecyclerView.ViewHolder {
        TextView textView,tv2,tv3;
        ImageView delete;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.cityname);
            tv2=itemView.findViewById(R.id.country);
            tv3=itemView.findViewById(R.id.population);
            delete=itemView.findViewById(R.id.delete);
        }

    }
}
