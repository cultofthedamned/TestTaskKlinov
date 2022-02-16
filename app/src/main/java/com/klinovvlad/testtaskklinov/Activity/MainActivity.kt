package com.klinovvlad.testtaskklinov.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.klinovvlad.testtaskklinov.Adapter.gifAdapter
import com.klinovvlad.testtaskklinov.Api.RetrofitInstance
import com.klinovvlad.testtaskklinov.Model.DataObj
import com.klinovvlad.testtaskklinov.databinding.ActivityMainBinding
import retrofit2.awaitResponse
import java.io.IOException

class MainActivity : AppCompatActivity(), gifAdapter.onItemClickListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var _Adapter: gifAdapter
    lateinit var linerLayoutManager: LinearLayoutManager
    private val dataListMain = mutableListOf<DataObj>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()

        binding.button.setOnClickListener {
            binding.textView.isGone = true
            binding.button.isGone = true
            setData()
        }
    }

    fun setData() {
        lifecycleScope.launchWhenCreated {
            binding.recyclerViewMain.setHasFixedSize(true)
            linerLayoutManager = LinearLayoutManager(this@MainActivity)
            binding.recyclerViewMain.layoutManager = linerLayoutManager
            try {
                val response = RetrofitInstance.api.getItem().awaitResponse()
                if(response.isSuccessful) {
                    val data = response.body()!!
                    dataListMain.addAll(data._data)
                    _Adapter = gifAdapter(baseContext, dataListMain, this@MainActivity)
                    _Adapter.notifyDataSetChanged()
                    binding.recyclerViewMain.adapter = _Adapter
                } else {
                    val builder_dialog = AlertDialog.Builder(this@MainActivity)
                    builder_dialog.setTitle("Error")
                    builder_dialog.setMessage("Response failed")
                    builder_dialog.setNegativeButton("OK") { dialogInterface, which ->

                    }
                    builder_dialog.setPositiveButton("TRY AGAIN") { dialogInterface, which ->
                        setData()
                    }
                    val alertDialog: AlertDialog = builder_dialog.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }
            } catch(e: IOException) {
                val builder_dialog = AlertDialog.Builder(this@MainActivity)
                builder_dialog.setTitle("Error")
                builder_dialog.setMessage("No internet connection")
                builder_dialog.setNegativeButton("OK") { dialogInterface, which ->
                    binding.textView.isVisible = true
                    binding.button.isVisible = true
                }
                builder_dialog.setPositiveButton("TRY AGAIN") { dialogInterface, which ->
                    setData()
                }
                val alertDialog: AlertDialog = builder_dialog.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this@MainActivity, GifActivity::class.java)
        intent.putExtra("image", dataListMain[position]._images._originalImage.url)
        startActivity(intent)
    }
}