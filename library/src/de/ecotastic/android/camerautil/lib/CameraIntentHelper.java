package de.ecotastic.android.camerautil.lib;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.util.Date;
import java.util.Locale;

/**
 * A helper class to start the camera activity and retrieve the location
 * and orientation of the photo.
 *  
 * @author Ralf Gehrer <ralf@ecotastic.de>
 */
public class CameraIntentHelper {
	private static final String DATE_CAMERA_INTENT_STARTED_STATE = "de.ecotastic.android.camerautil.example.TakePhotoActivity.dateCameraIntentStarted";
	private static final String CAMERA_PIC_URI_STATE = "de.ecotastic.android.camerautil.example.TakePhotoActivity.CAMERA_PIC_URI_STATE";
	private static final String PHOTO_URI_STATE = "de.ecotastic.android.camerautil.example.TakePhotoActivity.PHOTO_URI_STATE";
	private static final String ROTATE_X_DEGREES_STATE = "de.ecotastic.android.camerautil.example.TakePhotoActivity.ROTATE_X_DEGREES_STATE";
	
	/**
	 * Date and time the camera intent was started.
	 */
	private Date dateCameraIntentStarted = null;
	/**
	 * Default location where we want the photo to be ideally stored.
	 */
	private Uri preDefinedCameraUri = null;
    /**
     * Retrieved location of the photo.
     */
    private Uri photoUri = null;
    /**
	 * Potential 3rd location of photo data.
	 */
	private Uri photoUriIn3rdLocation = null;
	/**
	 * Orientation of the retrieved photo.
	 */
	private int rotateXDegrees = 0;


	private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	private final CameraIntentHelperCallback mCameraIntentHelperCallback;
	public Activity mActivity;


	public CameraIntentHelper(Activity activity, CameraIntentHelperCallback cameraIntentHelperCallback) {
		mActivity = activity;
		mCameraIntentHelperCallback = cameraIntentHelperCallback;
	}

    /**
     * Call to save the helpers instance state in mActivity's savedInstanceState.
     */
	public void onSaveInstanceState(Bundle savedInstanceState) {
		if (dateCameraIntentStarted != null) {
			savedInstanceState.putString(DATE_CAMERA_INTENT_STARTED_STATE, DateParser.dateToString(dateCameraIntentStarted));
		}
		if (preDefinedCameraUri != null) {
			savedInstanceState.putString(CAMERA_PIC_URI_STATE, preDefinedCameraUri.toString());
		}
		if (photoUri != null) {
			savedInstanceState.putString(PHOTO_URI_STATE, photoUri.toString());
		}
		savedInstanceState.putInt(ROTATE_X_DEGREES_STATE, rotateXDegrees);
	}

    /**
     * Call to reinitialize the helpers instance state.
     */
	public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(DATE_CAMERA_INTENT_STARTED_STATE)) {
                dateCameraIntentStarted = DateParser.stringToDate(savedInstanceState.getString(DATE_CAMERA_INTENT_STARTED_STATE));
            }
            if (savedInstanceState.containsKey(CAMERA_PIC_URI_STATE)) {
                preDefinedCameraUri = Uri.parse(savedInstanceState.getString(CAMERA_PIC_URI_STATE));
            }
            if (savedInstanceState.containsKey(PHOTO_URI_STATE)) {
                photoUri = Uri.parse(savedInstanceState.getString(PHOTO_URI_STATE));
            }
            if (savedInstanceState.containsKey(ROTATE_X_DEGREES_STATE)) {
                rotateXDegrees = savedInstanceState.getInt(ROTATE_X_DEGREES_STATE);
            }
        }
	}

	/**
	 * Starts the camera intent depending on the device configuration. 
	 * 
	 * <b>for Samsung and Sony devices:</b>
	 * We call the camera activity with the method call to startActivityForResult. We only set the
     * constant CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE. We do NOT set any other intent extras.
	 * 
	 * <b>for all other devices:</b>
	 * We call the camera activity with the method call to startActivityForResult as previously.
     * This time, however, we additionally set the intent extra MediaStore.EXTRA_OUTPUT and provide
     * an URI, where we want the image to be stored.
	 * 
	 * In both cases we remember the time the camera activity was started.
	 */
	public void startCameraIntent() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				// NOTE: Do NOT SET: intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPicUri) 
				// on Samsung Galaxy S2/S3/.. for the following reasons:
				// 1.) it will break the correct picture orientation
				// 2.) the photo will be stored in two locations (the given path and, additionally, in the MediaStore)
				String manufacturer = android.os.Build.MANUFACTURER.toLowerCase(Locale.ENGLISH);
				String model = android.os.Build.MODEL.toLowerCase(Locale.ENGLISH);
				String buildType = android.os.Build.TYPE.toLowerCase(Locale.ENGLISH);
				String buildDevice = android.os.Build.DEVICE.toLowerCase(Locale.ENGLISH);
				String buildId = android.os.Build.ID.toLowerCase(Locale.ENGLISH);
//				String sdkVersion = android.os.Build.VERSION.RELEASE.toLowerCase(Locale.ENGLISH);
				
				boolean setPreDefinedCameraUri = false;
				if (!(manufacturer.contains("samsung")) && !(manufacturer.contains("sony"))) { 
					setPreDefinedCameraUri = true;
				}
				if (manufacturer.contains("samsung") && model.contains("galaxy nexus")) { //TESTED
					setPreDefinedCameraUri = true;
				}
				if (manufacturer.contains("samsung") && model.contains("gt-n7000") && buildId.contains("imm76l")) { //TESTED 
					setPreDefinedCameraUri = true;
				}
				
				if (buildType.contains("userdebug") && buildDevice.contains("ariesve")) {  //TESTED
					setPreDefinedCameraUri = true;
				}
				if (buildType.contains("userdebug") && buildDevice.contains("crespo")) {   //TESTED
					setPreDefinedCameraUri = true;
				}
				if (buildType.contains("userdebug") && buildDevice.contains("gt-i9100")) { //TESTED
					setPreDefinedCameraUri = true;
				}
				
				///////////////////////////////////////////////////////////////////////////
				// TEST
				if (manufacturer.contains("samsung") && model.contains("sgh-t999l")) { //T-Mobile LTE enabled Samsung S3
					setPreDefinedCameraUri = true;
				}
				if (buildDevice.contains("cooper")) {
					setPreDefinedCameraUri = true;
				}
				if (buildType.contains("userdebug") && buildDevice.contains("t0lte")) {
					setPreDefinedCameraUri = true;
				}
				if (buildType.contains("userdebug") && buildDevice.contains("kot49h")) {
					setPreDefinedCameraUri = true;
				}
				if (buildType.contains("userdebug") && buildDevice.contains("t03g")) {
					setPreDefinedCameraUri = true;
				}
				if (buildType.contains("userdebug") && buildDevice.contains("gt-i9300")) {
					setPreDefinedCameraUri = true;
				}
				if (buildType.contains("userdebug") && buildDevice.contains("gt-i9195")) {
					setPreDefinedCameraUri = true;
				}
				if (buildType.contains("userdebug") && buildDevice.contains("xperia u")) {
					setPreDefinedCameraUri = true;
				}

				///////////////////////////////////////////////////////////////////////////

				
				dateCameraIntentStarted = new Date();
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				if (setPreDefinedCameraUri) {
					String filename = System.currentTimeMillis() + ".jpg";
					ContentValues values = new ContentValues();
					values.put(MediaStore.Images.Media.TITLE, filename);
					preDefinedCameraUri = mActivity.getContentResolver().insert(
												MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
												values);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, preDefinedCameraUri);
				}				
				mActivity.startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			} catch (ActivityNotFoundException e) {
				if (mCameraIntentHelperCallback != null) {
					mCameraIntentHelperCallback.logException(e);
					mCameraIntentHelperCallback.onCouldNotTakePhoto();
				}
			}
		} else {
			onSdCardNotMounted();
		}
	}
	
	/**
	 * Receives all activity results and triggers onCameraIntentResult if 
	 * the requestCode matches.
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		switch (requestCode) {
			case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE: {
				onCameraIntentResult(requestCode, resultCode, intent);
				break;
			}
		}
	}
	
	
	/**
	 * On camera activity result, we try to locate the photo.
	 * 
	 * <b>Mediastore:</b>
	 * First, we try to read the photo being captured from the MediaStore. Using a ContentResolver
     * on the MediaStore content, we retrieve the latest image being taken, as well as its
     * orientation property and its timestamp. If we find an image and it was not taken before
     * the camera intent was called, it is the image we were looking for. Otherwise, we dismiss
     * the result and try one of the following approaches.
     *
	 * <b>Intent extra:</b>
	 * Second, we try to get an image Uri from intent.getData() of the returning intent.
     * If this is not successful either, we continue with step 3.
     *
	 * <b>Default photo Uri:</b>
	 * If all of the above mentioned steps did not work, we use the image Uri we passed to
     * the camera activity.
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param intent
	 */
	private void onCameraIntentResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode == Activity.RESULT_OK) {
			Cursor myCursor = null;
			Date dateOfPicture = null;
			try {
				// Create a Cursor to obtain the file Path for the large image
				String[] largeFileProjection = {MediaStore.Images.ImageColumns._ID,
												MediaStore.Images.ImageColumns.DATA,
												MediaStore.Images.ImageColumns.ORIENTATION,
												MediaStore.Images.ImageColumns.DATE_TAKEN };
				String largeFileSort = MediaStore.Images.ImageColumns._ID + " DESC";
				myCursor = mActivity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
														largeFileProjection, 
														null, null, 
														largeFileSort);
				myCursor.moveToFirst();
				if (!myCursor.isAfterLast()) {
					// This will actually give you the file path location of the image.
					String largeImagePath = myCursor.getString(myCursor
													.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA));
					photoUri = Uri.fromFile(new File(largeImagePath));
					if (photoUri != null) {
						dateOfPicture = new Date(myCursor.getLong(myCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)));
						if (dateOfPicture != null && dateOfPicture.after(dateCameraIntentStarted)) {
							rotateXDegrees = myCursor.getInt(myCursor
												.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION));
						} else {
							photoUri = null;
						}
					}
					if (myCursor.moveToNext() && !myCursor.isAfterLast()) {
						String largeImagePath3rdLocation = myCursor.getString(myCursor
																	.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA));
						Date dateOfPicture3rdLocation = new Date(myCursor.getLong(myCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)));
						if (dateOfPicture3rdLocation != null && dateOfPicture3rdLocation.after(dateCameraIntentStarted)) {
							photoUriIn3rdLocation = Uri.fromFile(new File(largeImagePath3rdLocation));
						}
					}
				}
			} catch (Exception e) {
				if (mCameraIntentHelperCallback != null) {
					mCameraIntentHelperCallback.logException(e);
				}
			} finally {
				if (myCursor != null && !myCursor.isClosed()) {
					myCursor.close();
				}
			}

			if (photoUri == null) {
				try {
					photoUri = intent.getData();
				} catch (Exception e) {
				}
			}

			if (photoUri == null) {
				photoUri = preDefinedCameraUri;
			}
			
			try {
				if (photoUri != null && new File(photoUri.getPath()).length() <= 0) {
					if (preDefinedCameraUri != null) {
						Uri tempUri = photoUri;
						photoUri = preDefinedCameraUri;
						preDefinedCameraUri = tempUri;
					}
				}
			} catch (Exception e) { }

			photoUri = getFileUriFromContentUri(photoUri);
			preDefinedCameraUri = getFileUriFromContentUri(preDefinedCameraUri);
			try {
				if (photoUriIn3rdLocation != null) {
					if (photoUriIn3rdLocation.equals(photoUri) || photoUriIn3rdLocation.equals(preDefinedCameraUri)) {
						photoUriIn3rdLocation = null;
					} else {
						photoUriIn3rdLocation = getFileUriFromContentUri(photoUriIn3rdLocation);
					}
				}
			} catch (Exception e) { }

			if (photoUri != null) {
				onPhotoUriFound();
			} else {
				onPhotoUriNotFound();
			}
            deletePhotoInOtherLocations();
		} else if (resultCode == Activity.RESULT_CANCELED) {
			if (mCameraIntentHelperCallback != null) {
				mCameraIntentHelperCallback.onCanceled();
			}
		} else {
			if (mCameraIntentHelperCallback != null) {
				mCameraIntentHelperCallback.onCanceled();
			}
		}
	}

    /**
     * On some devices the taken photo is being stored in multiple locations. Consequently, we have
     * to remove images in any other location but the the one image in photoUri.
     */
    private void deletePhotoInOtherLocations() {
        if (preDefinedCameraUri != null && !preDefinedCameraUri.equals(photoUri) && mCameraIntentHelperCallback != null) {
            mCameraIntentHelperCallback.deletePhotoWithUri(preDefinedCameraUri);
        }
        if (photoUriIn3rdLocation != null && !photoUriIn3rdLocation.equals(photoUri) && mCameraIntentHelperCallback != null) {
            mCameraIntentHelperCallback.deletePhotoWithUri(photoUriIn3rdLocation);
        }
    }

    /**
	 * Being called if the photo could be located. The photo's Uri 
	 * and its orientation could be retrieved.
	 */
	private void onPhotoUriFound() {
		if (mCameraIntentHelperCallback != null) {
			mCameraIntentHelperCallback.onPhotoUriFound(dateCameraIntentStarted, photoUri, rotateXDegrees);
		}
	}
	
	/**
	 * Being called if the photo could not be located (photoUri == null).
	 */
	private void onPhotoUriNotFound() {
		if (mCameraIntentHelperCallback != null) {
			mCameraIntentHelperCallback.onPhotoUriNotFound();
		}
	}

	/**
	 * Being called if the SD card (or the internal mass storage respectively) is not mounted.
	 */
	private void onSdCardNotMounted() {
		if (mCameraIntentHelperCallback != null) {
			mCameraIntentHelperCallback.onSdCardNotMounted();
		}
	}

	/**
	 * Given an Uri that is a content Uri
     * (e.g.content://media/external/images/media/1884) this function returns the
	 * respective file Uri, that is e.g. file://media/external/DCIM/abc.jpg
	 * 
	 * @param cameraPicUri
	 * @return Uri
	 */
	private Uri getFileUriFromContentUri(Uri cameraPicUri) {
        Cursor cursor = null;
		try {
			if (cameraPicUri != null
					&& cameraPicUri.toString().startsWith("content")) {
				String[] proj = { MediaStore.Images.Media.DATA };
				cursor = mActivity.getContentResolver().query(cameraPicUri, proj, null, null, null);
				cursor.moveToFirst();
				// This will actually give you the file path location of the image.
				String largeImagePath = cursor.getString(cursor
												.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA));
				return Uri.fromFile(new File(largeImagePath));
			}
			return cameraPicUri;
		} catch (Exception e) {
			return cameraPicUri;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
	}
}