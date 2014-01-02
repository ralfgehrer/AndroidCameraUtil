package de.ecotastic.android.camerautil.example;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
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
		Toast.makeText(this, "photo uri: " + photoUri.toString(), Toast.LENGTH_LONG).show();
		
		Bitmap photo = BitmapHelper.readBitmap(this, photoUri);
        if (photo != null) {
            photo = BitmapHelper.shrinkBitmap(photo, 300, rotateXDegrees);
            ImageView imageView = (ImageView) findViewById(R.id.acitvity_take_photo_image_view);
 			imageView.setImageBitmap(photo); 
        }
		
        //Delete photo in second location (if applicable)
        if (preDefinedCameraUri != null && !preDefinedCameraUri.equals(photoUri)) {
        	BitmapHelper.deleteImageWithUriIfExists(preDefinedCameraUri, this);
        }
	}
	
	@Override
	protected void onPhotoUriNotFound() {
		super.onPhotoUriNotFound();
		Toast.makeText(this, getString(R.string.error_could_not_take_photo), Toast.LENGTH_LONG).show();
	}
}