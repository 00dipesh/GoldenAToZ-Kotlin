package com.goldendigitech.goldenatoz.ProductView

import android.os.Parcel
import android.os.Parcelable

data class ProductviewModel(
    val productname: String,
    val pweight: String,
    val pqty: String,
    val storename: String,
    val imagePath: Int,
    val Specialty: String,
    val Volume: String,
    val IngredientType: String,
    val Brand: String,
    val StorageInstructions: String,
    val PackageInformation: String,
    val Manufacturer: String,
    val Itempartnumber: String,
    val NetQuantity: String,
    val ProductDimensions: String,
    val Flavour: String,
    val IncludedComponents: String,
    var quantity: Int = 1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productname)
        parcel.writeString(pweight)
        parcel.writeString(pqty)
        parcel.writeString(storename)
        parcel.writeInt(imagePath)
        parcel.writeString(Specialty)
        parcel.writeString(Volume)
        parcel.writeString(IngredientType)
        parcel.writeString(Brand)
        parcel.writeString(StorageInstructions)
        parcel.writeString(PackageInformation)
        parcel.writeString(Manufacturer)
        parcel.writeString(Itempartnumber)
        parcel.writeString(NetQuantity)
        parcel.writeString(ProductDimensions)
        parcel.writeString(Flavour)
        parcel.writeString(IncludedComponents)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductviewModel> {
        override fun createFromParcel(parcel: Parcel): ProductviewModel {
            return ProductviewModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductviewModel?> {
            return arrayOfNulls(size)
        }
    }
}
