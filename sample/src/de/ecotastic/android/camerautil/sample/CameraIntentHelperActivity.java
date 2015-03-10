package de.ecotastic.android.camerautil.sample;

import java.util.Date;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
import de.ecotastic.android.camerautil.R;
import de.ecotastic.android.camerautil.lib.*;

/**
 * A helper Activity to start the camera activity and retrieve the location 
 * and orientation of the photo.
 *  
 * @author Ralf Gehrer <ralf@ecotastic.de>
 */
public class CameraIntentHelperActivity extends FragmentActivity {
	CameraIntentHelper mCameraIntentHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCameraIntentHelper = new CameraIntentHelper(this, new CameraIntentHelperCallback() {
			@Override
			public void logMessage(String s) {
				CameraIntentHelperActivity.this.logMessage(s);
			}

			@Override
			public void onCanceled() {
				logMessage("Camera Intent was canceled");
			}

			@Override
			public void logException(Exception e) {
				logMessage(e.toString());
			}

			@Override
			public void onCouldNotTakePhoto() {
				Toast.makeText(getApplicationContext(), getString(R.string.error_could_not_take_photo), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onPhotoUriFound(Date dateCameraIntentStarted, Uri preDefinedCameraUri, Uri photoUriIn3rdLocation, Uri photoUri) {
				logMessage("Your photo is stored under: " + photoUri.toString());
			}

			@Override
			public void onPhotoUriNotFound() {
				logMessage("Could not find a photoUri that is != null");
			}

			@Override
			public void onSdCardNotMounted() {
				Toast.makeText(getApplicationContext(), getString(R.string.error_sd_card_not_mounted), Toast.LENGTH_LONG).show();
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

	/**
	 * Logs the passed exception messages.
	 * @param exceptionMessage
	 */
	protected void logMessage(String exceptionMessage) {
		Log.d(getClass().getName(), exceptionMessage);
	}
}
