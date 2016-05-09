package net.wabum.facedetection.ImageCoordinatorPooling;

import org.apache.commons.pool2.impl.DefaultPooledObject;

import net.wabum.facedetection.model.ImageCoordinator;

public class ImageCoordinatorPooled extends DefaultPooledObject<ImageCoordinator>{

	public ImageCoordinatorPooled(ImageCoordinator object) {
		super(object);
	}
}
