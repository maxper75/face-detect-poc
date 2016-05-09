package net.wabum.facedetection.model;
//version 1.1
import org.opencv.core.Mat;

public class ImageCoordinator {
	public Image real;
	public Image rotated;
	public Detector faceDetector;
	public Detector eyesDetector;
	public PercentageCalculator percent;

	public ImageCoordinator(){
		this.faceDetector = new Detector("/ssd-data/ebarba/src/opencv-3.1.0/data/haarcascades/haarcascade_frontalface_default.xml");
		this.eyesDetector = new Detector("/ssd-data/ebarba/src/opencv-3.1.0/data/haarcascades/haarcascade_eye.xml");
		this.percent = new PercentageCalculator();
		this.real = new Image();
		this.rotated = new Image();
	}

	public void setImage(Mat image){
		if(image != null){
			this.releaseResources();
			setImageDetector(image);
			this.real.setImage(image, this.eyesDetector.getObjects(), this.faceDetector.getObjects());
		}
	}

	private void rotatedImageInitializer(){
		Mat rotated = real.rotateImage();
		setImageDetector(rotated);
		this.rotated.setImage(rotated, this.eyesDetector.getObjects(), this.faceDetector.getObjects());
	}

	private void setImageDetector(Mat image){
		this.faceDetector.setImage(image);
		this.eyesDetector.setImage(image);
		faceDetector.detectObj();
		eyesDetector.detectObj();
	}
	
	/**
	 * Return a data transfer object containing The best face found by the Rect , the number of faces found 
	 * and the percentage of a correct detection   
	 * @return dtoReturn: dto containing the Rect, the faces found and the percentage
	 */
	public InternalDto detectFace(){
		Image winner = real;
		InternalDto dtoReturned = new InternalDto();
		if (percent.eyesInsideFaces(real.eyes, real.faces) < 2 && real.pairFound()){
			this.rotatedImageInitializer();
			if (getPercentage(real) < getPercentage(rotated))
				winner = rotated;
		}
		dtoReturned.setFaceNumber(winner.faces.length);
		dtoReturned.setFace(winner.getBestFace());
		dtoReturned.setPercentage(getPercentage(winner));
		return dtoReturned;
	}

	/**
	 * Return a data transfer object containing The best face found by the Rect , the number of faces found
	 * and the percentage of a correct detection 
	 * @param image: the absolute path to the image
	 * @return dtoReturn: dto containing the Rect, the faces found and the percentage
	 */
	public InternalDto detectFace(Mat image){
		this.setImage(image);
		return this.detectFace();
	}

	private double getPercentage(Image i){
		if(i != null){
			percent.setEyes(i.eyes);
			percent.setFaces(i.faces);
			return percent.getPercentage();
		}
		else return 0;
	}

	/**
	 * release all Mat object linked with this class
	 */
	public void releaseResources(){
		if (real != null) {this.real.release();}
		if (rotated != null) {this.rotated.release();}
		this.eyesDetector.release();
		this.faceDetector.release();
	}

	/**
	 * release the real image and the rotated image
	 */
	public void release(){
		if (real != null) {this.real.release(); this.real = null;}
		if (rotated != null) {this.rotated.release(); this.rotated = null;}
	}

}