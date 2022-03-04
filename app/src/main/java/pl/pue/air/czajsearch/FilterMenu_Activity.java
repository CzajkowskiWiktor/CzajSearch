package pl.pue.air.czajsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


public class FilterMenu_Activity extends AppCompatActivity {

    // DATA
    String[] cities = {"Poznań", "Warszawa", "Wrocław", "Gdańsk"};
    String[] shopsWar = {"Leroy Merlin, ul. Malborska 31a", "Leroy Merlin, al. Jerozolimskie 255","Leroy Merlin, ul. Ostrobramska 12","Leroy Merlin, ul. Modlińska 9",
            "Castorama, Aleja Krakowska 15", "Castorama, ul. Popularna 1", "Castorama, ul. Grochowska 51", "Castorama, ul. Krakowiaków 12",
            "Carrefour, ul. Wołoska 63c", "Carrefour, al. Jerozolimskie 14", "Carrefour, ul. Złota 55", "Carrefour, ul. Targowa 11",
            "Biedronka, ul. Nowy Świat 3", "Biedronka, ul. Pruszowska 21",
            "Biedronka, ul. Grzybowska 11", "Biedronka, ul. Dzika 1","Biedronka, al. Jerozolimskie 55",
            "Biedronka, ul. Żelazna 21","Biedronka, ul. Puławska 111a"};
    String[] shopsPzn = {"Leroy Merlin, ul. Pleszewska 11", "Leroy Merlin, ul. Głogowska 3",
            "Castorama, ul. Murawa 12","Castorama, ul. Konopnickiej 22", "Carrefour, ul. Pleszewska 11",
            "Carrefour, ul. Zamenhofa 122","Carrefour, ul. Nowogrodzka 11/12", "Biedronka, ul. Polanka 33", "Biedronka, ul. Bobrzańska 21",
            "Biedronka, plac Wiosny Ludów 1", "Biedronka, ul. Niemca 1"};
    String[] shopsWro = {"Leroy Merlin, ul. Krakowska 51", "Leroy Merlin, ul. Graniczna 4",
            "Leroy Merlin, ul. Tyska 16","Castorama, ul. Krzywoustego 126","Castorama, ul. Graniczna 2a",
            "Castorama, ul. Legnicka 58", "Carrefour, ul. Gen. Hallera 52","Carrefour, ul. Graniczna 2a",
            "Carrefour, ul. Dominikańska 3", "Biedronka, ul. Piłsudskiego 3", "Biedronka, ul. Krakowska 21",
            "Biedronka, ul. Krawiecka 3a", "Biedronka, ul. Szybka 2/5"};
    String[] shopsGda = {"Leroy Merlin, ul. Szczęśliwa 7", "Leroy Merlin, aleja Grunwaldzka 31",
            "Castorama, ul. Odyseusza 2", "Carrefour, ul. Kołobrzeska 55",
            "Carrefour, ul. Schuberta 12a", "Carrefour, ul. Przywidzka 6", "Biedronka, ul. Rajska 6",
            "Biedronka, ul. Długie Ogrody 15", "Biedronka, ul. Kartuska 26", "Biedronka, ul. Karmelicka 1"};

    // Init textViews
    AutoCompleteTextView filterCityInputAutocomplete;
    AutoCompleteTextView filterShopInputAutocomplete;

    TextInputLayout filterCityInput;
    TextInputLayout filterShopInput;

    ArrayAdapter<String> adapterCities;
    ArrayAdapter<String> adapterShops;

    Button filterSearchButton;

    // to move to another activity
    public static final String CITY_VAR = "pl.pue.air.czajsearch.CITY";
    public static final String SHOP_VAR = "pl.pue.air.czajsearch.SHOP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_menu);

        filterCityInputAutocomplete = findViewById(R.id.filter_city_input_autocomplete);
        filterShopInputAutocomplete = findViewById(R.id.filter_shop_input_autocomplete);
        filterSearchButton = findViewById(R.id.filter_search_button);
        filterCityInput = findViewById(R.id.filter_city_input);
        filterShopInput = findViewById(R.id.filter_shop_input);

        adapterCities = new ArrayAdapter<String>(this, R.layout.list_items, cities);
        filterCityInputAutocomplete.setAdapter(adapterCities);

        filterCityInputAutocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                filterCityInput.setError(null);

                //setting shop data depending on chosen city
                if (item.equals("Poznań")) {
                    filterShopInputAutocomplete.setText("");
                    adapterShops = new ArrayAdapter<String>(view.getContext(), R.layout.list_items, shopsPzn);
                    filterShopInputAutocomplete.setAdapter(adapterShops);
                }
                if (item.equals("Wrocław")) {
                    filterShopInputAutocomplete.setText("");
                    adapterShops = new ArrayAdapter<String>(view.getContext(), R.layout.list_items, shopsWro);
                    filterShopInputAutocomplete.setAdapter(adapterShops);
                }
                if (item.equals("Warszawa")) {
                    filterShopInputAutocomplete.setText("");
                    adapterShops = new ArrayAdapter<String>(view.getContext(), R.layout.list_items, shopsWar);
                    filterShopInputAutocomplete.setAdapter(adapterShops);
                }
                if (item.equals("Gdańsk")) {
                    filterShopInputAutocomplete.setText("");
                    adapterShops = new ArrayAdapter<String>(view.getContext(), R.layout.list_items, shopsGda);
                    filterShopInputAutocomplete.setAdapter(adapterShops);
                }
            }
        });

        filterShopInputAutocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                filterShopInput.setError(null);
            }
        });

        final LoadingDialog loadingDialog = new LoadingDialog(FilterMenu_Activity.this);

        filterSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable city = filterCityInput.getEditText().getText();
                Editable shop = filterShopInput.getEditText().getText();

                if (city.length() == 0 && shop.length() == 0) {
                    filterCityInput.setError("Puste pole");
                    filterShopInput.setError("Puste pole");
                    Toast.makeText(getApplicationContext(), "Proszę uzupełnić brakujące pola!", Toast.LENGTH_SHORT).show();
                } else if(city.length() == 0) {
                    filterCityInput.setError("Puste pole");
                    Toast.makeText(getApplicationContext(), "Proszę uzupełnić brakujące pole!", Toast.LENGTH_SHORT).show();
                } else if(shop.length() == 0) {
                    filterShopInput.setError("Puste pole");
                    Toast.makeText(getApplicationContext(), "Proszę uzupełnić brakujące pole!", Toast.LENGTH_SHORT).show();
                } else {
                    loadingDialog.startLoadingDialog();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismissDialog();
                            Intent intent = new Intent(FilterMenu_Activity.this, MainScreen.class);
                            intent.putExtra(SHOP_VAR, shop.toString());
                            intent.putExtra(CITY_VAR, city.toString());
                            startActivity(intent);
                        }
                    }, 1500);


                    //TODO: dialog informacyjny ze jest sie juz obok podlaczonego artykulu

                }
            }
        });
    }
}