package com.tuk.jetsetgo.presentation.mypage

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentMyprofileBinding
import com.tuk.jetsetgo.domain.model.request.mypage.PatchUserRequestModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.mypage.adapter.MypageViewModel
import com.tuk.jetsetgo.util.network.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyprofileFragment: BaseFragment<FragmentMyprofileBinding>(R.layout.fragment_myprofile) {

    private val mypageViewModel: MypageViewModel by activityViewModels()

    override fun initObserver() {

    }

    override fun initView() {
        setClickListener()
        editText()
        getUser()
        patchUser()
    }

    private fun getUser() {
        mypageViewModel.getUser()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mypageViewModel.getUserState.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            Log.d("MyprofileFragment", "Loading user data")
                            // 로딩 인디케이터 띄우기 등
                        }

                        is UiState.Success -> {
                            // userInfo에서 실제 유저 정보 꺼내오기
                            val user = mypageViewModel.userInfo.value
                            if (user != null) {
                                Log.d("MyprofileFragment", "유저 정보 UI 업데이트: $user")
                                binding.tvMyprofileName.text = "${user.name} 님"
                                binding.etMyprofileName.setText(user.name)
                                binding.tvMyprofileEmail.text = user.email
                                binding.tvMyprofileCreateDate.text = user.createdAt.substringBefore("T")
                            }
                        }

                        is UiState.Error -> {
                            Toast.makeText(requireContext(), "사용자 정보 로드 실패", Toast.LENGTH_SHORT).show()
                            Log.e("MyprofileFragment", "사용자 정보 로드 실패: ${state.error?.message}")
                        }

                        UiState.Empty -> {
                            Log.d("MyprofileFragment", "userInfo 상태: Empty")
                        }
                    }
                }
            }
        }
    }

    private fun patchUser(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mypageViewModel.patchUserState.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            Log.d("MyprofileFragment", "사용자 정보 수정 중...")
                        }

                        is UiState.Success -> {
                            val newName = binding.etMyprofileName.text.toString().trim()
                            binding.tvMyprofileName.text = "$newName 님"

                            Snackbar.make(binding.root, "수정이 완료되었습니다.", Snackbar.LENGTH_SHORT)
                                .setAnchorView(binding.tvMypageWithdraw)
                                .show()
                        }

                        is UiState.Error -> {
                            Toast.makeText(requireContext(), "이름 수정 실패", Toast.LENGTH_SHORT).show()
                            Log.e("MyprofileFragment", "이름 수정 실패: ${state.error?.message}")
                        }

                        UiState.Empty -> Unit
                    }
                }
            }
        }
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
            val newName = binding.etMyprofileName.text.toString().trim()
            if (newName.isNotEmpty()) {
                mypageViewModel.patchUser(PatchUserRequestModel(name = newName))
            } else {
                Toast.makeText(requireContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }


    }


    private fun showWithdrawDialog() {
        binding.viewDialogBg.visibility = View.VISIBLE

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_withdraw_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.8).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val withdrawBtn = dialog.findViewById<TextView>(R.id.tv_dialog_yes)
        withdrawBtn.setOnClickListener {
            dialog.dismiss()
            //deleteUser()
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
