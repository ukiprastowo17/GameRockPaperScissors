package com.blackhole.gamerockpaperscissors.ui.onboarding

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.viewpager2.widget.ViewPager2
import com.blackhole.gamerockpaperscissors.R
import com.blackhole.gamerockpaperscissors.databinding.ActivityOnboardingBinding
import com.blackhole.gamerockpaperscissors.model.SliderData
import com.blackhole.gamerockpaperscissors.ui.SliderFragment
import com.blackhole.gamerockpaperscissors.ui.form.FormFragment
import com.blackhole.gamerockpaperscissors.ui.form.OnNameSubmittedListener
import com.blackhole.gamerockpaperscissors.ui.menu.MenuGameActivity
import com.blackhole.gamerockpaperscissors.util.ViewPagerAdapter
import com.blackhole.gamerockpaperscissors.util.getNextIndex
import com.blackhole.gamerockpaperscissors.util.getPreviousIndex

import com.google.android.material.snackbar.Snackbar

class OnboardingActivity : AppCompatActivity(),  OnNameSubmittedListener {
    private val binding: ActivityOnboardingBinding by lazy {
        ActivityOnboardingBinding.inflate(layoutInflater)
    }


    private val pagerAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(supportFragmentManager, lifecycle)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        initFragmentViewPager()
        setOnClickListeners()
    }

    private fun initAdapter(){
        pagerAdapter.apply {
            addFragment(
                SliderFragment.newInstance(
                    SliderData(
                        title = getString(R.string.text_this_rock),
                        desc = getString(R.string.desc_rock),
                        imgSlider = "https://firebasestorage.googleapis.com/v0/b/market-e0906.appspot.com/o/ic_rock.png?alt=media&token=85d00b52-9d03-4171-ad58-48eff590697d"
                    )
                )
            )
            addFragment(
                SliderFragment.newInstance(
                    SliderData(
                        title = getString(R.string.text_this_paper),
                        desc = getString(R.string.desc_paper),
                        imgSlider = "https://firebasestorage.googleapis.com/v0/b/market-e0906.appspot.com/o/ic_paper.png?alt=media&token=fe01062e-11ca-490d-8a0b-acca1a1709dd"
                    )
                )
            )
            addFragment(
                SliderFragment.newInstance(
                    SliderData(
                        title = getString(R.string.text_this_scissor),
                        desc = getString(R.string.desc_scissor),
                        imgSlider = "https://firebasestorage.googleapis.com/v0/b/market-e0906.appspot.com/o/ic_scissor.png?alt=media&token=b0230121-57bd-47d3-818b-4a4494f265c9"
                    )
                )
            )
            addFragment(FormFragment.newInstance().apply {
                setNameSubmittedListener(this@OnboardingActivity)
            })
        }
    }

    private fun setupViewPager(){
        binding.vpIntro.apply {
            adapter = pagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when{
                        position == 0 -> {
                            binding.tvNext.isInvisible = false
                            binding.tvNext.isEnabled =  true
                            binding.tvPrevious.isInvisible = true
                            binding.tvPrevious.isEnabled =  true
                        }
                        position < pagerAdapter.getMaxIndex() ->{

                            binding.tvNext.isInvisible = false
                            binding.tvNext.isEnabled =  true
                            binding.tvPrevious.isInvisible = false
                            binding.tvPrevious.isEnabled =  true

                        }
                        position == pagerAdapter.getMaxIndex()  ->{
                            binding.tvNext.isInvisible = true
                            binding.tvNext.isEnabled =  false
                            binding.tvPrevious.isInvisible = false
                            binding.tvPrevious.isEnabled =  true
                        }
                    }
                }
            })
        }
        binding.dotsIndicator.attachTo(binding.vpIntro)
    }

    private fun initFragmentViewPager(){
        initAdapter()
        setupViewPager()
    }

    private fun setOnClickListeners(){
        binding.tvNext.setOnClickListener {
            navigateToNextFragment()
        }

        binding.tvPrevious.setOnClickListener {
            navigateToPreviosFragment()
        }
    }

    private fun navigateToPreviosFragment() {
      val nextIndex =  binding.vpIntro.getPreviousIndex()
          if (nextIndex != -1){
              binding.vpIntro.setCurrentItem(nextIndex, true)

      }
    }

    private fun navigateToNextFragment(){
        val nextIndex =  binding.vpIntro.getNextIndex()
        if (nextIndex != -1){
            binding.vpIntro.setCurrentItem(nextIndex, true)

        }
    }

    override fun onNameSubmitted(name: String) {
        navigateToMenu(name)
    }

    private fun navigateToMenu(name: String) {
        MenuGameActivity.startActivity(this@OnboardingActivity, name)
    }

}