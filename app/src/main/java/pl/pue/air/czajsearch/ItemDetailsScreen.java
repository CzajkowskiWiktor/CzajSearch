package pl.pue.air.czajsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.Image;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailsScreen extends AppCompatActivity {

    private static final String TAG = "ItemDetailsScreen";
    private Context context;
    private Database favDB;
    private String itemTitle;
    private float itemPrice;
    private String itemInfo;
    private String itemAvailability;
    private String itemLocation;
    private int itemImg;
    private String shopName;

    private boolean itemConnected = false;

    Dialog mDialog;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private NotificationChannel channel;
    private NotificationManagerCompat notificationManagerCompat;
    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

//    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_screen);

        ImageView itemImage = null;
        if(getIntent().hasExtra("itemImage") && getIntent().hasExtra("itemTitle")
                && getIntent().hasExtra("itemInfo") && getIntent().hasExtra("itemPrice")
                && getIntent().hasExtra("itemAvailability") && getIntent().hasExtra("itemLocation"))
        {
            Bundle bundle = getIntent().getExtras();

            itemTitle = getIntent().getStringExtra("itemTitle");
            itemPrice = bundle.getFloat("itemPrice");
            itemInfo = getIntent().getStringExtra("itemInfo");
            itemAvailability = getIntent().getStringExtra("itemAvailability");
            itemLocation = getIntent().getStringExtra("itemLocation");
            itemImg = getIntent().getIntExtra("itemImage", 0);

            //shopName
            shopName = getIntent().getStringExtra("shop");

            //topBar
            String shopName = getIntent().getStringExtra(FilterMenu_Activity.SHOP_VAR);
            String cityName = getIntent().getStringExtra(FilterMenu_Activity.CITY_VAR);

            TextView itemTitleTV = findViewById(R.id.item_details_title_textView);
            TextView itemInfoTV = findViewById(R.id.item_details_info_textView);
            TextView itemPriceTV = findViewById(R.id.item_details_price_input_textView);
            TextView itemAvailabilityTV = findViewById(R.id.item_details_availability_input_textView);
            TextView itemLocationTV = findViewById(R.id.item_details_location_input_textView);
            ImageView itemImageIM = findViewById(R.id.item_details_imageView);

            itemTitleTV.setText(itemTitle);
            itemInfoTV.setText(itemInfo);
            itemPriceTV.setText(String.valueOf(itemPrice)+ " zł");

            if(itemAvailability.equals("brak")){
                itemAvailabilityTV.setText(itemAvailability);
                itemAvailabilityTV.setTextColor(Color.RED);
            } else {
                itemAvailabilityTV.setText(itemAvailability);
                itemAvailabilityTV.setTextColor(Color.BLACK);
            }

            itemLocationTV.setText(itemLocation);
            itemImageIM.setImageResource(itemImg);

            //Topbar
            TextView topBarCityTextView = findViewById(R.id.topBar_city_textView);
            TextView topBarShopTextView = findViewById(R.id.topBar_shop_textView);
            topBarCityTextView.setText(cityName);
            topBarShopTextView.setText(shopName);
        }

        Button favBtn = findViewById(R.id.item_details_toList_button);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (favBtn.getText().equals("Dodaj do ulubionych")){
                    //favDB.insertIntoTheDatabase();
                    //favDB.insertIntoTheDatabase(itemTitle, itemInfo, itemLocation, itemAvailability,
                    //String.valueOf(itemPrice),itemImg,"1","1");
                    showSuccessFavoriteAlertDialog();
                    favBtn.setText(R.string.item_details_removeList_button);
                } else {
                    //favDB.insertIntoTheDatabase();
                    //favDB.insertIntoTheDatabase(itemTitle, itemInfo, itemLocation, itemAvailability,
                    //String.valueOf(itemPrice),itemImg,"1","1");
                    Toast.makeText(getApplicationContext(), "Produkt został usunięty z ulubionych",
                            Toast.LENGTH_SHORT).show();
                    favBtn.setText(R.string.item_details_toList_button);
                }

            }
        });

        Button mapBtn = findViewById(R.id.item_details_showOnMap_button);

        if(itemAvailability.equals("brak")) {
            mapBtn.setEnabled(false);
        } else {
            mapBtn.setEnabled(true);
        }

        mDialog = new Dialog(ItemDetailsScreen.this);

        // show
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setContentView(R.layout.popup_map_layout);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();
                ImageView imgItem = mDialog.findViewById(R.id.popup_map_itemOnMap);

                //set correct image
                if(shopName.equals("Leroy Merlin")){
                    if(itemTitle.equals("Klucz 14\"")){
                        imgItem.setImageResource(R.drawable.leroy_plan_klucz);
                    }
                    if(itemTitle.equals("Wiertarka udarowa BOSH")){
                        imgItem.setImageResource(R.drawable.leroy_plan_wiertarka);
                    }
                    if(itemTitle.equals("Młotek")){
                        imgItem.setImageResource(R.drawable.leroy_plan_mlotek);
                    }
                    if(itemTitle.equals("Farba biała Delux")){
                        imgItem.setImageResource(R.drawable.leroy_plan_farba);
                    }
                    if(itemTitle.equals("Taśma LED SUPER SLIM 4mmy")){
                        imgItem.setImageResource(R.drawable.leroy_plan_oswietlenie);
                    }
                    if(itemTitle.equals("Zaprawa Murarska M-5")){
                        imgItem.setImageResource(R.drawable.leroy_plan_budowlany);
                    }
                }
                if(shopName.equals("Castorama")){
                    if(itemTitle.equals("Klucz 14\"")){
                        imgItem.setImageResource(R.drawable.kastorama_layout_klucz);
                    }
                    if(itemTitle.equals("Wiertarka udarowa BOSH")){
                        imgItem.setImageResource(R.drawable.kastorama_layout_wiertarka);
                    }
                    if(itemTitle.equals("Młotek")){
                        imgItem.setImageResource(R.drawable.kastorama_layout_mlotek);
                    }
                    if(itemTitle.equals("Farba biała Delux")){
                        imgItem.setImageResource(R.drawable.kastorama_layout_farba);
                    }
                    if(itemTitle.equals("Taśma LED SUPER SLIM 4mm")){
                        imgItem.setImageResource(R.drawable.kastorama_layout_oswietlenie);
                    }
                    if(itemTitle.equals("Zaprawa Murarska M-5")){
                        imgItem.setImageResource(R.drawable.leroy_plan_budowlany);
                    }
                }
                if(shopName.equals("Carrefour")){
                    if(itemTitle.equals("Pierś z kurczaka")){
                        imgItem.setImageResource(R.drawable.kerf_layout_piers);
                    }
                    if(itemTitle.equals("Łaciate Mleko UHT 3,2% 1l")){
                        imgItem.setImageResource(R.drawable.kerf_layout_mleko);
                    }
                    if(itemTitle.equals("Olej Kujawski 0,7l")){
                        imgItem.setImageResource(R.drawable.kerf_layout_olej);
                    }
                    if(itemTitle.equals("Chleb Tostowy Schulstad")){
                        imgItem.setImageResource(R.drawable.kerf_layout_chleb_tostowy);
                    }
                    if(itemTitle.equals("Coca Cola Zero 1,5l")){
                        imgItem.setImageResource(R.drawable.kerf_layout_cola_zero);
                    }
                    if(itemTitle.equals("Fanta 250ml")){
                        imgItem.setImageResource(R.drawable.kerf_layout_fanta);
                    }
                    if(itemTitle.equals("Polski Pomidor")){
                        imgItem.setImageResource(R.drawable.kerf_layout_pomidor);
                    }
                    if(itemTitle.equals("Chipsy Lays")){
                        imgItem.setImageResource(R.drawable.kerf_layout_chipsy);
                    }
                    if(itemTitle.equals("Baton Wedel WW")){
                        imgItem.setImageResource(R.drawable.kerf_layout_baton);
                    }
                    if(itemTitle.equals("Tyskie 500ml")){
                        imgItem.setImageResource(R.drawable.kerf_layout_piwo);
                    }
                    if(itemTitle.equals("Płyn do naczyń 1l")){
                        imgItem.setImageResource(R.drawable.kerf_layout_plyn_naczyn);
                    }
                    if(itemTitle.equals("Papier Toaletowy Velvet")){
                        imgItem.setImageResource(R.drawable.kerf_layout_papier_toaletowy);
                    }
                }
                if(shopName.equals("Biedronka")){
                    if(itemTitle.equals("Pierś z kurczaka")){
                        imgItem.setImageResource(R.drawable.biedrona_layout_piers);
                    }
                    if(itemTitle.equals("Łaciate Mleko UHT 3,2% 1l")){
                        imgItem.setImageResource(R.drawable.biedrona_layout_mleko);
                    }
                    if(itemTitle.equals("Olej Kujawski 0,7l")){
                        imgItem.setImageResource(R.drawable.biedrona_layout_olej);
                    }
                    if(itemTitle.equals("Chleb Tostowy Schulstad")){
                        imgItem.setImageResource(R.drawable.biedrona_layout_chleb_tostowy);
                    }
                    if(itemTitle.equals("Coca Cola Zero 1,5l")){
                        imgItem.setImageResource(R.drawable.biedrona_layout_cola_zero);
                    }
                    if(itemTitle.equals("Fanta 250ml")){
                        imgItem.setImageResource(R.drawable.biedrona_layout_fanta);
                    }
                    if(itemTitle.equals("Polski Pomidor")){
                        imgItem.setImageResource(R.drawable.biedrona_layout_pomidor);
                    }
                    if(itemTitle.equals("Chipsy Lays")){
                        imgItem.setImageResource(R.drawable.biedrona_layout_chipsy);
                    }
                    if(itemTitle.equals("Baton Wedel WW")){
                        imgItem.setImageResource(R.drawable.biedrona_layout_baton);
                    }
                    if(itemTitle.equals("Tyskie 500ml")){
                        imgItem.setImageResource(R.drawable.biedrona_layout_piwo);
                    }
                    if(itemTitle.equals("Płyn do naczyń 1l")){
                        imgItem.setImageResource(R.drawable.biedrona_layout_plyn_naczynia);
                    }
                    if(itemTitle.equals("Papier Toaletowy Velvet")){
                        imgItem.setImageResource(R.drawable.biedrona_layout_papier_toaletowy);
                    }
                }

                Button connectBtn = mDialog.findViewById(R.id.popup_map_connectBluetooth);

                if(itemConnected){
                    connectBtn.setText("Odłącz od artykułu");
                    connectBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loadingDialog.startLoadingDialog();
                            itemConnected=false;
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadingDialog.dismissDialog();
                                    showSuccessDisconnectedAlertDialog();
                                }
                            },1000);
                        }
                    });
                } else {
                    connectBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showWarningAlertDialog();
                        }
                    });
                }
            }
        });

        // changing TopBar
        ImageView backButton = findViewById(R.id.arrow_back);

        // clicking back arrow to back activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    final LoadingDialog loadingDialog = new LoadingDialog(ItemDetailsScreen.this);

    private void showWarningAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailsScreen.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ItemDetailsScreen.this).inflate(
                R.layout.alert_warning_layout, (ConstraintLayout)findViewById(R.id.alert_warning_layoutContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.alert_warning_textTitle)).setText(R.string.alert_warning_title);
        ((TextView) view.findViewById(R.id.alert_warning_textMessage)).setText(R.string.alert_warning_message);
        ((Button) view.findViewById(R.id.alert_warning_buttonYes)).setText(R.string.alert_warning_buttonYes);
        ((Button) view.findViewById(R.id.alert_warning_buttonNo)).setText(R.string.alert_warning_buttonNO);
        ((ImageView) view.findViewById(R.id.alert_warning_imageIcon)).setImageResource(R.drawable.ic_alert_warning);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.alert_warning_buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.alert_warning_buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Notification
                        // channel for notifiaction
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            channel = new NotificationChannel("Connected",
                                    "Connected", NotificationManager.IMPORTANCE_HIGH);
                            channel.setDescription("Urzadzenie podlaczone");
                            channel.enableVibration(true);
                            NotificationManager manager = getSystemService(NotificationManager.class);
                            manager.createNotificationChannel(channel);
                        }
                        notificationManagerCompat = NotificationManagerCompat.from(ItemDetailsScreen.this);
                        NotificationCompat.Builder builderNofi = new NotificationCompat.Builder(ItemDetailsScreen.this, "Connected");
                        builderNofi.setContentTitle("Urządzenie podłączone");
                        builderNofi.setStyle(new NotificationCompat.BigTextStyle().bigText("Urządzenie zostało poprawnie podłączone z artykułem "+itemTitle+ ". Kieruj się do lokalizacji: "+itemLocation));
                        builderNofi.setContentText("Urządzenie zostało poprawnie podłączone z artykułem "+itemTitle+ ". Kieruj się do lokalizacji: "+itemLocation);
                        builderNofi.setSmallIcon(R.drawable.czajsearch_logo_v2);
                        builderNofi.setDefaults(Notification.DEFAULT_SOUND);
                        builderNofi.setDefaults(Notification.DEFAULT_VIBRATE);
                        builderNofi.setAutoCancel(true);
                        builderNofi.setSilent(false);
                        notificationManagerCompat.notify(1,builderNofi.build());


                        //dismiss dialog and show success window
                        alertDialog.dismiss();
                        loadingDialog.dismissDialog();
                        showSuccessAlertDialog();
                    }
                }, 1500);

                //set item connected status and turn off map
                itemConnected = true;
            }
        });

        if(alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

    }


    private void showSuccessAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailsScreen.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ItemDetailsScreen.this).inflate(
                R.layout.alert_success_layout, (ConstraintLayout)findViewById(R.id.alert_success_layoutContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.alert_success_textTitle)).setText(R.string.alert_success_title);
        ((TextView) view.findViewById(R.id.alert_success_textMessage)).setText(R.string.alert_success_message);
        ((Button) view.findViewById(R.id.alert_success_buttonAction)).setText(R.string.alert_success_button);
        ((ImageView) view.findViewById(R.id.alert_success_imageIcon)).setImageResource(R.drawable.ic_alert_done);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.alert_success_buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

    }

    private void showSuccessFavoriteAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailsScreen.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ItemDetailsScreen.this).inflate(
                R.layout.alert_success_layout, (ConstraintLayout)findViewById(R.id.alert_success_layoutContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.alert_success_textTitle)).setText(R.string.alert_success_favorite_title);
        ((TextView) view.findViewById(R.id.alert_success_textMessage)).setText(R.string.alert_success_favorite_message);
        ((Button) view.findViewById(R.id.alert_success_buttonAction)).setText(R.string.alert_success_favorite_button);
        ((ImageView) view.findViewById(R.id.alert_success_imageIcon)).setImageResource(R.drawable.ic_alert_done);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.alert_success_buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

    }

    private void showSuccessDisconnectedAlertDialog() {

        //Notification
        // channel for notifiaction
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("Disconnected",
                    "Disconnected", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Urzadzenie odłączone");
            channel.enableVibration(true);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        notificationManagerCompat = NotificationManagerCompat.from(ItemDetailsScreen.this);
        NotificationCompat.Builder builderNofi = new NotificationCompat.Builder(ItemDetailsScreen.this, "Disconnected");
        builderNofi.setContentTitle("Urządzenie odłączone");
        builderNofi.setContentText("Urządzenie zostało poprawnie ddłączone z artykułem");
        builderNofi.setSmallIcon(R.drawable.czajsearch_logo_v2);
        builderNofi.setDefaults(Notification.DEFAULT_SOUND);
        builderNofi.setDefaults(Notification.DEFAULT_VIBRATE);
        builderNofi.setAutoCancel(true);
        notificationManagerCompat.notify(1,builderNofi.build());

        //build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailsScreen.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ItemDetailsScreen.this).inflate(
                R.layout.alert_success_layout, (ConstraintLayout)findViewById(R.id.alert_success_layoutContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.alert_success_textTitle)).setText(R.string.alert_success_disconnected_title);
        ((TextView) view.findViewById(R.id.alert_success_textMessage)).setText(R.string.alert_success_diconntected_message);
        ((Button) view.findViewById(R.id.alert_success_buttonAction)).setText(R.string.alert_success_disconnected_button);
        ((ImageView) view.findViewById(R.id.alert_success_imageIcon)).setImageResource(R.drawable.ic_alert_done);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.alert_success_buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

    }

    private void showErrorAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailsScreen.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ItemDetailsScreen.this).inflate(
                R.layout.alert_error_layout, (ConstraintLayout)findViewById(R.id.alert_error_layoutContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.alert_error_textTitle)).setText(R.string.alert_error_title);
        ((TextView) view.findViewById(R.id.alert_error_textMessage)).setText(R.string.alert_error_message);
        ((Button) view.findViewById(R.id.alert_error_buttonAction)).setText(R.string.alert_error_button);
        ((ImageView) view.findViewById(R.id.alert_error_imageIcon)).setImageResource(R.drawable.ic_alert_error);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.alert_error_buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

    }
}