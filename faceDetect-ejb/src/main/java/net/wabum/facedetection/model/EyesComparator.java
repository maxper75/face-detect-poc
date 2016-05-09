package net.wabum.facedetection.model;
//vresione 1
import java.util.Comparator;

import org.opencv.core.Rect;

public class EyesComparator implements Comparator<Rect> {

	public int compare(Rect o1, Rect o2) {
		return (int) (o1.area() - o2.area());
	}
}
