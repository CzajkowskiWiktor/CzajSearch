package pl.pue.air.czajsearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private Context context;
    private Database favDB;
    private String shopName;

    public ItemAdapter(List<Item> itemList, String shop){
        this.itemList = itemList;
        this.shopName = shop;
    }

    public void setFilteredList(List<Item> filteredList){
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView itemTitleTV;
        private ImageView itemImage;
        private TextView itemPrice;
        private TextView itemAvailability;
        private CardView cardView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.eachCardView);
            itemTitleTV = itemView.findViewById(R.id.itemTitle_textView);
            itemPrice = itemView.findViewById(R.id.itemPrice_input);
            itemAvailability = itemView.findViewById(R.id.itemAvailability_input);
            itemImage = itemView.findViewById(R.id.itemImage_imageView);

        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //create FAV table
//        favDB = new Database(context);
//        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
//        boolean firstStart = prefs.getBoolean("firstStart", true);
//        if(firstStart) {
//            createTableOnFirst();
//        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout ,parent, false);
        return new ItemViewHolder(view);
    }

    private void createTableOnFirst() {
        favDB.insertEmpty();
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", true);
        editor.apply();
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemTitleTV.setText(item.getItemTitle());
        holder.itemPrice.setText(String.valueOf(item.getItemPrice())+" zł");
        holder.itemImage.setImageResource(item.getItemImage());
        if(item.getItemAvailability().equals("brak")){
            holder.itemAvailability.setText(item.getItemAvailability());
            holder.itemAvailability.setTextColor(Color.RED);
        } else {
            holder.itemAvailability.setText(item.getItemAvailability() + " szt.");
            holder.itemAvailability.setTextColor(Color.BLACK);
        }
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.card_anim));

        holder.cardView.setOnClickListener((view) -> {
            Log.d("HELLO", "onClick: clicked on:" + shopName);
//            Toast.makeText(view.getContext(), item.getItemTitle(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(view.getContext(), ItemDetailsScreen.class);
            intent.putExtra("itemImage", item.getItemImage());
            intent.putExtra("itemTitle", item.getItemTitle());
            intent.putExtra("itemPrice", item.getItemPrice());
            intent.putExtra("itemInfo", item.getItemInfo());
            intent.putExtra("itemAvailability", item.getItemAvailability());
            intent.putExtra("itemLocation", item.getItemLocation());
            //topbar
            intent.putExtra("shop", shopName);
            intent.putExtra("city", R.id.topBar_city_textView);

            view.getContext().startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        if (itemList == null){
            return 0;
        } else {
            return itemList.size();
        }
    }
}