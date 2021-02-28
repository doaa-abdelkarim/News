package com.example.news.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName

data class News(

	@field:SerializedName("totalResults")
	var totalResults: Int? = null,

	@field:SerializedName("articles")
	var articles: List<Article?>? = null

): BaseResponse()

@Entity(
	tableName = "article",
	foreignKeys = [ForeignKey(
		entity = Source::class,
		parentColumns = arrayOf("id"),
		childColumns = arrayOf("sourceId"),
		onDelete = ForeignKey.CASCADE
	)]
)
data class Article (

	@ColumnInfo
	@field:SerializedName("publishedAt")
	var publishedAt: String? = null,

	@ColumnInfo
	@field:SerializedName("author")
	var author: String? = null,

	@ColumnInfo
	@field:SerializedName("urlToImage")
	var urlToImage: String? = null,

	@ColumnInfo
	@field:SerializedName("description")
	var description: String? = null,

	@Ignore
	@field:SerializedName("source")
	var source: Source? = null,

	@ColumnInfo
	@field:SerializedName("title")
	var title: String? = null,

	@ColumnInfo
	@field:SerializedName("url")
	var url: String? = null,

	@ColumnInfo
	@field:SerializedName("content")
	var content: String? = null,

	@ColumnInfo
	var sourceId:String? = null,

	@ColumnInfo
	@PrimaryKey(autoGenerate = true)
	var id:Int? = null

): Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		null,
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(publishedAt)
		parcel.writeString(author)
		parcel.writeString(urlToImage)
		parcel.writeString(description)
		parcel.writeString(title)
		parcel.writeString(url)
		parcel.writeString(content)
		parcel.writeString(sourceId)
		parcel.writeValue(id)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Article> {
		override fun createFromParcel(parcel: Parcel): Article {
			return Article(parcel)
		}

		override fun newArray(size: Int): Array<Article?> {
			return arrayOfNulls(size)
		}
	}

}