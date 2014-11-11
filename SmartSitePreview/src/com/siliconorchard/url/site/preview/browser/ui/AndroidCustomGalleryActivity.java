package com.siliconorchard.url.site.preview.browser.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.androidquery.AQuery;
import com.siliconorchard.url.site.preview.browser.R;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class AndroidCustomGalleryActivity extends Activity {
	private int count;
	
	public static String[] arrPath;
	private ImageAdapter imageAdapter;
	public static ArrayList<String> sel;
	public static ArrayList<File> impfile;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		String selection=MediaStore.Images.Media.DATA +" like?";
		final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID , MediaStore.Images.Media.HEIGHT};
		final String orderBy = MediaStore.Images.Media._ID;
		Cursor imagecursor = getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, selection,
				new String[] {"%firstinfirstout%"}, orderBy);
		int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media.HEIGHT);
		
	//	this.thumbnails = new Bitmap[this.count];
		this.arrPath = new String[imagecursor.getCount()];
	//	this.thumbnailsselection = new boolean[this.count];
		int in=0;
		for (int i = 0; i < imagecursor.getCount(); i++) {
			imagecursor.moveToPosition(i);
			int id = imagecursor.getInt(image_column_index);
			Log.d("kd", "a"+id);
			int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
	          if(id<=4096){
			arrPath[in]= imagecursor.getString(dataColumnIndex);
	          in++;
	          }
		}
		this.count = in;
		GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
		imageAdapter = new ImageAdapter();
		imagegrid.setAdapter(imageAdapter);
		imagecursor.close();
		
		Toast.makeText(getApplicationContext(), "Some Images Dont load due to memory size, U can view them using folder browse of firstinfirstout in memorycard",Toast.LENGTH_SHORT);
	
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
					Intent ia = new Intent(getApplicationContext(),PreviewfileImage.class);
					ia.putExtra("url",id);
					startActivity(ia);

				}
			});
		    
            AQuery aq = new AQuery(convertView);
            File file = new File(arrPath[position]);  
            try{
            aq.id(holder.imageview).progress(R.id.progress).image(file,300);
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