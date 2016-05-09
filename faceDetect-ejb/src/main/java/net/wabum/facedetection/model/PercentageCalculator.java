package net.wabum.facedetection.model;
//version 1.1

import org.opencv.core.Point;
import org.opencv.core.Rect;

public class PercentageCalculator {
	private Rect[] eyes;
	private Rect[] faces;

	public PercentageCalculator(){}

	public PercentageCalculator(Rect[] e, Rect[] f) {
		this.eyes = e;
		this.faces = f;
	}

	public double getPercentage(){
		return eyesWithFacePoint();
	}

	private double eyesWithFacePoint(){
		int eif = eyesInsideFaces(faces, eyes);
		double point =  faces.length * 40 + 5 * eyes.length + 10 * eif;
		if(point > 100)	point = 100;
		return point;
	}

	public int eyesInsideFaces(Rect[] faces, Rect[] eyes){
		int eif = 0;
		for (Rect rect : faces)
			eif += eyesInsideAFace(rect, eyes);
		return eif;
	}

	public int eyesInsideAFace(Rect face, Rect[] eyes){
		int toRet = 0;
		for (Rect rectE : eyes)
			if (rectOverRect(face, rectE)) 
				toRet++;
		return toRet;
	}

	private boolean rectOverRect(Rect bigger, Rect smaller){
		return smaller.area() < bigger.area() && pointInToRect(smaller.tl(), bigger) && pointInToRect(smaller.br(), bigger);
	}

	private boolean pointInToRect(Point point, Rect rect){
		return point.x >= rect.x && point.y >= rect.y && point.x <= rect.br().x && point.y <= rect.br().y;
	}

	public void setFaces(Rect[] f){
		this.faces = f;
	}

	public void setEyes(Rect[] e){
		this.eyes = e;
	}
}