package support.appodeal.com.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.NativeCallbacks;

import support.appodeal.com.test.adapters.CustomAdapter;

public class NativeAdActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ad);
        bind();
        setNativeAdCallbacks();
        Appodeal.cache(NativeAdActivity.this, Appodeal.NATIVE);

    }

    public void bind(){
        recyclerView = findViewById(R.id.rvNativeAd);
        progressBar = findViewById(R.id.progressBar);
    }

    public void setNativeAdCallbacks(){
        Appodeal.setNativeCallbacks(new NativeCallbacks() {
            @Override
            public void onNativeLoaded() {
                progressBar.setVisibility(View.GONE);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(NativeAdActivity.this));
                adapter = new CustomAdapter(NativeAdActivity.this, Appodeal.getNativeAds(5));
                recyclerView.setAdapter(adapter);

                Appodeal.cache(NativeAdActivity.this, Appodeal.NATIVE);
            }

            @Override
            public void onNativeFailedToLoad() {
            }

            @Override
            public void onNativeShown(NativeAd nativeAd) {
            }

            @Override
            public void onNativeClicked(NativeAd nativeAd) {
            }
        });
    }
}
