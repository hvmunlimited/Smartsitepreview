package com.siliconorchard.url.site.preview.browser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gdata.util.common.html.HtmlToText;
import com.siliconorchard.ComplexPreferences;
import com.siliconorchard.ConstantsData;
import com.siliconorchard.database.ListUserComplexPref;
import com.siliconorchard.database.Settings;
import com.siliconorchard.database.User;
import com.siliconorchard.url.site.preview.browser.ui.AndroidCustomGalleryActivity;
import com.siliconorchard.url.site.preview.browser.ui.PreviewGallery;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract.Contacts.Data;
import android.provider.ContactsContract.Directory;
import android.provider.SyncStateContract.Constants;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;

import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class PreviewActivity extends Activity {

	public static String URL = "";
	static int i = 0;
	static int searchindex = 0;
	static int limit = 1;
	public static String search_item = "";
	public static ArrayList<String> imagelist = new ArrayList<String>();
	public static ArrayList<String> searchlist = new ArrayList<String>();
	static boolean full = false;
	Context con = null;
	WebView webView = null;
	ProgressDialog progressDialog;
	private MenuItem mShareItem;
	 String extension="";
	 ProgressBar   bar;
	 boolean pluginenabled =true;
	 ComplexPreferences sett=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		con = this;
		sett = ComplexPreferences
				.getComplexPreferences(this, "mypref", MODE_PRIVATE);
		Settings settings = new Settings();
		settings = sett.getObject("Settings",
				Settings.class);
		if(settings!=null)
		{
		pluginenabled=settings.pluginenables;
		limit=settings.limit;
		}
		else
		{
		settings = new Settings();
	
		settings.pluginenables=pluginenabled;
		settings.limit=limit;
		sett.putObject("Settings",
				settings);
		sett.commit();
		}
	/*	progressDialog = ProgressDialog.show(this, null, null, false, true,
				new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub
						Toast.makeText(con, "Page Loading Please wait",
								Toast.LENGTH_SHORT).show();
					}
				});*/
	
		final Intent id = getIntent();
		URL = id.getDataString();
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
		
		getActionBar().setLogo(R.drawable.images);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setHomeButtonEnabled(false);
		 bar = (ProgressBar) findViewById(R.id.progressBar);
		 	bar.setVisibility(View.VISIBLE);
			 bar.setVisibility(View.VISIBLE);
		
		
		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(pluginenabled);
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.getSettings().setDisplayZoomControls(false);// (PluginState.ON);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setAppCacheEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true);
		webView.setHorizontalScrollbarOverlay(true);
		webView.setVerticalScrollbarOverlay(true);
		webView.getSettings().setLoadWithOverviewMode(true);

	
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webView.getSettings()
				.setUserAgentString(
						"Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");

		String url = URL;

		// TODO Auto-generated method stub
		Log.d("kd", "" + url);
		if (ConstantsData.isdownloadable(url)){
            Uri source = Uri.parse(url);
          	String s = URL.replaceAll("[^a-zA-Z0-9]", "") + "."+extension;
            // Make a new request pointing to the .apk url
            DownloadManager.Request request = new DownloadManager.Request(source);
            // appears the same in Notification bar while downloading
            request.setDescription("Downloading "+ s);
            request.setTitle(s);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            // save the file in the "Downloads" folder of SDCARD
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "SmartPigs.apk");
            // get download service and enqueue file
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
          
		   }
		if (url.contains("www.youtube.com")) {
			try {
				url = url.replace("https://google.com/url?q=", "");
				String[] ut = url.split("&");
				ut[0] = ut[0].replace("%3Fv%3D", "?v=");
				webView.getSettings().setJavaScriptEnabled(true);
				webView.loadUrl(Uri.parse(
						ut[0].replace("www.youtube.com", "m.youtube.com"))
						.toString());
				Log.d("kd",
						Uri.parse(ut[0].replace("youtube.com", "m.youtube.com"))
								.toString());

			} catch (Exception e) {
			}
		}
		if (url.contains("rtsp://")) {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

		} else {

			webView.loadUrl(url);

		}
		
		
		imagelist.clear();
		
		

		webView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				// TODO Auto-generated method stub
				Log.d("kd", "" + url);
				imagelist.clear();
			
				Log.d("kd", "" + extension);
				if (ConstantsData.isdownloadable(url)){
		               Uri source = Uri.parse(url);
		             	String s = URL.replaceAll("[^a-zA-Z0-9]", "") + "."+extension;
		               // Make a new request pointing to the .apk url
		               DownloadManager.Request request = new DownloadManager.Request(source);
		               // appears the same in Notification bar while downloading
		               request.setDescription("Downloading "+ s);
		               request.setTitle(s);
		               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
		                   request.allowScanningByMediaScanner();
		                   request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		               }
		               // save the file in the "Downloads" folder of SDCARD
		               request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "SmartPigs.apk");
		               // get download service and enqueue file
		               DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		               manager.enqueue(request);
		             
				   }
				if (url.contains("www.youtube.com")) {
					try {
						url = url.replace("https://google.com/url?q=", "");
						String[] ut = url.split("&");
						ut[0] = ut[0].replace("%3Fv%3D", "?v=");

						webView.loadUrl(Uri.parse(
								ut[0].replace("www.youtube.com",
										"m.youtube.com")).toString());
						Log.d("kd",
								Uri.parse(
										ut[0].replace("youtube.com",
												"m.youtube.com")).toString());

						return true;
					} catch (Exception e) {
					}
				}
				if (url.contains("rtsp://")) {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					return true;
				} else {
					 webView.loadUrl(url);
					//startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
				}

				// webView.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				i = 0;
				view.setVisibility(View.VISIBLE);
				bar.setVisibility(View.GONE);
				try {
				//	progressDialog.dismiss();
				} catch (Exception e) {
				}
				getActionBar().setHomeButtonEnabled(true);
				super.onPageFinished(view, url);
			}

			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view,
					String url) {
				WebResourceResponse wr = null;
				try {
					wr = new WebResourceResponse("", "", getAssets().open(
							"b.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				if (url.contains(".png") || url.contains(".jpg")
						|| url.contains(".jpeg") || url.contains(".gif")
						|| url.contains(".tiff") || url.contains(".JPEG")
						|| url.contains(".webhp") || url.contains(".tif")
						|| url.contains("data:image")
						|| url.contains("/images")) {
					imagelist.add(url);
					Log.d("kd", url);
					i++;
					if (i >= limit && full == false) {

						return wr;
					} else
						return super.shouldInterceptRequest(view, "");
				} else {

					return super.shouldInterceptRequest(view, url);
				}
			}

		});

		//seekbar implementation
		SeekBar seekBar = (SeekBar) findViewById(R.id.sb_volume);
		seekBar.setProgress(limit);
		  seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			  int progress = 0;
			  
			  @Override
			  public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
				
				  limit=progresValue;
				   }
			
			  @Override
			  public void onStartTrackingTouch(SeekBar seekBar) {
				 // Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
				  progress = seekBar.getProgress();
			  }
			
			  @Override
			  public void onStopTrackingTouch(SeekBar seekBar) {
				  
				  if(progress!=limit)
				  {
				  webView.getSettings().setJavaScriptEnabled(true);
					webView.reload();
				
					Settings settings = new Settings();
					settings.pluginenables=pluginenabled;
					settings.limit=limit;
					sett.putObject("Settings",
							settings);
					sett.commit();
					 Toast.makeText(getApplicationContext(), "Image allowed up to "+limit, 800).show();
						
				  }//Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
			  }
		   });
		  
		  /////
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
	

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
			intent.putExtra(Intent.EXTRA_TEXT, URL);
			startActivity(intent);
			break;
         case R.id.browser:
			
        	 startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webView.getUrl())));
        	 finish();
			break;
		case R.id.camera:
			webView.setDrawingCacheEnabled(true);
			webView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
			webView.measure(MeasureSpec.makeMeasureSpec(
					MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			webView.layout(0, 120, webView.getMeasuredWidth(),
					(int) (webView.getMeasuredHeight() * 0.30));
			new Background().execute();
	
			break;
		case R.id.save:
			ComplexPreferences complexPreferences = ComplexPreferences
					.getComplexPreferences(this, "mypref", MODE_PRIVATE);
			ListUserComplexPref complexObject = new ListUserComplexPref();
			complexObject = complexPreferences.getObject("list",
					ListUserComplexPref.class);

			List<User> users = new ArrayList<User>();
			// populate your List
			if(complexObject!=null){
			for (User user : complexObject.users) {
				users.add(user);
			}
			
			}
			else
			{
				complexObject=new ListUserComplexPref();
			}
			User user = new User();
			user.setName("URL");
			user.setAge(users.size());
			user.setActive(URL);
			Log.d("kd", "sd"+user.getActive());
			users.add(user);
			complexObject.setUsers(users);

			complexPreferences.putObject("list", complexObject);
			complexPreferences.commit();
			//
			 complexPreferences = ComplexPreferences
					.getComplexPreferences(this, "mypref", MODE_PRIVATE);
			 complexObject = complexPreferences.getObject(
					"list", ListUserComplexPref.class);
			int in = 0;
		
			for (User items : complexObject.getUsers()) {
				
				Log.d("kd", "dsf"+items.getName());
				in++;
				
			}
			    
			break;
		case android.R.id.home:
			Log.d("kd", "h");
			Intent intent2 = new Intent(this,PreviewGallery.class);
			
	        startActivity(intent2); 
			webView.getSettings().setJavaScriptEnabled(true);
			webView.reload();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}

	class Background extends AsyncTask<Void, Void, Bitmap> {
		void load(Bitmap bm) {

			String s = URL.replaceAll("[^a-zA-Z0-9]", "") + ".png";
			if (bm != null) {
				try {

					File folder = new File(
							Environment.getExternalStorageDirectory(),
							"/firstinfirstout");
					boolean success = true;
					if (!folder.exists()) {
						success = folder.mkdir();
					}
					if (success) {
						File file = new File(
								Environment.getExternalStorageDirectory(),
								"/firstinfirstout/" + s);
						
						OutputStream fOut = null;
						fOut = new FileOutputStream(file);

						bm.compress(Bitmap.CompressFormat.PNG, 25, fOut);
						fOut.flush();
						fOut.close();
						bm.recycle();
						
					} else {
						File file = new File(
								Environment.getExternalStorageDirectory(),
								"/firstinfirstout/" + s);
					
						OutputStream fOut = null;
						fOut = new FileOutputStream(file);

						bm.compress(Bitmap.CompressFormat.PNG, 25, fOut);
						fOut.flush();
						fOut.close();
						bm.recycle();

						
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			try {
				/**/
				Bitmap bm =null;
				try {

					webView.buildDrawingCache();
				} catch (Exception e) {
					Log.d("kd", "failed 2" + e.getMessage());
				}
				try {

			   bm = Bitmap.createBitmap(webView.getMeasuredWidth(),
						webView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
				}
				catch (Exception e) {
					
					
				}
				Canvas bigcanvas = new Canvas(bm);
				Paint paint = new Paint();
				int iHeight = bm.getHeight();
				bigcanvas.drawBitmap(bm, 0, iHeight, paint);
				webView.draw(bigcanvas);
				load(bm);
				return bm;
			}

			catch (Exception e) {

				try {
					Log.d("kd", "failed step2" + e.getMessage());
					Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(),
							webView.getHeight(), Config.ARGB_8888);
					Canvas canvas = new Canvas(bitmap);
					webView.draw(canvas);
					load(bitmap);
					return bitmap;
				}

				catch (Exception ea) {
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Bitmap bm) {
			if(bm!=null){
			Toast.makeText(con, "Loaded", Toast.LENGTH_LONG).show();}
			else
			{
				Toast.makeText(con, "Not Enough Memory to Capture the full web as image, Zoom out for minimize the image size", Toast.LENGTH_LONG).show();
			}
			}
	}
}
