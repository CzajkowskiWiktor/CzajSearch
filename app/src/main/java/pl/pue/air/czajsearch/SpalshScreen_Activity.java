package pl.pue.air.czajsearch;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SpalshScreen_Activity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3000;

    Animation logoAnimation, textAnimation;
    ImageView splash_logo;
    TextView splash_design;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        //Animation
        logoAnimation = AnimationUtils.loadAnimation(this,R.anim.logo_animation);
        textAnimation = AnimationUtils.loadAnimation(this,R.anim.text_animation);

        splash_logo = findViewById(R.id.splash_logo);
        splash_design = findViewById(R.id.splash_designed);

        splash_logo.setAnimation(logoAnimation);
        splash_design.setAnimation(textAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SpalshScreen_Activity.this, FilterMenu_Activity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

    }
}
