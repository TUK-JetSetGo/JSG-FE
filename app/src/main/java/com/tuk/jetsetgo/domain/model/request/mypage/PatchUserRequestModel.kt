package com.tuk.jetsetgo.domain.model.request.mypage

import com.tuk.jetsetgo.data.dto.request.mypage.PatchUserRequestDto

data class PatchUserRequestModel(
    val name: String
) {
    fun toPatchUserRequestDto() =
        PatchUserRequestDto(name)
}