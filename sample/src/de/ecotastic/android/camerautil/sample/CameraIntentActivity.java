package de.ecotastic.android.camerautil.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import de.ecotastic.android.camerautil.lib.CameraIntentHelper;
import de.ecotastic.android.camerautil.lib.CameraIntentHelperCallback;
import de.ecotastic.android.camerautil.sample.util.BitmapHelper;

/**
 * Example activity of how to use the CameraIntentHelper to retrieve the location
 * and orientation of the photo taken with via camera intent.
 *  
 * @author Ralf Gehrer <ralf@ecotastic.de>
 */
public class CameraIntentActivity extends FragmentActivity {
	CameraIntentHelper mCameraIntentHelper;

    TextView messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(de.ecotastic.android.camerautil.sample.R.layout.activity_camera_intent);
        messageView = (TextView) findViewById(de.ecotastic.android.camerautil.sample.R.id.activity_camera_intent_message);
        Button startCameraButton = (Button) findViewById(de.ecotastic.android.camerautil.sample.R.id.activity_camera_intent_start_camera_button);
        startCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCameraIntentHelper != null) {
                    mCameraIntentHelper.startCameraIntent();
                }
            }
        });

        setupCameraIntentHelper();
    }

    private void setupCameraIntentHelper() {
        mCameraIntentHelper = new CameraIntentHelper(this, new CameraIntentHelperCallback() {
            @Override
            public void onPhotoUriFound(Date dateCameraIntentStarted, Uri photoUri, int rotateXDegrees) {
                messageView.setText(getString(R.string.activity_camera_intent_photo_uri_found) + photoUri.toString());

                Bitmap photo = BitmapHelper.readBitmap(CameraIntentActivity.this, photoUri);
                if (photo != null) {
                    photo = BitmapHelper.shrinkBitmap(photo, 300, rotateXDegrees);
                    ImageView imageView = (ImageView) findViewById(de.ecotastic.android.camerautil.sample.R.id.activity_camera_intent_image_view);
                    imageView.setImageBitmap(photo);
                }
            }

            @Override
            public void deletePhotoWithUri(Uri photoUri) {
                BitmapHelper.deleteImageWithUriIfExists(photoUri, CameraIntentActivity.this);
            }

            @Override
            public void onSdCardNotMounted() {
                Toast.makeText(getApplicationContext(), getString(R.string.error_sd_card_not_mounted), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCanceled() {
                Toast.makeText(getApplicationContext(), getString(R.string.warning_camera_intent_canceled), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCouldNotTakePhoto() {
                Toast.makeText(getApplicationContext(), getString(R.string.error_could_not_take_photo), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPhotoUriNotFound() {
                messageView.setText(getString(R.string.activity_camera_intent_photo_uri_not_found));
            }

            @Override
            public void logException(Exception e) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_sth_went_wrong), Toast.LENGTH_LONG).show();
                Log.d(getClass().getName(), e.getMessage());
            }
        });
    }

    @Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
        mCameraIntentHelper.onSaveInstanceState(savedInstanceState);
    }
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mCameraIntentHelper.onRestoreInstanceState(savedInstanceState);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		mCameraIntentHelper.onActivityResult(requestCode, resultCode, intent);
    }
}