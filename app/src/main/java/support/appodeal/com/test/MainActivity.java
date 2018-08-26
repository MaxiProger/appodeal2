package support.appodeal.com.test;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;

public class MainActivity extends AppCompatActivity {

    final String appKey = "31a6d19dbedc3b750b32b6f9273d2649d585c82a477ca249";
    private Button banner, interstitial;
    private boolean trigger = false;
    private Handler handler;
    private Runnable runnable;
    private TextView timer;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        banner = (Button) findViewById(R.id.btn);
        interstitial = (Button) findViewById(R.id.button2);

        timer = (TextView) findViewById(R.id.textView);
        handler = new Handler();

        Appodeal.disableLocationPermissionCheck();
        //Appodeal.setTesting(true);
        Appodeal.setBannerViewId(R.id.appodeal_banner_view);
        Appodeal.initialize(MainActivity.this, appKey, Appodeal.BANNER_TOP | Appodeal.INTERSTITIAL);

        banner.setOnClickListener(view -> showBanner());

        interstitial.setOnClickListener(view -> showInterstitial());

    }

    public void showInterstitial() {
        Appodeal.show(this, Appodeal.INTERSTITIAL);
    }

    public void showBanner() {
        trigger = !trigger;
        if (trigger)
            Appodeal.show(this, Appodeal.BANNER_TOP);
        else Appodeal.hide(this, Appodeal.BANNER_TOP);
    }

}
