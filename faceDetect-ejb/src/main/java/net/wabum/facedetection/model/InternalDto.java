package net.wabum.facedetection.model;

import java.lang.reflect.InvocationTargetException;

import org.opencv.core.Rect;

import net.wabum.facedetection.dto.DtoDetection;

public class InternalDto {
	private Rect face;
	private double percentage;
	private int faceNumber;

	public DtoDetection convertToSerializable() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		net.wabum.facedetection.dto.Rect rect = new net.wabum.facedetection.dto.Rect(face.x, face.y, face.width, face.height);
		DtoDetection dto = new DtoDetection();
		dto.setFace(rect);
		dto.setPercentage(percentage);
		dto.setFaceNumber(faceNumber);
		return dto;
	}
	
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
