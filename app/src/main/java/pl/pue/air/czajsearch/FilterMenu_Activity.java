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
    String[] shopsWar = {"Lerla Merlyn, ul. Malborska 31a", "Lerla Merlyn, al. Jerozolimskie 255","Lerla Merlyn, ul. Ostrobramska 12","Lerla Merlyn, ul. Modlińska 9",
            "Kastorama, Aleja Krakowska 15", "Kastorama, ul. Popularna 1", "Kastorama, ul. Grochowska 51", "Kastorama, ul. Krakowiaków 12",
            "Kerfur, ul. Wołoska 63c", "Kerfur, al. Jerozolimskie 14", "Kerfur, ul. Złota 55", "Kerfur, ul. Targowa 11",
            "Biedrona, ul. Nowy Świat 3", "Biedrona, ul. Pruszowska 21",
            "Biedrona, ul. Grzybowska 11", "Biedrona, ul. Dzika 1","Biedrona, al. Jerozolimskie 55",
            "Biedrona, ul. Żelazna 21","Biedrona, ul. Puławska 111a"};
    String[] shopsPzn = {"Lerla Merlyn, ul. Pleszewska 11", "Lerla Merlyn, ul. Głogowska 3",
            "Kastorama, ul. Murawa 12","Kastorama, ul. Konopnickiej 22", "Kerfur, ul. Pleszewska 11",
            "Kerfur, ul. Zamenhofa 122","Kerfur, ul. Nowogrodzka 11/12", "Biedrona, ul. Polanka 33", "Biedrona, ul. Bobrzańska 21",
            "Biedrona, plac Wiosny Ludów 1", "Biedrona, ul. Niemca 1"};
    String[] shopsWro = {"Lerla Merlyn, ul. Krakowska 51", "Lerla Merlyn, ul. Graniczna 4",
            "Lerla Merlyn, ul. Tyska 16","Kastorama, ul. Krzywoustego 126","Kastorama, ul. Graniczna 2a",
            "Kastorama, ul. Legnicka 58", "Kerfur, ul. Gen. Hallera 52","Kerfur, ul. Graniczna 2a",
            "Kerfur, ul. Dominikańska 3", "Biedrona, ul. Piłsudskiego 3", "Biedrona, ul. Krakowska 21",
            "Biedrona, ul. Krawiecka 3a", "Biedrona, ul. Szybka 2/5"};
    String[] shopsGda = {"Lerla Merlyn, ul. Szczęśliwa 7", "Lerla Merlyn, aleja Grunwaldzka 31",
            "Kastorama, ul. Odyseusza 2", "Kerfur, ul. Kołobrzeska 55",
            "Kerfur, ul. Schuberta 12a", "Kerfur, ul. Przywidzka 6", "Biedrona, ul. Rajska 6",
            "Biedrona, ul. Długie Ogrody 15", "Biedrona, ul. Kartuska 26", "Biedrona, ul. Karmelicka 1"};

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