package com.blackhole.gamerockpaperscissors.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.blackhole.gamerockpaperscissors.databinding.FragmentFormBinding
import com.blackhole.gamerockpaperscissors.ui.onboarding.OnboardingActivity


class FormFragment : Fragment() {

    private lateinit var binding: FragmentFormBinding

    private var listener: OnNameSubmittedListener? = null

    fun setNameSubmittedListener(listener: OnboardingActivity) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFormBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnSetName.setOnClickListener {
            val name = binding.etName.text.toString().trim()


            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Please input your name !", Toast.LENGTH_SHORT).show()
            } else {
                listener?.onNameSubmitted(name)
            }

//            Toast.makeText(requireContext(), name, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FormFragment()
    }
}

interface OnNameSubmittedListener {
    fun onNameSubmitted(name: String)
}

