package pl.pue.air.czajsearch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private List<Item> favItemList;
    private ItemAdapter itemAdapter;
    private String shopNameString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.favorite_item_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        favItemList = new ArrayList<>();

        String city = this.getArguments().getString("city");
        String shop = this.getArguments().getString("shop");
        String[] shopName = shop.split(",");
        shopNameString = shopName[0];

        // init data to favorites
        if(shopName[0].equals("Lerla Merlyn") || shopName[0].equals("Kastorama") || shopName[0].equals("Obi")){
            favItemList.add(new Item("Farba biała Delux","Dział: 21, Regał: 2",
                    72.99f,"Farba biała Delux - idealna do pokoju",
                    "70 sztuk",R.drawable.farba));
            favItemList.add(new Item("Młotek","Dział: 7, Regał: 1",
                    55.50f,"Młotek uniwersalny - średni",
                    "brak",R.drawable.mlotek));
            favItemList.add(new Item("Wiertarka udarowa BOSH","Dział: 5, Regał: 15",
                    399.99f,"Wiertarka udarowa BOSH 550W GSB",
                    "13 sztuk",R.drawable.wiertarka));
        }
        if(shopName[0].equals("Kerfur") || shopName[0].equals("Biedrona")){
            favItemList.add(new Item("Chleb Tostowy Schulstad","Dział: 15, Regał: 7",
                    5.29f,"Chleb Tostowy Schulstad 700g, chleb tostowy pełnoziarnisty",
                    "70 sztuk",R.drawable.chleb_tostowy));
            favItemList.add(new Item("Coca Cola Zero 1,5l","Dział: 12, Regał: 9",
                    5.29f,"Coca Cola Zero 1,5l, zero kalorii. Idealna na imprezy towarzyskie i dla osób, które liczą kalorie",
                    "brak",R.drawable.cola));
            favItemList.add(new Item("Pierś z kurczaka","Dział: 2, Regał: 1",
                    17.99f,"Pierś z kurczaka z Polskiej produkcji. Najwyższa jakość mięsa",
                    "45 sztuk",R.drawable.piers_kurzcak));
        }

        recyclerView.setAdapter(new FavItemAdapter(favItemList,shopName[0]));

        return view;
    }
}