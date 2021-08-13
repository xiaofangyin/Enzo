package com.enzo.commonlib.widget.picker.picker

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.RawRes
import com.enzo.commonlib.R
import com.enzo.commonlib.widget.picker.picker.ex.WheelAmPmView
import com.enzo.commonlib.widget.picker.picker.ex.WheelHourView
import com.enzo.commonlib.widget.picker.picker.ex.WheelMinuteView
import com.enzo.commonlib.widget.picker.picker.ex.WheelSecondView
import com.enzo.commonlib.widget.picker.picker.helper.TimePicker
import com.enzo.commonlib.widget.picker.picker.helper.TimePickerHelper
import com.enzo.commonlib.widget.picker.picker.helper.WheelPicker
import com.enzo.commonlib.widget.picker.picker.listener.AmPmTextHandler
import com.enzo.commonlib.widget.picker.picker.listener.OnTimeSelectedListener
import com.enzo.commonlib.widget.picker.wheel.WheelView
import com.enzo.commonlib.widget.picker.wheel.formatter.IntTextFormatter
import com.enzo.commonlib.widget.picker.wheel.listener.OnScrollChangedListener
import java.util.*

/**
 * 时间选择器 View
 *
 * @author zyyoona7
 */
class TimePickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : LinearLayout(context, attrs), TimePicker, WheelPicker {

    private val timePickerHelper: TimePickerHelper
    private var widthWeightMode = false
    private var amPmWeight: Float = 1f
    private var hourWeight: Float = 1f
    private var minuteWeight: Float = 1f
    private var secondWeight: Float = 1f
    private var is24Hour: Boolean = false
    private var isShowHour: Boolean = true
    private var isShowMinute: Boolean = true
    private var isShowSecond: Boolean = true

    init {
        val amPmWheel = WheelAmPmView(context)
        val hourWheel = WheelHourView(context)
        val minuteWheel = WheelMinuteView(context)
        val secondWheel = WheelSecondView(context)
        amPmWheel.id = R.id.wheel_am_pm
        hourWheel.id = R.id.wheel_hour
        minuteWheel.id = R.id.wheel_minute
        secondWheel.id = R.id.wheel_second
        timePickerHelper = TimePickerHelper(amPmWheel, hourWheel, minuteWheel, secondWheel)

        attrs?.let {
            initAttrs(context, it)
        }
        addViews(amPmWheel, hourWheel, minuteWheel, secondWheel)
        setShowHour(isShowHour)
        setShowMinute(isShowMinute)
        setShowSecond(isShowSecond)
        set24Hour(is24Hour)
        setMinuteTextFormatter(IntTextFormatter())
        setSecondTextFormatter(IntTextFormatter())
        setMaxTextWidthMeasureType(WheelView.MeasureType.MAX_LENGTH,
                WheelView.MeasureType.MAX_LENGTH,
                WheelView.MeasureType.SAME_WIDTH,
                WheelView.MeasureType.SAME_WIDTH)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimePickerView)
        is24Hour = typedArray.getBoolean(R.styleable.TimePickerView_tpv_is24Hour, TimePickerHelper.is24HourMode(context))
        widthWeightMode = typedArray.getBoolean(R.styleable.TimePickerView_tpv_widthWeightMode, false)
        amPmWeight = typedArray.getFloat(R.styleable.TimePickerView_tpv_amPmWeight, 1f)
        hourWeight = typedArray.getFloat(R.styleable.TimePickerView_tpv_hourWeight, 1f)
        minuteWeight = typedArray.getFloat(R.styleable.TimePickerView_tpv_minuteWeight, 1f)
        secondWeight = typedArray.getFloat(R.styleable.TimePickerView_tpv_secondWeight, 1f)
        isShowHour = typedArray.getBoolean(R.styleable.TimePickerView_tpv_showHour, true)
        isShowMinute = typedArray.getBoolean(R.styleable.TimePickerView_tpv_showMinute, true)
        isShowSecond = typedArray.getBoolean(R.styleable.TimePickerView_tpv_showSecond, true)
        setVisibleItems(typedArray.getInt(R.styleable.TimePickerView_tpv_visibleItems,
                WheelView.DEFAULT_VISIBLE_ITEM))
        setLineSpacing(typedArray.getDimensionPixelSize(R.styleable.TimePickerView_tpv_lineSpacing,
                WheelView.DEFAULT_LINE_SPACING))
        setCyclic(typedArray.getBoolean(R.styleable.TimePickerView_tpv_cyclic, true))
        setTextSize(typedArray.getDimensionPixelSize(R.styleable.TimePickerView_tpv_textSize,
                WheelView.DEFAULT_TEXT_SIZE))
        setTextAlign(WheelView.convertTextAlign(typedArray.getInt(R.styleable.TimePickerView_tpv_textAlign,
                WheelView.TEXT_ALIGN_CENTER)))
        setTextPadding(typedArray.getDimensionPixelSize(R.styleable.TimePickerView_tpv_textPadding,
                WheelView.DEFAULT_TEXT_PADDING))
        val amPmLeftText = typedArray.getText(R.styleable.TimePickerView_tpv_amPmLeftText)
                ?: ""
        val hourLeftText = typedArray.getText(R.styleable.TimePickerView_tpv_hourLeftText)
                ?: ""
        val minuteLeftText = typedArray.getText(R.styleable.TimePickerView_tpv_minuteLeftText)
                ?: ""
        val secondLeftText = typedArray.getText(R.styleable.TimePickerView_tpv_secondLeftText)
                ?: ""
        setLeftText(amPmLeftText, hourLeftText, minuteLeftText, secondLeftText)
        val amPmRightText = typedArray.getText(R.styleable.TimePickerView_tpv_amPmRightText)
                ?: ""
        val hourRightText = typedArray.getText(R.styleable.TimePickerView_tpv_hourRightText)
                ?: ""
        val minuteRightText = typedArray.getText(R.styleable.TimePickerView_tpv_minuteRightText)
                ?: ""
        val secondRightText = typedArray.getText(R.styleable.TimePickerView_tpv_secondRightText)
                ?: ""
        setRightText(amPmRightText, hourRightText, minuteRightText, secondRightText)
        setLeftTextSize(typedArray.getDimensionPixelSize(R.styleable.TimePickerView_tpv_leftTextSize,
                WheelView.DEFAULT_TEXT_SIZE))
        setRightTextSize(typedArray.getDimensionPixelSize(R.styleable.TimePickerView_tpv_rightTextSize,
                WheelView.DEFAULT_TEXT_SIZE))
        setLeftTextMarginRight(typedArray.getDimensionPixelSize(R.styleable.TimePickerView_tpv_leftTextMarginRight,
                WheelView.DEFAULT_TEXT_PADDING))
        setRightTextMarginLeft(typedArray.getDimensionPixelSize(R.styleable.TimePickerView_tpv_rightTextMarginLeft,
                WheelView.DEFAULT_TEXT_PADDING))
        setLeftTextColor(typedArray.getColor(R.styleable.TimePickerView_tpv_leftTextColor,
                WheelView.DEFAULT_SELECTED_TEXT_COLOR))
        setRightTextColor(typedArray.getColor(R.styleable.TimePickerView_tpv_rightTextColor,
                WheelView.DEFAULT_SELECTED_TEXT_COLOR))
        val leftGravity = typedArray.getInt(R.styleable.TimePickerView_tpv_leftTextGravity, 0)
        setLeftTextGravity(WheelView.covertExtraGravity(leftGravity))
        val rightGravity = typedArray.getInt(R.styleable.TimePickerView_tpv_rightTextGravity, 0)
        setRightTextGravity(WheelView.covertExtraGravity(rightGravity))
        setNormalTextColor(typedArray.getColor(R.styleable.TimePickerView_tpv_normalTextColor,
                WheelView.DEFAULT_NORMAL_TEXT_COLOR))
        setSelectedTextColor(typedArray.getColor(R.styleable.TimePickerView_tpv_selectedTextColor,
                WheelView.DEFAULT_SELECTED_TEXT_COLOR))
        setShowDivider(typedArray.getBoolean(R.styleable.TimePickerView_tpv_showDivider, false))
        setDividerType(WheelView.convertDividerType(typedArray.getInt(R.styleable.TimePickerView_tpv_dividerType,
                WheelView.DIVIDER_FILL)))
        setDividerColor(typedArray.getColor(R.styleable.TimePickerView_tpv_dividerColor,
                WheelView.DEFAULT_SELECTED_TEXT_COLOR))
        setDividerHeight(typedArray.getDimensionPixelSize(R.styleable.TimePickerView_tpv_dividerHeight,
                WheelView.DEFAULT_DIVIDER_HEIGHT))
        setWheelDividerPadding(typedArray.getDimensionPixelSize(R.styleable.TimePickerView_tpv_dividerPadding,
                WheelView.DEFAULT_TEXT_PADDING))
        setDividerOffsetY(typedArray.getDimensionPixelOffset(R.styleable.TimePickerView_tpv_dividerOffsetY, 0))
        setCurved(typedArray.getBoolean(R.styleable.TimePickerView_tpv_curved, true))
        setCurvedArcDirection(WheelView.convertCurvedArcDirection(typedArray.getInt(R.styleable.TimePickerView_tpv_curvedArcDirection,
                WheelView.CURVED_ARC_DIRECTION_CENTER)))
        setCurvedArcDirectionFactor(typedArray.getFloat(R.styleable.TimePickerView_tpv_curvedArcDirectionFactor,
                WheelView.DEFAULT_CURVED_FACTOR))
        setShowCurtain(typedArray.getBoolean(R.styleable.TimePickerView_tpv_showCurtain, false))
        setCurtainColor(typedArray.getColor(R.styleable.TimePickerView_tpv_curtainColor, Color.TRANSPARENT))
        typedArray.recycle()
    }

    private fun addViews(amPmView: WheelAmPmView, hourView: WheelHourView,
                         minuteView: WheelMinuteView, secondView: WheelSecondView) {
        orientation = HORIZONTAL
        val width = if (widthWeightMode) 0 else ViewGroup.LayoutParams.WRAP_CONTENT
        val amPmLp = LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        val hourLp = LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        val minuteLp = LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        val secondLp = LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        amPmLp.gravity = Gravity.CENTER_VERTICAL
        hourLp.gravity = Gravity.CENTER_VERTICAL
        minuteLp.gravity = Gravity.CENTER_VERTICAL
        secondLp.gravity = Gravity.CENTER_VERTICAL
        if (widthWeightMode) {
            amPmLp.weight = amPmWeight
            hourLp.weight = hourWeight
            minuteLp.weight = minuteWeight
            secondLp.weight = secondWeight
        }
        addView(amPmView, amPmLp)
        addView(hourView, hourLp)
        addView(minuteView, minuteLp)
        addView(secondView, secondLp)
    }

    override fun setAmPmTextHandler(textHandler: AmPmTextHandler) {
        timePickerHelper.setAmPmTextHandler(textHandler)
    }

    override fun setHourTextFormatter(textFormatter: IntTextFormatter) {
        timePickerHelper.setHourTextFormatter(textFormatter)
    }

    override fun setMinuteTextFormatter(textFormatter: IntTextFormatter) {
        timePickerHelper.setMinuteTextFormatter(textFormatter)
    }

    override fun setSecondTextFormatter(textFormatter: IntTextFormatter) {
        timePickerHelper.setSecondTextFormatter(textFormatter)
    }

    override fun setOnScrollChangedListener(listener: OnScrollChangedListener?) {
        timePickerHelper.setOnScrollChangedListener(listener)
    }

    override fun setOnTimeSelectedListener(listener: OnTimeSelectedListener?) {
        timePickerHelper.setOnTimeSelectedListener(listener)
    }

    override fun setTime(calendar: Calendar, is24Hour: Boolean) {
        timePickerHelper.setTime(calendar, is24Hour)
    }

    override fun setTime(hour: Int, minute: Int, second: Int, is24Hour: Boolean, isAm: Boolean) {
        timePickerHelper.setTime(hour, minute, second, is24Hour, isAm)
    }

    override fun setTimeFor24(hour: Int, minute: Int, second: Int) {
        timePickerHelper.setTimeFor24(hour, minute, second)
    }

    override fun setTimeFor12(hour: Int, minute: Int, second: Int, isAm: Boolean) {
        timePickerHelper.setTimeFor12(hour, minute, second, isAm)
    }

    override fun setShowHour(isShow: Boolean) {
        timePickerHelper.setShowHour(isShow)
    }

    override fun setShowMinute(isShow: Boolean) {
        timePickerHelper.setShowMinute(isShow)
    }

    override fun setShowSecond(isShow: Boolean) {
        timePickerHelper.setShowSecond(isShow)
    }

    override fun setAmPmMaxTextWidthMeasureType(measureType: WheelView.MeasureType) {
        timePickerHelper.setAmPmMaxTextWidthMeasureType(measureType)
    }

    override fun setHourMaxTextWidthMeasureType(measureType: WheelView.MeasureType) {
        timePickerHelper.setHourMaxTextWidthMeasureType(measureType)
    }

    override fun setMinuteMaxTextWidthMeasureType(measureType: WheelView.MeasureType) {
        timePickerHelper.setMinuteMaxTextWidthMeasureType(measureType)
    }

    override fun setSecondMaxTextWidthMeasureType(measureType: WheelView.MeasureType) {
        timePickerHelper.setSecondMaxTextWidthMeasureType(measureType)
    }

    override fun setMaxTextWidthMeasureType(measureType: WheelView.MeasureType) {
        timePickerHelper.setMaxTextWidthMeasureType(measureType)
    }

    override fun setMaxTextWidthMeasureType(amPmType: WheelView.MeasureType,
                                            hourType: WheelView.MeasureType,
                                            minuteType: WheelView.MeasureType,
                                            secondType: WheelView.MeasureType) {
        timePickerHelper.setMaxTextWidthMeasureType(amPmType, hourType, minuteType, secondType)
    }

    override fun setLeftText(amPmText: CharSequence, hourText: CharSequence,
                             minuteText: CharSequence, secondText: CharSequence) {
        timePickerHelper.setLeftText(amPmText, hourText, minuteText, secondText)
    }

    override fun setRightText(amPmText: CharSequence, hourText: CharSequence,
                              minuteText: CharSequence, secondText: CharSequence) {
        timePickerHelper.setRightText(amPmText, hourText, minuteText, secondText)
    }

    override fun getWheelAmPmView(): WheelAmPmView {
        return timePickerHelper.getWheelAmPmView()
    }

    override fun getWheelHourView(): WheelHourView {
        return timePickerHelper.getWheelHourView()
    }

    override fun getWheelMinuteView(): WheelMinuteView {
        return timePickerHelper.getWheelMinuteView()
    }

    override fun getWheelSecondView(): WheelSecondView {
        return timePickerHelper.getWheelSecondView()
    }

    override fun setVisibleItems(visibleItems: Int) {
        timePickerHelper.setVisibleItems(visibleItems)
    }

    override fun setLineSpacing(lineSpacingPx: Int) {
        timePickerHelper.setLineSpacing(lineSpacingPx)
    }

    override fun setLineSpacing(lineSpacingDp: Float) {
        timePickerHelper.setLineSpacing(lineSpacingDp)
    }

    override fun setCyclic(isCyclic: Boolean) {
        timePickerHelper.setCyclic(isCyclic)
    }

    override fun setTextSize(textSizePx: Int) {
        timePickerHelper.setTextSize(textSizePx)
    }

    override fun setTextSize(textSizeSp: Float) {
        timePickerHelper.setTextSize(textSizeSp)
    }

    override fun setAutoFitTextSize(autoFit: Boolean) {
        timePickerHelper.setAutoFitTextSize(autoFit)
    }

    override fun setMinTextSize(minTextSizePx: Int) {
        timePickerHelper.setMinTextSize(minTextSizePx)
    }

    override fun setMinTextSize(minTextSizeSp: Float) {
        timePickerHelper.setMinTextSize(minTextSizeSp)
    }

    override fun setTextAlign(textAlign: Paint.Align) {
        timePickerHelper.setTextAlign(textAlign)
    }

    override fun setNormalTextColor(@ColorInt textColor: Int) {
        timePickerHelper.setNormalTextColor(textColor)
    }

    override fun setNormalTextColorRes(@ColorRes textColorRes: Int) {
        timePickerHelper.setNormalTextColorRes(textColorRes)
    }

    override fun setSelectedTextColor(@ColorInt textColor: Int) {
        timePickerHelper.setSelectedTextColor(textColor)
    }

    override fun setSelectedTextColorRes(@ColorRes textColorRes: Int) {
        timePickerHelper.setSelectedTextColorRes(textColorRes)
    }

    override fun setTextPadding(paddingPx: Int) {
        timePickerHelper.setTextPadding(paddingPx)
    }

    override fun setTextPadding(paddingDp: Float) {
        timePickerHelper.setTextPadding(paddingDp)
    }

    override fun setTextPaddingLeft(textPaddingLeftPx: Int) {
        timePickerHelper.setTextPaddingLeft(textPaddingLeftPx)
    }

    override fun setTextPaddingLeft(textPaddingLeftDp: Float) {
        timePickerHelper.setTextPaddingLeft(textPaddingLeftDp)
    }

    override fun setTextPaddingRight(textPaddingRightPx: Int) {
        timePickerHelper.setTextPaddingRight(textPaddingRightPx)
    }

    override fun setTextPaddingRight(textPaddingRightDp: Float) {
        timePickerHelper.setTextPaddingRight(textPaddingRightDp)
    }

    override fun setTypeface(typeface: Typeface) {
        timePickerHelper.setTypeface(typeface)
    }

    override fun setTypeface(typeface: Typeface, isBoldForSelectedItem: Boolean) {
        timePickerHelper.setTypeface(typeface, isBoldForSelectedItem)
    }

    override fun setShowDivider(showDivider: Boolean) {
        timePickerHelper.setShowDivider(showDivider)
    }

    override fun setDividerColor(@ColorInt dividerColor: Int) {
        timePickerHelper.setDividerColor(dividerColor)
    }

    override fun setDividerColorRes(@ColorRes dividerColorRes: Int) {
        timePickerHelper.setDividerColorRes(dividerColorRes)
    }

    override fun setDividerHeight(dividerHeightPx: Int) {
        timePickerHelper.setDividerHeight(dividerHeightPx)
    }

    override fun setDividerHeight(dividerHeightDp: Float) {
        timePickerHelper.setDividerHeight(dividerHeightDp)
    }

    override fun setDividerType(dividerType: WheelView.DividerType) {
        timePickerHelper.setDividerType(dividerType)
    }

    override fun setWheelDividerPadding(paddingPx: Int) {
        timePickerHelper.setWheelDividerPadding(paddingPx)
    }

    override fun setWheelDividerPadding(paddingDp: Float) {
        timePickerHelper.setWheelDividerPadding(paddingDp)
    }

    override fun setDividerCap(cap: Paint.Cap) {
        timePickerHelper.setDividerCap(cap)
    }

    override fun setDividerOffsetY(offsetYPx: Int) {
        timePickerHelper.setDividerOffsetY(offsetYPx)
    }

    override fun setDividerOffsetY(offsetYDp: Float) {
        timePickerHelper.setDividerOffsetY(offsetYDp)
    }

    override fun setShowCurtain(showCurtain: Boolean) {
        timePickerHelper.setShowCurtain(showCurtain)
    }

    override fun setCurtainColor(@ColorInt curtainColor: Int) {
        timePickerHelper.setCurtainColor(curtainColor)
    }

    override fun setCurtainColorRes(@ColorRes curtainColorRes: Int) {
        timePickerHelper.setCurtainColorRes(curtainColorRes)
    }

    override fun setCurved(curved: Boolean) {
        timePickerHelper.setCurved(curved)
    }

    override fun setCurvedArcDirection(direction: WheelView.CurvedArcDirection) {
        timePickerHelper.setCurvedArcDirection(direction)
    }

    override fun setCurvedArcDirectionFactor(factor: Float) {
        timePickerHelper.setCurvedArcDirectionFactor(factor)
    }

    override fun setRefractRatio(ratio: Float) {
        timePickerHelper.setRefractRatio(ratio)
    }

    override fun setSoundEffect(soundEffect: Boolean) {
        timePickerHelper.setSoundEffect(soundEffect)
    }

    override fun setSoundResource(@RawRes soundRes: Int) {
        timePickerHelper.setSoundResource(soundRes)
    }

    override fun setSoundVolume(playVolume: Float) {
        timePickerHelper.setSoundVolume(playVolume)
    }

    override fun setResetSelectedPosition(reset: Boolean) {
        timePickerHelper.setResetSelectedPosition(reset)
    }

    override fun setLeftText(text: CharSequence) {
        timePickerHelper.setLeftText(text)
    }

    override fun setRightText(text: CharSequence) {
        timePickerHelper.setRightText(text)
    }

    override fun setLeftTextSize(textSizePx: Int) {
        timePickerHelper.setLeftTextSize(textSizePx)
    }

    override fun setLeftTextSize(textSizeSp: Float) {
        timePickerHelper.setLeftTextSize(textSizeSp)
    }

    override fun setRightTextSize(textSizePx: Int) {
        timePickerHelper.setRightTextSize(textSizePx)
    }

    override fun setRightTextSize(textSizeSp: Float) {
        timePickerHelper.setRightTextSize(textSizeSp)
    }

    override fun setLeftTextColor(@ColorInt color: Int) {
        timePickerHelper.setLeftTextColor(color)
    }

    override fun setLeftTextColorRes(@ColorRes colorRes: Int) {
        timePickerHelper.setLeftTextColorRes(colorRes)
    }

    override fun setRightTextColor(@ColorInt color: Int) {
        timePickerHelper.setRightTextColor(color)
    }

    override fun setRightTextColorRes(@ColorRes colorRes: Int) {
        timePickerHelper.setRightTextColorRes(colorRes)
    }

    override fun setLeftTextMarginRight(marginRightPx: Int) {
        timePickerHelper.setLeftTextMarginRight(marginRightPx)
    }

    override fun setLeftTextMarginRight(marginRightDp: Float) {
        timePickerHelper.setLeftTextMarginRight(marginRightDp)
    }

    override fun setRightTextMarginLeft(marginLeftPx: Int) {
        timePickerHelper.setRightTextMarginLeft(marginLeftPx)
    }

    override fun setRightTextMarginLeft(marginLeftDp: Float) {
        timePickerHelper.setRightTextMarginLeft(marginLeftDp)
    }

    override fun setLeftTextGravity(gravity: Int) {
        timePickerHelper.setLeftTextGravity(gravity)
    }

    override fun setRightTextGravity(gravity: Int) {
        timePickerHelper.setRightTextGravity(gravity)
    }

    override fun set24Hour(is24Hour: Boolean) {
        timePickerHelper.set24Hour(is24Hour)
    }

    override fun is24Hour(): Boolean {
        return timePickerHelper.is24Hour()
    }

    override fun isAm(): Boolean {
        return timePickerHelper.isAm()
    }

    override fun getSelectedHour(): Int {
        return timePickerHelper.getSelectedHour()
    }

    override fun getSelectedMinute(): Int {
        return timePickerHelper.getSelectedMinute()
    }

    override fun getSelectedSecond(): Int {
        return timePickerHelper.getSelectedSecond()
    }
}