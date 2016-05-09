package net.wabum.facedetection.model;
//vresione 1
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Image {
	Mat imageM = null;
	Rect[] faces;
	Rect[] eyes;

	public Image(){}

	public Image(Mat image, Rect[] e, Rect[] f){
		this.setImage(image, e, f);
	}
	
	public void setImage(Mat image, Rect[] e, Rect[] f){
		this.release();
		this.eyes = e;
		this.faces = f;
		this.imageM = image;
	}

	public Rect getBestFace(){
		PercentageCalculator calc = new PercentageCalculator();
		Rect bigger = null;
		Rect moreEif = null;
		Rect toRet = null;
		for (Rect rect : faces) {
			if (bigger == null || rect.size().area() > bigger.size().area())
				bigger = rect;
			if ((calc.eyesInsideAFace(rect, eyes) > 0) && (moreEif == null || calc.eyesInsideAFace(rect, eyes) > calc.eyesInsideAFace(moreEif, eyes))) {
				moreEif = rect;
			}
		}
		if (moreEif != null) {
			toRet = moreEif;
		}
		else {
			toRet = bigger;
		}
		return toRet;
	}

	private Eyes getBestEyes(){
		List<Rect> eyesLs = new ArrayList<Rect>();
		Rect primo;
		Rect secondo;
		Rect prec;
		Eyes ret = null;
		for (Rect rect : eyes) {
			eyesLs.add(rect);
		}
		// il sort non funziona ma non sai perchè l'hai messo SE QUALCOSA NON DOVESSE FUNZIONARE GUARDA QUI
		
		//eyesLs.sort(new EyesComparator());
		primo = eyesLs.get(0);
		secondo = eyesLs.get(1);
		prec = new Rect(0, 0, 0, 0);
		for (Rect rect : eyesLs) {
			if (Math.abs(rect.area() - prec.area()) < Math.abs(primo.area() - secondo.area())){
				primo = prec;
				secondo = rect;
			}
			prec = rect;
		}
		ret = new Eyes(primo, secondo);
		return ret;
	}

	public Eyes getEyes(){
		Eyes eyesPair = null;
		if (this.pairFound())
			eyesPair = getBestEyes();
		return eyesPair;
	}

	public boolean pairFound(){
		return this.eyes.length > 1;
	}
	
	public void release(){
		if (this.imageM != null) {
			this.imageM.release();
		}
	}

	public boolean eyesFound(){
		return this.eyes.length > 0;
	}

	public boolean facesFound(){
		return this.faces.length > 0;
	}
	
	public Mat rotateImage(){
		Eyes eyesForTrasf;
		Mat dest = null;
		if((eyesForTrasf = this.getEyes()) != null){
			Point center = new Point(imageM.cols()/2.0, imageM.rows()/2.0);
			double angle = eyesForTrasf.eyesCorner();
			Mat rot = Imgproc.getRotationMatrix2D(center, angle, 1);
			dest = new Mat();
			Imgproc.warpAffine(imageM, dest, rot, imageM.size());
			rot.release();
		}
		return dest;
	}
	
	public Mat facesMarker(){
		Mat imgRet = this.imageM.clone();
		for (Rect rect : faces) {
			Imgproc.rectangle(imgRet, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
		}
		return imgRet;
	}

	public Mat bestFaceMarker(){
		Mat imgRet = null;
		if(this.faces.length > 0){
			Rect rect = this.getBestFace();
			imgRet = this.imageM.clone();
			Imgproc.rectangle(imgRet, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
		}
		return imgRet;
	}
	
	public Mat eyesMarker(){
		Mat toRet = this.imageM.clone();
		if (eyes.length > 0){
			for (Rect eyes : eyes) {
				Imgproc.rectangle(toRet, new Point(eyes.x, eyes.y), new Point(eyes.x + eyes.width, eyes.y + eyes.height), new Scalar(0, 255, 0));
			}
		}
		return toRet;
	}
	
	public Mat	bestEyesMarker(){
		Mat toRet = this.imageM.clone();
		if (this.getBestEyes() != null) {
			for (Rect eyes : this.getBestEyes().getEyes()) {
				Imgproc.rectangle(toRet, new Point(eyes.x, eyes.y), new Point(eyes.x + eyes.width, eyes.y + eyes.height), new Scalar(0, 255, 0));
			}
		}
		return toRet;
	}
}
