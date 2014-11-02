package com.aniwaredev.ellipse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
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
	private boolean jump2 = false;
	private int count=-10;
	private int sizeJump=10;
	private MovingObject hero;
	private MovingObject hero2;
	private Rect rect1;
	private Rect rect2;
	
	

	public DrawingPanel(Context context) {
		super(context);
		gameLoopThread = new PanelThread(this);

		setOnTouchListener(new OnTouchListener() {


			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getY() >= threeQuarterHeight){
				if(jump)
					return true;
				return jump = !jump;
				}
				else if(event.getY() <= quarterHeight){
					if(jump2)
						return true;
					return jump2 = !jump2;
				}
				return true;
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
		if(x == 0){
			x =1;
			height = getHeight();
			width = getWidth();
			quarterHeight = height/4;
			threeQuarterHeight = height-height/4;
			halfHeight = height/2;
			halfWidth = width/2;
			hero = new MovingObject(height,width,margingSide+sizeHero,quarterHeight,margingSide,sizeHero,margingUpDown,0);
			hero2 = new MovingObject(height,width,width-margingSide-sizeHero,threeQuarterHeight,margingSide,sizeHero,margingUpDown,1);
			rect1 = new Rect(width - 40, halfHeight, width - 10, halfHeight - 10);
			rect2 = new Rect(10, halfHeight+20,40, halfHeight+10);
		}
		
		jump = hero.move(jump,100,speed,sizeJump);
		jump2 = hero2.move(jump2,100,speed,sizeJump);
		
		drawBackground(canvas);
		
		drawNumber(canvas);

		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.STROKE);

		canvas.drawRect(rect1, paint);
		canvas.drawRect(rect2, paint);
		
		paint.setColor(Color.WHITE);
		canvas.drawCircle(hero.getX(),hero.getY(),sizeHero,paint);
		canvas.drawCircle(hero2.getX(),hero2.getY(),sizeHero,paint);
		
//		if(x+sizeHero >= width-40 &&( y-sizeHero<=halfHeight && y-sizeHero>=halfHeight -10 || y+sizeHero<=halfHeight && y+sizeHero>=halfHeight -10)) {
//			
//		}
	}

	private void drawNumber(Canvas canvas) {
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.YELLOW);
		paint.setTextSize(width/2);
		canvas.drawText("1", width/2-width/8, height/2+height/8, paint);
	}

	private void drawBackground(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(10);
		canvas.drawLine(margingSide, quarterHeight, margingSide,threeQuarterHeight, paint);
		canvas.drawLine(width-margingSide, quarterHeight, width-margingSide, threeQuarterHeight, paint);
		canvas.drawArc(new RectF(margingSide, halfHeight, width-margingSide, height-margingUpDown), -180, -180,false,paint);
		canvas.drawArc(new RectF(margingSide, margingUpDown, width-margingSide, halfHeight), 180, 180,false,paint);
	}

}
