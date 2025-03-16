package com.tuk.jetsetgo.presentation.addTravel

import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelSearchBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.AddTravelViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.SearchAdapter
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TravelSearchFragment : BaseFragment<FragmentTravelSearchBinding>(R.layout.fragment_travel_search) {
    private lateinit var searchAdapter: SearchAdapter
    private val viewModel: TravelMapViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val addTravelViewModel: AddTravelViewModel by viewModels()

    private val searchList = mutableListOf("장소1", "장소2", "장소3")

    override fun initObserver() {
        viewModel.searchResults.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                Log.d("TravelSearchFragment", "검색 결과: ${result}") // 검색 결과 로그 확인
            }.onFailure {
                Toast.makeText(requireContext(), "검색 실패: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initView() {
        initRecyclerView()
        setupSearchButton()
    }

    private fun initRecyclerView() {
        binding.rvTravelSearch.layoutManager = LinearLayoutManager(requireContext())
        searchAdapter = SearchAdapter(searchList) { position ->
            searchAdapter.removeItem(position)
        }
        binding.rvTravelSearch.adapter = searchAdapter
    }

    private fun setupSearchButton() {
        val performSearch = {
            val searchText = binding.etTravelSearchSearch.text.toString().trim()
            val regex = "^[a-zA-Z0-9가-힣]+$".toRegex() // 한글, 영문, 숫자만 허용

            if (searchText.isNotEmpty() && searchText.matches(regex)) {
                viewModel.fetchSearchSpots(searchText)

                viewModel.searchResults.observe(viewLifecycleOwner) { result ->
                    result.onSuccess {
                        findNavController().navigateUp()
                    }.onFailure {
                        Toast.makeText(requireContext(), "검색 실패: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "한글, 영문, 숫자만 입력 가능해요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 검색 버튼 클릭 시
        binding.ivTravelSearchSearch.setOnSingleClickListener {
            performSearch()
        }

        binding.ivTravelSearchArrow.setOnSingleClickListener {
            findNavController().navigateUp()
        }

        // 엔터 키 입력 시
        binding.etTravelSearchSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true // 이벤트 소비 (키보드 닫힘)
            } else {
                false // 다른 액션은 처리하지 않음
            }
        }
    }
}
