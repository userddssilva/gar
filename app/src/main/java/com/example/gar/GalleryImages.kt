package com.example.gar

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@Suppress("DEPRECATION", "PrivatePropertyName")
class GalleryImages(private var contextOfApplication: Context) {

    private var TAG = GalleryImages::class.java.simpleName
    private var imagePaths: ArrayList<String>? = ArrayList()

    companion object {
        private const val PERMISSION_REQUEST_CODE = 200
    }

    private fun checkPermission(): Boolean {
        // Checking if the permissions are granted or not and returning the result.
        val result = ContextCompat.checkSelfPermission(
            contextOfApplication,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        // Requesting read external storage permissions.
        ActivityCompat.requestPermissions(
            contextOfApplication as Activity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    fun requestPermissions() {
        if (checkPermission()) {
            Toast.makeText(contextOfApplication, "Permissions granted..", Toast.LENGTH_SHORT).show()
        } else {
            // if the permissions are not granted
            // call method to request permissions.
            requestPermission()
        }
    }

    fun getImagePath(): ArrayList<String>? {
        // Creating a new list with
        // images data with their ids.
        val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)

        // Creating a new string to order images
        val orderBy = MediaStore.Images.Media._ID

        // Stores all the images
        // from the gallery in Cursor
        val cursor: Cursor? = contextOfApplication.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            columns,
            null,
            null,
            orderBy
        )

        // Saving images on array
        saveImagesPath(cursor)

        return imagePaths
    }

    private fun saveImagesPath(cursor: Cursor?) {
        cursor?.let {
            // below line is to get total number of images
            val count: Int = cursor.count

            // Running a loop to add
            // the image file path in an array list.
            for (i in 0 until count) {

                // Moving cursor position
                cursor.moveToPosition(i)

                //Getting image file path
                val dataColumnIndex: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)

                // Getting the image file path
                // and adding that path in an array list.
                val uri = cursor.getString(dataColumnIndex)
                Log.d(TAG, "[Image URI]: $uri")
                imagePaths!!.add(uri)
            }
            // Closing cursor.
            cursor.close()
        }
    }
}