package support.appodeal.com.test;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.appodeal.ads.InterstitialCallbacks;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String appKey = "31a6d19dbedc3b750b32b6f9273d2649d585c82a477ca249";
    private Button btn;
    private boolean trigger = false;
    private Handler handler;
    private Runnable runnable;
    private TextView timer;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        timer = (TextView) findViewById(R.id.textView);
        handler = new Handler();

        Appodeal.disableLocationPermissionCheck();
        Appodeal.initialize(this, appKey, Appodeal.BANNER_TOP);
        Appodeal.show(this, Appodeal.BANNER_TOP);

        Appodeal.setBannerCallbacks(new BannerCallbacks() {
            @Override
            public void onBannerLoaded(int i, boolean b) {

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        Appodeal.hide(MainActivity.this, Appodeal.BANNER_TOP);
                    }
                };


                 new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.postDelayed(runnable, 5000);
                    }
                }).start();
            }

            @Override
            public void onBannerFailedToLoad() {

            }

            @Override
            public void onBannerShown() {

            }

            @Override
            public void onBannerClicked() {

            }
        });



            Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
                @Override
                public void onInterstitialLoaded(boolean b) {

                }

                @Override
                public void onInterstitialFailedToLoad() {
                    Appodeal.destroy(Appodeal.INTERSTITIAL);
                    countDownTimer.start();
                }

                @Override
                public void onInterstitialShown() {

                }

                @Override
                public void onInterstitialClicked() {

                }

                @Override
                public void onInterstitialClosed() {

                }
            });

        btn.setOnClickListener(this);

        countDownTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long l) {
                timer.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {
                Appodeal.show(MainActivity.this, Appodeal.INTERSTITIAL);
                btn.setOnClickListener(null);
                countDownTimer.cancel();
            }
        };
        countDownTimer.start();

    }


    @Override
    public void onClick(View view) {
        countDownTimer.cancel();
        timer.setVisibility(View.GONE);
    }
}
