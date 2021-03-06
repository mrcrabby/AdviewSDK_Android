package com.kyview.adapters;

import android.app.Activity;
import android.util.Log;
import cn.appmedia.ad.AdManager;
import cn.appmedia.ad.AdViewListener;
import cn.appmedia.ad.BannerAdView;

import com.kyview.AdViewLayout;
import com.kyview.AdViewTargeting;
import com.kyview.AdViewTargeting.RunMode;
import com.kyview.obj.Ration;
import com.kyview.util.AdViewUtil;
import com.kyview.AdViewAdRegistry;

public class AppMediaAdapter extends AdViewAdapter implements AdViewListener{

	private static int networkType() {
		return AdViewUtil.NETWORK_TYPE_APPMEDIA;
	}
	
	public static void load(AdViewAdRegistry registry) {
		try {
			if(Class.forName("cn.appmedia.ad.BannerAdView") != null) {
				registry.registerClass(networkType(), AppMediaAdapter.class);
			}
		} catch (ClassNotFoundException e) {}
	}

	public AppMediaAdapter() {
	}
	
	@Override
	public void initAdapter(AdViewLayout adViewLayout, Ration ration) {
		// TODO Auto-generated constructor stub
		AdManager.setAid(ration.key);
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "Into AppMedia");
		  AdViewLayout adViewLayout = adViewLayoutReference.get();
		  if(adViewLayout == null) {
			  return;
		  }
		 	
		  Activity activity = adViewLayout.activityReference.get();
		  if(activity == null) {
			  return;
		  }
		  BannerAdView adView = new BannerAdView(activity);
		  adView.setAdListener(this);
		  adView.requestAd();
	}

	@Override
	public void onReceiveAdFailure(BannerAdView arg0) {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "AppMedia failure");
	    
		arg0.setAdListener(null);

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
		}
		adViewLayout.adViewManager.resetRollover_pri();
		adViewLayout.rotateThreadedPri();
		
	}

	@Override
	public void onReceiveAdSuccess(BannerAdView arg0) {
		// TODO Auto-generated method stub
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			  Log.d(AdViewUtil.ADVIEW, "AppMedia success");
		AdViewLayout adViewLayout = adViewLayoutReference.get();

		arg0.setAdListener(null);
	
		if (adViewLayout == null) {
			return;
		}
		adViewLayout.adViewManager.resetRollover();
		adViewLayout.handler.post(new AdViewLayout.ViewAdRunnable(adViewLayout, arg0));
		adViewLayout.rotateThreadedDelayed();

	}



}
