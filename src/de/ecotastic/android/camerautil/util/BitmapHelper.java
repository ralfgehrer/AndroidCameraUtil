package de.ecotastic.android.camerautil.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;

/**
 * A helper class to conveniently alter Bitmap data
 * 
 * @author Ralf Gehrer <ralf@ecotastic.de>
 */
public class BitmapHelper {

	/**
     * Converts a Bitmap to a byteArray.
     * @param Bitmap
     * @return byteArray
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    /**
     * Converts a byteArray to a Bitmap object
     * @param byteArray
     * @return Bitmap
     */
    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        } else {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
    }

    /**
     * Shrinks and a passed Bitmap.
     * 
     * @param bm
     * @param maxLengthOfEdge
     * @return Bitmap
     */
    public static Bitmap shrinkBitmap(Bitmap bm, int maxLengthOfEdge) {
        return shrinkBitmap(bm, maxLengthOfEdge, 0);
    }

    /**
     * Shrinks and rotates (if necessary) a passed Bitmap.
     * 
     * @param bm
     * @param maxLengthOfEdge
     * @param rotateXDegree
     * @return Bitmap
     */
    public static Bitmap shrinkBitmap(Bitmap bm, int maxLengthOfEdge, int rotateXDegree) {
        if (maxLengthOfEdge > bm.getWidth() && maxLengthOfEdge > bm.getHeight()) {
            return bm;
        } else {
            // shrink image
            float scale = (float) 1.0;
            if (bm.getHeight() > bm.getWidth()) {
                scale = ((float) maxLengthOfEdge) / bm.getHeight();
            } else {
                scale = ((float) maxLengthOfEdge) / bm.getWidth();
            }
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scale, scale);
            matrix.postRotate(rotateXDegree);

            // RECREATE THE NEW BITMAP
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(),
                    matrix, false);

            matrix = null;
            System.gc();

            return bm;
        }
    }

    /**
     * Reads a Bitmap from an Uri.
     * 
     * @param context
     * @param selectedImage
     * @return Bitmap
     */
    public static Bitmap readBitmap(Context context, Uri selectedImage) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inScaled = false;
//      options.inSampleSize = 3;
        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = context.getContentResolver().openAssetFileDescriptor(selectedImage, "r");
        } catch (FileNotFoundException e) {
            return null;
        } finally {
            try {
                bm = BitmapFactory.decodeFileDescriptor(
                        fileDescriptor.getFileDescriptor(), null, options);
                fileDescriptor.close();
            } catch (IOException e) {
                return null;
            }
        }
        return bm;
    }

    /**
     * Clears all Bitmap data, that is, recycles the Bitmap and 
     * triggers the garbage collection.
     * 
     * @param bm
     */
    public static void clearBitmap(Bitmap bm) {
        bm.recycle();
        System.gc();
    }	
    
    /**
     * Deletes an image given its Uri.
     * @param cameraPicUri
     * @param context
     * @return true if it was deleted successfully, false otherwise.
     */
	public static boolean deleteImageWithUriIfExists(Uri cameraPicUri, Context context) {
		if (cameraPicUri != null) {
			File fdelete = new File(cameraPicUri.getPath());
	        if (fdelete.exists()) {
	            if (fdelete.delete()) {
	            	context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" +  Environment.getExternalStorageDirectory())));
	            	return true;
	            }
	        }	
		}
		return false;
	}
}