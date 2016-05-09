package net.wabum.facedetection.model;
//vresione 1
import org.opencv.core.Point;
import org.opencv.core.Rect;

class Eyes{
	Rect sx;
	Rect dx;

	public Eyes(Rect eyes1,Rect eyes2){
		if(eyes1.x > eyes2.x){
			sx = eyes2;
			dx = eyes1;
		}
		else {
			sx = eyes1;
			dx = eyes2;
		}
	}

	public Rect getSx(){
		return this.sx;
	}

	public Rect getDx(){
		return this.dx;
	}
	
	public double eyesCorner(){
		double x = dx.x - sx.x;
		double y = dx.y - sx.y;
		double conv = 57.33;
		return (Math.asin(y/(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)))))*conv;
	}
	
	public Point eyesCenter(){
		Point eCent = new Point((this.dx.x + this.sx .x)/2, (this.dx.y + this.sx.y)/2);
		return eCent;
	}
	
	
	public Rect[] getEyes(){
		Rect[] toRet = new Rect[2];
		toRet[0] = this.dx;
		toRet[1] = this.sx;
		return toRet;
	}
}

