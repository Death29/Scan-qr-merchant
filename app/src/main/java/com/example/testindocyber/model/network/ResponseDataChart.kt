package com.example.testindocyber.model.network

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

sealed class DataChart{
	@Parcelize
	data class DonutChartData(
		@field:SerializedName("data")
		val data: List<DataDonutChart>?
	): DataChart(), Parcelable

	@Parcelize
	data class LineChartData(
		@field:SerializedName("data")
		val data: DataLineChart
	): DataChart(), Parcelable
}

@Parcelize
data class DataDonutChart(
	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("percentage")
	val percentage: String,

	@field:SerializedName("data")
	val listDataDonut: List<DetailDataDonut>?
):Parcelable

@Parcelize
data class DetailDataDonut(
	@field:SerializedName("trx_date")
	val trxDate: String,

	@field:SerializedName("nominal")
	val nominal: Long
):Parcelable

@Parcelize
data class DataLineChart(
	@field:SerializedName("month")
	val month: List<Int>?
):Parcelable