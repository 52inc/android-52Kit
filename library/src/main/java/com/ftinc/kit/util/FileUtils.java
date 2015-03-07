/*
 * Copyright (c) 2015 52inc
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
 */

package com.ftinc.kit.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
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
     * Video thumbnail task callback
     */
    public static interface VideoThumbnailCallback{

        /**
         * Called when task is complete
         *
         * @param thumb     the thumbnail bitmap, or null
         */
        public void onThumbnail(Bitmap thumb);
    }

}
