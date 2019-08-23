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

package com.ftinc.kit.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Contacts
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils

import java.io.File
import java.net.URL

/**
 * @author Dmitriy Tarasov
 */
object IntentUtils {

    /**
     * Open app page at Google Play
     *
     * @param context       Application context
     * @param openInBrowser Should we try to open application page in web browser
     * if Play Store app not found on device
     */
    @JvmOverloads
    fun openPlayStore(context: Context, openInBrowser: Boolean = true): Intent {
        val appPackageName = context.packageName
        val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))
        if (isIntentAvailable(context, marketIntent)) {
            return marketIntent
        }
        return if (openInBrowser) {
            openLink("https://play.google.com/store/apps/details?id=$appPackageName")
        } else marketIntent
    }

    /**
     * Send email message
     *
     * @param to      Receiver email
     * @param subject Message subject
     * @param text    Message body
     * @see .sendEmail
     */
    fun sendEmail(to: String, subject: String, text: String): Intent {
        return sendEmail(arrayOf(to), subject, text)
    }

    /**
     * @see .sendEmail
     */
    fun sendEmail(to: Array<String>, subject: String, text: String): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, to)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        return intent
    }

    /**
     * Share text via thirdparty app like twitter, facebook, email, sms etc.
     *
     * @param subject Optional subject of the message
     * @param text    Text to share
     */
    fun shareText(subject: String, text: String): Intent {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        if (!TextUtils.isEmpty(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.type = "text/plain"
        return intent
    }

    /**
     * Send SMS message using built-in app
     *
     * @param to      Receiver phone number
     * @param message Text to send
     */
    fun sendSms(to: String, message: String): Intent {
        val smsUri = Uri.parse("tel:$to")
        val intent = Intent(Intent.ACTION_VIEW, smsUri)
        intent.putExtra("address", to)
        intent.putExtra("sms_body", message)
        intent.type = "vnd.android-dir/mms-sms"
        return intent
    }

    /**
     * Opens the Street View application to the given location.
     * The URI scheme is based on the syntax used for Street View panorama information in Google Maps URLs.
     *
     * @param latitude  Latitude
     * @param longitude Longitude
     * @param yaw       Panorama center-of-view in degrees clockwise from North.
     * Note: The two commas after the yaw parameter are required.
     * They are present for backwards-compatibility reasons.
     * @param pitch     Panorama center-of-view in degrees from -90 (look straight up) to 90 (look straight down.)
     * @param zoom      Panorama zoom. 1.0 = normal zoom, 2.0 = zoomed in 2x, 3.0 = zoomed in 4x, and so on.
     * A zoom of 1.0 is 90 degree horizontal FOV for a nominal landscape mode 4 x 3 aspect ratio display Android
     * phones in portrait mode will adjust the zoom so that the vertical FOV is approximately the same as the
     * landscape vertical FOV. This means that the horizontal FOV of an Android phone in portrait mode is much
     * narrower than in landscape mode. This is done to minimize the fisheye lens effect that would be present
     * if a 90 degree horizontal FOV was used in portrait mode.
     * @param mapZoom   The map zoom of the map location associated with this panorama.
     * This value is passed on to the Maps activity when the Street View "Go to Maps" menu item is chosen.
     * It corresponds to the zoomLevel parameter in [.showLocation]
     */
    fun showStreetView(latitude: Float,
                       longitude: Float,
                       yaw: Float?,
                       pitch: Int?,
                       zoom: Float?,
                       mapZoom: Int?): Intent {
        val builder = StringBuilder("google.streetview:cbll=").append(latitude).append(",").append(longitude)
        if (yaw != null || pitch != null || zoom != null) {
            val cbpParam = String.format("%s,,%s,%s", yaw ?: "", pitch ?: "", zoom ?: "")
            builder.append("&cbp=1,").append(cbpParam)
        }
        if (mapZoom != null) {
            builder.append("&mz=").append(mapZoom)
        }

        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(builder.toString())
        return intent
    }

    /**
     * Opens the Maps application to the given location.
     *
     * @param latitude  Latitude
     * @param longitude Longitude
     * @param zoomLevel A zoom level of 1 shows the whole Earth, centered at the given lat,lng.
     * A zoom level of 2 shows a quarter of the Earth, and so on. The highest zoom level is 23.
     * A larger zoom level will be clamped to 23.
     * @see .findLocation
     */
    fun showLocation(latitude: Float, longitude: Float, zoomLevel: Int?): Intent {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        var data = String.format("geo:%s,%s", latitude, longitude)
        if (zoomLevel != null) {
            data = String.format("%s?z=%s", data, zoomLevel)
        }
        intent.data = Uri.parse(data)
        return intent
    }

    /**
     * Opens the Maps application to the given query.
     *
     * @param query Query string
     * @see .showLocation
     */
    fun findLocation(query: String): Intent {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val data = String.format("geo:0,0?q=%s", query)
        intent.data = Uri.parse(data)
        return intent
    }

    /**
     * Open system settings location services screen for turning on/off GPS
     */
    fun showLocationServices(): Intent {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        return intent
    }

    /**
     * Open a browser window to the URL specified.
     *
     * @param url Target url
     */
    fun openLink(url: String): Intent {
        var url = url
        // if protocol isn't defined use http by default
        if (!TextUtils.isEmpty(url) && !url.contains("://")) {
            url = "http://$url"
        }

        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(url)
        return intent
    }

    /**
     * @see .openLink
     */
    fun openLink(url: URL): Intent {
        return openLink(url.toString())
    }

    /**
     * Open a video file in appropriate app
     *
     * @param file File to open
     */
    fun openVideo(file: File): Intent {
        return openVideo(Uri.fromFile(file))
    }

    /**
     * @see .openVideo
     */
    fun openVideo(file: String): Intent {
        return openVideo(File(file))
    }

    /**
     * @see .openVideo
     */
    fun openVideo(uri: Uri): Intent {
        return openMedia(uri, "video/*")
    }

    /**
     * Open an audio file in appropriate app
     *
     * @param file File to open
     */
    fun openAudio(file: File): Intent {
        return openAudio(Uri.fromFile(file))
    }

    /**
     * @see .openAudio
     */
    fun openAudio(file: String): Intent {
        return openAudio(File(file))
    }

    /**
     * @see .openAudio
     */
    fun openAudio(uri: Uri): Intent {
        return openMedia(uri, "audio/*")
    }

    /**
     * Open an image file in appropriate app
     *
     * @param file File to open
     */
    fun openImage(file: String): Intent {
        return openImage(File(file))
    }

    /**
     * @see .openImage
     */
    fun openImage(file: File): Intent {
        return openImage(Uri.fromFile(file))
    }

    /**
     * @see .openImage
     */
    fun openImage(uri: Uri): Intent {
        return openMedia(uri, "image/*")
    }

    /**
     * Open a text file in appropriate app
     *
     * @param file File to open
     */
    fun openText(file: String): Intent {
        return openText(File(file))
    }

    /**
     * @see .openText
     */
    fun openText(file: File): Intent {
        return openText(Uri.fromFile(file))
    }

    /**
     * @see .openText
     */
    fun openText(uri: Uri): Intent {
        return openMedia(uri, "text/plain")
    }

    /**
     * Pick file from sdcard with file manager. Chosen file can be obtained from Intent in onActivityResult.
     * See code below for example:
     *
     * <pre>`
     * protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     * Uri file = data.getData();
     * }
    `</pre> *
     */
    fun pickFile(): Intent {
        return pick("file/*")
    }

    /**
     * Pick a type of file from the sdcard with the file manager.
     * Chosen file can be obtained from Intent in onActivityResult.
     * See code below for example:
     *
     * <pre>`
     * protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     * Uri file = data.getData();
     * }
    `</pre> *
     *
     * @param mimeType      the type of file to pick
     * @return              the intent to pick it
     */
    fun pick(mimeType: String): Intent {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = mimeType
        return intent
    }

    /**
     * Calls the entered phone number. Valid telephone numbers as defined in the IETF RFC 3966 are accepted.
     * Valid examples include the following:
     * tel:2125551212
     * tel: (212) 555 1212
     *
     * Note: This requires your application to request the following permission in your manifest:
     * `<uses-permission android:name="android.permission.CALL_PHONE"/>`
     *
     * @param phoneNumber Phone number
     */
    fun callPhone(phoneNumber: String): Intent {
        val intent = Intent()
        intent.action = Intent.ACTION_CALL
        intent.data = Uri.parse("tel:$phoneNumber")
        return intent
    }

    /**
     * Pick contact from phone book
     *
     * @param scope You can restrict selection by passing required content type.
     */
    @JvmOverloads
    fun pickContact(scope: String? = null): Intent {
        val intent: Intent
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ECLAIR) {
            intent = Intent(Intent.ACTION_PICK, Contacts.People.CONTENT_URI)
        } else {
            intent = Intent(Intent.ACTION_PICK, Uri.parse("content://com.android.contacts/contacts"))
        }

        if (!TextUtils.isEmpty(scope)) {
            intent.type = scope
        }
        return intent
    }

    /**
     * Pick image from gallery
     */
    fun pickImage(): Intent {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        return intent
    }

    /**
     * Dials (but does not actually initiate the call) the number given.
     * Telephone number normalization described for [.callPhone] applies to dial as well.
     *
     * @param phoneNumber Phone number
     */
    fun dialPhone(phoneNumber: String): Intent {
        val intent = Intent()
        intent.action = Intent.ACTION_DIAL
        intent.data = Uri.parse("tel:$phoneNumber")
        return intent
    }

    /**
     * Check that cropping application is available
     *
     * @param context Application context
     * @return true if cropping app is available
     * @see .cropImage
     */
    fun isCropAvailable(context: Context): Boolean {
        val intent = Intent("com.android.camera.action.CROP")
        intent.type = "image/*"
        return IntentUtils.isIntentAvailable(context, intent)
    }

    /**
     * Crop image. Before using, cropImage requires especial check that differs from
     * [.isIntentAvailable]
     * see [.isCropAvailable] instead
     *
     * @param context Application context
     * @param image   Image that will be used for cropping. This image is not changed during the cropImage
     * @param outputX Output image width
     * @param outputY Output image height
     * @param aspectX Crop frame aspect X
     * @param aspectY Crop frame aspect Y
     * @param scale   Scale or not cropped image if output image and cropImage frame sizes differs
     * @return Intent with `data`-extra in `onActivityResult` which contains result as a
     * [android.graphics.Bitmap]. See demo app for details
     */
    fun cropImage(context: Context, image: File, outputX: Int, outputY: Int, aspectX: Int, aspectY: Int, scale: Boolean): Intent {
        val intent = Intent("com.android.camera.action.CROP")
        intent.type = "image/*"

        val list = context.packageManager.queryIntentActivities(intent, 0)
        val res = list[0]

        intent.putExtra("outputX", outputX)
        intent.putExtra("outputY", outputY)
        intent.putExtra("aspectX", aspectX)
        intent.putExtra("aspectY", aspectY)
        intent.putExtra("scale", scale)
        intent.putExtra("return-data", true)
        intent.data = Uri.fromFile(image)

        intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
        return intent
    }

    /**
     * Call standard camera application for capturing an image
     *
     * @param file Full path to captured file
     */
    fun photoCapture(file: String): Intent {
        val uri = Uri.fromFile(File(file))
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        return intent
    }

    /**
     * Check that in the system exists application which can handle this intent
     *
     * @param context Application context
     * @param intent  Checked intent
     * @return true if intent consumer exists, false otherwise
     */
    fun isIntentAvailable(context: Context, intent: Intent): Boolean {
        val packageManager = context.packageManager
        val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return list.size > 0
    }

    private fun openMedia(uri: Uri, mimeType: String): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, mimeType)
        return intent
    }
}
