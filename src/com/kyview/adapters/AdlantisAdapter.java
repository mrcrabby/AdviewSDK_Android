package com.kyview.adapters;

import jp.adlantis.android.AdRequestListener;
import jp.adlantis.android.AdRequestNotifier;
import jp.adlantis.android.AdlantisView;
import jp.adlantis.android.utils.AdlantisUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.RelativeLayout;

import com.kyview.AdViewLayout;
import com.kyview.AdViewTargeting;
import com.kyview.AdViewTargeting.RunMode;
import com.kyview.obj.Ration;
import com.kyview.util.AdViewUtil;
import com.kyview.AdViewAdRegistry;

public class AdlantisAdapter extends AdViewAdapter implements AdRequestListener{
	AdlantisView adView;

	private static int networkType() {
		return AdViewUtil.NETWORK_TYPE_ADLANTIS;
	}
	
	public static void load(AdViewAdRegistry registry) {
		try {
			if(Class.forName("jp.adlantis.android.AdlantisView") != null) {
				registry.registerClass(networkType(), AdlantisAdapter.class);
			}
		} catch (ClassNotFoundException e) {}
	}

	public AdlantisAdapter() {
	}
	
	@Override
	public void initAdapter(AdViewLayout adViewLayout, Ration ration) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void handle() {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Adlantis");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
	 		return;
	 	}
		adView = new AdlantisView(adViewLayout.getContext());
		AdlantisUtils.adHeightForOrientation(Gravity.BOTTOM);
		adView.setPublisherID(ration.key);
		adViewLayout.addView(adView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 75));
		adViewLayout.setTag("Adlantis");
		adView.addRequestListener(this);
	}

	@Override
	public void onFailedToReceiveAd(AdRequestNotifier arg0) {
		 if(AdViewTargeting.getRunMode()==RunMode.TEST){
			 Log.i(AdViewUtil.ADVIEW, "Adlantis  fauire");
		 }
		adView.removeRequestListener(this);
		AdViewLayout adviewlayout=adViewLayoutReference.get();
		if(adviewlayout==null){
			return ;
		}
		adviewlayout.adViewManager.getRollover_pri();
		adviewlayout.rotatePriAd();
	}

	@Override
	public void onReceiveAd(AdRequestNotifier arg0) {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Adlantis success");
		  adView.removeRequestListener(this);
		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			  return;
		  }

		  adViewLayout.adViewManager.resetRollover();
//		  adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, arg0));
		  adViewLayout.rotateThreadedDelayed();
		
		
	}


}
