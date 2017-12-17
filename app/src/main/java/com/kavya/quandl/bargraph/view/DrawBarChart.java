package com.kavya.quandl.bargraph.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.kavya.quandl.bargraph.utils.AppUtils;

import java.util.ArrayList;

public class DrawBarChart extends View {

	private ArrayList<Float> values;
	private Paint textPaint;
	private Paint fgPaint;
	private Rect rect;
	private int barWidth;
	private int bottomTextDescent;
	private int topMargin;
	private int bottomTextHeight;
	private ArrayList<String> bottomTextList = new ArrayList<String>();
	private int MINI_BAR_WIDTH;
	private int BAR_SIDE_MARGIN;
	private int TEXT_TOP_MARGIN;

	public DrawBarChart(Context context) {
		super(context);
	}

	public DrawBarChart(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		fgPaint = new Paint();
		int FOREGROUND_COLOR = Color.parseColor("#FC496D");
		fgPaint.setColor(FOREGROUND_COLOR);
		rect = new Rect();
		topMargin = AppUtils.dip2px(context, 150);
		int textSize = AppUtils.sp2px(context, 15);
		barWidth = AppUtils.dip2px(context, 22);
		MINI_BAR_WIDTH = AppUtils.dip2px(context, 22);
		BAR_SIDE_MARGIN = AppUtils.dip2px(context, 22);
		TEXT_TOP_MARGIN = AppUtils.dip2px(context, 5);
		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		int TEXT_COLOR = Color.parseColor("#9B9A9B");
		textPaint.setColor(TEXT_COLOR);
		textPaint.setTextSize(textSize);
		textPaint.setTextAlign(Paint.Align.CENTER);
	}

	public DrawBarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setGraphAttributes(int textColor, int graphColor) {
		textPaint.setColor(textColor);
		fgPaint.setColor(graphColor);
	}

	/**
	 * dataList will be reset when method is called.
	 *
	 * @param bottomStringList The String ArrayList in the bottom.
	 */
	public void setBottomTextList(ArrayList<String> bottomStringList) {
		this.bottomTextList = bottomStringList;
		Rect r = new Rect();
		bottomTextDescent = 0;
		barWidth = MINI_BAR_WIDTH;
		for (String s : bottomTextList) {
			textPaint.getTextBounds(s, 0, s.length(), r);
			if (bottomTextHeight < r.height()) {
				bottomTextHeight = r.height();
			}
			if (barWidth < r.width()) {
				barWidth = r.width();
			}
			if (bottomTextDescent < (Math.abs(r.bottom))) {
				bottomTextDescent = Math.abs(r.bottom);
			}
		}
		setMinimumWidth(2);
		postInvalidate();
	}

	/**
	 * @param list The ArrayList of Float with the range of [0-max].
	 */
	public void setDataList(ArrayList<Float> list, int max) {
		values = new ArrayList<Float>();
		if (max == 0) max = 1;

		for (Float f : list) {
			values.add(1 -  f / (float) max);
		}
		setMinimumWidth(2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int i = 1;
		if (values != null && !values.isEmpty()) {
			for (Float f : values) {
				rect.set(BAR_SIDE_MARGIN * i + barWidth * (i - 1),
						topMargin + (int) ((getHeight() - topMargin - bottomTextHeight - TEXT_TOP_MARGIN) * values.get(i - 1)),
						(BAR_SIDE_MARGIN + barWidth) * i,
						getHeight() - bottomTextHeight - TEXT_TOP_MARGIN);
				canvas.drawRect(rect, fgPaint);
				i++;
			}
		}

		if (bottomTextList != null && !bottomTextList.isEmpty()) {
			i = 1;
			for (String s : bottomTextList) {
				canvas.drawText(s, BAR_SIDE_MARGIN * i + barWidth * (i - 1) + barWidth / 2,
						getHeight() - bottomTextDescent, textPaint);
				i++;
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mViewWidth = measureWidth(widthMeasureSpec);
		int mViewHeight = measureHeight(heightMeasureSpec);
		setMeasuredDimension(mViewWidth, mViewHeight);
	}

	private int measureWidth(int measureSpec) {
		int preferred = 0;
		if (bottomTextList != null) {
			preferred = bottomTextList.size() * (barWidth + BAR_SIDE_MARGIN);
		}
		return getMeasurement(measureSpec, preferred);
	}

	private int measureHeight(int measureSpec) {
		int preferred = 222;
		return getMeasurement(measureSpec, preferred);
	}

	private int getMeasurement(int measureSpec, int preferred) {
		int specSize = MeasureSpec.getSize(measureSpec);
		int measurement;
		switch (MeasureSpec.getMode(measureSpec)) {
			case MeasureSpec.EXACTLY:
				measurement = specSize;
				break;
			case MeasureSpec.AT_MOST:
				measurement = Math.min(preferred, specSize);
				break;
			default:
				measurement = preferred;
				break;
		}
		return measurement;
	}

}
