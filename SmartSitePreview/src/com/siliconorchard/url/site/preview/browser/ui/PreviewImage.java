package com.siliconorchard.url.site.preview.browser.ui;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.androidquery.AQuery;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.siliconorchard.url.site.preview.browser.PreviewActivity;
import com.siliconorchard.url.site.preview.browser.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class PreviewImage extends Activity{
	
	public static Context c;
	public static ImageLoader imageLoader = ImageLoader.getInstance();
	AutoScrollViewPager	pager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		c=this;
		imageLoader.init(ImageLoaderConfiguration.createDefault(c));
		int url = getIntent().getExtras().getInt("url");
	/*	ImageView im = new ImageView(this);
		im.setBackgroundColor(Color.BLACK);
		AQuery aq = new AQuery(im);
		aq.image(url, true, true);
		setContentView(im);*/
		setContentView(R.layout.image_slider);
		String[] imageUrls = new String[PreviewActivity.imagelist.size()];
		for(int i=0; i<PreviewActivity.imagelist.size(); i++)
		{
			/*ModelWPCategory modelWPCategory = new ModelWPCategory();
			modelWPCategory = wpCategories.get(i);*/
		//	categoryNames[i]= Imageurl.newyearsvalues.get(i);
			
			imageUrls[i] =PreviewActivity.imagelist.get(i);;
		}
		//int pagerPosition = bundle.getInt(Extra.IMAGE_POSITION, 0);

		ImagePagerAdapter im = new ImagePagerAdapter(imageUrls);
			pager = (AutoScrollViewPager) findViewById(R.id.pagera);
		pager.setAdapter(im);
		pager.setCurrentItem(url);
	//	pager.startAutoScroll();
		
		pager.setScrollbarFadingEnabled(true);
		/*Animation anim = AnimationUtils.loadAnimation(PreviewImage.c, R.anim.fade_out);
		anim.setRepeatMode(Animation.REVERSE);
		
	    pager.startAnimation(anim);
	    anim.start();*/
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.sharee, menu);
	

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		// toggle nav drawer on selecting action bar app icon/title

		switch (item.getItemId()) {
		case R.id.close:
			finish();
			break;
		case R.id.share:
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_TEXT, PreviewActivity.imagelist.get(pager.getCurrentItem()));
			startActivity(intent);
			break;
		default:
			break;	
		}
		return onContextItemSelected(item);
	}
	
	
}
