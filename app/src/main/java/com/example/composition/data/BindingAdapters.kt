package com.example.composition.data

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult

interface OnOptionClickListener {

    fun onOptionClick(option: Int)
}

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(tv: TextView, count: Int) {
    tv.text = String.format(
        tv.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("score")
fun bindScore(tv: TextView, score: Int) {
    tv.text = String.format(
        tv.context.getString(R.string.score_answers),
        score
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequirePercentage(tv: TextView, percentage: Int) {
    tv.text = String.format(
        tv.context.getString(R.string.required_percentage),
        percentage
    )
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(tv: TextView, gameResult: GameResult) {
    tv.text = String.format(
        tv.context.getString(R.string.score_percentage),
        getPercentOfRightAnswers(gameResult)
    )
}

@BindingAdapter("resultEmoji")
fun bindResultEmoji(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(getSmileResId(winner))
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(tv: TextView, enough: Boolean) {
    tv.setTextColor(getColorByState(tv.context, enough))
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, enough: Boolean) {
    val color = getColorByState(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(tv: TextView, number: Number) {
    tv.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(tv: TextView, clickListener: OnOptionClickListener) {
    tv.setOnClickListener {
        clickListener.onOptionClick(tv.text.toString().toInt())
    }
}

private fun getPercentOfRightAnswers(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}

private fun getSmileResId(winner: Boolean): Int {
    return if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }
}

private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}
