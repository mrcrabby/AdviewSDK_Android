package com.kyview.adapters;

import android.graphics.Color;
import android.util.Log;

import com.kyview.AdViewLayout;
import com.kyview.AdViewTargeting;
//import com.kyview.AdViewLayout.ViewAdRunnable;
import com.kyview.AdViewTargeting.RunMode;
import com.kyview.obj.Extra;
import com.kyview.obj.Ration;
import com.kyview.util.AdViewUtil;
import com.kyview.AdViewAdRegistry;

import com.wiyun.ad.AdView;
import com.wiyun.ad.AdView.AdListener;
import android.widget.RelativeLayout;


public class WiyunAdapter extends AdViewAdapter implements AdListener{
	AdView ad;

	private static int networkType() {
		return AdViewUtil.NETWORK_TYPE_WIYUN;
	}
	
	public static void load(AdViewAdRegistry registry) {
		try {
			if(Class.forName("com.wiyun.ad.AdView") != null) {
				registry.registerClass(networkType(), WiyunAdapter.class);
			}
		} catch (ClassNotFoundException e) {}
	}

	public WiyunAdapter() {
	}
	
	@Override
	public void initAdapter(AdViewLayout adViewLayout, Ration ration) {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void handle() {
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Wiyun");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
			return;
	 	}
	 	Extra extra = adViewLayout.extra;
		int bgColor = Color.rgb(extra.bgRed, extra.bgGreen, extra.bgBlue);
		int fgColor = Color.rgb(extra.fgRed, extra.fgGreen, extra.fgBlue);
		     
		// Instantiate an ad view and add it to the view
		ad=new AdView(adViewLayout.getContext());

		ad.setBackgroundColor(bgColor);
		ad.setTextColor(fgColor);
		ad.setResId(ration.key);
		if(AdViewTargeting.getRunMode()==RunMode.TEST)
			ad.setTestMode(true);
		else if(AdViewTargeting.getRunMode()==RunMode.NORMAL)
			ad.setTestMode(false);
		else{
			ad.setTestMode(false);
		}

		ad.setTransitionType(AdView.TRANSITION_TOP_PUSH);
		ad.setRefreshInterval(0);
		ad.requestAd();
		ad.setListener(this);	

		adViewLayout.removeAllViews();
		adViewLayout.activeRation = adViewLayout.nextRation;
		RelativeLayout.LayoutParams layoutParams;
		layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		adViewLayout.addView(ad, layoutParams);
		
	}

public void onAdClicked()
{
	if(AdViewTargeting.getRunMode()==RunMode.TEST)
		Log.d(AdViewUtil.ADVIEW, "Wiyun onAdClicked");

	AdViewLayout adViewLayout = adViewLayoutReference.get();
	if(adViewLayout == null) {
		return;
	}
	
	adViewLayout.reportClick();
}

public void onAdLoadFailed()
{
	if(AdViewTargeting.getRunMode()==RunMode.TEST)
		Log.d(AdViewUtil.ADVIEW, "Wiyun onAdLoadFailed");	

	ad.setListener(null);

	AdViewLayout adViewLayout = adViewLayoutReference.get();
	if(adViewLayout == null) {
		return;
	}
	adViewLayout.adViewManager.resetRollover_pri();
	adViewLayout.rotateThreadedPri();	
}

public void onAdLoaded()
{
	if(AdViewTargeting.getRunMode()==RunMode.TEST)
		Log.d(AdViewUtil.ADVIEW, "Wiyun onAdLoaded");

	ad.setListener(null);
	
	AdViewLayout adViewLayout = adViewLayoutReference.get();
	if(adViewLayout == null) {
		return;
	}

	adViewLayout.adViewManager.resetRollover();
	//adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, ad));
	adViewLayout.rotateThreadedDelayed();

	adViewLayout.reportImpression();
}

public void onExitButtonClicked()
{
	if(AdViewTargeting.getRunMode()==RunMode.TEST)
		 Log.d(AdViewUtil.ADVIEW, "Wiyun onExitButtonClicked");	
}


public void onAppDownloadFailed()
{
	if(AdViewTargeting.getRunMode()==RunMode.TEST)
		Log.d(AdViewUtil.ADVIEW, "Wiyun onAppDownloadFailed");	
}

public void onAppDownloaded()
{
	if(AdViewTargeting.getRunMode()==RunMode.TEST)
		Log.d(AdViewUtil.ADVIEW, "Wiyun onAppDownloaded");	
}
 
public void onMiniSiteClosed()
{
	if(AdViewTargeting.getRunMode()==RunMode.TEST)
		Log.d(AdViewUtil.ADVIEW, "Wiyun onMiniSiteClosed");	
}




}
