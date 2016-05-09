package net.wabum.facedetection.model;
//vresione 1
import org.opencv.core.Point;
import org.opencv.core.Rect;

// da vedere l'algoritmo che calcola la percentuale

public class PercentageCalculator {
	Rect[] eyes;
	Rect[] faces;
	
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
		System.out.println("sono state trovate " + faces.length + " facce");
		System.out.println("sono stati trovati " + eyes.length + " occhi");
		System.out.println("occhi nelle facce " + eif);
		return faces.length * 40 + 5 * eyes.length + 10 * eif;
	}
	
	
	
	public int eyesInsideFaces(Rect[] faces, Rect[] eyes){
		int eif = 0;
		for (Rect rect : faces) {
			eif += eyesInsideAFace(rect, eyes);
		}
		return eif;
	}
	
	public int eyesInsideAFace(Rect face, Rect[] eyes){
		int toRet = 0;
		for (Rect rectE : eyes) {
			if (rectOverRect(face, rectE)) {
				toRet++;
			}
		}
		return toRet;
	}

	//smaller area più piccola di bigger
	private boolean rectOverRect(Rect bigger, Rect smaller){
		return smaller.area() < bigger.area() && pointInToRect(smaller.tl(), bigger) && pointInToRect(smaller.br(), bigger);
	}

	private boolean pointInToRect(Point p, Rect r){
		return p.x >= r.x && p.y >= r.y && p.x <= r.br().x && p.y <= r.br().y;
	}
	
	public void setFaces(Rect[] f){
		this.faces = f;
	}
	
	public void setEyes(Rect[] e){
		this.eyes = e;
	}

}







