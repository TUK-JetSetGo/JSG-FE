package com.umc.jetsetgo.data.dto.response

import com.umc.jetsetgo.domain.model.TestModel

data class TestResponse (
    val body: String
){
    fun toTestModel() = TestModel(body)
}