package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class GetSwaggerResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("igdb_id") val igdbId: Int?,
    @SerializedName("name") val title : String?
)
