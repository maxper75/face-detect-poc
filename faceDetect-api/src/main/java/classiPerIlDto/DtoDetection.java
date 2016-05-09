package classiPerIlDto;

public class DtoDetection {
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
