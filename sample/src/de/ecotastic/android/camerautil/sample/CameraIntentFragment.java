package de.ecotastic.android.camerautil.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import de.ecotastic.android.camerautil.lib.CameraIntentHelper;
import de.ecotastic.android.camerautil.lib.CameraIntentHelperCallback;
import de.ecotastic.android.camerautil.sample.util.BitmapHelper;

/**
 * Example FRAGMENT of how to use the CameraIntentHelper to retrieve the location
 * and orientation of the photo taken via camera intent.
 *  
 * @author Ralf Gehrer <ralf@ecotastic.de>
 */
public class CameraIntentFragment extends Fragment {
	CameraIntentHelper mCameraIntentHelper;

    TextView messageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_camera_intent, container, false);

        messageView = (TextView) view.findViewById(R.id.fragment_camera_intent_message);
        Button startCameraButton = (Button) view.findViewById(R.id.fragment_camera_intent_start_camera_button);
        startCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCameraIntentHelper != null) {
                    mCameraIntentHelper.startCameraIntent();
                }
            }
        });

        setupCameraIntentHelper();
        return view;
    }


    private void setupCameraIntentHelper() {
        mCameraIntentHelper = new CameraIntentHelper(getActivity(), new CameraIntentHelperCallback() {
            @Override
            public void onPhotoUriFound(Date dateCameraIntentStarted, Uri photoUri, int rotateXDegrees) {
                messageView.setText(getString(R.string.activity_camera_intent_photo_uri_found) + photoUri.toString());

                Bitmap photo = BitmapHelper.readBitmap(getActivity(), photoUri);
                if (photo != null) {
                    photo = BitmapHelper.shrinkBitmap(photo, 300, rotateXDegrees);
                    ImageView imageView = (ImageView) getActivity().findViewById(R.id.fragment_camera_intent_image_view);
                    imageView.setImageBitmap(photo);
                }
            }

            @Override
            public void deletePhotoWithUri(Uri photoUri) {
                BitmapHelper.deleteImageWithUriIfExists(photoUri, getActivity());
            }

            @Override
            public void onSdCardNotMounted() {
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.error_sd_card_not_mounted), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCanceled() {
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.warning_camera_intent_canceled), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCouldNotTakePhoto() {
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.error_could_not_take_photo), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPhotoUriNotFound() {
                messageView.setText(getString(R.string.activity_camera_intent_photo_uri_not_found));
            }

            @Override
            public void logException(Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.error_sth_went_wrong), Toast.LENGTH_LONG).show();
                Log.d(getClass().getName(), e.getMessage());
            }
        });
    }

    @Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
        mCameraIntentHelper.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCameraIntentHelper.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mCameraIntentHelper.onActivityResult(requestCode, resultCode, intent);
    }
}