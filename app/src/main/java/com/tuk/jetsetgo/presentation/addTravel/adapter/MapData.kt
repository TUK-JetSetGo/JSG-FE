package com.tuk.jetsetgo.presentation.addTravel.adapter

data class MapData(
    val name: String,          // 장소 이름
    val address: String,          // 주소
    val keywords: List<String>,  // 키워드 리스트
    val pictures: List<String>   // 이미지 URL 리스트
)
