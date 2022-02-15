package com.klinovvlad.testtaskklinov.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.klinovvlad.testtaskklinov.Adapter.gifAdapter
import com.klinovvlad.testtaskklinov.Api.RetrofitInstance
import com.klinovvlad.testtaskklinov.databinding.ActivityMainBinding
import retrofit2.awaitResponse
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var _Adapter: gifAdapter
    lateinit var linerLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()
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
                    _Adapter = gifAdapter(baseContext, data._data)
                    _Adapter.notifyDataSetChanged()
                    binding.recyclerViewMain.adapter = _Adapter
                } else {
                    val builder_dialog = AlertDialog.Builder(this@MainActivity)
                    builder_dialog.setTitle("Error")
                    builder_dialog.setMessage("Response failed")
                    builder_dialog.setPositiveButton("OK") { dialogInterface, which ->

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
}