package net.wabum.facedetection.ImageCoordinatorPooling;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.opencv.core.Core;

import it.wabum.opencvCoreLoader.OpencvLoader;
import net.wabum.facedetection.model.ImageCoordinator;

public class ImageCoordinatorPool extends GenericObjectPool<ImageCoordinator>{
	private static ImageCoordinatorPool instance = null;
	
	protected ImageCoordinatorPool(ImageCoordinatorFactory factory){
		super(factory);
		this.setMaxIdle(3);
		this.setMaxTotal(6);
		this.setMaxWaitMillis(3000);
		this.setMinIdle(2);
	}
	
	public static ImageCoordinatorPool getInstance(){
		if(instance == null){
			OpencvLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			instance = new ImageCoordinatorPool(new ImageCoordinatorFactory());
		}
		return instance;
	}
}
