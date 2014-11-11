package com.siliconorchard.url.site.preview.browser.ui;

import java.net.URI;

import com.siliconorchard.url.site.preview.browser.PreviewActivity;
import com.siliconorchard.url.site.preview.browser.R;

import android.app.Activity;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class OpenurlActivity extends Activity{
	
	Button go=null;
	TextView URL=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewinbrowser);
		URL=(TextView)findViewById(R.id.urladress);
		try{
		URL.setText(readFromClipboard());
		}
		catch(Exception e){URL.setText("http://www.example.com");}
		
		go =(Button)findViewById(R.id.go);
		go.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.setClass(getApplicationContext(), PreviewActivity.class);
			String s =URL.getText()+"";
				if(!s.contains("http"))
				{
					s="http://"+s;
				}
			Uri ur =Uri.parse(s+"");
				in.setData(ur);
				startActivity(in);
				

			}
		});
	}
	public String readFromClipboard() {
	    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
	    if (clipboard.hasPrimaryClip()) {
	        android.content.ClipDescription description = clipboard.getPrimaryClipDescription();
	        android.content.ClipData data = clipboard.getPrimaryClip();
	        if (data != null && description != null && description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) 
	            return String.valueOf(data.getItemAt(0).getText());
	    }
	    return "http://www.example.com";
	}
}
