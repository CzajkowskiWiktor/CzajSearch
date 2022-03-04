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
        if(shopName[0].equals("Leroy Merlin") || shopName[0].equals("Castorama") || shopName[0].equals("Obi")){
            favItemList.add(new Item("Farba biała Delux","Dział: 21, Regał: 2",
                    72.99f,"Farba biała Delux - idealna do pokoju",
                    "70 sztuk",R.drawable.farba));
            favItemList.add(new Item("Taśma LED SUPER SLIM 4mm","Dział: 2, Regał: 14",
                    64.99f,"Taśma LED SUPER SLIM 4mm, moc 12W/m, 120diód/m barwa ZIMNA, długośc rolki to 5m",
                    "67 sztuk",R.drawable.tasma_led));
            favItemList.add(new Item("Wiertarka udarowa BOSH","Dział: 5, Regał: 15",
                    399.99f,"Wiertarka udarowa BOSH 550W GSB",
                    "13 sztuk",R.drawable.wiertarka));
            favItemList.add(new Item("Zaprawa Murarska M-5","Dział: 12, Regał: 1",
                    11.99f,"Zaprawa Murarska M-5 Rolas, 25kg. Najlepsza na rynku!",
                    "13 sztuk",R.drawable.zaprawa));
        }
        if(shopName[0].equals("Carrefour")){
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
        if(shopName[0].equals("Biedronka")){
            favItemList.add(new Item("Tyskie 500ml","Dział: 3, Regał: 2",
                    3.49f,"Tyskie 500ml, najlepsze Polskie piwo na rynku",
                    "82 sztuk",R.drawable.piwo));
            favItemList.add(new Item("Baton Wedel WW","Dział: 14, Regał: 11",
                    1.99f,"Baton Wedel WW, super czekoladowy i mleczny smak!",
                    "99 sztuk",R.drawable.baton));
            favItemList.add(new Item("Chipsy Lays","Dział: 11, Regał: 3",
                    5.99f,"Chipsy Lays o smaku zielona cebulka, duża paczka 200g",
                    "33 sztuk",R.drawable.chipsy));
        }

        recyclerView.setAdapter(new FavItemAdapter(favItemList,shopName[0]));

        return view;
    }
}