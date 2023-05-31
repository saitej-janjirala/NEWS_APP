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
import com.saitejajanjirala.news_app.adapter.CategoriesAdapter
import com.saitejajanjirala.news_app.adapter.HeadLinesAdapter
import com.saitejajanjirala.news_app.databinding.ActivityMainBinding
import com.saitejajanjirala.news_app.listeners.OnArticleClickListener
import com.saitejajanjirala.news_app.listeners.OnCategoryClickListener
import com.saitejajanjirala.news_app.models.Article
import com.saitejajanjirala.news_app.models.Result
import com.saitejajanjirala.news_app.ui.settings.SettingsActivity
import com.saitejajanjirala.news_app.ui.detail.DetailActivity
import com.saitejajanjirala.news_app.utils.Helper
import com.saitejajanjirala.news_app.utils.Keys
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnArticleClickListener,OnCategoryClickListener{
    private lateinit var binding : ActivityMainBinding
    private lateinit var headLinesAdapter: HeadLinesAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var categoriesList : ArrayList<Pair<String,Boolean>>
    private val mainViewModel : MainViewModel by viewModels()
    private var catPos = 0;
    companion object{
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.bind(View.inflate(this,R.layout.activity_main,null))
        setContentView(binding.root)
        binding.apply {
            retryButton.setOnClickListener {
                if(hasInternetConnection()) {
                    fetchData()
                }else{
                    showInternetNotConnectedToast()
                }
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
        headLinesAdapter = HeadLinesAdapter(emptyList(),this)
        binding.articlesRecyclerView.adapter = headLinesAdapter
        categoriesList = Helper.getListOfCategories()
        categoriesAdapter = CategoriesAdapter(categoriesList,this)
        binding.categoriesRecyclerView.adapter = categoriesAdapter
    }

    private fun setObservers() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.headLines.collect{
                when(it){
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.retryButton.visibility = View.VISIBLE
                        binding.articlesRecyclerView.visibility = View.GONE
                    }
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.retryButton.visibility = View.GONE
                        binding.articlesRecyclerView.visibility = View.GONE
                        if(!TextUtils.isEmpty(it.message)){
                            Toast.makeText(this@MainActivity, "${it.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.retryButton.visibility = View.GONE
                        binding.articlesRecyclerView.visibility = View.VISIBLE
                        updateRecyclerView(it.data)
                    }
                    else -> Unit
                }
            }

        }

    }

    private fun updateRecyclerView(data: ArrayList<Article>?) {
        data?.let {
            headLinesAdapter.setData(it)
            headLinesAdapter.notifyDataSetChanged()
        }
    }

    private fun fetchData() {
//        if(hasInternetConnection()){
//            mainViewModel.fetch()
//        }
//        else{
//            showInternetNotConnectedToast()
//        }
        mainViewModel.fetch()
    }

    private fun showInternetNotConnectedToast(){
        Toast.makeText(
            this@MainActivity,
            "Please check your internet connection",
            Toast.LENGTH_SHORT
        ).show()
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

    override fun onCategoryClicked(category: Pair<String, Boolean>, position: Int) {
        if(!category.second && catPos != position){
            val cat = Pair(categoriesList[catPos].first,false)
            categoriesList[catPos] = cat
            categoriesList[position] = Pair(categoriesList[position].first,true)
            categoriesAdapter.notifyDataSetChanged()
            if(hasInternetConnection()) {
                if (position != 0) {
                    mainViewModel.setCategory(category.first)
                } else {
                    mainViewModel.setCategory("")
                }
                fetchData()
            }
            else{
                showInternetNotConnectedToast()
            }
            catPos = position
        }
    }

}
