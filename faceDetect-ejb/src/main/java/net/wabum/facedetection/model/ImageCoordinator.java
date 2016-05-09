package net.wabum.facedetection.model;
//vresione 1
import org.opencv.core.Mat;

public class ImageCoordinator {
	public Image real;
	public Image rotated;
	public Detector fDec;
	public Detector eDec;
	public PercentageCalculator percent;

	public ImageCoordinator(){
		this.fDec = new Detector("/ssd-data/ebarba/src/opencv-3.1.0/data/haarcascades/haarcascade_frontalface_default.xml");
		this.eDec = new Detector("/ssd-data/ebarba/src/opencv-3.1.0/data/haarcascades/haarcascade_eye.xml");
		this.percent = new PercentageCalculator();
		this.real = new Image();
		this.rotated = new Image();
	}

	public void setImage(Mat image){
		if(image != null){
			System.out.println("settato");
			//this.releaseResources();
			setImageDetector(image);
			this.real.setImage(image, this.eDec.getObject(), this.fDec.getObject());
		}
	}

	private void setImageDetector(Mat image){
		this.fDec.setImage(image);
		this.eDec.setImage(image);
		fDec.detectObj();
		eDec.detectObj();
	}

	public void rotatedImageInitializer(){
		Mat rotated = real.rotateImage();
		setImageDetector(rotated);
		this.rotated.setImage(rotated, this.eDec.getObject(), this.fDec.getObject());
	}

	public double getPercentage(Image i){
		if(i != null){
			percent.setEyes(i.eyes);
			percent.setFaces(i.faces);
			return percent.getPercentage();
		}
		else return 0;
	}

	public Mat detect(){
		System.out.println(getPercentage(real));
		Mat ret = real.bestFaceMarker();
		if ((ret == null || percent.eyesInsideFaces(real.eyes, real.faces) <= 1) && real.pairFound()) {
			this.rotatedImageInitializer();
			System.out.println(getPercentage(rotated));
			if (rotated.bestFaceMarker() != null && getPercentage(real) < getPercentage(rotated)){
				if(ret != null) ret.release();
				ret = rotated.bestFaceMarker();
			}
		}
		return ret;
	}

	public InternalDto detectFace(){
		Image winner = real;
		InternalDto toRet = new InternalDto();
		Mat ret = real.bestFaceMarker();
		if ((ret == null || percent.eyesInsideFaces(real.eyes, real.faces) <= 1) && real.pairFound()) {
			this.rotatedImageInitializer();
			System.out.println(getPercentage(rotated));
			if (rotated.bestFaceMarker() != null && getPercentage(real) < getPercentage(rotated)){
				if(ret != null) ret.release();
				ret = rotated.bestFaceMarker();
				winner = rotated;
			}
		}
		toRet.setFaceNumber(winner.faces.length);
		toRet.setFace(winner.getBestFace());
		toRet.setPercentage(getPercentage(winner));
		return toRet;
	}

	public void releaseResources(){
		if (real != null) {this.real.release();}
		if (rotated != null) {this.rotated.release();}
		this.eDec.release();
		this.fDec.release();
	}

	public void release(){
		if (real != null) {this.real.release(); this.real = null;}
		if (rotated != null) {this.rotated.release(); this.rotated = null;}
	}
}
