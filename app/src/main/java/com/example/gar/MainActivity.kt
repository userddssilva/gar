package com.example.gar

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private var TAG = MainActivity::class.java.simpleName
    private var imagePaths: ArrayList<String>? = null
    private var imagesRV: RecyclerView? = null
    private var imageRVAdapter: RecyclerViewAdapter? = null
    private lateinit var gallery: GalleryImages

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // creating a new array list and
        // initializing our recycler view.
        imagePaths = ArrayList()
        imagesRV = findViewById(R.id.idRVImages)

        // Getting Images
        gallery = GalleryImages(this)
        gallery.requestPermissions()
        imagePaths = gallery.getImagePath()

        // calling a method to
        // prepare our recycler view.
        prepareRecyclerView()
    }

    private fun prepareRecyclerView() {

        // in this method we are preparing our recycler view.
        // on below line we are initializing our adapter class.
        imageRVAdapter = RecyclerViewAdapter(this@MainActivity, imagePaths!!)

        // on below line we are creating a new grid layout manager.
        val manager = GridLayoutManager(this@MainActivity, 4)

        // on below line we are setting layout
        // manager and adapter to our recycler view.
        imagesRV!!.layoutManager = manager
        imagesRV!!.adapter = imageRVAdapter
    }


}