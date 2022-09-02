package com.example.quote

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var dialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RequestManager(this@MainActivity).GetAllQuotes(listener)
        dialog = ProgressDialog(this@MainActivity)
        dialog?.setTitle("Loading...")
        dialog?.show()
    }
    private val listener: QuotesResponseListener = object : QuotesResponseListener{
        override fun didFetch(response: List<QuotesResponse>, message: String) {
            dialog?.dismiss()
            recycler_home.setHasFixedSize(true)
            recycler_home.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            val  adapter = QuotesListAdapter(this@MainActivity, response)
            recycler_home.adapter=adapter
        }

        override fun didError(message: String) {
            dialog?.dismiss()
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
        }

    }
}