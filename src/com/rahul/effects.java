package com.rahul;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;

public class effects 
{

	private static final int COLOR_MAX = 0XFF;




	public static Bitmap doBrightness(Bitmap src, int value) {
	    // image size
	    int width = src.getWidth();
	    int height = src.getHeight();
	    // create output bitmap
	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
	    // color information
	    int A, R, G, B;
	    int pixel;
	 
	    // scan through all pixels
	    for (int x = 0; x < width; ++x) {
	        for(int y = 0; y < height; ++y) {
	            // get pixel color
	            pixel = src.getPixel(x, y);
	            A = Color.alpha(pixel);
	            R = Color.red(pixel);
	            G = Color.green(pixel);
	            B = Color.blue(pixel);
	 
	            // increase/decrease each channel
	            R += value;
	            if(R > 255) { R = 255; }
	            else if(R < 0) { R = 0; }
	 
	            G += value;
	            if(G > 255) { G = 255; }
	            else if(G < 0) { G = 0; }
	 
	            B += value;
	            if(B > 255) { B = 255; }
	            else if(B < 0) { B = 0; }
	 
	            // apply new pixel color to output bitmap
	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
	        }
	    }
	 
	    // return final image
	    return bmOut;
	}
	
	
	
	
	public static Bitmap applySnowEffect(Bitmap source) {
	    // get image size
	    int width = source.getWidth();
	    int height = source.getHeight();
	    int[] pixels = new int[width * height];
	    // get pixel array from source
	    source.getPixels(pixels, 0, width, 0, 0, width, height);
	    // random object
	    Random random = new Random();
	     
	    int R, G, B, index = 0, thresHold = 50;
	    // iteration through pixels
	    for(int y = 0; y < height; ++y) {
	        for(int x = 0; x < width; ++x) {
	            // get current index in 2D-matrix
	            index = y * width + x;              
	            // get color
	            R = Color.red(pixels[index]);
	            G = Color.green(pixels[index]);
	            B = Color.blue(pixels[index]);
	            // generate threshold
	            thresHold = random.nextInt(COLOR_MAX);
	            if(R > thresHold && G > thresHold && B > thresHold) {
	                pixels[index] = Color.rgb(COLOR_MAX, COLOR_MAX, COLOR_MAX);
	            }                           
	        }
	    }
	    // output bitmap                
	    Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	    bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
	    return bmOut;
	}
	
	
	
	
	
	
	public static Bitmap applyReflection(Bitmap originalImage) {
	    // gap space between original and reflected
	    final int reflectionGap = 4;
	    // get image size
	    int width = originalImage.getWidth();
	    int height = originalImage.getHeight();          
	 
	    // this will not scale but will flip on the Y axis
	    Matrix matrix = new Matrix();
	    matrix.preScale(1, -1);
	       
	    // create a Bitmap with the flip matrix applied to it.
	    // we only want the bottom half of the image
	    Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height/2, width, height/2, matrix, false);          
	           
	    // create a new bitmap with same width but taller to fit reflection
	    Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height/2), Config.ARGB_8888);
	     
	    // create a new Canvas with the bitmap that's big enough for
	    // the image plus gap plus reflection
	    Canvas canvas = new Canvas(bitmapWithReflection);
	    // draw in the original image
	    canvas.drawBitmap(originalImage, 0, 0, null);
	    // draw in the gap
	    Paint defaultPaint = new Paint();
	    canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
	    // draw in the reflection
	    canvas.drawBitmap(reflectionImage,0, height + reflectionGap, null);
	      
	    // create a shader that is a linear gradient that covers the reflection
	    Paint paint = new Paint();
	    LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0,
	            bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
	            TileMode.CLAMP);
	    // set the paint to use this shader (linear gradient)
	    paint.setShader(shader);
	    // set the Transfer mode to be porter duff and destination in
	    paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
	    // draw a rectangle using the paint with our linear gradient
	    canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
	             
	    return bitmapWithReflection;
	}
	
	
	
	public static Bitmap doGreyscale(Bitmap src) {
	    // constant factors
	    final double GS_RED = 0.299;
	    final double GS_GREEN = 0.587;
	    final double GS_BLUE = 0.114;
	 
	    // create output bitmap
	    Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
	    // pixel information
	    int A, R, G, B;
	    int pixel;
	 
	    // get image size
	    int width = src.getWidth();
	    int height = src.getHeight();
	 
	    // scan through every single pixel
	    for(int x = 0; x < width; ++x) {
	        for(int y = 0; y < height; ++y) {
	            // get one pixel color
	            pixel = src.getPixel(x, y);
	            // retrieve color of all channels
	            A = Color.alpha(pixel);
	            R = Color.red(pixel);
	            G = Color.green(pixel);
	            B = Color.blue(pixel);
	            // take conversion up to one single value
	            R = G = B = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
	            // set new pixel color to output bitmap
	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
	        }
	    }
	 
	    // return final image
	    return bmOut;
	}
	
	public static Bitmap applyHueFilter(Bitmap source, int level) {
	    // get image size
	    int width = source.getWidth();
	    int height = source.getHeight();
	    int[] pixels = new int[width * height];
	    float[] HSV = new float[3];
	    // get pixel array from source
	    source.getPixels(pixels, 0, width, 0, 0, width, height);
	     
	    int index = 0;
	    // iteration through pixels
	    for(int y = 0; y < height; ++y) {
	        for(int x = 0; x < width; ++x) {
	            // get current index in 2D-matrix
	            index = y * width + x;              
	            // convert to HSV
	            Color.colorToHSV(pixels[index], HSV);
	            // increase Saturation level
	            HSV[0] *= level;
	            HSV[0] = (float) Math.max(0.0, Math.min(HSV[0], 360.0));
	            // take color back
	            pixels[index] |= Color.HSVToColor(HSV);
	        }
	    }
	    // output bitmap                
	    Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
	    return bmOut;       
	}
	
	
	public static Bitmap applySaturationFilter(Bitmap source, int level) {
	    // get image size
	    int width = source.getWidth();
	    int height = source.getHeight();
	    int[] pixels = new int[width * height];
	    float[] HSV = new float[3];
	    // get pixel array from source
	    source.getPixels(pixels, 0, width, 0, 0, width, height);
	 
	    int index = 0;
	    // iteration through pixels
	    for(int y = 0; y < height; ++y) {
	        for(int x = 0; x < width; ++x) {
	            // get current index in 2D-matrix
	            index = y * width + x;
	            // convert to HSV
	            Color.colorToHSV(pixels[index], HSV);
	            // increase Saturation level
	            HSV[1] *= level;
	            HSV[1] = (float) Math.max(0.0, Math.min(HSV[1], 1.0));
	            // take color back
	            pixels[index] |= Color.HSVToColor(HSV);
	        }
	    }
	    // output bitmap
	    Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
	    return bmOut;
	}
	
	
	public static final int FLIP_VERTICAL = 1;
	public static final int FLIP_HORIZONTAL = 2;
	 
	public static Bitmap flip(Bitmap src, int type) {
	    // create new matrix for transformation
	    Matrix matrix = new Matrix();
	    // if vertical
	    if(type == FLIP_VERTICAL) {
	        // y = y * -1
	        matrix.preScale(1.0f, -1.0f);
	    }
	    // if horizonal
	    else if(type == FLIP_HORIZONTAL) {
	        // x = x * -1
	        matrix.preScale(-1.0f, 1.0f);
	    // unknown type
	    } else {
	        return null;
	    }
	 
	    // return transformed image
	    return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
	}
	

}
