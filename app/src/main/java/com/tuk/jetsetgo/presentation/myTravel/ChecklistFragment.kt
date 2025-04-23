package com.tuk.jetsetgo.presentation.myTravel

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentChecklistBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.ChecklistItemAdapter
import com.tuk.jetsetgo.presentation.myTravel.adapter.PresetAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChecklistFragment : BaseFragment<FragmentChecklistBinding>(R.layout.fragment_checklist) {

    private val viewModel: ChecklistViewModel by viewModels()

    private lateinit var presetAdapter: PresetAdapter
    private lateinit var checklistItemAdapter: ChecklistItemAdapter

    override fun initObserver() {
        viewModel.presetList.observe(viewLifecycleOwner) { presets ->
            presetAdapter = PresetAdapter(presets) { preset ->
                viewModel.onPresetSelected(preset)
            }
            binding.rvPreset.adapter = presetAdapter
        }

        viewModel.checklistItems.observe(viewLifecycleOwner) { items ->
            Log.d("ChecklistFragment", "옵저버 감지됨! 아이템 수: ${items.size}") // ✅ 옵저버가 데이터 받고 있는지 확인
            checklistItemAdapter = ChecklistItemAdapter(items)
            binding.rvChecklistItems.adapter = checklistItemAdapter
        }
    }

    override fun initView() {
        initRecyclerView()
        setClickListener()
        setBackPressedCallback()
    }

    private fun initRecyclerView() {
        // 프리셋 리사이클러뷰 (가로 스크롤)
        binding.rvPreset.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        // 준비물 항목 리사이클러뷰 (2열 그리드)
        binding.rvChecklistItems.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun setClickListener() {
        binding.tvBtnAddItem.setOnClickListener {
            val newItem = binding.etAddItem.text.toString()
            if (newItem.isNotBlank()) {
                viewModel.addItem(newItem)
                binding.etAddItem.text?.clear()
            }
        }

        binding.ivChecklistBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : androidx.activity.OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }
}
