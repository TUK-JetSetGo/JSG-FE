package com.tuk.jetsetgo.presentation.review

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
        ReviewData("강릉 여행 추천지", "김다희", listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/%EA%B0%95%EB%AC%B8%ED%95%B4%EB%B3%80.jpg/640px-%EA%B0%95%EB%AC%B8%ED%95%B4%EB%B3%80.jpg", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSbxNQQLawIrXZfs53TNqf20yXymmjbG_F-nQ&s"),"0","3","0",),
        ReviewData("서울 핫플", "김기찬", listOf("https://cdn.womennews.co.kr/news/thumbnail/202105/211362_343480_619_v150.jpg", "https://offloadmedia.feverup.com/secretseoul.com/wp-content/uploads/2023/03/07211252/Untitled-design-35.png", "https://www.newspeak.kr/news/photo/201210/26212_12958_210.jpg"),"3","6","1",),
        ReviewData("해운대 맛집", "박준하", listOf("https://www.visitbusan.net/uploadImgs/files/cntnts/20191229153533649_wufrotr", "https://newsimg.hankookilbo.com/2018/07/27/201807271302064274_1.jpg"),"5","2","9",),
        ReviewData("제주도 3박4일 후기", "정동훈", listOf("https://happist.com/wp-content/uploads/2022/12/%EC%A0%9C%EC%A3%BC-%EC%97%AC%ED%96%89-%EA%B4%91%EC%B9%98%EA%B8%B0%ED%95%B4%EB%B3%80%EC%97%90%EC%84%9C-%EB%B3%B8-%EC%84%B1%EC%82%B0%EC%9D%BC%EC%B6%9C%EB%B4%89-%EC%9D%BC%EC%B6%9C-%ED%92%8D%EA%B2%BD-Photo-from-gu_ni222-Instagram-1024x767.jpg", "https://api.cdn.visitjeju.net/photomng/imgpath/202111/12/dd57fe99-46ea-483b-bfcc-b6fa954baa7c.jpg","https://blog-static.kkday.com/ko/blog/wp-content/uploads/jeju_rapeseed_1.jpeg"),"4","8","7",),
   )

    private val reviewDetailList = listOf(
        ReviewDetailData("1일차 후기", listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/%EA%B0%95%EB%AC%B8%ED%95%B4%EB%B3%80.jpg/640px-%EA%B0%95%EB%AC%B8%ED%95%B4%EB%B3%80.jpg", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSbxNQQLawIrXZfs53TNqf20yXymmjbG_F-nQ&s"), "강문해변 산책하고 커피거리 카페 들렀어요."),
        ReviewDetailData("2일차 후기", listOf("https://a.travel-assets.com/findyours-php/viewfinder/images/res70/247000/247840-Anmok-Beach.jpg", "https://a.travel-assets.com/findyours-php/viewfinder/images/res70/247000/247578-Gangwon.jpg"), "안목해변 가서 해수욕도 하고 맛집도 들렀어요."),
        ReviewDetailData("3일차 후기", listOf("https://blog.kakaocdn.net/dn/VvKX6/btsKTUg4Irg/V1ye2kyK0bu1QuyoD97Wqk/img.png"), "경포대에서 일몰 보고 감동했습니다.")
    )

    override fun initObserver() {
        initRecyclerView()
    }

    override fun initView() {

    }

    private fun initRecyclerView() {
        binding.rvTravelLocation.layoutManager = LinearLayoutManager(requireContext())
        reviewAdapter = ReviewAdapter(reviewList) { selectedReview ->
            findNavController().navigate(R.id.goToReviewDetail)
        }
        binding.rvTravelLocation.adapter = reviewAdapter
    }

}