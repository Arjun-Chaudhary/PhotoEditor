package com.rahul;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class uploadpic extends Activity implements OnClickListener{
	
	Button camera;
	Button gallery;
	Button startediting;
	InputStream is=null;
	Bitmap bmp=null;
	ImageView iv;
	Bundle bd1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uploadpic);
		camera=(Button)findViewById(R.id.btnCamera);
		gallery=(Button)findViewById(R.id.btnGallery);
		startediting=(Button)findViewById(R.id.startedit);
		camera.setOnClickListener(this);
		gallery.setOnClickListener(this);
		startediting.setOnClickListener(this);
		iv=(ImageView)findViewById(R.id.imageView1);
		startediting.setVisibility(View.INVISIBLE);
		iv.setVisibility(View.INVISIBLE);
		
		
		
	}

	@Override
	public void onClick(View v) 
	
	{
		switch (v.getId()) 
		{
		case R.id.btnCamera:
			Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i,20);
			break;

			case R.id.btnGallery:
				Intent j=new Intent(Intent.ACTION_VIEW,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(j, 10);
				
				break;
				
			case R.id.startedit:
				
				Intent k=new Intent(this,editpic.class);
				Bundle finalimage=new Bundle();
				finalimage.putAll(bd1);
				k.putExtras(finalimage);
				startActivity(k);
				
				break;
		default:
			break;
		}
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	
	{

		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		
		if(data!=null)
		{
			bd1=data.getExtras();
		}
	
		
		if(requestCode==20 && data!=null && resultCode != RESULT_CANCELED)
		{
		try{
		
			bmp=(Bitmap)bd1.get("data");
			
			iv.setImageBitmap(bmp);
			startediting.setVisibility(View.VISIBLE);
			iv.setVisibility(View.VISIBLE);
			
		}
		catch(NullPointerException np)
		{
			
		}
		}
		
		else if (requestCode == 10 && resultCode == RESULT_OK && data!=null) 
		{
			
			
			
			try{
				
			
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
             
            startediting.setVisibility(View.VISIBLE);
			iv.setVisibility(View.VISIBLE);
           iv.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			}
			
			catch(NullPointerException ex)
			{
				
			}

			
		}
	

}
}
