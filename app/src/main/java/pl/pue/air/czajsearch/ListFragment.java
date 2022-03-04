package pl.pue.air.czajsearch;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchViewListItem;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    private String shopNameFull;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list, container, false);


        String city = this.getArguments().getString("city");
        String shop = this.getArguments().getString("shop");
        String[] shopName = shop.split(",");
        shopNameFull = shopName[0];
        Log.d("TAG", "onCreateView: "+city);

        // search items
        searchViewListItem = view.findViewById(R.id.searchViewListItem);
        searchViewListItem.clearFocus();
        searchViewListItem.setIconifiedByDefault(false);
        searchViewListItem.setQueryHint(getString(R.string.search_view_list_item_queryHint));

        searchViewListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchViewListItem.onActionViewExpanded();
            }
        });

        searchViewListItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        // init recyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        itemList = new ArrayList<>();

        //Add data to item list
        // hardware shop
        if(shopName[0].equals("Leroy Merlin") || shopName[0].equals("Castorama") || shopName[0].equals("Obi")){
            itemList.add(new Item("Taśma LED SUPER SLIM 4mm","Dział: 2, Regał: 14",
                    64.99f,"Taśma LED SUPER SLIM 4mm, moc 12W/m, 120diód/m barwa ZIMNA, długośc rolki to 5m",
                    "67 sztuk",R.drawable.tasma_led));
            itemList.add(new Item("Klucz 14\"","Dział: 13, Regał: 5",
                    24.99f,"Klucz uniwersalny 14 cali do roznego zastosowania",
                    "150 sztuk",R.drawable.item_image));
            itemList.add(new Item("Zaprawa Murarska M-5","Dział: 12, Regał: 1",
                    11.99f,"Zaprawa Murarska M-5 Rolas, 25kg. Najlepsza na rynku!",
                    "56 sztuk",R.drawable.zaprawa));
            itemList.add(new Item("Wiertarka udarowa BOSH","Dział: 5, Regał: 15",
                    399.99f,"Wiertarka udarowa BOSH 550W GSB",
                    "13 sztuk",R.drawable.wiertarka));
            itemList.add(new Item("Młotek","Dział: 7, Regał: 1",
                    55.50f,"Młotek uniwersalny - średni",
                    "brak",R.drawable.mlotek));
            itemList.add(new Item("Farba biała Delux","Dział: 21, Regał: 2",
                    72.99f,"Farba biała Delux - idealna do pokoju",
                    "70 sztuk",R.drawable.farba));
            itemList.add(new Item("Taśma LED SUPER SLIM 4mm","Dział: 2, Regał: 14",
                    64.99f,"Taśma LED SUPER SLIM 4mm, moc 12W/m, 120diód/m barwa ZIMNA, długośc rolki to 5m",
                    "67 sztuk",R.drawable.tasma_led));
            itemList.add(new Item("Klucz 14\"","Dział: 13, Regał: 5",
                    24.99f,"Klucz uniwersalny 14 cali do roznego zastosowania",
                    "150 sztuk",R.drawable.item_image));
            itemList.add(new Item("Zaprawa Murarska M-5","Dział: 12, Regał: 1",
                    11.99f,"Zaprawa Murarska M-5 Rolas, 25kg. Najlepsza na rynku!",
                    "56 sztuk",R.drawable.zaprawa));
            itemList.add(new Item("Wiertarka udarowa BOSH","Dział: 5, Regał: 15",
                    399.99f,"Wiertarka udarowa BOSH 550W GSB",
                    "13 sztuk",R.drawable.wiertarka));
            itemList.add(new Item("Młotek","Dział: 7, Regał: 1",
                    55.50f,"Młotek uniwersalny - średni",
                    "brak",R.drawable.mlotek));
            itemList.add(new Item("Farba biała Delux","Dział: 21, Regał: 2",
                    72.99f,"Farba biała Delux - idealna do pokoju",
                    "70 sztuk",R.drawable.farba));
            itemList.add(new Item("Taśma LED SUPER SLIM 4mm","Dział: 2, Regał: 14",
                    64.99f,"Taśma LED SUPER SLIM 4mm, moc 12W/m, 120diód/m barwa ZIMNA, długośc rolki to 5m",
                    "67 sztuk",R.drawable.tasma_led));
            itemList.add(new Item("Klucz 14\"","Dział: 13, Regał: 5",
                    24.99f,"Klucz uniwersalny 14 cali do roznego zastosowania",
                    "150 sztuk",R.drawable.item_image));
            itemList.add(new Item("Zaprawa Murarska M-5","Dział: 12, Regał: 1",
                    11.99f,"Zaprawa Murarska M-5 Rolas, 25kg. Najlepsza na rynku!",
                    "56 sztuk",R.drawable.zaprawa));
            itemList.add(new Item("Wiertarka udarowa BOSH","Dział: 5, Regał: 15",
                    399.99f,"Wiertarka udarowa BOSH 550W GSB",
                    "13 sztuk",R.drawable.wiertarka));
            itemList.add(new Item("Młotek","Dział: 7, Regał: 1",
                    55.50f,"Młotek uniwersalny - średni",
                    "brak",R.drawable.mlotek));
            itemList.add(new Item("Farba biała Delux","Dział: 21, Regał: 2",
                    72.99f,"Farba biała Delux - idealna do pokoju",
                    "70 sztuk",R.drawable.farba));
            itemList.add(new Item("Klucz 14\"","Dział: 13, Regał: 5",
                    24.99f,"Klucz uniwersalny 14 cali do roznego zastosowania",
                    "150 sztuk",R.drawable.item_image));
            itemList.add(new Item("Wiertarka udarowa BOSH","Dział: 5, Regał: 15",
                    399.99f,"Wiertarka udarowa BOSH 550W GSB",
                    "13 sztuk",R.drawable.wiertarka));
            itemList.add(new Item("Młotek","Dział: 7, Regał: 1",
                    55.50f,"Młotek uniwersalny - średni",
                    "brak",R.drawable.mlotek));
            itemList.add(new Item("Farba biała Delux","Dział: 21, Regał: 2",
                    72.99f,"Farba biała Delux - idealna do pokoju",
                    "70 sztuk",R.drawable.farba));
            itemList.add(new Item("Klucz 14\"","Dział: 13, Regał: 5",
                    24.99f,"Klucz uniwersalny 14 cali do roznego zastosowania",
                    "150 sztuk",R.drawable.item_image));
            itemList.add(new Item("Wiertarka udarowa BOSH","Dział: 5, Regał: 15",
                    399.99f,"Wiertarka udarowa BOSH 550W GSB",
                    "13 sztuk",R.drawable.wiertarka));
            itemList.add(new Item("Młotek","Dział: 7, Regał: 1",
                    55.50f,"Młotek uniwersalny - średni",
                    "brak",R.drawable.mlotek));
            itemList.add(new Item("Farba biała Delux","Dział: 21, Regał: 2",
                    72.99f,"Farba biała Delux - idealna do pokoju",
                    "70 sztuk",R.drawable.farba));
            itemList.add(new Item("Klucz 14\"","Dział: 13, Regał: 5",
                    24.99f,"Klucz uniwersalny 14 cali do roznego zastosowania",
                    "150 sztuk",R.drawable.item_image));
            itemList.add(new Item("Wiertarka udarowa BOSH","Dział: 5, Regał: 15",
                    399.99f,"Wiertarka udarowa BOSH 550W GSB",
                    "13 sztuk",R.drawable.wiertarka));
            itemList.add(new Item("Młotek","Dział: 7, Regał: 1",
                    55.50f,"Młotek uniwersalny - średni",
                    "brak",R.drawable.mlotek));
            itemList.add(new Item("Farba biała Delux","Dział: 21, Regał: 2",
                    72.99f,"Farba biała Delux - idealna do pokoju",
                    "70 sztuk",R.drawable.farba));
            itemList.add(new Item("Klucz 14\"","Dział: 13, Regał: 5",
                    24.99f,"Klucz uniwersalny 14 cali do roznego zastosowania",
                    "150 sztuk",R.drawable.item_image));
            itemList.add(new Item("Wiertarka udarowa BOSH","Dział: 5, Regał: 15",
                    399.99f,"Wiertarka udarowa BOSH 550W GSB",
                    "13 sztuk",R.drawable.wiertarka));
            itemList.add(new Item("Młotek","Dział: 7, Regał: 1",
                    55.50f,"Młotek uniwersalny - średni",
                    "brak",R.drawable.mlotek));
            itemList.add(new Item("Farba biała Delux","Dział: 21, Regał: 2",
                    72.99f,"Farba biała Delux - idealna do pokoju",
                    "70 sztuk",R.drawable.farba));
            itemList.add(new Item("Klucz 14\"","Dział: 13, Regał: 5",
                    24.99f,"Klucz uniwersalny 14 cali do roznego zastosowania",
                    "150 sztuk",R.drawable.item_image));
            itemList.add(new Item("Wiertarka udarowa BOSH","Dział: 5, Regał: 15",
                    399.99f,"Wiertarka udarowa BOSH 550W GSB",
                    "13 sztuk",R.drawable.wiertarka));
            itemList.add(new Item("Młotek","Dział: 7, Regał: 1",
                    55.50f,"Młotek uniwersalny - średni",
                    "brak",R.drawable.mlotek));
            itemList.add(new Item("Farba biała Delux","Dział: 21, Regał: 2",
                    72.99f,"Farba biała Delux - idealna do pokoju",
                    "70 sztuk",R.drawable.farba));
        }

        // grocery shop
        if(shopName[0].equals("Carrefour") || shopName[0].equals("Biedronka")){
            itemList.add(new Item("Pierś z kurczaka","Dział: 2, Regał: 1",
                    17.99f,"Pierś z kurczaka z Polskiej produkcji. Najwyższa jakość mięsa",
                    "45 sztuk",R.drawable.piers_kurzcak));
            itemList.add(new Item("Łaciate Mleko UHT 3,2% 1l","Dział: 3, Regał: 15",
                    3.29f,"Polskie mleko firmy łaciate 3,2% UHT. Opakowanie poddawane recyklingowi",
                    "133 sztuk",R.drawable.mleko));
            itemList.add(new Item("Olej Kujawski 0,7l","Dział: 7, Regał: 3",
                    10.99f,"Olej Kujawski 0,7l z pierwszego tłoczenia. Idealny do frytek, produkt Polski",
                    "31 sztuk",R.drawable.olej));
            itemList.add(new Item("Chleb Tostowy Schulstad","Dział: 15, Regał: 7",
                    5.29f,"Chleb Tostowy Schulstad 700g, chleb tostowy pełnoziarnisty",
                    "70 sztuk",R.drawable.chleb_tostowy));
            itemList.add(new Item("Coca Cola Zero 1,5l","Dział: 12, Regał: 9",
                    5.29f,"Coca Cola Zero 1,5l, zero kalorii. Idealna na imprezy towarzyskie i dla osób, które liczą kalorie",
                    "brak",R.drawable.cola));
            itemList.add(new Item("Pierś z kurczaka","Dział: 2, Regał: 1",
                    17.99f,"Pierś z kurczaka z Polskiej produkcji. Najwyższa jakość mięsa",
                    "45 sztuk",R.drawable.piers_kurzcak));
            itemList.add(new Item("Łaciate Mleko UHT 3,2% 1l","Dział: 3, Regał: 15",
                    3.29f,"Polskie mleko firmy łaciate 3,2% UHT. Opakowanie poddawane recyklingowi",
                    "133 sztuk",R.drawable.mleko));
            itemList.add(new Item("Olej Kujawski 0,7l","Dział: 7, Regał: 3",
                    10.99f,"Olej Kujawski 0,7l z pierwszego tłoczenia. Idealny do frytek, produkt Polski",
                    "31 sztuk",R.drawable.olej));
            itemList.add(new Item("Chleb Tostowy Schulstad","Dział: 15, Regał: 7",
                    5.29f,"Chleb Tostowy Schulstad 700g, chleb tostowy pełnoziarnisty",
                    "70 sztuk",R.drawable.chleb_tostowy));
            itemList.add(new Item("Coca Cola Zero 1,5l","Dział: 12, Regał: 9",
                    5.29f,"Coca Cola Zero 1,5l, zero kalorii. Idealna na imprezy towarzyskie i dla osób, które liczą kalorie",
                    "brak",R.drawable.cola));
            itemList.add(new Item("Pierś z kurczaka","Dział: 2, Regał: 1",
                    17.99f,"Pierś z kurczaka z Polskiej produkcji. Najwyższa jakość mięsa",
                    "45 sztuk",R.drawable.piers_kurzcak));
            itemList.add(new Item("Łaciate Mleko UHT 3,2% 1l","Dział: 3, Regał: 15",
                    3.29f,"Polskie mleko firmy łaciate 3,2% UHT. Opakowanie poddawane recyklingowi",
                    "133 sztuk",R.drawable.mleko));
            itemList.add(new Item("Olej Kujawski 0,7l","Dział: 7, Regał: 3",
                    10.99f,"Olej Kujawski 0,7l z pierwszego tłoczenia. Idealny do frytek, produkt Polski",
                    "31 sztuk",R.drawable.olej));
            itemList.add(new Item("Chleb Tostowy Schulstad","Dział: 15, Regał: 7",
                    5.29f,"Chleb Tostowy Schulstad 700g, chleb tostowy pełnoziarnisty",
                    "70 sztuk",R.drawable.chleb_tostowy));
            itemList.add(new Item("Coca Cola Zero 1,5l","Dział: 12, Regał: 9",
                    5.29f,"Coca Cola Zero 1,5l, zero kalorii. Idealna na imprezy towarzyskie i dla osób, które liczą kalorie",
                    "brak",R.drawable.cola));
        }


        itemAdapter = new ItemAdapter(itemList, shopNameFull);
        recyclerView.setAdapter(itemAdapter);

        return view;
    }

    private void filterList(String text) {
        List<Item> filteredList = new ArrayList<>();

        for (Item item: itemList) {
            if (item.getItemTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }

            if (filteredList.isEmpty()){
//                Toast.makeText(getActivity(), "Brak szukanych artykułów", Toast.LENGTH_SHORT).show();
                itemAdapter.setFilteredList(filteredList);
            } else {
                itemAdapter.setFilteredList(filteredList);
            }
        }
    }
}