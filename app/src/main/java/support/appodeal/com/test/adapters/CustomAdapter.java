package support.appodeal.com.test.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.native_ad.views.NativeAdViewContentStream;

import java.util.List;

import support.appodeal.com.test.R;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    public CustomAdapter( Activity activity,List<NativeAd> nativeAds) {
        this.activity = activity;
        list = nativeAds;
        notifyDataSetChanged();
    }

    private List<NativeAd> list = null;
    private Activity activity;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NativeAd nativeAd = list.get(position);
        holder.nativeAd.setNativeAd(nativeAd);
        Appodeal.cache(activity, Appodeal.NATIVE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private NativeAdViewContentStream nativeAd;

        public ViewHolder(View itemView) {
            super(itemView);
            nativeAd = itemView.findViewById(R.id.nativeView);
        }
    }
}
