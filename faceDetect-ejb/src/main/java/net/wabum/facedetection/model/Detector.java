package net.wabum.facedetection.model;
// version 1.1

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

public class Detector {
	private Mat image;
	private String haarcasc;
	private boolean objectFound;
	private Rect[] objects;
	private CascadeClassifier objectDetector;
	
	public Detector(){
		this.objectFound = false;
	}
	
	public Detector(String ha) {
		this.haarcasc = ha;
		objectFound = false;
		this.objectDetector = new CascadeClassifier(haarcasc);
	}

	public void detectObj(){
			MatOfRect objectDetections = new MatOfRect();
			this.getObjectDetector().detectMultiScale(image, objectDetections);
			this.objects = objectDetections.toArray();
			this.objectFound = objects.length > 0;
			objectDetections.release();
	}
	
	public void setImage(Mat image){
		if(this.image != null) this.image.release();
		this.image = image;
	}

	public boolean objFound(){
		return this.objectFound;
	}

	public Rect[] getObjects(){
		return this.objects;
	}

	public Mat getImage(){
		return this.image;
	}

	public CascadeClassifier getObjectDetector() {
		return objectDetector;
	}

	public void setObjectDetector(CascadeClassifier objectDetector) {
		this.objectDetector = objectDetector;
	}
	
	public void release(){
		if (this.image != null) 
			this.image.release();
	}

}
