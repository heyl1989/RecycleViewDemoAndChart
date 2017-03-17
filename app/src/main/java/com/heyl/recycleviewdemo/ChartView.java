package com.heyl.recycleviewdemo;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ChartView extends View {
	public int XPoint;// 原点的X坐标
	public int YPoint; // 原点的Y坐标
	public int XScale; // X的刻度长度
	public int YScale; // Y的刻度长度
	public int XLength; // X轴的长度
	public int YLength; // Y轴的长度
	public String[] XLabel; // X的刻度
	public String[] YLabel = {"30","90","150"}; // Y的刻度
	public String[] Data; // 数据
	private int textsize;//轴文字大小
	private int YstopX;//Y轴刻度x方向结束偏移量
	private int YtextX;//Y轴文字x坐标
	private int Y30 ;

	public ChartView(Activity context) {
		super(context);
		XPoint = DP2PX.dip2px(context, 15);
		YPoint = DP2PX.dip2px(context, 120);
		XLength = context.getWindowManager().getDefaultDisplay().getWidth() - DP2PX.dip2px(context, 60);
		YLength = DP2PX.dip2px(context, 120);
		textsize = DP2PX.dip2px(context, 10);
		YstopX = DP2PX.dip2px(context, 5);
		YtextX = DP2PX.dip2px(context, 12);
		Y30 = DP2PX.dip2px(context,60);
	}

	public void setInfo(int xnum, String[] XLabels, String[] AllData) {
		XScale = XLength / xnum;
		YScale = YLength / 2;
		XLabel = XLabels;
		Data = AllData;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);// 重写onDraw方法

		// canvas.drawColor(Color.WHITE);//设置背景颜色
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);// 去锯齿
		paint.setColor(Color.BLACK);// 颜色
		paint.setTextSize(textsize); // 设置轴文字大小

		Paint paint1 = new Paint();
		paint1.setStyle(Paint.Style.STROKE);
		paint1.setAntiAlias(true);// 去锯齿
		paint1.setColor(Color.RED);

		// 设置Y轴
		canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint); // 轴线
		for (int i = 0; i * YScale < YLength; i++) {
			canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + YstopX, YPoint - i * YScale, paint); // 刻度
			try {
				canvas.drawText(YLabel[i], XPoint - YtextX, YPoint - i * YScale + 5, paint); // 文字
			} catch (Exception e) {
			}
		}

		// 设置X轴
		canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, paint); // 轴线
		canvas.drawLine(XPoint, YPoint-Y30, XPoint + XLength, YPoint-Y30, paint); // 平均线
		for (int i = 0; i * XScale < XLength; i++) {
			canvas.drawLine(XPoint + i * XScale, YPoint, XPoint + i * XScale, YPoint - YstopX, paint); // 刻度
			try {
				canvas.drawText(XLabel[i], XPoint + i * XScale - 10, YPoint + YtextX, paint); // 文字
				// 数据值
				if (i > 0 && YCoord(Data[i - 1]) != -999 && YCoord(Data[i]) != -999) // 保证有效数据
					canvas.drawLine(XPoint + (i - 1) * XScale, YCoord(Data[i - 1]), XPoint + i * XScale,
							YCoord(Data[i]), paint1);
				canvas.drawCircle(XPoint + i * XScale, YCoord(Data[i]), 5, paint1);// 圆圈
			} catch (Exception e) {
			}
		}
	}

	private int YCoord(String y0) // 计算绘制时的Y坐标，无数据时返回-999
	{
		int y;
		try {
			y = Integer.parseInt(y0);
		} catch (Exception e) {
			return -999; // 出错则返回-999
		}
		try {
			return YPoint - y * YScale / Integer.parseInt(YLabel[1]);
		} catch (Exception e) {
		}
		return y;
	}
}
