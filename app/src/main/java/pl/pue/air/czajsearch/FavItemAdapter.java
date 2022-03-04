package pl.pue.air.czajsearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class FavItemAdapter extends RecyclerView.Adapter<FavItemAdapter.ItemViewHolder> {

    private String title;
    private String location;
    private String shopName;
    private List<Item> itemList;
    private boolean itemConnected = false;
    private boolean yesToConnect = false;
    private String itemConnectedTitle;

    private Context context;
    private Database favDB;
    Dialog mDialog;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private NotificationChannel channel;
    private NotificationManagerCompat notificationManagerCompat;
    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    public FavItemAdapter(List<Item> itemList, String shopName){
        this.itemList = itemList;
        this.shopName = shopName;
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

        // show bottom sheet
        holder.cardView.setOnClickListener((view) -> {
            Log.d("HELLO", "onClick: clicked on:" + item.getItemImage());

            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                    view.getContext(), R.style.BottomSheetDialogTheme
            );
            View bottomSheetView = LayoutInflater.from(view.getContext()).inflate(
                    R.layout.bottom_view_layout, (LinearLayout) view.findViewById(R.id.bottom_item_details_container)
            );

            //init variables in layout
            TextView itemTitle = bottomSheetView.findViewById(R.id.bottom_view_itemTitleTV);
            TextView itemInfo = bottomSheetView.findViewById(R.id.bottom_view_itemInfoTV);
            TextView itemLocation = bottomSheetView.findViewById(R.id.bottom_view_itemLocationTV);
            TextView itemPrice = bottomSheetView.findViewById(R.id.bottom_view_itemPriceTV);
            TextView itemAvailability = bottomSheetView.findViewById(R.id.bottom_view_itemAvailabilityTV);
            ImageView itemImage = bottomSheetView.findViewById(R.id.bottom_view_itemImage);
            Button addToMapItem = bottomSheetView.findViewById(R.id.bottom_view_addToMapButton);

            itemTitle.setText(item.getItemTitle());
            itemInfo.setText(item.getItemInfo());
            itemLocation.setText(item.getItemLocation());
            if(item.getItemAvailability().equals("brak")){
                itemAvailability.setText(item.getItemAvailability());
                itemAvailability.setTextColor(Color.RED);
            } else {
                itemAvailability.setText(item.getItemAvailability());
                itemAvailability.setTextColor(Color.BLACK);
            }
            itemPrice.setText(String.valueOf(item.getItemPrice())+ " zł");
            itemImage.setImageResource(item.getItemImage());

            if(item.getItemAvailability().equals("brak")) {
                addToMapItem.setEnabled(false);
            } else {
                addToMapItem.setEnabled(true);
            }

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();

            mDialog = new Dialog(view.getContext());

            // Show map dialog
            addToMapItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.setContentView(R.layout.popup_map_layout);
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mDialog.show();
                    ImageView imgItem = mDialog.findViewById(R.id.popup_map_itemOnMap);



                    //set correct image
                    if(shopName.equals("Leroy Merlin")){
                        if(title.equals("Klucz 14\"")){
                            imgItem.setImageResource(R.drawable.leroy_plan_klucz);
                        }
                        if(title.equals("Wiertarka udarowa BOSH")){
                            imgItem.setImageResource(R.drawable.leroy_plan_wiertarka);
                        }
                        if(title.equals("Młotek")){
                            imgItem.setImageResource(R.drawable.leroy_plan_mlotek);
                        }
                        if(title.equals("Farba biała Delux")){
                            Log.d("TAG", "onClick: "+shopName);
                            imgItem.setImageResource(R.drawable.leroy_plan_farba);
                        }
                        if(title.equals("Taśma LED SUPER SLIM 4mm")){
                            imgItem.setImageResource(R.drawable.leroy_plan_oswietlenie);
                        }
                        if(title.equals("Zaprawa Murarska M-5")){
                            imgItem.setImageResource(R.drawable.leroy_plan_budowlany);
                        }
                    }
                    if(shopName.equals("Castorama")){
                        if(title.equals("Klucz 14\"")){
                            imgItem.setImageResource(R.drawable.kastorama_layout_klucz);
                        }
                        if(title.equals("Wiertarka udarowa BOSH")){
                            imgItem.setImageResource(R.drawable.kastorama_layout_wiertarka);
                        }
                        if(title.equals("Młotek")){
                            imgItem.setImageResource(R.drawable.kastorama_layout_mlotek);
                        }
                        if(title.equals("Farba biała Delux")){
                            imgItem.setImageResource(R.drawable.kastorama_layout_farba);
                        }
                        if(title.equals("Taśma LED SUPER SLIM 4mm")){
                            imgItem.setImageResource(R.drawable.kastorama_layout_oswietlenie);
                        }
                        if(title.equals("Zaprawa Murarska M-5")){
                            imgItem.setImageResource(R.drawable.leroy_plan_budowlany);
                        }
                    }
                    if(shopName.equals("Carrefour")){
                        if(title.equals("Pierś z kurczaka")){
                            imgItem.setImageResource(R.drawable.kerf_layout_piers);
                        }
                        if(title.equals("Łaciate Mleko UHT 3,2% 1l")){
                            imgItem.setImageResource(R.drawable.kerf_layout_mleko);
                        }
                        if(title.equals("Olej Kujawski 0,7l")){
                            imgItem.setImageResource(R.drawable.kerf_layout_olej);
                        }
                        if(title.equals("Chleb Tostowy Schulstad")){
                            imgItem.setImageResource(R.drawable.kerf_layout_chleb_tostowy);
                        }
                        if(title.equals("Coca Cola Zero 1,5l")){
                            imgItem.setImageResource(R.drawable.kerf_layout_cola_zero);
                        }
                    }
                    if(shopName.equals("Biedronka")){
                        if(title.equals("Pierś z kurczaka")){
                            imgItem.setImageResource(R.drawable.biedrona_layout_piers);
                        }
                        if(title.equals("Łaciate Mleko UHT 3,2% 1l")){
                            imgItem.setImageResource(R.drawable.biedrona_layout_mleko);
                        }
                        if(title.equals("Olej Kujawski 0,7l")){
                            imgItem.setImageResource(R.drawable.biedrona_layout_olej);
                        }
                        if(title.equals("Chleb Tostowy Schulstad")){
                            imgItem.setImageResource(R.drawable.biedrona_layout_chleb_tostowy);
                        }
                        if(title.equals("Coca Cola Zero 1,5l")){
                            imgItem.setImageResource(R.drawable.biedrona_layout_cola_zero);
                        }
                    }

                    Button connectBtn = mDialog.findViewById(R.id.popup_map_connectBluetooth);

                    if(itemConnected && item.getItemTitle().equals(itemConnectedTitle)){
                        connectBtn.setText("Odłącz od artykułu");
                        final LoadingDialog loadingDialog = new LoadingDialog(v);
                        connectBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                itemConnected=false;
                                loadingDialog.startLoadingDialogContext(v);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        showSuccessDisconnectedAlertDialog(v);
                                        loadingDialog.dismissDialog();
                                    }
                                },1000);

                            }
                        });
                    } else {
                        connectBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showWarningAlertDialog(v);
                            }
                        });
                    }
                }
            });

            //init title and location for notification alert
            title = item.getItemTitle();
            location = item.getItemLocation();
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

//    final LoadingDialog loadingDialog = new LoadingDialog(v.getContext());

    private void showWarningAlertDialog(View v) {
        // build loading screen
        final LoadingDialog loadingDialog = new LoadingDialog(v);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(v.getContext()).inflate(
                R.layout.alert_warning_layout, (ConstraintLayout) v.findViewById(R.id.alert_warning_layoutContainer)
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
////                showErrorAlertDialog();
                alertDialog.dismiss();

                yesToConnect = true;
                itemConnected = true;
                itemConnectedTitle = title;

                loadingDialog.startLoadingDialogContext(v);
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

                            NotificationManager manager = v.getContext().getSystemService(NotificationManager.class);
                            manager.createNotificationChannel(channel);
                        }

                        notificationManagerCompat = NotificationManagerCompat.from(v.getContext());

                        NotificationCompat.Builder builderNofi = new NotificationCompat.Builder(v.getContext(), "Connected");
                        builderNofi.setContentTitle("Urządzenie podłączone");
                        builderNofi.setStyle(new NotificationCompat.BigTextStyle().bigText("Urządzenie zostało poprawnie podłączone z artykułem "+title+ ". Kieruj się do lokalizacji: "+location));
                        builderNofi.setContentText("Urządzenie zostało poprawnie podłączone z artykułem "+title+ ". Kieruj się do lokalizacji: "+location);
                        builderNofi.setSmallIcon(R.drawable.czajsearch_logo_v2);
//                        builderNofi.setVibrate(new long[] { 1000, 1000});
                        builderNofi.setDefaults(Notification.DEFAULT_SOUND);
                        builderNofi.setDefaults(Notification.DEFAULT_VIBRATE);
//                        builderNofi.setSound(alarmSound);
                        builderNofi.setAutoCancel(true);

                        notificationManagerCompat.notify(1,builderNofi.build());


                        showSuccessAlertDialog(v);
                        loadingDialog.dismissDialog();
                    }
                }, 1500);
            }
        });

        if(alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

    }


    private void showSuccessAlertDialog(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(v.getContext()).inflate(
                R.layout.alert_success_layout, (ConstraintLayout)v.findViewById(R.id.alert_success_layoutContainer)
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

    private void showSuccessDisconnectedAlertDialog(View v) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("Disconnected",
                    "Disconnected", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Disconnected notification");
            channel.enableVibration(true);

            NotificationManager manager = v.getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //Notification
        notificationManagerCompat = NotificationManagerCompat.from(v.getContext());
        NotificationCompat.Builder builderNofi = new NotificationCompat.Builder(v.getContext(), "Disconnected");
        builderNofi.setContentTitle("Urządzenie odłączone");
        builderNofi.setContentText("Urządzenie zostało poprawnie ddłączone z artykułem");
        builderNofi.setSmallIcon(R.drawable.czajsearch_logo_v2);
        builderNofi.setDefaults(Notification.DEFAULT_SOUND);
        builderNofi.setDefaults(Notification.DEFAULT_VIBRATE);
        builderNofi.setAutoCancel(true);
        notificationManagerCompat.notify(1,builderNofi.build());

        //Builder Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(v.getContext()).inflate(
                R.layout.alert_success_layout, (ConstraintLayout)v.findViewById(R.id.alert_success_layoutContainer)
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

    private void showErrorAlertDialog(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(v.getContext()).inflate(
                R.layout.alert_error_layout, (ConstraintLayout)v.findViewById(R.id.alert_error_layoutContainer)
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