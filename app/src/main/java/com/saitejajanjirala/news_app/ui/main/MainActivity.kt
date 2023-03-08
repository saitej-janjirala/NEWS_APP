package com.saitejajanjirala.news_app.ui.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.saitejajanjirala.news_app.R
import com.saitejajanjirala.news_app.adapter.HeadLinesAdapter
import com.saitejajanjirala.news_app.databinding.ActivityMainBinding
import com.saitejajanjirala.news_app.models.Article
import com.saitejajanjirala.news_app.models.HeadLine
import com.saitejajanjirala.news_app.models.Result
import com.saitejajanjirala.news_app.ui.settings.SettingsActivity
import com.saitejajanjirala.news_app.ui.detail.DetailActivity
import com.saitejajanjirala.news_app.utils.Keys
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), HeadLinesAdapter.onHeadLineItemClickListener{
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: HeadLinesAdapter
    private val mainViewModel : MainViewModel by viewModels()
    companion object{
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.bind(View.inflate(this,R.layout.activity_main,null))
        setContentView(binding.root)
        binding.apply {
            retryButton.setOnClickListener {
                fetchData()
            }
        }
        setUpAdapters()
        setObservers()
        fetchData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_item ->{
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            }
            else -> Unit
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpAdapters() {
        adapter = HeadLinesAdapter(emptyList(),this)
        binding.recyclerView.adapter = adapter
    }

    private fun setObservers() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.headLines.collect{
                when(it){
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.retryButton.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.retryButton.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        if(!TextUtils.isEmpty(it.message)){
                            Toast.makeText(this@MainActivity, "${it.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.retryButton.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        updateRecyclerView(it.data)
                    }
                    else -> Unit
                }
            }

        }

    }

    private fun updateRecyclerView(data: HeadLine?) {
        data?.let {
            it.articles?.let {a->
                adapter.setData(a)
                adapter.notifyDataSetChanged()
            }
        }

    }

    private fun fetchData() {
        if(hasInternetConnection()){
            mainViewModel.fetch()
        }
        else{
            showInternetNotConnectedToast()
        }
    }

    private fun showInternetNotConnectedToast(){
        Toast.makeText(
            this@MainActivity,
            "Please check your internet connection",
            Toast.LENGTH_SHORT
        ).show()
        mainViewModel.setToRetry()
    }
    private fun hasInternetConnection(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    override fun onClicked(article: Article) {
        if(article.url != null){
            val intent = Intent(this@MainActivity,DetailActivity::class.java)
            intent.putExtra(Keys.URL_INTENT_KEY,article.url)
            startActivity(intent)
        }
        else{
            Toast.makeText(this@MainActivity,"No resources found",Toast.LENGTH_SHORT).show()
        }

    }

}
