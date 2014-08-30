package com.rahul;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class editpic extends Activity implements OnClickListener,OnSeekBarChangeListener
{

	  Bitmap bmp;
	
	ImageView iv_edit;
	Button greyscale,snoweffect,reflection,fliph,flipv;
	SlidingDrawer sd;
	SeekBar sb1;
	SeekBar sb2;
	SeekBar sb3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editphoto);
		iv_edit=(ImageView)findViewById(R.id.iv_edit);
	greyscale=(Button)findViewById(R.id.greyscale);
	snoweffect=(Button)findViewById(R.id.snoweffect);
	reflection=(Button)findViewById(R.id.reflection);
	fliph=(Button)findViewById(R.id.fliph);
	flipv=(Button)findViewById(R.id.flipv);
	sd=(SlidingDrawer)findViewById(R.id.slidingDrawer1);
	sb1=(SeekBar)findViewById(R.id.seekBar1);
	sb2=(SeekBar)findViewById(R.id.seekBar2);
	sb3=(SeekBar)findViewById(R.id.seekBar2);
	greyscale.setOnClickListener(this);
	reflection.setOnClickListener(this);
	snoweffect.setOnClickListener(this);
	fliph.setOnClickListener(this);
	flipv.setOnClickListener(this);
	sb1.setOnSeekBarChangeListener(this);
	sb2.setOnSeekBarChangeListener(this);
	sb3.setOnSeekBarChangeListener(this);
	
		Bundle recievedimage=getIntent().getExtras();
		bmp=(Bitmap)recievedimage.get("data");
		
		iv_edit.setImageBitmap(bmp);
		
	

      
		
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) 
	{
	
		// TODO Auto-generated method stub
		
		switch (v.getId())
		{ case R.id.greyscale:
			
			effects ef1=new effects();
			bmp=ef1.doGreyscale(bmp);
			iv_edit.setImageBitmap(bmp);
		
			break;
			
		case R.id.snoweffect:
			effects ef2=new effects();
		bmp=ef2.applySnowEffect(bmp);
			iv_edit.setImageBitmap(bmp);
			break;
			
			
		case R.id.reflection:
			effects ef3=new effects();
			bmp=ef3.applyReflection(bmp);
			iv_edit.setImageBitmap(bmp);

		default:
			break;
			
		case R.id.fliph:
			effects effh=new effects();
			bmp=effh.flip(bmp, 2);
			iv_edit.setImageBitmap(bmp);

		
			break;
			
		case R.id.flipv:
			effects effv=new effects();
			bmp=effv.flip(bmp, 1);
			iv_edit.setImageBitmap(bmp);

		
			break;
		}
		
	}
	
	

@Override
public boolean onCreateOptionsMenu(Menu menu)

{
	// TODO Auto-generated method stub
	
	MenuItem item1=menu.add(1,1,1,"Save");
	item1.setIcon(android.R.drawable.ic_menu_save);
	
	
	MenuItem item2=menu.add(1,2,2,"New Image");
	item2.setIcon(android.R.drawable.picture_frame);
	
	
	
	
	
	
	
	
	
	return super.onCreateOptionsMenu(menu);
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub
	
	switch(item.getItemId())
	
	{
	
	case 1:boolean x=saveToInternalSorage(bmp);
			Toast.makeText(this,"Image saved",5000).show();
		
		
		break;
		
		
	case 2:
		Intent i=new Intent(this,uploadpic.class);
		startActivity(i);
	break;
	
	}
	
	
	return super.onOptionsItemSelected(item);
}

@Override
public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	// TODO Auto-generated method stub
	
	switch(seekBar.getId())
	{
	
	case R.id.seekBar1:
	effects efb=new effects();
	 bmp=efb.doBrightness(bmp, progress);
	iv_edit.setImageBitmap(bmp);
	break;
	
	case R.id.seekBar2:
		effects efs=new effects();
		bmp=efs.applySaturationFilter(bmp, progress);
		iv_edit.setImageBitmap(bmp);
		break;
		
	case R.id.seekBar3:
		effects efh=new effects();
	 bmp=efh.applyHueFilter(bmp, progress);
		iv_edit.setImageBitmap(bmp);
		break;
	
	
	}
}

@Override
public void onStartTrackingTouch(SeekBar seekBar) {
	// TODO Auto-generated method stub
	
}

@Override
public void onStopTrackingTouch(SeekBar seekBar)
{
	// TODO Auto-generated method stub
	
}



private boolean saveToInternalSorage(Bitmap bitmap)
{
	File sdCardDirectory = Environment.getExternalStorageDirectory();
	
	   File image = new File(sdCardDirectory, "test.png");
	
	 boolean success = false;

	    // Encode the file as a PNG image.
	    FileOutputStream outStream;
	    try {

	        outStream = new FileOutputStream(image);
	        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream); 
	        /* 100 to keep full quality of the image */

	        outStream.flush();
	        outStream.close();
	        success = true;
	    } catch (FileNotFoundException e) 
	    {
	        e.printStackTrace();
	    } catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	    
	    if (success) {
	        Toast.makeText(getApplicationContext(), "Image saved with success",
	                Toast.LENGTH_LONG).show();
	    } else {
	        Toast.makeText(getApplicationContext(),
	                "Error during image saving", Toast.LENGTH_LONG).show();
	    }
	    
	    return success;
	    
}



}

