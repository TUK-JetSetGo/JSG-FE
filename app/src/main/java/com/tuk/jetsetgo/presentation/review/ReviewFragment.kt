package com.tuk.jetsetgo.presentation.review

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentReviewBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.review.adapter.ReviewAdapter
import com.tuk.jetsetgo.presentation.review.adapter.ReviewData
import com.tuk.jetsetgo.presentation.review.adapter.ReviewDetailData

class ReviewFragment: BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {
    private lateinit var reviewAdapter: ReviewAdapter

    private val reviewList = listOf(
        ReviewData("강릉 2박3일 여행 후기", "김다희", listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/%EA%B0%95%EB%AC%B8%ED%95%B4%EB%B3%80.jpg/640px-%EA%B0%95%EB%AC%B8%ED%95%B4%EB%B3%80.jpg", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSbxNQQLawIrXZfs53TNqf20yXymmjbG_F-nQ&s", "https://a.travel-assets.com/findyours-php/viewfinder/images/res70/247000/247840-Anmok-Beach.jpg"),"1","3","0",),
        ReviewData("서울 핫플 정복기", "김기찬", listOf("https://cdn.womennews.co.kr/news/thumbnail/202105/211362_343480_619_v150.jpg", "https://offloadmedia.feverup.com/secretseoul.com/wp-content/uploads/2023/03/07211252/Untitled-design-35.png", "https://www.newspeak.kr/news/photo/201210/26212_12958_210.jpg"),"3","2","1",),
        ReviewData("부산 해운대 맛집 투어", "박준하", listOf("https://www.visitbusan.net/uploadImgs/files/cntnts/20191229153533649_wufrotr", "https://newsimg.hankookilbo.com/2018/07/27/201807271302064274_1.jpg","https://www.newsrep.co.kr/news/photo/201801/39225_36332_418.jpg"),"5","4","9",),
        ReviewData("제주도 3박4일 힐링 코스", "정동훈", listOf("https://happist.com/wp-content/uploads/2022/12/%EC%A0%9C%EC%A3%BC-%EC%97%AC%ED%96%89-%EA%B4%91%EC%B9%98%EA%B8%B0%ED%95%B4%EB%B3%80%EC%97%90%EC%84%9C-%EB%B3%B8-%EC%84%B1%EC%82%B0%EC%9D%BC%EC%B6%9C%EB%B4%89-%EC%9D%BC%EC%B6%9C-%ED%92%8D%EA%B2%BD-Photo-from-gu_ni222-Instagram-1024x767.jpg", "https://api.cdn.visitjeju.net/photomng/imgpath/202111/12/dd57fe99-46ea-483b-bfcc-b6fa954baa7c.jpg","https://blog-static.kkday.com/ko/blog/wp-content/uploads/jeju_rapeseed_1.jpeg"),"4","3","7",),
    )

    override fun initObserver() {
        initRecyclerView()
    }

    override fun initView() {
    }

    private fun initRecyclerView() {
        binding.rvTravelLocation.layoutManager = LinearLayoutManager(requireContext())
        reviewAdapter = ReviewAdapter(reviewList) { selectedReview ->
            val key = selectedReview.title
            findNavController().navigate(
                R.id.goToReviewDetail,
                bundleOf("reviewKey" to key)
            )
        }
        binding.rvTravelLocation.adapter = reviewAdapter
    }

}
