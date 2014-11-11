package com.siliconorchard.url.site.preview.browser.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.siliconorchard.ComplexPreferences;
import com.siliconorchard.database.ListUserComplexPref;
import com.siliconorchard.database.User;
import com.siliconorchard.url.site.preview.browser.PreviewActivity;
import com.siliconorchard.url.site.preview.browser.R;
import com.siliconorchard.url.site.preview.browser.ui.HistoryActivity.ImageAdapter;
import com.siliconorchard.url.site.preview.browser.ui.HistoryActivity.ViewHolder;

public class PreviewGallery extends Activity{
private int count;
	
	private String[] arrPath;
	private ImageAdapter imageAdapter;
	public static ArrayList<String> sel;
	public static ArrayList<File> impfile;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
				WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		LayoutParams params = getWindow().getAttributes();
		Point p = new Point();
		getWindow().getWindowManager().getDefaultDisplay().getSize(p);
		Log.d("ld", p.x + "-" + p.y);
		params.height = p.y - ((p.y / 1920) * 180);
		params.width = p.x - ((p.x / 1080) * 180); // fixed width
		params.alpha = 1.0f;
		params.dimAmount = 0.5f;
		getWindow().setAttributes(
				(android.view.WindowManager.LayoutParams) params);
		setContentView(R.layout.previewurl);

		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#B40404")));
		
		getActionBar().setDisplayShowTitleEnabled(false);
		
		getActionBar().setLogo(R.drawable.home);
		getActionBar().setHomeButtonEnabled(true);
		setContentView(R.layout.main);

		
		int in = 0;
	
	/*	if(complexObject!=null)*/
		{	
			arrPath= new String[PreviewActivity.imagelist.size()];
		}
		for (String items : PreviewActivity.imagelist) {
			Log.d("kd", "dasd"+items);
			arrPath[in] = items;
			in++;
			
		}

		this.count = in;
		GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
		imageAdapter = new ImageAdapter();
		imagegrid.setAdapter(imageAdapter);
		/*}
		else
		{  
			Toast.makeText(getApplicationContext(), "No record Available",Toast.LENGTH_LONG).show();
			finish();
		}*/
	
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		// toggle nav drawer on selecting action bar app icon/title

		switch (item.getItemId()) {

		case android.R.id.home:
			Log.d("kd", "h");
	        finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}
	public class ImageAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public ImageAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return count;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(
						R.layout.galleryitem, null);
				holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
				holder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);
				
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.checkbox.setId(position);
			holder.imageview.setId(position);
		/*	holder.checkbox.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					CheckBox cb = (CheckBox) v;
					int id = cb.getId();
					if (thumbnailsselection[id]){
						cb.setChecked(false);
						thumbnailsselection[id] = false;
					} else {
						cb.setChecked(true);
						thumbnailsselection[id] = true;
					}
				}
			});*/
			holder.checkbox.setVisibility(View.GONE);
			holder.imageview.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					int id = v.getId();
					Intent ia = new Intent(getApplicationContext(),PreviewImage.class);
					ia.putExtra("url",id);
					startActivity(ia);

				}
			});
		    
            AQuery aq = new AQuery(convertView);
          //  File file = new File(arrPath[position]);  
            try{
            aq.id(holder.imageview).progress(R.id.progress).image(arrPath[position]);
            }
            catch(Exception e)
            {}
            //	//holder.imageview.setImageBitmap(thumbnails[position]);
		//	holder.checkbox.setChecked(thumbnailsselection[position]);
			holder.id = position;
			return convertView;
		}
	}
	class ViewHolder {
		ImageView imageview;
		CheckBox checkbox;
		int id;
	}
	
}
