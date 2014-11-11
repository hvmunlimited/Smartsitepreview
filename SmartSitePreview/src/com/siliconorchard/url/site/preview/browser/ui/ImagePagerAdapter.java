package com.siliconorchard.url.site.preview.browser.ui;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.siliconorchard.url.site.preview.browser.R;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;





public class ImagePagerAdapter extends PagerAdapter {
	private final static String stpos="";
	private String[] images;
	private LayoutInflater inflater;

	public ImagePagerAdapter(String[] images) {
		this.images = images;
		inflater = ((Activity) PreviewImage.c).getLayoutInflater();
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public void finishUpdate(View container) {
	}

	@Override
	public int getCount() {
		return images.length;
	}


	@Override
	public Object instantiateItem(View view,  int position) {
		if(view!=null){
		final View imageLayout = inflater.inflate(R.layout.item_pager_image, null);
		final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image_item_pager);
		final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.progress_loading_item_pager);
/*		Animation anim = AnimationUtils.loadAnimation(PreviewImage.c, R.anim.fade_out);
		anim.setRepeatMode(Animation.REVERSE);
		imageView.setAnimation(anim);
		imageView.startAnimation(anim);
		anim.start();*/
		spinner.setVisibility(View.VISIBLE);
	/*	AQuery aq = new AQuery(imageLayout);
		Log.d("ds","sd" +images[position] );
		aq.image(images[position]);
		aq.image(new BitmapAjaxCallback(){
			
		@Override
		protected void callback(String url, ImageView iv, Bitmap bm,
				AjaxStatus status) {
			// TODO Auto-generated method stub
			super.callback(url, iv, bm, status);

			spinner.setVisibility(View.GONE);
		}
		});*/
	//	if(imageUrl=="")
         String		imageUrl = images[position];
		
		Log.d("vcvcb",imageUrl+""+this.getItemPosition(position));
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.boroeser)
		.cacheOnDisk(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.build();
	
		PreviewImage.imageLoader.displayImage(images[position], imageView, options, new ImageLoadingListener() {
		
	
		

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
				spinner.setVisibility(View.GONE);
				/*Animation anim = AnimationUtils.loadAnimation(PreviewImage.c, R.anim.fade_out);
				imageView.setAnimation(anim);
				anim.start();*/
			}

		
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				// TODO Auto-generated method stub
				spinner.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				// TODO Auto-generated method stub
				spinner.setVisibility(View.GONE);
				imageView.setImageResource(R.drawable.refresh);
			}
		});

		((ViewPager) view).addView(imageLayout, 0);
		
		return imageLayout;
		}
		else
			return view;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View container) {
	}
}

