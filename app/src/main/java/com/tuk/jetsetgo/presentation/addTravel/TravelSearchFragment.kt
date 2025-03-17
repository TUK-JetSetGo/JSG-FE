package com.tuk.jetsetgo.presentation.addTravel

import android.content.Context
import android.content.SharedPreferences
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

    private lateinit var sharedPreferences: SharedPreferences
    private val SEARCH_HISTORY_KEY = "search_history"

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
        sharedPreferences = requireContext().getSharedPreferences("travel_prefs", Context.MODE_PRIVATE)

        initRecyclerView()
        setupSearchButton()
        setupDeleteAllButton()
        loadSearchHistory()
    }

    private fun initRecyclerView() {
        binding.rvTravelSearch.layoutManager = LinearLayoutManager(requireContext())
        searchAdapter = SearchAdapter(
            onItemClick = { searchQuery ->
                binding.etTravelSearchSearch.setText(searchQuery) // 검색어를 입력창에 반영
                performSearch(searchQuery) // 검색 실행
            },
            onDeleteClick = { deletedQuery ->
                deleteSearchHistory(deletedQuery) // 검색 기록 삭제
            }
        )
        binding.rvTravelSearch.adapter = searchAdapter
    }

    private fun setupSearchButton() {
        // 검색 버튼 클릭 시
        binding.ivTravelSearchSearch.setOnSingleClickListener {
            val searchText = binding.etTravelSearchSearch.text.toString().trim()
            performSearch(searchText)
        }

        binding.ivTravelSearchArrow.setOnSingleClickListener {
            findNavController().navigateUp()
        }

        // 엔터 키 입력 시
        binding.etTravelSearchSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchText = binding.etTravelSearchSearch.text.toString().trim()
                performSearch(searchText)
                true // 이벤트 소비 (키보드 닫힘)
            } else {
                false // 다른 액션은 처리하지 않음
            }
        }
    }

    private fun setupDeleteAllButton() {
        binding.tvTravelSearchDelete.setOnClickListener {
            clearAllSearchHistory()
        }
    }

    private fun performSearch(searchText: String) {
        val regex = "^[a-zA-Z0-9가-힣]+$".toRegex() // 한글, 영문, 숫자만 허용
        if (searchText.isNotEmpty() && searchText.matches(regex)) {
            viewModel.fetchSearchSpots(searchText)

            viewModel.searchResults.observe(viewLifecycleOwner) { result ->
                result.onSuccess {
                    saveSearchHistory(searchText) // 검색어 저장
                    findNavController().navigateUp()
                }.onFailure {
                    Toast.makeText(requireContext(), "검색 실패: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "한글, 영문, 숫자만 입력 가능해요.", Toast.LENGTH_SHORT).show()
        }
    }

    // 검색 기록을 SharedPreferences에 저장
    private fun saveSearchHistory(query: String) {
        val history = getSearchHistory().toMutableSet()
        history.add(query) // 중복 방지
        sharedPreferences.edit().putStringSet(SEARCH_HISTORY_KEY, history).apply()
        loadSearchHistory() // UI 업데이트
    }

    // SharedPreferences에서 검색 기록 불러오기
    private fun loadSearchHistory() {
        val history = getSearchHistory().toList().sortedDescending()
        searchAdapter.submitList(history)
    }

    private fun getSearchHistory(): Set<String> {
        return sharedPreferences.getStringSet(SEARCH_HISTORY_KEY, emptySet()) ?: emptySet()
    }

    // 검색 기록 삭제
    private fun deleteSearchHistory(query: String) {
        val history = getSearchHistory().toMutableSet()
        history.remove(query)
        sharedPreferences.edit().putStringSet(SEARCH_HISTORY_KEY, history).apply()
        loadSearchHistory()
    }

    // 전체 검색 기록 삭제
    private fun clearAllSearchHistory() {
        sharedPreferences.edit().remove(SEARCH_HISTORY_KEY).apply()
        loadSearchHistory() // UI 업데이트
    }
}
