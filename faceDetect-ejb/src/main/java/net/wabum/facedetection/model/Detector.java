package net.wabum.facedetection.model;
// vresione 1

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

public class Detector {
	private Mat image;
	private String haarcasc;
	private boolean objectFound;
	private Rect[] object;
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
			this.getObjectDetector().detectMultiScale(image,objectDetections);
			this.object = objectDetections.toArray();
			this.objectFound = object.length > 0;
			objectDetections.release();
	}
	
	public void setImage(Mat image){
		if(this.image != null) this.image.release();
		this.image = image;
	}

	public boolean objFound(){
		return this.objectFound;
	}

	public Rect[] getObject(){
		return this.object;
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
