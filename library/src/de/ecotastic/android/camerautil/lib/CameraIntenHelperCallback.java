package de.ecotastic.android.camerautil.lib;

import android.net.*;

import java.util.*;

/**
 * Created by peter on 3/10/15.
 */
public interface CameraIntenHelperCallback {
	void logMessage(String s);

	void onCanceled();

	void logException(Exception e);

	void onCouldNotTakePhoto();

	void onPhotoUriFound(Date dateCameraIntentStarted, Uri preDefinedCameraUri, Uri photoUriIn3rdLocation, Uri photoUri);
}
