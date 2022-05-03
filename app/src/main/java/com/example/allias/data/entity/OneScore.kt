package com.example.allias.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "score_table")
data class OneScore(
    @PrimaryKey(autoGenerate = true)
    var scoreId: Long = 0L,

    @ColumnInfo(name = "time_score")
    var time: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "total_score")
    var totalScore: Int = 0
): Parcelable