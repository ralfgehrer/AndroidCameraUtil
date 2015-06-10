package de.ecotastic.android.camerautil.sample;

import android.graphics.Bitmap;
import android.net.*;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

/**
 * Example Activity of how to use the CameraIntentHelperActivity
 * 
 * @author Ralf Gehrer <ralf@ecotastic.de>
 */
public class TakePhotoActivity extends CameraIntentHelperActivity {
	private de.ecotastic.android.camerautil.sample.util.BitmapHelper BitmapHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_photo);
	}

	@Override
	void onPhotoUriFound(Date dateCameraIntentStarted, Uri preDefinedCameraUri, Uri photoUriIn3rdLocation, Uri photoUri, int rotateXDegrees) {
		TextView uirView = (TextView) findViewById(R.id.activity_take_photo_image_uri);
		uirView.setText("photo uri: " + photoUri.toString());
		
		Bitmap photo = BitmapHelper.readBitmap(this, photoUri);
        if (photo != null) {
            photo = BitmapHelper.shrinkBitmap(photo, 300, rotateXDegrees);
            ImageView imageView = (ImageView) findViewById(R.id.activity_take_photo_image_view);
 			imageView.setImageBitmap(photo); 
        }
		
        //Delete photo in second location (if applicable)
        if (preDefinedCameraUri != null && !preDefinedCameraUri.equals(photoUri)) {
        	BitmapHelper.deleteImageWithUriIfExists(preDefinedCameraUri, this);
        }
        //Delete photo in third location (if applicable)
        if (photoUriIn3rdLocation != null) {
        	BitmapHelper.deleteImageWithUriIfExists(photoUriIn3rdLocation, this);
        }
	}
	
	@Override
	void onPhotoUriNotFound() {
		super.onPhotoUriNotFound();
		TextView uirView = (TextView) findViewById(R.id.activity_take_photo_image_uri);
		uirView.setText("photo uri: not found");
	}

	public void onStartCamera(View view) {
		mCameraIntentHelper.startCameraIntent();
	}
}
