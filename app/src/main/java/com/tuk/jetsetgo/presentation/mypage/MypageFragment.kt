package com.tuk.jetsetgo.presentation.mypage

import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentMypageBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class MypageFragment: BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {
    override fun initObserver() {

    }

    override fun initView() {
        setClickListener()
        binding.tvMypageName.text = "${binding.tvMypageName.text} 님"
    }

    private fun setClickListener(){
        binding.clMypageProfile.setOnSingleClickListener {
            findNavController().navigate(R.id.goToProfile)
        }
        binding.tvMypageLogout.setOnClickListener { showLogoutDialog() }

    }

    private fun showLogoutDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_logout_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.8).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val logoutBtn = dialog.findViewById<TextView>(R.id.tv_dialog_yes)
        logoutBtn.setOnClickListener {
            dialog.dismiss()
            //logoutUser()
        }

        val cancelBtn = dialog.findViewById<TextView>(R.id.tv_dialog_no)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}