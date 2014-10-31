package com.aniwaredev.ellipse;

import android.util.Log;

public class MovingObject {

	private int height;
	private int width;
	private int quarterHeight;
	private int threeQuarterHeight;
	private int halfWidth;
	private int y;
	private int x;
	private int count = -10;
	private double angle = 0;
	private int sizeHero;
	private int margingSide;
	private int margingUpDown;
	private int halfHeight;

	public MovingObject(int h, int w, int x, int y,int sizeHero, int margingSide, int margingUpDown, int reverse){
		height = h;
		width = w;
		quarterHeight = height/4;
		count = -10;
		angle = 0 + reverse*Math.PI;
		threeQuarterHeight = height-height/4;
		halfHeight = height/2;
		halfWidth = width/2;
		this.x=x;
		this.y=y;
		this.margingSide = margingSide;
		this.sizeHero = sizeHero;
		this.margingUpDown = margingUpDown;
	}

	public boolean move(boolean jump, int i, float speed, int sizeJump) {
		if(jump && count == -10){
			count = 8;}
		if(angle>= 360){
			angle -=360;
		}
		if (x >= margingSide+sizeHero && x <=margingSide+sizeHero+100 && y >=quarterHeight && y<=threeQuarterHeight) {
			y += speed*5;
			if( count >= 0){
				x+=sizeJump;count--;}
			if( count < 0 && count > -10){
				x-=sizeJump;count--;}
		}

		if (x >= width-margingSide-sizeHero-100 && x <= width-margingSide-sizeHero && y >=quarterHeight && y<=threeQuarterHeight) {
			y -= speed*5;
			if( count >= 0){
				x-=sizeJump;count--;
				}
			if( count < 0 && count > -10){
				x+=sizeJump;count--;
				}
		}
		if (y>=threeQuarterHeight) {
				
			if(count == -10){
				x = (int) (halfWidth- (halfWidth -margingSide-sizeHero)*Math.cos(angle));
				y = (int) (threeQuarterHeight-margingUpDown+ (quarterHeight -margingSide-sizeHero)*Math.sin(angle));
			}
			if( count >= 0){
				x = (int) (halfWidth- (halfWidth -margingSide-sizeHero-(9-count)*sizeJump)*Math.cos(angle));
				y = (int) (threeQuarterHeight-margingUpDown+ (quarterHeight -margingSide-sizeHero-(9-count)*sizeJump)*Math.sin(angle));
				count--;
				}
			
			if( count < 0 && count > -10){
				x = (int) (halfWidth- (halfWidth -margingSide-sizeHero-(9+count)*sizeJump)*Math.cos(angle));
				y = (int) (threeQuarterHeight-margingUpDown+ (quarterHeight -margingSide-sizeHero-(10+count)*sizeJump)*Math.sin(angle));
				count--;
				}
			angle+=speed*Math.PI/128;
		}
		if (y<=quarterHeight) {
			if(count == -10){
				x = (int) (halfWidth- (halfWidth -margingSide-sizeHero)*Math.cos(angle));
				y = (int) (quarterHeight-margingUpDown+ (quarterHeight -margingSide-sizeHero)*Math.sin(angle));
			}
			if( count >= 0){
				x = (int) (halfWidth- (halfWidth -margingSide-sizeHero-(9-count)*sizeJump)*Math.cos(angle));
				y = (int) (quarterHeight-margingUpDown+ (quarterHeight -margingSide-sizeHero-(9-count)*sizeJump)*Math.sin(angle));
				count--;
				}
		
			if( count < 0 && count > -10){
				x = (int) (halfWidth- (halfWidth -margingSide-sizeHero-(9+count)*sizeJump)*Math.cos(angle));
				y = (int) (quarterHeight-margingUpDown+ (quarterHeight -margingSide-sizeHero-(10+count)*sizeJump)*Math.sin(angle));
				count--;
				}
			angle+=speed*Math.PI/128;
		}
		if (jump && count ==-10){
			jump = ! jump;
		}
		Log.e("hjhj", angle + "hjh" );
		return jump;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}
}
