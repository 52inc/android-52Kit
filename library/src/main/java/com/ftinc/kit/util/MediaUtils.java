package com.ftinc.kit.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.exceptions.OnErrorThrowable;
import timber.log.Timber;

/**
 * This is the utility class for helping capture photos and media
 * from local storage, aka the Storage Access Framework
 */
public class MediaUtils {

    /***********************************************************************************************
     *
     * Constants
     *
     */

    public static final int CAPTURE_PHOTO_REQUEST_CODE = 7234;
    public static final int PICK_MEDIA_REQUEST_CODE = 7235;

    private static Uri mCurrentCaptureUri;

    /***********************************************************************************************
     *
     * Intent Builders
     *
     */

    /**
     * Generate the appropriate intent to launch the existing camera application
     * to capture an image
     *
     *
     * @see #CAPTURE_PHOTO_REQUEST_CODE
     * @return          the intent necessary to launch the camera to capture a photo
     */
    public static Intent getCameraCaptureIntent(Context ctx, String authority){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            // Get the app's local file storage
            mCurrentCaptureUri = createAccessibleTempFile(ctx, authority);
            grantPermissions(ctx, mCurrentCaptureUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentCaptureUri);
        } catch (IOException e) {
            Timber.e(e, "Error creating temporary file for capture");
        }

        return intent;
    }

    /**
     * Generate the appropriate intent to launch the document chooser to allow the user
     * to pick an image to upload.
     *
     * @see #PICK_MEDIA_REQUEST_CODE
     * @param mimeType the MIME type you want to constrict the chooser to
     * @return the choose media intent
     */
    public static Intent getChooseMediaIntent(String mimeType){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(mimeType);
        return intent;
    }

    /***********************************************************************************************
     *
     * Activity Result Handler Methods
     *
     */

    /**
     * Handle the activity result of an intent launched to capture a photo or choose an image
     *
     * @see #getCameraCaptureIntent(Context, String)
     * @see #getChooseMediaIntent(String)
     *
     * @param context           the activity context
     * @param resultCode        the result of the return code
     * @param requestCode       the request code that launched the activity that caused the result
     * @param data              the data from the result
     */
    public static Observable<File> handleActivityResult(final Context context, int resultCode, int requestCode, Intent data){
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case CAPTURE_PHOTO_REQUEST_CODE:
                    if(mCurrentCaptureUri != null){
                        revokePermissions(context, mCurrentCaptureUri);
                        File file = getAccessibleTempFile(context, mCurrentCaptureUri);
                        Timber.d("Handling photo capture result(%s)", file.getPath());
                        mCurrentCaptureUri = null;
                        return Observable.just(file);
                    }
                    return Observable.error(new FileNotFoundException("Unable to find camera capture"));
                case PICK_MEDIA_REQUEST_CODE:
                    final Uri mediaUri = data.getData();
                    final String fileName = getFileName(context, mediaUri);
                    if(mediaUri != null){
                        return Observable.fromCallable(new Callable<File>() {
                            @Override
                            public File call() throws Exception {
                                Timber.d("[MediaUtils] Loading chosen media: [%s]: %s", fileName, mediaUri);

                                ParcelFileDescriptor parcelFileDescriptor = null;
                                try {
                                    parcelFileDescriptor = context.getContentResolver().openFileDescriptor(mediaUri, "r");

                                    // Now that we have the file description, get the associated input stream
                                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                                    FileInputStream fis = new FileInputStream(fileDescriptor);
                                    FileOutputStream fos = null;

                                    // Generate temporary output file and output file data to it
                                    try {
                                        File tempFile = createTempFile(context, fileName);
                                        fos = new FileOutputStream(tempFile);

                                        byte[] buffer = new byte[1024];

                                        int len;
                                        while ((len = fis.read(buffer)) > 0) {
                                            fos.write(buffer, 0, len);
                                        }

                                        return tempFile;
                                    } catch (IOException e) {
                                        Timber.e(e, "Error creating temporary file");
                                        throw OnErrorThrowable.from(e);
                                    } finally {

                                        try {
                                            parcelFileDescriptor.close();
                                            fis.close();
                                            if (fos != null) {
                                                fos.close();
                                            }
                                        } catch (IOException e1) {
                                            Timber.e(e1, "Error closing streams");
                                        }
                                    }

                                } catch (FileNotFoundException e) {
                                    throw OnErrorThrowable.from(e);
                                }
                            }
                        }).compose(RxUtils.<File>applyWorkSchedulers());

                    }
                    return Observable.error(new FileNotFoundException("Unable to load selected file"));
                default:
                    return Observable.empty();
            }
        }
        return Observable.empty();
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    /**
     * Get the filename of an {@link Uri} that was returned from the storage access framework
     *
     * @param context       the application context to get the content resolver
     * @param uri           the uri to get the name of
     * @return              the name, or null
     */
    private static String getFileName(Context context, Uri uri){
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);
        int nameIndex = c.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        c.moveToFirst();
        String fileName = c.getString(nameIndex);
        c.close();
        return fileName;
    }

    /**
     * Create a temporary file to use to handle selected images from the picker
     *
     * @return      the created temporary file
     * @throws IOException
     */
    private static File createTempFile(Context ctx, String fileName) throws IOException {
        File storageDir = new File(ctx.getFilesDir(), "temp");
        storageDir.mkdir();
        return nonDuplicateFile(storageDir, fileName);
    }

    /**
     * Generate an accessible temporary file URI to be used for camera captures
     * @param ctx
     * @return
     * @throws IOException
     */
    private static Uri createAccessibleTempFile(Context ctx, String authority) throws IOException {
        File dir = new File(ctx.getCacheDir(), "camera");
        File tmp = createTempFile(dir);

        // Give permissions
        return FileProvider.getUriForFile(ctx, authority, tmp);
    }

    private static File getAccessibleTempFile(Context ctx, Uri contentUri){
        File dir = ctx.getCacheDir();
        String dirPath = dir.getPath().endsWith("/") ? dir.getPath().substring(0,dir.getPath().length()-1) : dir.getPath();
        String fullPath = dirPath + contentUri.getPath();
        return new File(fullPath);
    }

    /**
     * Grant URI permissions for all potential camera applications that can handle the capture
     * intent.
     */
    private static void grantPermissions(Context ctx, Uri uri){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> resolvedIntentActivities = ctx.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
            String packageName = resolvedIntentInfo.activityInfo.packageName;

            // Grant Permissions
            ctx.grantUriPermission(packageName, uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    /**
     * Revoke URI permissions to a specific URI that had been previously granted
     */
    private static void revokePermissions(Context ctx, Uri uri){
        ctx.revokeUriPermission(uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                        Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }

    /**
     * Create a temporary file to use to handle selected images from the picker
     *
     * @return  the temporary file in the given storage directory
     *
     * @throws IOException
     */
    private static File createTempFile(File storageDir) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        if(!storageDir.mkdir() && !storageDir.exists()){
            throw new IOException("Unable to create temporary media directory");
        }

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    /**
     * Clean the extension off of the file name.
     * @param fileName the full file name
     * @return The cleaned file name
     */
    public static String cleanFilename(String fileName){
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, lastIndex);
    }

    /**
     * Create a non duplicate file in the given directory with the given name, appending a duplicate
     * counter to the end of the name but before the extension like: `some_file(1).jpg`
     *
     * @param dir The parent directory of the desired file
     * @param name The desired name of the file
     * @return The non-duplicate file
     */
    public static File nonDuplicateFile(File dir, String name){
        int count = 1;
        File outputFile = new File(dir, name);
        while(outputFile.exists()){
            String cleanName = cleanFilename(name);
            int extIndex = name.lastIndexOf(".");
            String extension = "";
            if(extIndex != -1) {
                extension = name.substring(extIndex + 1);
            }

            String revisedFileName = String.format(Locale.US, "%s(%d).%s", cleanName, count, extension);
            outputFile = new File(dir, revisedFileName);
            count++;
        }

        return outputFile;
    }

}