package net.wabum.facedetection.model;
//version 1.1
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
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
			if ((calc.eyesInsideAFace(rect, eyes) > 0) && (moreEif == null || calc.eyesInsideAFace(rect, eyes) > calc.eyesInsideAFace(moreEif, eyes))) 
				moreEif = rect;
		}
		toRet = (moreEif != null) ? moreEif : bigger; 
		return toRet;
	}

	private Eyes getBestEyes(){
		List<Rect> eyesList = new ArrayList<Rect>(Arrays.asList(this.eyes));
		Rect primo = eyesList.get(0);
		Rect secondo = eyesList.get(1);
		Rect precedente = new Rect(0, 0, 0, 0);
		Eyes bestEyes;
		for (Rect rect : eyesList) {
			if (Math.abs(rect.area() - precedente.area()) < Math.abs(primo.area() - secondo.area())){
				primo = precedente;
				secondo = rect;
			}
			precedente = rect;
		}
		bestEyes = new Eyes(primo, secondo);
		return bestEyes;
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
		if (this.imageM != null)
			this.imageM.release();
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
}