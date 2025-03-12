package com.tuk.jetsetgo.data.repositoryImpl.addTravel

import com.tuk.jetsetgo.data.datasource.addTravel.AddTravelDataSource
import com.tuk.jetsetgo.domain.model.request.addTravel.CreatePlanRequestModel
import com.tuk.jetsetgo.domain.model.response.addTravel.CreatePlanResponseModel
import com.tuk.jetsetgo.domain.model.response.addTravel.PurposeResponseModel
import com.tuk.jetsetgo.domain.model.response.addTravel.SelectCityResponseModel
import com.tuk.jetsetgo.domain.model.response.addTravel.SelectCountryResponseModel
import com.tuk.jetsetgo.domain.model.response.addTravel.SpotInfoResponseModel
import com.tuk.jetsetgo.domain.model.response.addTravel.ThemesResponseModel
import com.tuk.jetsetgo.domain.repository.addTravel.AddTravelRepository
import javax.inject.Inject

class AddTravelRepositoryImpl @Inject constructor(
    private val addTravelDataSource: AddTravelDataSource
): AddTravelRepository {
    override suspend fun fetchSelectCountry(): Result<SelectCountryResponseModel> = runCatching {
        addTravelDataSource.fetchSelectCountry().data.toSelectCountryResponseModel()
    }

    override suspend fun fetchSelectCity(countryId: Int): Result<SelectCityResponseModel> = runCatching {
        addTravelDataSource.fetchSelectCity(countryId).data.toSelectCityResponseModel()
    }

    override suspend fun fetchPurpose(): Result<PurposeResponseModel> = runCatching {
        addTravelDataSource.fetchPurpose().data.toPurposeResponseModel()
    }

    override suspend fun fetchThemes(): Result<ThemesResponseModel> = runCatching {
        addTravelDataSource.fetchThemes().data.toThemesResponseModel()
    }

    override suspend fun fetchCreatePlan(request: CreatePlanRequestModel): Result<CreatePlanResponseModel?> = runCatching {
        addTravelDataSource.fetchCreatePlan(request.toCreatePlanRequestDto()).data?.toCreatePlanResponseModel()
    }

    override suspend fun fetchSearchSpots(
        keyword: String?,
        category: String?,
        page: Int,
        size: Int,
        sort: String,
    ): Result<SpotInfoResponseModel> = runCatching {
        val pageableJson = """{"page": $page, "size": $size, "sort": ["$sort"]}"""
        addTravelDataSource.fetchSearchSpots(keyword, category, pageableJson).data.toSpotInfoResponseModel()
    }
}