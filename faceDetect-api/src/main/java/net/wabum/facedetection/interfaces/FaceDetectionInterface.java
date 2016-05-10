package net.wabum.facedetection.interfaces;

import javax.ejb.Remote;

import net.wabum.facedetection.dto.DtoDetection;

@Remote
public interface FaceDetectionInterface {
	
	public DtoDetection detect(String path);

}
