package com.siliconorchard.url.site.preview.browser.ui;

import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.androidquery.AQuery;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.siliconorchard.url.site.preview.browser.PreviewActivity;
import com.siliconorchard.url.site.preview.browser.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class HowtouseActivity extends Activity{
	
	public static Context c;
	public static ImageLoader imageLoader = ImageLoader.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		c=this;
		imageLoader.init(ImageLoaderConfiguration.createDefault(c));
		//int url = getIntent().getExtras().getInt("url");
	/*	ImageView im = new ImageView(this);
		im.setBackgroundColor(Color.BLACK);
		AQuery aq = new AQuery(im);
		aq.image(url, true, true);
		setContentView(im);*/
		setContentView(R.layout.image_slider);
		String[] imageUrls = new String[5];
	
		for(int i=0; i<4; i++)
		{
			/*ModelWPCategory modelWPCategory = new ModelWPCategory();
			modelWPCategory = wpCategories.get(i);*/
		//	categoryNames[i]= Imageurl.newyearsvalues.get(i);
			int id=i+1;
			imageUrls[i] ="assets://"+id+".png";
		}
		//int pagerPosition = bundle.getInt(Extra.IMAGE_POSITION, 0);

		ImagefilePagerAdapter im = new ImagefilePagerAdapter(imageUrls);
		AutoScrollViewPager	pager = (AutoScrollViewPager) findViewById(R.id.pagera);
		pager.setAdapter(im);
		pager.setCurrentItem(0);
    	pager.startAutoScroll();
	
		pager.setScrollbarFadingEnabled(true);
		/*Animation anim = AnimationUtils.loadAnimation(PreviewImage.c, R.anim.fade_out);
		anim.setRepeatMode(Animation.REVERSE);
		
	    pager.startAnimation(anim);
	    anim.start();*/
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.deg, menu);
	

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		// toggle nav drawer on selecting action bar app icon/title

		switch (item.getItemId()) {
		
		case R.id.share:
			try{
				clearde();
			}
			catch(Exception e)
			{
			
			     

				Intent allApps = new Intent(Intent.ACTION_VIEW);
				allApps.setData(Uri.parse("http://www.google.com"));
				ResolveInfo allAppList = getPackageManager().resolveActivity(allApps, PackageManager.MATCH_DEFAULT_ONLY);
			    String s=allAppList.activityInfo.packageName;
				
	            if(!allAppList.activityInfo.name.contains("Resolver")){
				startActivityForResult(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).addCategory(Intent.CATEGORY_DEFAULT).setData(Uri.parse("package:" + s)), 0);
			
	            Toast.makeText(this, "Clear Default", Toast.LENGTH_LONG).show();
	            }
	            
			}
			break;
		default:
			break;	
		}
		return onContextItemSelected(item);
	}
	
	public	void clearde()
	{
		PackageManager packageManager = getPackageManager(); 
		String str1 = "android.intent.category.DEFAULT"; 
		String str2 = "android.intent.category.BROWSABLE"; 
		String str3 = "android.intent.action.VIEW"; 

		// Set the default entry must be one of the parameters, the user's actions comply with the filter, the default settings onset 
		IntentFilter filter = new IntentFilter (str3); 
		filter.addCategory (str1); 
		filter.addCategory (str2); 
		filter.addDataScheme ("http"); 
		// Set the browser page Activity 
		ComponentName component = new ComponentName (getApplicationContext().getPackageName (), PreviewActivity.class.getName ()); 

		Intent intent = new Intent (str3); 
		intent.addCategory (str2); 
		intent.addCategory (str1); 
		Uri uri = Uri.parse ("http://"); 
		intent.setDataAndType (uri, null); 

		// Find out the phone all installed browser program 
		List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS); 

		int size = resolveInfoList.size (); 
		ComponentName [] arrayOfComponentName = new ComponentName [size]; 
		for (int i = 0; i <size; i++) 
		{ 
		ActivityInfo activityInfo = resolveInfoList.get (i). activityInfo; 
		String packageName = activityInfo.packageName; 
		String className = activityInfo.name; 
		// Clear before the default settings 
		packageManager.clearPackagePreferredActivities(packageName); 
		ComponentName componentName = new ComponentName (packageName, 
		className); 
		arrayOfComponentName [i] = componentName; 
		} 
		try{
		packageManager.addPreferredActivity (filter, 
		IntentFilter.MATCH_CATEGORY_SCHEME, arrayOfComponentName, 
		component);
		}
		catch(Exception w)
		{}
	}
}
