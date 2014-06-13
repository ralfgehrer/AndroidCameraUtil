package de.ecotastic.android.camerautil.example;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.ecotastic.android.camerautil.R;
import de.ecotastic.android.camerautil.lib.CameraIntentHelperActivity;
import de.ecotastic.android.camerautil.util.BitmapHelper;

/**
 * Example Activity of how to use the CameraIntentHelperActivity
 * 
 * @author Ralf Gehrer <ralf@ecotastic.de>
 */
public class TakePhotoActivity extends CameraIntentHelperActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_photo);
	}
	
	public void onStartCamera(View view) {
		startCameraIntent();
	}
	
	@Override
	protected void onPhotoUriFound() {
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
	protected void onPhotoUriNotFound() {
		super.onPhotoUriNotFound();
		TextView uirView = (TextView) findViewById(R.id.activity_take_photo_image_uri);
		uirView.setText("photo uri: not found");
	}
}
