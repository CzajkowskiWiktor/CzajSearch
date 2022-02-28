package pl.pue.air.czajsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pl.pue.air.czajsearch.databinding.ActivityMainScreenBinding;

public class MainScreen extends AppCompatActivity {

    ActivityMainScreenBinding binding;
    Bundle bundle = new Bundle();
    private String itemTitle;
    private float itemPrice;
    private String itemInfo;
    private String itemAvailability;
    private String itemLocation;
    private int itemImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_main_screen);
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment(), bundle);

        //getting names of shop and city from previous activity
        Intent intent = getIntent();
        String shopName = intent.getStringExtra(FilterMenu_Activity.SHOP_VAR);
        String cityName = intent.getStringExtra(FilterMenu_Activity.CITY_VAR);

        // changing TopBar
        ImageView backButton = findViewById(R.id.arrow_back);
        TextView topBarCityTextView = findViewById(R.id.topBar_city_textView);
        TextView topBarShopTextView = findViewById(R.id.topBar_shop_textView);

        topBarCityTextView.setText(cityName);
        topBarShopTextView.setText(shopName);

        bundle.putString("city", cityName);
        bundle.putString("shop", shopName);

//        Bundle bundle2 = getIntent().getExtras();
//        itemTitle = getIntent().getStringExtra("itemTitle");
//        itemPrice = bundle.getFloat("itemPrice");
//        itemInfo = getIntent().getStringExtra("itemInfo");
//        itemAvailability = getIntent().getStringExtra("itemAvailability");
//        itemLocation = getIntent().getStringExtra("itemLocation");
//        itemImg = getIntent().getIntExtra("itemImage", 0);
//
//        if (getIntent().hasExtra("itemTitle")){
//            Log.d("TAG", "onCreate: "+getIntent().getStringExtra("itemTitle"));
//        }

        // clicking back arrow to back activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // switch screens depending on bottom menu
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.bottom_menu_home:
                    replaceFragment(new HomeFragment() ,bundle);
                    break;
                case R.id.bottom_menu_list:
                    replaceFragment(new ListFragment() ,bundle);
                    break;
                case R.id.bottom_menu_location:
                    replaceFragment(new FavoritesFragment(), bundle);
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();
    }
}