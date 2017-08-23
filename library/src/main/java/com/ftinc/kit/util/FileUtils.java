/*
 * Copyright (c) 2017 52inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ftinc.kit.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;


public class FileUtils {

	/**
	 * Constants
	 */
	
	/* Media State Constants */
	public static final int READ_WRITE = 0;
	public static final int READ_ONLY = 1;
	public static final int UNAVAILABLE = 2;
	
	public static final int IO_FAIL = -1;
	public static final int IO_SUCCESS = 0;
	

	/**
	 * Check the state of the external media (i.e. SDCard) on whether 
	 * it is read/write, read only, or unavailable
	 * 
	 * @return 	the corresponding constant to the media state(RW/RO/Unavailable)
	 */
	public static int checkMediaState(){
		int mediastate = UNAVAILABLE;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mediastate = READ_WRITE;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mediastate = READ_ONLY;
		} else {
		    // Something else is wrong. It may2 nor write
		    mediastate = UNAVAILABLE;
		}
		
		return mediastate;
	}

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @author paulburke
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = BuildUtils.isKitKat();

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * Dump Data straight to the SDCard
     *
     * @param ctx           the application context
     * @param filename      the dump filename
     * @param data          the data to dump
     * @return              the return
     */
    public static int crapToDisk(Context ctx, String filename, byte[] data){
        int code = IO_FAIL;

        File dir = Environment.getExternalStorageDirectory();
        File output = new File(dir, filename);
        try {
            FileOutputStream fos = new FileOutputStream(output);
            try {
                fos.write(data);
                code = IO_SUCCESS;
            } catch (IOException e) {
                code = IO_FAIL;
            } finally {
                fos.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return code;
    }

    /**
     * Closes the given stream inside a try/catch. Does nothing if stream is null.
     */
    public static void safeClose(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                // Silent
            }
        }
    }

    /**
     * Closes the given stream inside a try/catch. Does nothing if stream is null.
     */
    public static void safeClose(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                // Silent
            }
        }
    }

    /**
     * Closes the given stream inside a try/catch. Does nothing if stream is null.
     */
    public static void safeClose(Reader in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                // Silent
            }
        }
    }

    /**
     * Closes the given stream inside a try/catch. Does nothing if stream is null.
     */
    public static void safeClose(Writer out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                // Silent
            }
        }
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get a thumbnail bitmap for a given video
     *
     * @param videoPath     the path to the video
     * @return              the thumbnail of the video, or null
     */
    public static Bitmap getVideoThumbnail(String videoPath){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(videoPath);
        return mmr.getFrameAtTime();
    }

    /**
     * Get the thumbnail of a video asynchronously
     *
     * @param videoPath     the path to the video
     * @param cb            the callback
     */
    public static void getVideoThumbnail(String videoPath, final VideoThumbnailCallback cb){
        new AsyncTask<String, Void, Bitmap>(){
            @Override
            protected Bitmap doInBackground(String... params) {
                if(params.length > 0) {
                    String path = params[0];
                    if(!TextUtils.isEmpty(path)){
                        return getVideoThumbnail(path);
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                cb.onThumbnail(bitmap);
            }
        }.execute(videoPath);
    }

    /**
     * Copy a file from it's source input to the specified output
     * file if it can.
     *
     * @param source        the input file to copy
     * @param output        the output destination
     * @return              true if successful, false otherwise
     */
    public static boolean copy(File source, File output){

        // Check to see if output exists
        if(output.exists() && output.canWrite()){
            // Delete the existing file, and create a new one
            if(output.delete()) {
                try {
                    output.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if(!output.exists()){
            try {
                output.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // now that we have performed a prelimanary check on the output, time to copy
        if(source.exists() && source.canRead()){

            try {
                FileInputStream fis = new FileInputStream(source);
                FileOutputStream fos = new FileOutputStream(output);
                byte[] buffer = new byte[1024];

                int len=0;
                while((len=fis.read(buffer)) > 0){
                    fos.write(buffer, 0, len);
                }

                fis.close();
                fos.close();

                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    /**
     * Video thumbnail task callback
     */
    public interface VideoThumbnailCallback{

        /**
         * Called when task is complete
         *
         * @param thumb     the thumbnail bitmap, or null
         */
        void onThumbnail(Bitmap thumb);
    }

}
