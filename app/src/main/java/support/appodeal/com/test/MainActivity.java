package support.appodeal.com.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.appodeal.ads.InterstitialCallbacks;

public class MainActivity extends AppCompatActivity {

    final String appKey = "31a6d19dbedc3b750b32b6f9273d2649d585c82a477ca249";
    private Button disableInterstitialBtn, bonusTaskBtn;
    private Handler hBanner;
    private boolean isTimerOn = true;
    private Runnable closeBanner;
    private TextView timer;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            isTimerOn = savedInstanceState.getBoolean("isTimerOn");
        }
        setContentView(R.layout.activity_main);

        bind();
        createTimer();
        if(isTimerOn)
        startTimer();

        Appodeal.disableLocationPermissionCheck();
        Appodeal.setTesting(true);
        Appodeal.initialize(MainActivity.this, appKey, Appodeal.BANNER_TOP | Appodeal.INTERSTITIAL | Appodeal.NATIVE);

        setAppodealCallbacks();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if(isTimerOn)
        startTimer();

    }

    public void bind() {

        disableInterstitialBtn = (Button) findViewById(R.id.btn);
        bonusTaskBtn = (Button) findViewById(R.id.btn_bonus);

        //button starts new activity with native ad recyclerView
        bonusTaskBtn.setOnClickListener(view -> {
            countDownTimer.cancel();
            Intent intent = new Intent(MainActivity.this, NativeAdActivity.class);
            Bundle bundle =new Bundle();
            bundle.putBoolean("isTimerOn", isTimerOn);
            onSaveInstanceState(bundle);
            startActivity(intent);
        });

        //button disable interstitial ad and stops and hides timer
        disableInterstitialBtn.setOnClickListener(view -> {
            isTimerOn = !isTimerOn;
            countDownTimer.cancel();
            hideTimer();
            disableInterstitialBtn.setVisibility(View.GONE);
        });

        timer = (TextView) findViewById(R.id.tvTimer);

        closeBanner = () -> Appodeal.hide(MainActivity.this, Appodeal.BANNER_TOP);

        hBanner = new Handler();
    }

    public void createTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                timer.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                showInterstitial();

                disableInterstitialBtn.setOnClickListener(null);
            }
        };
    }

    public void startTimer() {
        countDownTimer.start();
    }

    public void showInterstitial() {
        Appodeal.show(MainActivity.this, Appodeal.INTERSTITIAL);
    }

    public void showBanner() {
        Appodeal.show(MainActivity.this, Appodeal.BANNER_TOP);
    }

    public void hideTimer() {
        timer.setVisibility(View.GONE);
    }

    public void setAppodealCallbacks() {

        Appodeal.setBannerCallbacks(new BannerCallbacks() {
            @Override
            public void onBannerLoaded(int i, boolean b) {
                showBanner();
            }

            @Override
            public void onBannerFailedToLoad() {
                Toast.makeText(MainActivity.this, "onBannerFailedToLoad", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onBannerShown() {
                new Thread(() -> hBanner.postDelayed(closeBanner, 5000)).start();
            }

            @Override
            public void onBannerClicked() {}
        });

        Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
            @Override
            public void onInterstitialLoaded(boolean b) {}

            @Override
            public void onInterstitialFailedToLoad() {
                Toast.makeText(MainActivity.this, "onInterstitialFailedToLoad", Toast.LENGTH_LONG).show();
                startTimer();
            }

            @Override
            public void onInterstitialShown() {}

            @Override
            public void onInterstitialClicked() {}

            @Override
            public void onInterstitialClosed() {
                startTimer();
            }
        });
    }
}
