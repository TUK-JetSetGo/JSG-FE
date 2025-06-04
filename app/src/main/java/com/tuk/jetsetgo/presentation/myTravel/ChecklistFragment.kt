package com.tuk.jetsetgo.presentation.myTravel

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentChecklistBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.myTravel.adapter.ChecklistItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChecklistFragment : BaseFragment<FragmentChecklistBinding>(R.layout.fragment_checklist) {

    private val viewModel: ChecklistViewModel by viewModels()
    private val myTravelViewModel: MyTravelViewModel by activityViewModels()
    private lateinit var checklistItemAdapter: ChecklistItemAdapter

    override fun initObserver() {
        myTravelViewModel.travelPlanId.value?.let { travelPlanId ->
            Log.d("ChecklistFragment", "travelPlanId 전달받음: $travelPlanId")
            viewModel.loadChecklist(travelPlanId)
        }

        viewModel.checklistItems.observe(viewLifecycleOwner) { items ->
            checklistItemAdapter = ChecklistItemAdapter(
                items = items,
                onDeleteClick = { item ->
                    val travelPlanId = myTravelViewModel.travelPlanId.value
                    if (travelPlanId != null) {
                        Log.d("ChecklistFragment", "삭제 버튼 클릭됨: ${item.itemName}")
                        viewModel.deleteCheckItem(item.checklistId, travelPlanId)
                    } else {
                        Log.w("ChecklistFragment", "travelPlanId 없음. 삭제 요청 불가")
                    }
                },
                onCheckChanged = { item, isChecked ->
                    viewModel.patchCheckItem(item.checklistId, isChecked)
                }
            )
            binding.rvChecklistItems.adapter = checklistItemAdapter
        }



    }

    override fun initView() {
        initRecyclerView()
        setClickListener()
        setBackPressedCallback()
    }

    private fun initRecyclerView() {
        // 준비물 항목 리사이클러뷰 ()
        binding.rvChecklistItems.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setClickListener() {
        binding.tvBtnAddItem.setOnClickListener {
            val newItem = binding.etAddItem.text.toString()
            if (newItem.isNotBlank()) {
                val travelPlanId = myTravelViewModel.travelPlanId.value
                if (travelPlanId != null) {
                    Log.d("ChecklistFragment", "항목 추가 요청: $newItem (travelPlanId=$travelPlanId)")
                    viewModel.postChecklistItem(travelPlanId, newItem)
                    binding.etAddItem.text?.clear()
                } else {
                    Log.w("ChecklistFragment", "travelPlanId가 null입니다. 추가 요청 불가")
                }
            } else {
                Log.d("ChecklistFragment", "입력값이 비어있습니다")
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
