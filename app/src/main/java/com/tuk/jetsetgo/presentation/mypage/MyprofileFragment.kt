package com.tuk.jetsetgo.presentation.mypage

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentMyprofileBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment

class MyprofileFragment: BaseFragment<FragmentMyprofileBinding>(R.layout.fragment_myprofile) {
    override fun initObserver() {

    }

    override fun initView() {
        setClickListener()
        editText()
        binding.tvMyprofileName.text = "${binding.tvMyprofileName.text} 님"
    }

    private fun setClickListener(){
        binding.ivMyprofileBack.setOnClickListener { findNavController().popBackStack() }
        binding.ivMyprofileDelete.setOnClickListener { binding.etMyprofileName.setText("") }
        binding.tvMypageWithdraw.setOnClickListener { showWithdrawDialog() }
        binding.clMyprofileImage.setOnClickListener { openGallery() }
    }

    private fun editText() {
        binding.etMyprofileName.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                return@setOnKeyListener true // 화면 키보드 엔터 방지
            }
            false
        }

        binding.etMyprofileName.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_NEXT ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                return@setOnEditorActionListener true // 자판 키보드 엔터 방지
            }
            false
        }

        binding.ivMyprofileSave.setOnClickListener {
            Snackbar.make(it, "수정이 완료되었습니다.", Snackbar.LENGTH_SHORT)
                .setAnchorView(binding.tvMypageWithdraw) // 버튼 위로 스낵바 위치 조정
                .show()
        }

    }


    private fun showWithdrawDialog() {
        binding.viewDialogBg.visibility = View.VISIBLE

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

        dialog.setOnDismissListener {
            binding.viewDialogBg.visibility = View.GONE
        }

        dialog.show()
    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                setProfileImage(uri)
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        imagePickerLauncher.launch(intent)
    }

    private fun setProfileImage(imageUri: Uri) {
        binding.ivMyprofileImage.setImageURI(imageUri)
    }
}
