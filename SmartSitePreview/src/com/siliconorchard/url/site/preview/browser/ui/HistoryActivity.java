package com.siliconorchard.url.site.preview.browser.ui;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.siliconorchard.ComplexPreferences;
import com.siliconorchard.database.ListUserComplexPref;
import com.siliconorchard.database.User;
import com.siliconorchard.url.site.preview.browser.R;

public class HistoryActivity extends Activity {
	private int count;
	
	private String[] arrPath;
	private ImageAdapter imageAdapter;
	public static ArrayList<String> sel;
	public static ArrayList<File> impfile;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ComplexPreferences complexPreferences = ComplexPreferences
				.getComplexPreferences(this, "mypref", MODE_PRIVATE);
		ListUserComplexPref complexObject = complexPreferences.getObject(
				"list", ListUserComplexPref.class);
		int in = 0;
	
		if(complexObject!=null)
		{	arrPath= new String[complexObject.getUsers().size()];
		for (User items : complexObject.getUsers()) {
			Log.d("kd", "dasd"+items.getActive());
			arrPath[in] = items.getActive().toString();
			in++;
			
		}

		this.count = in;
		GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
		imageAdapter = new ImageAdapter();
		imagegrid.setAdapter(imageAdapter);
		}
		else
		{  
			Toast.makeText(getApplicationContext(), "No record Available",Toast.LENGTH_LONG).show();
			finish();
		}
	
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
						R.layout.historyitem, null);
				holder.imageview = (WebView) convertView.findViewById(R.id.thumbImage);
				
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
		
			holder.imageview.setId(position);
			
			holder.imageview.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					int id = v.getId();
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(((WebView) v).getUrl())));
				}
			});
			holder.imageview.setHapticFeedbackEnabled(false);
			// Intercept long click events so that they dont reach the webview
			holder.imageview.setOnLongClickListener(new OnLongClickListener() {
			    @Override
			    public boolean onLongClick(final View v) {
			        return true;
			    }
			});
			// Intercept any touch event not related to scrolling/zooming
			holder.imageview.setOnTouchListener(new View.OnTouchListener() {
			    @Override
			    public boolean onTouch(final View v, final MotionEvent event) {
			        if (event.getAction() == MotionEvent.ACTION_DOWN) {
			            // Change ACTION_DOWN location to outside of the webview so that it doesnt affect 
			            // pressable element in the webview (buttons receiving PRESS" will change appearance)
			        	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(((WebView) v).getUrl())));
						
			        }
			        // Intercept any "up" event
			        return event.getAction() == MotionEvent.ACTION_UP;
			    }
			});
			holder.imageview.loadUrl(arrPath[position]);
			holder.imageview.getSettings().getUseWideViewPort();
			holder.imageview.getSettings().setUseWideViewPort(true);
			holder.imageview.setWebViewClient(new WebViewClient(){
				
				@Override
				public boolean shouldOverrideUrlLoading(WebView view,
						String url) {
					// TODO Auto-generated method stub
					view.loadUrl(url);
					return true;
					
				}
			});
			holder.id = position;
			return convertView;
		}
	}
	class ViewHolder {
		WebView imageview;
		
		int id;
	}
	
	
}