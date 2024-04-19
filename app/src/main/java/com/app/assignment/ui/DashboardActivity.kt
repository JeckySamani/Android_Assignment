package com.app.assignment.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.assignment.R
import com.app.assignment.adapters.ImageAdapter
import com.app.assignment.base.AppBaseActivity
import com.app.assignment.databinding.ActivityDashboardBinding
import com.app.assignment.helpers.Parameters
import com.app.assignment.models.ImageModel
import com.app.assignment.network.NetworkResult
import com.app.assignment.viewmodels.GetImagesViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("CAST_NEVER_SUCCEEDS")
@AndroidEntryPoint
class DashboardActivity : AppBaseActivity() {

    private lateinit var binding : ActivityDashboardBinding

    private val getImagesViewModel : GetImagesViewModel by viewModels()
    private var imageList = mutableListOf<ImageModel>()
    private lateinit var layoutManager: RecyclerView.LayoutManager

    private var moreLoad = true
    private var offset = 1
    private var limit = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initObservers()
        setSwipe()
    }


    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun setSwipe() {

        binding.swipeFeed.setOnRefreshListener {

            offset = 1
            fetchData()
        }

        binding.swipeFeed.post {
            binding.swipeFeed.isRefreshing = false
        }

        binding.swipeFeed.setColorSchemeResources(R.color.cp)
    }

    private fun fetchData() {
        getImagesViewModel.getImages(Parameters.clientId , limit.toString() , offset.toString())
    }

    override fun init() {
        binding.rvImages.setHasFixedSize(true)
        layoutManager = GridLayoutManager(this , 2)
            .apply { isAutoMeasureEnabled = false }
        binding.rvImages.layoutManager = layoutManager
        binding.rvImages.isNestedScrollingEnabled = false

    }

    override fun setListsAndAdapters() {
        TODO("Not yet implemented")
    }

    override fun setListeners() {
        TODO("Not yet implemented")
    }

    override fun initObservers() {

        Log.e("Data =>" , "3========================================================================================================================")


        getImagesViewModel.getImages.observe(this) { response ->
            dismissProgressDialog()
            when (response) {
                is NetworkResult.Success -> {
                    Log.e("Data =>" , "4========================================================================================================================")
                    try {
                        Log.e("Data =>" , "5========================================================================================================================")
                        Log.e("Data =>" , "Data->" + response.data.toString())

                        val imageModel = response.data!! as MutableList<ImageModel>
                        Log.e("Size","Size1->"+imageModel.size)

                        if (offset == 0) {
                            moreLoad = true
                            imageList = ArrayList()
                        }

                        if (imageModel.size == 0)
                            moreLoad = false
                        else if (imageModel.size < limit)
                            moreLoad = false

                        imageList.addAll(imageModel)
                        Log.e("Size","Size2->"+imageList.size)

                        val imageAdapter = ImageAdapter(this, imageListener)
                        binding.rvImages.adapter = imageAdapter
                        Log.e("Size","Size3->"+imageList.size)
                        imageAdapter.submitList(imageList)

                        if (offset == 1)
                            loadMoreGrid()

                    }catch (e : Exception){
                        e.printStackTrace()
                    }finally {
                        binding.swipeFeed.isRefreshing = false
                    }

                }
                is NetworkResult.Error -> {
                    //Utils.showToast(context , response.message)
                    Log.e("Data =>" ,  response.message.toString())
                }
                is NetworkResult.Loading -> {
                    // show a progress bar
                }

            }
        }



    }

    private val imageListener = { _: MutableList<ImageModel>,
                                  _: Int,
                                  _: ImageModel ->}

    private fun loadMoreGrid() {

        binding.mNestedScrollView.setOnScrollChangeListener {
                v: NestedScrollView, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->

            if (v.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1)
                        .measuredHeight - v.measuredHeight
                    && scrollY > oldScrollY
                ) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems =
                        (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                        if (moreLoad) {
                            offset += 1

                            fetchData()
                        }
                    }
                }
            }
        }
    }



}