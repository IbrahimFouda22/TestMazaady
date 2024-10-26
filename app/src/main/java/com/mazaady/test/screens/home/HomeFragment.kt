package com.mazaady.test.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.mazaady.test.R
import com.mazaady.test.databinding.FragmentHomeBinding
import com.mazaady.test.screens.home.adapter.HomeChatAdapter
import com.mazaady.test.screens.home.adapter.PagerHomeAdapter
import kotlin.math.abs

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var listImages: List<Int>
    private lateinit var listImagesCourses: List<Int>
    private lateinit var homeChatAdapter: HomeChatAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.recyclerChat.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        listImages = listOf(
            R.drawable.img_profile_1,
            R.drawable.img_profile_2,
            R.drawable.img_profile_3,
            R.drawable.img_profile_4,
        )
        listImagesCourses = listOf(
            R.drawable.img_course,
            R.drawable.img_course,
            R.drawable.img_course,
        )
        homeChatAdapter = HomeChatAdapter()
        binding.recyclerChat.adapter = homeChatAdapter
        homeChatAdapter.submitList(listImages)

        binding.chipCategoryFilter.check(R.id.chipAll)
        val homeCoursesAdapter = PagerHomeAdapter(listImagesCourses)
        binding.viewPager.adapter = homeCoursesAdapter
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        setUpTransformer()
        binding.dotsIndicatorSliderHome.attachTo(binding.viewPager)
        return binding.root
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(16))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
            page.alpha = when {
                position <= -1 -> 0f
                position <= 1 -> 0.5f + (1 - abs(position)) * 0.5f
                else -> 0f
            }

            if (position < 0) {
                page.alpha = maxOf(0.5f + (1 + position) * 0.5f, page.alpha)
            }
        }

        binding.viewPager.setPageTransformer(transformer)
    }
}