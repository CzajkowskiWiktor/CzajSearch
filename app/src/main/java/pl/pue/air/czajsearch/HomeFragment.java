package pl.pue.air.czajsearch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class HomeFragment extends Fragment {

    Animation shopAnimation;
    ImageView shopMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // getting chosen data
        String city = this.getArguments().getString("city");
        String shop = this.getArguments().getString("shop");
        String[] shopName = shop.split(",");
//        Log.d("TAG", "onCreateView: city - "+city);
//        Log.d("TAG", "onCreateView: shop - "+shopName[0]);

        // setting appropriate shop MAP
        shopMap = view.findViewById(R.id.shopMapView);
        if(shopName[0].equals("Leroy Merlin")){
            shopMap.setImageResource(R.drawable.leroy_layout);
        }
        if(shopName[0].equals("Castorama")){
            shopMap.setImageResource(R.drawable.kastorama_layout);
        }
        if(shopName[0].equals("Obi")){
            shopMap.setImageResource(R.drawable.obi_layout);
        }
        if(shopName[0].equals("Carrefour")){
            shopMap.setImageResource(R.drawable.kerf_layout);
        }
        if(shopName[0].equals("Biedronka")){
            shopMap.setImageResource(R.drawable.biedrona_layout);
        }


        //Animation
        shopAnimation = AnimationUtils.loadAnimation(view.getContext(),R.anim.card_anim);
        shopMap.setAnimation(shopAnimation);


        return view;
    }
}