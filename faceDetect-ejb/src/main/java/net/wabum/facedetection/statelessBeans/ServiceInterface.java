package net.wabum.facedetection.statelessBeans;
//vresione 1


import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import net.wabum.facedetection.ImageCoordinatorPooling.ImageCoordinatorPool;
import net.wabum.facedetection.dto.DtoDetection;
import net.wabum.facedetection.interfaces.FaceDetectionInterface;
import net.wabum.facedetection.model.ImageCoordinator;
import net.wabum.facedetection.model.InternalDto;

@Stateless
@Remote(FaceDetectionInterface.class)
public class ServiceInterface implements FaceDetectionInterface{

	public ServiceInterface(){}

	/**
	 * 
	 * @param input full path to input image
	 * @param output full path of processed image
	 * @throws Exception missing available object in the pool
	 */
	public void detect(String input, String output) throws Exception{
		System.out.println(input);
		ImageCoordinator coord = ImageCoordinatorPool.getInstance().borrowObject();
		coord.setImage(read(input));
		write(output, coord.detect());
	}
	
	/**
	 * @param path : full path to input image
	 * @return DtoDetection : data transfer object containing Face, percentage of face found and number of faces found 
	 */
	public DtoDetection detect(String path) {
		DtoDetection ret = null;
		InternalDto internalDto;
		
		try {
			ImageCoordinator coord = ImageCoordinatorPool.getInstance().borrowObject();
			coord.setImage(read(path));
			internalDto = coord.detectFace();
			ret = internalDto.convertToSerializable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	private Mat read(String path){
		return Imgcodecs.imread(path);
	}

	private void write(String outputPath, Mat img){
		Imgcodecs.imwrite(outputPath, img);
	}

}
