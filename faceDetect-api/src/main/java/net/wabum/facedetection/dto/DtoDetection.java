package net.wabum.facedetection.dto;

import java.io.Serializable;

public class DtoDetection implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1012738837472589237L;
	
	private Rect face;
	private double percentage;
	private int faceNumber;
	
	public Rect getFace() {
		return face;
	}
	public void setFace(Rect face) {
		this.face = face;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double d) {
		this.percentage = d;
	}
	public int getFaceNumber() {
		return faceNumber;
	}
	public void setFaceNumber(int faceNumber) {
		this.faceNumber = faceNumber;
	}
}
