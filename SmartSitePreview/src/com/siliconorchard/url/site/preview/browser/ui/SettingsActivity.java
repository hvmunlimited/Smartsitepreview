package com.siliconorchard.url.site.preview.browser.ui;

import com.siliconorchard.ComplexPreferences;
import com.siliconorchard.database.Settings;
import com.siliconorchard.url.site.preview.browser.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity {
	
	static int limit = 1;
	boolean pluginenabled =true;
	 ComplexPreferences sett=null;
	 TextView text = null;
	ToggleButton tog = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.settings);
	    text=(TextView)findViewById(R.id.textView1);
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
		
		
		
		  text.setText("No of image to load "+limit+"");
		  
		  tog =(ToggleButton)findViewById(R.id.toggleButton1);
		  tog.setChecked(pluginenabled);
		  tog.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Settings settings = new Settings();
				settings.pluginenables=isChecked;
				pluginenabled=isChecked;
				settings.limit=limit;
				sett.putObject("Settings",
						settings);
				sett.commit();
			}
		});
		  
		SeekBar seekBar = (SeekBar) findViewById(R.id.sb_volume);
		seekBar.setProgress(limit);
		  seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			  int progress = 0;
			  
			  @Override
			  public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
				  progress = progresValue;
				  limit=progresValue;
				  text.setText("No of image to load "+progresValue+"");
			//	  Toast.makeText(getApplicationContext(), "Image allowed up to "+limit, 800).show();
			  }
			
			  @Override
			  public void onStartTrackingTouch(SeekBar seekBar) {
				 // Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
			  }
			
			  @Override
			  public void onStopTrackingTouch(SeekBar seekBar) {
				
				
					Settings settings = new Settings();
					settings.pluginenables=pluginenabled;
					settings.limit=limit;
					sett.putObject("Settings",
							settings);
					sett.commit();
				  //Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
			  }
		   });
	}
}
