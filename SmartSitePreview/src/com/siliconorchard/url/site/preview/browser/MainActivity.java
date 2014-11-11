package com.siliconorchard.url.site.preview.browser;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.siliconorchard.database.Settings;
import com.siliconorchard.url.site.preview.browser.ui.AndroidCustomGalleryActivity;
import com.siliconorchard.url.site.preview.browser.ui.HistoryActivity;
import com.siliconorchard.url.site.preview.browser.ui.HowtouseActivity;
import com.siliconorchard.url.site.preview.browser.ui.OpenurlActivity;
import com.siliconorchard.url.site.preview.browser.ui.SettingsActivity;

public class MainActivity extends Activity implements OnClickListener {

	Button Images = null;
	Button History = null;
	Button settings= null;
	Button rateus=null;
	TextView preview=null;
	TextView howtouse=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Images = (Button)findViewById(R.id.images);
		Images.setOnClickListener(this);
		History  = (Button)findViewById(R.id.histry);
		History.setOnClickListener(this);
		settings  = (Button)findViewById(R.id.settings);
		settings.setOnClickListener(this);
		rateus  = (Button)findViewById(R.id.rateus);
		rateus.setOnClickListener(this);
		
		preview  = (TextView)findViewById(R.id.help);
		preview.setOnClickListener(this);
		
		howtouse= (TextView)findViewById(R.id.textview);
		howtouse.setOnClickListener(this);
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.images:
		Intent intent = new Intent(this,AndroidCustomGalleryActivity.class);
		
        startActivity(intent); 
		break;
	case R.id.help:
		Intent helpintent = new Intent(this,OpenurlActivity.class);
		
        startActivity(helpintent); 
		break;	
	case R.id.histry:
		Intent intenthstr = new Intent(this,HistoryActivity.class);
		
        startActivity(intenthstr); 
		break;
	case R.id.settings:
		Intent intensetr = new Intent(this,SettingsActivity.class);
		
        startActivity(intensetr); 
		break;
	case R.id.rateus:

        String str ="https://play.google.com/store/apps/details?id=com.siliconorchard.url.site.preview.browser";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));

		break;
	case R.id.textview:
        Intent intenho = new Intent(this,HowtouseActivity.class);
		
        startActivity(intenho); 
     

		break;
	default:
		break;
	}
		
	}



}
