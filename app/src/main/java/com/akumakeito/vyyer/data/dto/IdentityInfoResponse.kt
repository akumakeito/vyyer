package com.akumakeito.vyyer.data.dto
import com.google.gson.annotations.SerializedName


data class IdentityInfoResponse(
    @SerializedName("Address")
    val address: String,
    @SerializedName("Ban")
    val ban: Int,
    @SerializedName("BanEndAt")
    val banEndAt: String,
    @SerializedName("BanReasonID")
    val banReasonID: Int,
    @SerializedName("BanStartAt")
    val banStartAt: String,
    @SerializedName("BannedBy")
    val bannedBy: String,
    @SerializedName("Birthday")
    val birthday: String,
    @SerializedName("City")
    val city: String,
    @SerializedName("CreatedAt")
    val createdAt: String,
    @SerializedName("ExpiresAt")
    val expiresAt: String,
    @SerializedName("EyeColor")
    val eyeColor: String,
    @SerializedName("FirstName")
    val firstName: String,
    @SerializedName("FullName")
    val fullName: String,
    @SerializedName("Gender")
    val gender: String,
    @SerializedName("HairColor")
    val hairColor: String,
    @SerializedName("Height")
    val height: String,
    @SerializedName("ID")
    val id: Int,
    @SerializedName("IssuedAt")
    val issuedAt: String,
    @SerializedName("LastName")
    val lastName: String,
    @SerializedName("LastScannedAt")
    val lastScannedAt: String,
    @SerializedName("LicenseNumber")
    val licenseNumber: String,
    @SerializedName("MiddleName")
    val middleName: String,
    @SerializedName("OrgID")
    val orgID: String,
    @SerializedName("Orientation")
    val orientation: Int,
    @SerializedName("PostalCode")
    val postalCode: String,
    @SerializedName("ScansInPeriod")
    val scansInPeriod: Int,
    @SerializedName("State")
    val state: String,
    @SerializedName("Street")
    val street: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("UID")
    val uID: String,
    @SerializedName("UserID")
    val userID: String,
    @SerializedName("VIP")
    val vIP: Int,
    @SerializedName("VIPBy")
    val vIPBy: String,
    @SerializedName("VIPEndAt")
    val vIPEndAt: String,
    @SerializedName("VIPStartAt")
    val vIPStartAt: String,
    @SerializedName("Visits")
    val visits: Int,
    @SerializedName("Weight")
    val weight: String
) {

}
