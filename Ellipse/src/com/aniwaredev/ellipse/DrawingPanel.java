package com.aniwaredev.ellipse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class DrawingPanel extends SurfaceView {
	private SurfaceHolder holder;
	private PanelThread gameLoopThread;
	private int x = 0;
	private int y = 0;
	private int margingSide = 10;
	private int margingUpDown = 5;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private double angle = 0;
	private int width;
	private int height;
	private int quarterHeight;
	private int threeQuarterHeight;
	private int halfHeight;
	private int halfWidth;
	private int sizeHero = 20;
	private float speed = 2;
	private boolean jump = false;
	private int count=-7;

	public DrawingPanel(Context context) {
		super(context);
		gameLoopThread = new PanelThread(this);
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(jump)
					return;
				jump = !jump;
				
			}
		});
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while (retry) {
					try {
						gameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) {
					}
				}
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {

			}

		});

		//bmp = BitmapFactory.decodeResource(getResources(),R.drawable.chargedown);

	}

	@Override
	public void onDraw(Canvas canvas) {
		if(x == 0 && y ==0){
			height = getHeight();
			width = getWidth();
			quarterHeight = height/4;
			threeQuarterHeight = height-height/4;
			halfHeight = height/2;
			halfWidth = width/2;
			x=margingSide+sizeHero;
			y=quarterHeight;
			
		}
		if(jump && count == -7){
			count = 5;}
		if(angle>= 360){
			angle-=360;
		}
		if (x >= margingSide+sizeHero && x <=margingSide+sizeHero+60 && y >=quarterHeight && y<=threeQuarterHeight) {
			y += speed*5;
			if( count >= 0){
				x+=10;count--;}
			if( count < 0 && count > -7){
				x-=10;count--;}
		}

		if (x >= width-margingSide-sizeHero-60 && x <= width-margingSide-sizeHero && y >=quarterHeight && y<=threeQuarterHeight) {
			y -= speed*5;
			if( count >= 0){
				x-=10;count--;}
			if( count < 0 && count > -7){
				x+=10;count--;}
		}
		if (y>=threeQuarterHeight) {
			x = (int) (halfWidth- (halfWidth -margingSide-sizeHero)*Math.cos(angle));
			y = (int) (threeQuarterHeight-margingUpDown+ (quarterHeight -margingSide-sizeHero)*Math.sin(angle));
			angle+=speed*Math.PI/128;
		}
		if (y<=quarterHeight) {
			x = (int) (halfWidth- (halfWidth -margingSide-sizeHero)*Math.cos(angle));
			y = (int) (quarterHeight-margingUpDown+ (quarterHeight -margingSide-sizeHero)*Math.sin(angle));
			angle+=speed*Math.PI/128;
		}
		if (jump && count ==-7)		{
		jump = ! jump}
		canvas.drawColor(Color.BLACK);
		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(10);
		canvas.drawLine(margingSide, quarterHeight, margingSide,threeQuarterHeight, paint);
		canvas.drawLine(width-margingSide, quarterHeight, width-margingSide, threeQuarterHeight, paint);
		canvas.drawArc(new RectF(margingSide, halfHeight, width-margingSide, height-margingUpDown), -180, -180,false,paint);
		canvas.drawArc(new RectF(margingSide, margingUpDown, width-margingSide, halfHeight), 180, 180,false,paint);
		
		paint.setColor(Color.WHITE);
		canvas.drawCircle(x,y,sizeHero,paint);
	}

}
