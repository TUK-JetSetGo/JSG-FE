package com.tuk.jetsetgo.presentation.review

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentReviewDetailBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.review.adapter.CommentAdapter
import com.tuk.jetsetgo.presentation.review.adapter.CommentData
import com.tuk.jetsetgo.presentation.review.adapter.ReviewDetailAdapter
import com.tuk.jetsetgo.presentation.review.adapter.ReviewDetailData

class ReviewDetailFragment :
    BaseFragment<FragmentReviewDetailBinding>(R.layout.fragment_review_detail) {

    companion object {
        const val ARG_REVIEW_KEY = "reviewKey"
    }

    private lateinit var reviewDetailAdapter: ReviewDetailAdapter
    private lateinit var commentAdapter: CommentAdapter

    private var isLiked = false
    private var isBookmarked = false

    private var reviewKey: String = ""

    private val dummyDetailMap: Map<String, Pair<List<ReviewDetailData>, List<CommentData>>> = mapOf(
        "강릉 2박3일 여행 후기" to (
                listOf(
                    ReviewDetailData(
                        "1일차",
                        listOf(
                            "https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/%EA%B0%95%EB%AC%B8%ED%95%B4%EB%B3%80.jpg/640px-%EA%B0%95%EB%AC%B8%ED%95%B4%EB%B3%80.jpg",
                            "https://a.travel-assets.com/findyours-php/viewfinder/images/res70/247000/247840-Anmok-Beach.jpg"
                        ),
                        "강문해변 산책 후 커피거리 카페 들렀어요"
                    ),
                    ReviewDetailData(
                        "2일차",
                        listOf(
                            "https://api.cdn.visitjeju.net/photomng/imgpath/202111/12/dd57fe99-46ea-483b-bfcc-b6fa954baa7c.jpg"
                        ),
                        "안목해변에서 수영하고 맛집 탐방~"
                    ),
                    ReviewDetailData(
                        "3일차",
                        listOf(
                            "https://blog.kakaocdn.net/dna/VvKX6/btsKTUg4Irg/AAAAAAAAAAAAAAAAAAAAACPeJCdfkQQStdvNxOY3ZJ3r_798Qzn5eJ5ersfCYSpD/img.png?credential=yqXZFxpELC7KVnFOS48ylbz2pIh7yKj8&expires=1759244399&allow_ip=&allow_referer=&signature=R537YL1zO9DOupTn%2FcW8iSrwfCo%3D",
                            "https://www.citytourbusan.com/program/upload/surroundings/20140306085322_0.jpg"
                        ),
                        "경포대에서 일몰 감상 후 중앙시장 들러서 저녁 먹고 귀가했어요"
                    )
                ) to listOf(
                    CommentData("김기찬", "사진 감성 좋네요"),
                    CommentData("박준하", "코스 공유 부탁드려요!"),
                    CommentData("정동훈", "렌트 추천하나요?")
                )
                ),
        "서울 핫플 정복기" to (
                listOf(
                    ReviewDetailData(
                        "1일차",
                        listOf(
                            "https://offloadmedia.feverup.com/secretseoul.com/wp-content/uploads/2023/03/07211252/Untitled-design-35.png",
                            "https://www.newspeak.kr/news/photo/201210/26212_12958_210.jpg"
                        ),
                        "성수 카페 투어.\n분위기 좋네요"
                    ),
                    ReviewDetailData(
                        "2일차",
                        listOf(
                            "https://cdn.womennews.co.kr/news/thumbnail/202105/211362_343480_619_v150.jpg"
                        ),
                        "을지로 노포 탐방했어요!"
                    )
                ) to listOf(
                    CommentData("정동훈", "한남동도 추천해요~"),
                    CommentData("박준하", "주차 가능한가요?")
                )
                ),
        "부산 해운대 맛집 투어" to (
                listOf(
                    ReviewDetailData(
                        "1일차",
                        listOf(
                            "https://www.visitbusan.net/uploadImgs/files/cntnts/20191229153533649_wufrotr"
                        ),
                        "해운대 회, 튀김 맛집이에요. 강력 추천합니다."
                    ),
                    ReviewDetailData(
                        "2일차",
                        listOf(
                            "https://newsimg.hankookilbo.com/2018/07/27/201807271302064274_1.jpg"
                        ),
                        "야경 보며 산책했어요. \n근데 사람이 정말 많네요."
                    )
                ) to listOf(
                    CommentData("김다희", "저기 맛있죠."),
                    CommentData("김기찬", "주차 가능한 곳인가요?"),
                    CommentData("박준하", "야경이 예쁘네요"),
                    CommentData("사용자", "잘 보고 갑니다")
                )
                ),
        "제주도 3박4일 힐링 코스" to (
                listOf(
                    ReviewDetailData(
                        "1일차",
                        listOf(
                            "https://happist.com/wp-content/uploads/2022/12/%EC%A0%9C%EC%A3%BC-%EC%97%AC%ED%96%89-%EA%B4%91%EC%B9%98%EA%B8%B0%ED%95%B4%EB%B3%80%EC%97%90%EC%84%9C-%EB%B3%B8-%EC%84%B1%EC%82%B0%EC%9D%BC%EC%B6%9C%EB%B4%89-%EC%9D%BC%EC%B6%9C-%ED%92%8D%EA%B2%BD-Photo-from-gu_ni222-Instagram-1024x767.jpg"
                        ),
                        "용두암, 이호테우 해변 드라이브"
                    ),
                    ReviewDetailData(
                        "2일차",
                        listOf(
                            "https://blog-static.kkday.com/ko/blog/wp-content/uploads/jeju_rapeseed_1.jpeg"
                        ),
                        "유채밭 포토스팟"
                    ),
                    ReviewDetailData(
                        "3일차",
                        listOf(
                            "https://api.cdn.visitjeju.net/photomng/imgpath/202111/12/dd57fe99-46ea-483b-bfcc-b6fa954baa7c.jpg"
                        ),
                        "오름 트래킹"
                    )
                ) to listOf(
                    CommentData("김기찬", "1일차 코스 좋네요."),
                    CommentData("박준하", "렌트 추천하나요?"),
                    CommentData("김다희", "잘 보고 갑니다")
                )
                )
    )

    private fun bindHeaderByKey(key: String) {
        when (key) {
            "강릉 2박3일 여행 후기" -> {
                binding.tvReviewDetailName.text = "김다희"
                binding.tvReviewDetailTitle.text = "강릉 2박3일 여행 후기"
                binding.tvReviewDetailStar.text = "4.5"
                binding.tvReviewDetailLike.text = "0"
                binding.tvReviewDetailComment.text = "3"
                binding.tvReviewDetailBookmark.text = "0"
                binding.tvReviewDetailTotalReviewContent.text = "바다 예쁘고 커피도 좋아서 힐링했어요."
            }
            "서울 핫플 정복기" -> {
                binding.tvReviewDetailName.text = "김기찬"
                binding.tvReviewDetailTitle.text = "서울 핫플 정복기"
                binding.tvReviewDetailStar.text = "4.2"
                binding.tvReviewDetailLike.text = "3"
                binding.tvReviewDetailComment.text = "6"
                binding.tvReviewDetailBookmark.text = "1"
                binding.tvReviewDetailTotalReviewContent.text = "성수·을지로 위주로 다녀왔어요."
            }
            "부산 해운대 맛집 투어" -> {
                binding.tvReviewDetailName.text = "박준하"
                binding.tvReviewDetailTitle.text = "부산 해운대 맛집 투어"
                binding.tvReviewDetailStar.text = "4.7"
                binding.tvReviewDetailLike.text = "5"
                binding.tvReviewDetailComment.text = "2"
                binding.tvReviewDetailBookmark.text = "9"
                binding.tvReviewDetailTotalReviewContent.text = "회와 튀김, 야경 산책이 최고였어요."
            }
            "제주도 3박4일 힐링 코스" -> {
                binding.tvReviewDetailName.text = "정동훈"
                binding.tvReviewDetailTitle.text = "제주도 3박4일 힐링 코스"
                binding.tvReviewDetailStar.text = "4.8"
                binding.tvReviewDetailLike.text = "4"
                binding.tvReviewDetailComment.text = "8"
                binding.tvReviewDetailBookmark.text = "7"
                binding.tvReviewDetailTotalReviewContent.text = "오름 트래킹과 드라이브가 인상 깊었어요."
            }
            else -> {
                binding.tvReviewDetailName.text = "관리자"
                binding.tvReviewDetailTitle.text = "임시 리뷰"
                binding.tvReviewDetailStar.text = "4.0"
                binding.tvReviewDetailLike.text = "0"
                binding.tvReviewDetailComment.text = "0"
                binding.tvReviewDetailBookmark.text = "0"
                binding.tvReviewDetailTotalReviewContent.text = "임시 데이터입니다."
            }
        }
    }

    override fun initObserver() {
        // 1) 키 읽기
        reviewKey = arguments?.getString(ARG_REVIEW_KEY).orEmpty()

        // 2) 키에 해당하는 더미 데이터 가져오기
        val (detailList, commentList) = dummyDetailMap[reviewKey]
            ?: (listOf(ReviewDetailData("1일차", emptyList(), "임시 본문입니다."))
                    to listOf(CommentData("관리자", "임시 댓글입니다.")))

        // 3) 상세 섹션 리스트
        reviewDetailAdapter = ReviewDetailAdapter(detailList)
        binding.rvReviewDetail.apply {
            adapter = reviewDetailAdapter
            layoutManager = LinearLayoutManager(requireContext())
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }

        // 4) 댓글 리스트
        commentAdapter = CommentAdapter(commentList.toMutableList())
        binding.rvReviewDetailComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            isNestedScrollingEnabled = false
        }
    }

    override fun initView() {
        // 헤더 텍스트들 세팅
        bindHeaderByKey(reviewKey)

        // 뒤로가기
        binding.ivReviewDetailBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // 댓글 등록 (목업)
        binding.tvReviewDetailAddCommentBtn.setOnClickListener {
            val text = binding.etReviewDetailAddComment.text?.toString()?.trim().orEmpty()
            if (text.isNotEmpty()) {
                val newComment = CommentData(username = "나", content = text)
                // CommentAdapter에 구현한 addComment(comment) 사용(이전에 안내한 대로 수정되어 있어야 함)
                commentAdapter.addComment(newComment)

                // 입력창 초기화
                binding.etReviewDetailAddComment.setText("")

                binding.rvReviewDetailComment.requestLayout()

                // 상단 댓글 수 +1
                val currentCount = binding.tvReviewDetailComment.text.toString().toIntOrNull() ?: 0
                binding.tvReviewDetailComment.text = (currentCount + 1).toString()

                // 마지막으로 스크롤
                binding.rvReviewDetailComment.smoothScrollToPosition(commentAdapter.itemCount - 1)
            }
        }

        // 좋아요 버튼
        binding.viewReviewDetailLike.setOnClickListener {
            val currentCount = binding.tvReviewDetailLike.text.toString().toIntOrNull() ?: 0
            if (isLiked) {
                // 좋아요 취소
                binding.viewReviewDetailLike.setBackgroundResource(R.drawable.ic_like)
                binding.tvReviewDetailLike.text = (currentCount - 1).coerceAtLeast(0).toString()
            } else {
                // 좋아요 추가
                binding.viewReviewDetailLike.setBackgroundResource(R.drawable.ic_like_sel)
                binding.tvReviewDetailLike.text = (currentCount + 1).toString()
            }
            isLiked = !isLiked
        }

        // 북마크 버튼
        binding.viewReviewDetailBookmark.setOnClickListener {
            val currentCount = binding.tvReviewDetailBookmark.text.toString().toIntOrNull() ?: 0
            if (isBookmarked) {
                // 북마크 취소
                binding.viewReviewDetailBookmark.setBackgroundResource(R.drawable.ic_bookmark)
                binding.tvReviewDetailBookmark.text = (currentCount - 1).coerceAtLeast(0).toString()
            } else {
                // 북마크 추가
                binding.viewReviewDetailBookmark.setBackgroundResource(R.drawable.ic_bookmark_sel)
                binding.tvReviewDetailBookmark.text = (currentCount + 1).toString()
            }
            isBookmarked = !isBookmarked
        }
    }
}

