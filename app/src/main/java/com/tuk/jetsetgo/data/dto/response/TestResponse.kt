package com.tuk.jetsetgo.data.dto.response

import com.tuk.jetsetgo.domain.model.TestModel

data class TestResponse (
    val body: String
){
    fun toTestModel() = TestModel(body)
}