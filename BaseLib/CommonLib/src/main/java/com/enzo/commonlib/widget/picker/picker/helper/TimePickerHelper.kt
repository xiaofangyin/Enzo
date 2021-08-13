package com.enzo.commonlib.widget.picker.picker.helper

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.text.format.DateFormat
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.RawRes
import com.enzo.commonlib.widget.picker.picker.ex.WheelAmPmView
import com.enzo.commonlib.widget.picker.picker.ex.WheelHourView
import com.enzo.commonlib.widget.picker.picker.ex.WheelMinuteView
import com.enzo.commonlib.widget.picker.picker.ex.WheelSecondView
import com.enzo.commonlib.widget.picker.wheel.WheelView
import com.enzo.commonlib.widget.picker.wheel.adapter.ArrayWheelAdapter
import com.enzo.commonlib.widget.picker.wheel.formatter.IntTextFormatter
import com.enzo.commonlib.widget.picker.picker.listener.AmPmTextHandler
import com.enzo.commonlib.widget.picker.picker.listener.OnAmPmChangedListener
import com.enzo.commonlib.widget.picker.picker.listener.OnTimeSelectedListener
import com.enzo.commonlib.widget.picker.wheel.listener.OnItemSelectedListener
import com.enzo.commonlib.widget.picker.wheel.listener.OnScrollChangedListener
import java.util.*

class TimePickerHelper(private var wheelAmPmView: WheelAmPmView?,
                       private var wheelHourView: WheelHourView?,
                       private var wheelMinuteView: WheelMinuteView?,
                       private var wheelSecondView: WheelSecondView?)
    : OnItemSelectedListener, OnScrollChangedListener, OnAmPmChangedListener, TimePicker, WheelPicker {

    private var scrollChangedListener: OnScrollChangedListener? = null
    private var hourTextFormatter: IntTextFormatter? = null
    private var timeSelectedListener: OnTimeSelectedListener? = null

    init {
        wheelAmPmView?.setOnItemSelectedListener(this)
        wheelHourView?.setOnItemSelectedListener(this)
        wheelMinuteView?.setOnItemSelectedListener(this)
        wheelSecondView?.setOnItemSelectedListener(this)

        wheelAmPmView?.setOnScrollChangedListener(this)
        wheelHourView?.setOnScrollChangedListener(this)
        wheelMinuteView?.setOnScrollChangedListener(this)
        wheelSecondView?.setOnScrollChangedListener(this)

        wheelHourView?.setOnAmPmChangedListener(this)
    }

    companion object {

        fun is24HourMode(context: Context): Boolean {
            return DateFormat.is24HourFormat(context)
        }
    }

    override fun onItemSelected(wheelView: WheelView, adapter: ArrayWheelAdapter<*>, position: Int) {
        val amPmId = wheelView.id
        wheelAmPmView?.let {
            if (it.id == amPmId) {
                wheelHourView?.hourType = if (position == 0) WheelHourView.HourType.AM else WheelHourView.HourType.PM
            }
        }
        val is24Hour = wheelHourView?.is24Hour ?: false
        val hour = wheelHourView?.getAdapter()?.getSelectedItem<Int>() ?: 0
        val isAm = wheelHourView?.hourType == WheelHourView.HourType.AM
        val minute = wheelMinuteView?.getAdapter()?.getSelectedItem<Int>() ?: 0
        val second = wheelSecondView?.getAdapter()?.getSelectedItem<Int>() ?: 0
        timeSelectedListener?.onTimeSelected(is24Hour, hour, minute, second, isAm)
    }

    override fun onScrollChanged(wheelView: WheelView, scrollOffsetY: Int) {
        scrollChangedListener?.onScrollChanged(wheelView, scrollOffsetY)
    }

    override fun onScrollStateChanged(wheelView: WheelView, state: Int) {
        scrollChangedListener?.onScrollStateChanged(wheelView, state)
    }

    override fun onAmPmChanged(wheelHourView: WheelHourView, isAm: Boolean) {
        wheelAmPmView?.setSelectedPosition(if (isAm) 0 else 1, true)
    }

    override fun setAmPmTextHandler(textHandler: AmPmTextHandler) {
        wheelAmPmView?.amPmTextHandler = textHandler
    }

    override fun setHourTextFormatter(textFormatter: IntTextFormatter) {
        this.hourTextFormatter = textFormatter
        wheelHourView?.setTextFormatter(textFormatter)
    }

    override fun setMinuteTextFormatter(textFormatter: IntTextFormatter) {
        wheelMinuteView?.setTextFormatter(textFormatter)
    }

    override fun setSecondTextFormatter(textFormatter: IntTextFormatter) {
        wheelSecondView?.setTextFormatter(textFormatter)
    }

    override fun setOnScrollChangedListener(listener: OnScrollChangedListener?) {
        scrollChangedListener = listener
    }

    override fun setOnTimeSelectedListener(listener: OnTimeSelectedListener?) {
        timeSelectedListener = listener
    }

    override fun setTime(calendar: Calendar, is24Hour: Boolean) {
        val hourFor24 = calendar.get(Calendar.HOUR_OF_DAY)
        val hourFor12 = calendar.get(Calendar.HOUR)
        val amPm = calendar.get(Calendar.AM_PM)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        if (is24Hour) {
            setTimeFor24(hourFor24, minute, second)
        } else {
            setTimeFor12(hourFor12, minute, second, amPm == Calendar.AM)
        }
    }

    override fun setTime(hour: Int, minute: Int, second: Int, is24Hour: Boolean, isAm: Boolean) {
        val calendar = Calendar.getInstance()
        if (is24Hour) {
            calendar.set(Calendar.HOUR_OF_DAY, hour)
        } else {
            calendar.set(Calendar.HOUR, hour)
        }
        calendar.set(Calendar.AM_PM, if (isAm) Calendar.AM else Calendar.PM)
        calendar.set(Calendar.MINUTE,minute)
        calendar.set(Calendar.SECOND,second)
        setTime(calendar,is24Hour)
    }

    override fun setTimeFor24(hour: Int, minute: Int, second: Int) {
        val is24Hour = wheelHourView?.is24Hour ?: true
        if (!is24Hour) {
            set24Hour(true)
        }
        wheelHourView?.setSelectedHour(hour)
        wheelMinuteView?.setSelectedMinute(minute)
        wheelSecondView?.setSelectedSecond(second)
    }

    override fun setTimeFor12(hour: Int, minute: Int, second: Int, isAm: Boolean) {
        val is24Hour = wheelHourView?.is24Hour ?: true
        if (is24Hour) {
            set24Hour(false)
        }
        wheelAmPmView?.setSelectedPosition(if (isAm) 0 else 1)
        wheelHourView?.setSelectedHour(hour)
        wheelMinuteView?.setSelectedMinute(minute)
        wheelSecondView?.setSelectedSecond(second)
    }

    override fun setShowHour(isShow: Boolean) {
        val visibility = if (isShow) View.VISIBLE else View.GONE
        wheelHourView?.visibility = visibility
        wheelAmPmView?.visibility = if (isShow && wheelHourView?.is24Hour == false) {
            View.VISIBLE
        } else {
            visibility
        }
    }

    override fun setShowMinute(isShow: Boolean) {
        wheelMinuteView?.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun setShowSecond(isShow: Boolean) {
        wheelSecondView?.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun setAmPmMaxTextWidthMeasureType(measureType: WheelView.MeasureType) {
        wheelAmPmView?.maxTextWidthMeasureType = measureType
    }

    override fun setHourMaxTextWidthMeasureType(measureType: WheelView.MeasureType) {
        wheelHourView?.maxTextWidthMeasureType = measureType
    }

    override fun setMinuteMaxTextWidthMeasureType(measureType: WheelView.MeasureType) {
        wheelMinuteView?.maxTextWidthMeasureType = measureType
    }

    override fun setSecondMaxTextWidthMeasureType(measureType: WheelView.MeasureType) {
        wheelSecondView?.maxTextWidthMeasureType = measureType
    }

    override fun setMaxTextWidthMeasureType(measureType: WheelView.MeasureType) {
        setMaxTextWidthMeasureType(measureType, measureType, measureType, measureType)
    }

    override fun setMaxTextWidthMeasureType(amPmType: WheelView.MeasureType,
                                            hourType: WheelView.MeasureType,
                                            minuteType: WheelView.MeasureType,
                                            secondType: WheelView.MeasureType) {
        setAmPmMaxTextWidthMeasureType(amPmType)
        setHourMaxTextWidthMeasureType(hourType)
        setMinuteMaxTextWidthMeasureType(minuteType)
        setSecondMaxTextWidthMeasureType(secondType)
    }

    override fun getWheelAmPmView(): WheelAmPmView {
        require(wheelAmPmView != null) {
            "WheelAmPmView is null."
        }
        return wheelAmPmView!!
    }

    override fun getWheelHourView(): WheelHourView {
        require(wheelHourView != null) {
            "WheelHourView is null."
        }
        return wheelHourView!!
    }

    override fun getWheelMinuteView(): WheelMinuteView {
        require(wheelMinuteView != null) {
            "WheelMinuteView is null."
        }
        return wheelMinuteView!!
    }

    override fun getWheelSecondView(): WheelSecondView {
        require(wheelSecondView != null) {
            "WheelSecondView is null."
        }
        return wheelSecondView!!
    }

    override fun setVisibleItems(visibleItems: Int) {
        wheelAmPmView?.visibleItems = visibleItems
        wheelHourView?.visibleItems = visibleItems
        wheelMinuteView?.visibleItems = visibleItems
        wheelSecondView?.visibleItems = visibleItems
    }

    override fun setLineSpacing(lineSpacingPx: Int) {
        wheelAmPmView?.lineSpacing = lineSpacingPx
        wheelHourView?.lineSpacing = lineSpacingPx
        wheelMinuteView?.lineSpacing = lineSpacingPx
        wheelSecondView?.lineSpacing = lineSpacingPx
    }

    override fun setLineSpacing(lineSpacingDp: Float) {
        wheelAmPmView?.setLineSpacing(lineSpacingDp)
        wheelHourView?.setLineSpacing(lineSpacingDp)
        wheelMinuteView?.setLineSpacing(lineSpacingDp)
        wheelSecondView?.setLineSpacing(lineSpacingDp)
    }

    override fun setCyclic(isCyclic: Boolean) {
//        amPmWheel?.isCyclic = isCyclic
        wheelHourView?.isCyclic = isCyclic
        wheelMinuteView?.isCyclic = isCyclic
        wheelSecondView?.isCyclic = isCyclic
    }

    override fun setTextSize(textSizePx: Int) {
        wheelAmPmView?.textSize = textSizePx
        wheelHourView?.textSize = textSizePx
        wheelMinuteView?.textSize = textSizePx
        wheelSecondView?.textSize = textSizePx
    }

    override fun setTextSize(textSizeSp: Float) {
        wheelAmPmView?.setTextSize(textSizeSp)
        wheelHourView?.setTextSize(textSizeSp)
        wheelMinuteView?.setTextSize(textSizeSp)
        wheelSecondView?.setTextSize(textSizeSp)
    }

    override fun setAutoFitTextSize(autoFit: Boolean) {
        wheelAmPmView?.isAutoFitTextSize = autoFit
        wheelHourView?.isAutoFitTextSize = autoFit
        wheelMinuteView?.isAutoFitTextSize = autoFit
        wheelSecondView?.isAutoFitTextSize = autoFit
    }

    override fun setMinTextSize(minTextSizePx: Int) {
        wheelAmPmView?.minTextSize = minTextSizePx
        wheelHourView?.minTextSize = minTextSizePx
        wheelMinuteView?.minTextSize = minTextSizePx
        wheelSecondView?.minTextSize = minTextSizePx
    }

    override fun setMinTextSize(minTextSizeSp: Float) {
        wheelAmPmView?.setMinTextSize(minTextSizeSp)
        wheelHourView?.setMinTextSize(minTextSizeSp)
        wheelMinuteView?.setMinTextSize(minTextSizeSp)
        wheelSecondView?.setMinTextSize(minTextSizeSp)
    }

    override fun setTextAlign(textAlign: Paint.Align) {
        wheelAmPmView?.textAlign = textAlign
        wheelHourView?.textAlign = textAlign
        wheelMinuteView?.textAlign = textAlign
        wheelSecondView?.textAlign = textAlign
    }

    override fun setNormalTextColor(@ColorInt textColor: Int) {
        wheelAmPmView?.normalTextColor = textColor
        wheelHourView?.normalTextColor = textColor
        wheelMinuteView?.normalTextColor = textColor
        wheelSecondView?.normalTextColor = textColor
    }

    override fun setNormalTextColorRes(@ColorRes textColorRes: Int) {
        wheelAmPmView?.setNormalTextColorRes(textColorRes)
        wheelHourView?.setNormalTextColorRes(textColorRes)
        wheelMinuteView?.setNormalTextColorRes(textColorRes)
        wheelSecondView?.setNormalTextColorRes(textColorRes)
    }

    override fun setSelectedTextColor(@ColorInt textColor: Int) {
        wheelAmPmView?.selectedTextColor = textColor
        wheelHourView?.selectedTextColor = textColor
        wheelMinuteView?.selectedTextColor = textColor
        wheelSecondView?.selectedTextColor = textColor
    }

    override fun setSelectedTextColorRes(@ColorRes textColorRes: Int) {
        wheelAmPmView?.setSelectedTextColorRes(textColorRes)
        wheelHourView?.setSelectedTextColorRes(textColorRes)
        wheelMinuteView?.setSelectedTextColorRes(textColorRes)
        wheelSecondView?.setSelectedTextColorRes(textColorRes)
    }

    override fun setTextPadding(paddingPx: Int) {
        wheelAmPmView?.textPaddingLeft = paddingPx
        wheelAmPmView?.textPaddingRight = paddingPx
        wheelHourView?.textPaddingLeft = paddingPx
        wheelHourView?.textPaddingRight = paddingPx
        wheelMinuteView?.textPaddingLeft = paddingPx
        wheelMinuteView?.textPaddingRight = paddingPx
        wheelSecondView?.textPaddingLeft = paddingPx
        wheelSecondView?.textPaddingRight = paddingPx
    }

    override fun setTextPadding(paddingDp: Float) {
        wheelAmPmView?.setTextPadding(paddingDp)
        wheelHourView?.setTextPadding(paddingDp)
        wheelMinuteView?.setTextPadding(paddingDp)
        wheelSecondView?.setTextPadding(paddingDp)
    }

    override fun setTextPaddingLeft(textPaddingLeftPx: Int) {
        wheelAmPmView?.textPaddingLeft = textPaddingLeftPx
        wheelHourView?.textPaddingLeft = textPaddingLeftPx
        wheelMinuteView?.textPaddingLeft = textPaddingLeftPx
        wheelSecondView?.textPaddingLeft = textPaddingLeftPx
    }

    override fun setTextPaddingLeft(textPaddingLeftDp: Float) {
        wheelAmPmView?.setTextPaddingLeft(textPaddingLeftDp)
        wheelHourView?.setTextPaddingLeft(textPaddingLeftDp)
        wheelMinuteView?.setTextPaddingLeft(textPaddingLeftDp)
        wheelSecondView?.setTextPaddingLeft(textPaddingLeftDp)
    }

    override fun setTextPaddingRight(textPaddingRightPx: Int) {
        wheelAmPmView?.textPaddingRight = textPaddingRightPx
        wheelHourView?.textPaddingRight = textPaddingRightPx
        wheelMinuteView?.textPaddingRight = textPaddingRightPx
        wheelSecondView?.textPaddingRight = textPaddingRightPx
    }

    override fun setTextPaddingRight(textPaddingRightDp: Float) {
        wheelAmPmView?.setTextPaddingRight(textPaddingRightDp)
        wheelHourView?.setTextPaddingRight(textPaddingRightDp)
        wheelMinuteView?.setTextPaddingRight(textPaddingRightDp)
        wheelSecondView?.setTextPaddingRight(textPaddingRightDp)
    }

    override fun setTypeface(typeface: Typeface) {
        wheelAmPmView?.setTypeface(typeface, false)
        wheelHourView?.setTypeface(typeface, false)
        wheelMinuteView?.setTypeface(typeface, false)
        wheelSecondView?.setTypeface(typeface, false)
    }

    override fun setTypeface(typeface: Typeface, isBoldForSelectedItem: Boolean) {
        wheelAmPmView?.setTypeface(typeface, isBoldForSelectedItem)
        wheelHourView?.setTypeface(typeface, isBoldForSelectedItem)
        wheelMinuteView?.setTypeface(typeface, isBoldForSelectedItem)
        wheelSecondView?.setTypeface(typeface, isBoldForSelectedItem)
    }

    override fun setShowDivider(showDivider: Boolean) {
        wheelAmPmView?.isShowDivider = showDivider
        wheelHourView?.isShowDivider = showDivider
        wheelMinuteView?.isShowDivider = showDivider
        wheelSecondView?.isShowDivider = showDivider
    }

    override fun setDividerColor(@ColorInt dividerColor: Int) {
        wheelAmPmView?.dividerColor = dividerColor
        wheelHourView?.dividerColor = dividerColor
        wheelMinuteView?.dividerColor = dividerColor
        wheelSecondView?.dividerColor = dividerColor
    }

    override fun setDividerColorRes(@ColorRes dividerColorRes: Int) {
        wheelAmPmView?.setDividerColorRes(dividerColorRes)
        wheelHourView?.setDividerColorRes(dividerColorRes)
        wheelMinuteView?.setDividerColorRes(dividerColorRes)
        wheelSecondView?.setDividerColorRes(dividerColorRes)
    }

    override fun setDividerHeight(dividerHeightPx: Int) {
        wheelAmPmView?.dividerHeight = dividerHeightPx
        wheelHourView?.dividerHeight = dividerHeightPx
        wheelMinuteView?.dividerHeight = dividerHeightPx
        wheelSecondView?.dividerHeight = dividerHeightPx
    }

    override fun setDividerHeight(dividerHeightDp: Float) {
        wheelAmPmView?.setDividerHeight(dividerHeightDp)
        wheelHourView?.setDividerHeight(dividerHeightDp)
        wheelMinuteView?.setDividerHeight(dividerHeightDp)
        wheelSecondView?.setDividerHeight(dividerHeightDp)
    }

    override fun setDividerType(dividerType: WheelView.DividerType) {
        wheelAmPmView?.dividerType = dividerType
        wheelHourView?.dividerType = dividerType
        wheelMinuteView?.dividerType = dividerType
        wheelSecondView?.dividerType = dividerType
    }

    override fun setWheelDividerPadding(paddingPx: Int) {
        wheelAmPmView?.dividerPadding = paddingPx
        wheelHourView?.dividerPadding = paddingPx
        wheelMinuteView?.dividerPadding = paddingPx
        wheelSecondView?.dividerPadding = paddingPx
    }

    override fun setWheelDividerPadding(paddingDp: Float) {
        wheelAmPmView?.setDividerPadding(paddingDp)
        wheelHourView?.setDividerPadding(paddingDp)
        wheelMinuteView?.setDividerPadding(paddingDp)
        wheelSecondView?.setDividerPadding(paddingDp)
    }

    override fun setDividerCap(cap: Paint.Cap) {
        wheelAmPmView?.dividerCap = cap
        wheelHourView?.dividerCap = cap
        wheelMinuteView?.dividerCap = cap
        wheelSecondView?.dividerCap = cap
    }

    override fun setDividerOffsetY(offsetYPx: Int) {
        wheelAmPmView?.dividerOffsetY = offsetYPx
        wheelHourView?.dividerOffsetY = offsetYPx
        wheelMinuteView?.dividerOffsetY = offsetYPx
        wheelSecondView?.dividerOffsetY = offsetYPx
    }

    override fun setDividerOffsetY(offsetYDp: Float) {
        wheelAmPmView?.setDividerOffsetY(offsetYDp)
        wheelHourView?.setDividerOffsetY(offsetYDp)
        wheelMinuteView?.setDividerOffsetY(offsetYDp)
        wheelSecondView?.setDividerOffsetY(offsetYDp)
    }

    override fun setShowCurtain(showCurtain: Boolean) {
        wheelAmPmView?.isShowCurtain = showCurtain
        wheelHourView?.isShowCurtain = showCurtain
        wheelMinuteView?.isShowCurtain = showCurtain
        wheelSecondView?.isShowCurtain = showCurtain
    }

    override fun setCurtainColor(@ColorInt curtainColor: Int) {
        wheelAmPmView?.curtainColor = curtainColor
        wheelHourView?.curtainColor = curtainColor
        wheelMinuteView?.curtainColor = curtainColor
        wheelSecondView?.curtainColor = curtainColor
    }

    override fun setCurtainColorRes(@ColorRes curtainColorRes: Int) {
        wheelAmPmView?.setCurtainColorRes(curtainColorRes)
        wheelHourView?.setCurtainColorRes(curtainColorRes)
        wheelMinuteView?.setCurtainColorRes(curtainColorRes)
        wheelSecondView?.setCurtainColorRes(curtainColorRes)
    }

    override fun setCurved(curved: Boolean) {
        wheelAmPmView?.isCurved = curved
        wheelHourView?.isCurved = curved
        wheelMinuteView?.isCurved = curved
        wheelSecondView?.isCurved = curved
    }

    override fun setCurvedArcDirection(direction: WheelView.CurvedArcDirection) {
        wheelAmPmView?.curvedArcDirection = direction
        wheelHourView?.curvedArcDirection = direction
        wheelMinuteView?.curvedArcDirection = direction
        wheelSecondView?.curvedArcDirection = direction
    }

    override fun setCurvedArcDirectionFactor(factor: Float) {
        wheelAmPmView?.curvedArcDirectionFactor = factor
        wheelHourView?.curvedArcDirectionFactor = factor
        wheelMinuteView?.curvedArcDirectionFactor = factor
        wheelSecondView?.curvedArcDirectionFactor = factor
    }

    override fun setRefractRatio(ratio: Float) {
        wheelAmPmView?.refractRatio = ratio
        wheelHourView?.refractRatio = ratio
        wheelMinuteView?.refractRatio = ratio
        wheelSecondView?.refractRatio = ratio
    }

    override fun setSoundEffect(soundEffect: Boolean) {
        wheelAmPmView?.isSoundEffect = soundEffect
        wheelHourView?.isSoundEffect = soundEffect
        wheelMinuteView?.isSoundEffect = soundEffect
        wheelSecondView?.isSoundEffect = soundEffect
    }

    override fun setSoundResource(@RawRes soundRes: Int) {
        wheelAmPmView?.setSoundResource(soundRes)
        wheelHourView?.setSoundResource(soundRes)
        wheelMinuteView?.setSoundResource(soundRes)
        wheelSecondView?.setSoundResource(soundRes)
    }

    override fun setSoundVolume(playVolume: Float) {
        wheelAmPmView?.setSoundVolume(playVolume)
        wheelHourView?.setSoundVolume(playVolume)
        wheelMinuteView?.setSoundVolume(playVolume)
        wheelSecondView?.setSoundVolume(playVolume)
    }

    override fun setResetSelectedPosition(reset: Boolean) {
        wheelAmPmView?.isResetSelectedPosition = reset
        wheelHourView?.isResetSelectedPosition = reset
        wheelMinuteView?.isResetSelectedPosition = reset
        wheelSecondView?.isResetSelectedPosition = reset
    }

    override fun setLeftText(text: CharSequence) {
        setLeftText(text, text, text, text)
    }

    override fun setLeftText(amPmText: CharSequence, hourText: CharSequence,
                             minuteText: CharSequence, secondText: CharSequence) {
        wheelAmPmView?.leftText = amPmText
        wheelHourView?.leftText = hourText
        wheelMinuteView?.leftText = minuteText
        wheelSecondView?.leftText = secondText
    }

    override fun setRightText(text: CharSequence) {
        setRightText(text, text, text, text)
    }

    override fun setRightText(amPmText: CharSequence, hourText: CharSequence,
                              minuteText: CharSequence, secondText: CharSequence) {
        wheelAmPmView?.rightText = amPmText
        wheelHourView?.rightText = hourText
        wheelMinuteView?.rightText = minuteText
        wheelSecondView?.rightText = secondText
    }

    override fun setLeftTextSize(textSizePx: Int) {
        wheelAmPmView?.leftTextSize = textSizePx
        wheelHourView?.leftTextSize = textSizePx
        wheelMinuteView?.leftTextSize = textSizePx
        wheelSecondView?.leftTextSize = textSizePx
    }

    override fun setLeftTextSize(textSizeSp: Float) {
        wheelAmPmView?.setLeftTextSize(textSizeSp)
        wheelHourView?.setLeftTextSize(textSizeSp)
        wheelMinuteView?.setLeftTextSize(textSizeSp)
        wheelSecondView?.setLeftTextSize(textSizeSp)
    }

    override fun setRightTextSize(textSizePx: Int) {
        wheelAmPmView?.rightTextSize = textSizePx
        wheelHourView?.rightTextSize = textSizePx
        wheelMinuteView?.rightTextSize = textSizePx
        wheelSecondView?.rightTextSize = textSizePx
    }

    override fun setRightTextSize(textSizeSp: Float) {
        wheelAmPmView?.setRightTextSize(textSizeSp)
        wheelHourView?.setRightTextSize(textSizeSp)
        wheelMinuteView?.setRightTextSize(textSizeSp)
        wheelSecondView?.setRightTextSize(textSizeSp)
    }

    override fun setLeftTextColor(@ColorInt color: Int) {
        wheelAmPmView?.leftTextColor = color
        wheelHourView?.leftTextColor = color
        wheelMinuteView?.leftTextColor = color
        wheelSecondView?.leftTextColor = color
    }

    override fun setLeftTextColorRes(@ColorRes colorRes: Int) {
        wheelAmPmView?.setLeftTextColorRes(colorRes)
        wheelHourView?.setLeftTextColorRes(colorRes)
        wheelMinuteView?.setLeftTextColorRes(colorRes)
        wheelSecondView?.setLeftTextColorRes(colorRes)
    }

    override fun setRightTextColor(@ColorInt color: Int) {
        wheelAmPmView?.rightTextColor = color
        wheelHourView?.rightTextColor = color
        wheelMinuteView?.rightTextColor = color
        wheelSecondView?.rightTextColor = color
    }

    override fun setRightTextColorRes(@ColorRes colorRes: Int) {
        wheelAmPmView?.setRightTextColorRes(colorRes)
        wheelHourView?.setRightTextColorRes(colorRes)
        wheelMinuteView?.setRightTextColorRes(colorRes)
        wheelSecondView?.setRightTextColorRes(colorRes)
    }

    override fun setLeftTextMarginRight(marginRightPx: Int) {
        wheelAmPmView?.leftTextMarginRight = marginRightPx
        wheelHourView?.leftTextMarginRight = marginRightPx
        wheelMinuteView?.leftTextMarginRight = marginRightPx
        wheelSecondView?.leftTextMarginRight = marginRightPx
    }

    override fun setLeftTextMarginRight(marginRightDp: Float) {
        wheelAmPmView?.setLeftTextMarginRight(marginRightDp)
        wheelHourView?.setLeftTextMarginRight(marginRightDp)
        wheelMinuteView?.setLeftTextMarginRight(marginRightDp)
        wheelSecondView?.setLeftTextMarginRight(marginRightDp)
    }

    override fun setRightTextMarginLeft(marginLeftPx: Int) {
        wheelAmPmView?.rightTextMarginLeft = marginLeftPx
        wheelHourView?.rightTextMarginLeft = marginLeftPx
        wheelMinuteView?.rightTextMarginLeft = marginLeftPx
        wheelSecondView?.rightTextMarginLeft = marginLeftPx
    }

    override fun setRightTextMarginLeft(marginLeftDp: Float) {
        wheelAmPmView?.setRightTextMarginLeft(marginLeftDp)
        wheelHourView?.setRightTextMarginLeft(marginLeftDp)
        wheelMinuteView?.setRightTextMarginLeft(marginLeftDp)
        wheelSecondView?.setRightTextMarginLeft(marginLeftDp)
    }

    override fun setLeftTextGravity(gravity: Int) {
        wheelAmPmView?.leftTextGravity = gravity
        wheelHourView?.leftTextGravity = gravity
        wheelMinuteView?.leftTextGravity = gravity
        wheelSecondView?.leftTextGravity = gravity
    }

    override fun setRightTextGravity(gravity: Int) {
        wheelAmPmView?.rightTextGravity = gravity
        wheelHourView?.rightTextGravity = gravity
        wheelMinuteView?.rightTextGravity = gravity
        wheelSecondView?.rightTextGravity = gravity
    }

    override fun set24Hour(is24Hour: Boolean) {
        val isHourVisible = wheelHourView?.visibility == View.VISIBLE
        wheelAmPmView?.visibility = if (is24Hour) View.GONE else
            (if (isHourVisible) View.VISIBLE else View.GONE)
        wheelHourView?.is24Hour = is24Hour
        if (hourTextFormatter == null) {
            wheelHourView?.setTextFormatter(
                    if (is24Hour) IntTextFormatter()
                    else IntTextFormatter(IntTextFormatter.SINGLE_INT_FORMAT))
        }
    }

    override fun is24Hour(): Boolean {
        return wheelHourView?.is24Hour ?: false
    }

    override fun isAm(): Boolean {
        return wheelAmPmView?.isAm() ?: false
    }

    override fun getSelectedHour(): Int {
        return getWheelHourView().getSelectedItem<Int>() ?: 12
    }

    override fun getSelectedMinute(): Int {
        return getWheelMinuteView().getSelectedItem<Int>() ?: 0
    }

    override fun getSelectedSecond(): Int {
        return getWheelSecondView().getSelectedItem<Int>() ?: 0
    }
}