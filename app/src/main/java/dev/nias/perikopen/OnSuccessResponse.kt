package dev.nias.perikopen

import com.apollographql.apollo.api.Response

interface OnSuccessResponse {
    fun onSuccessResponse(response: Response<FeedQuery.Data>)
}