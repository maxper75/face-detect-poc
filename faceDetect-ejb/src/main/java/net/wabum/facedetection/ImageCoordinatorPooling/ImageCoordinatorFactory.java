package net.wabum.facedetection.ImageCoordinatorPooling;
import org.apache.commons.pool2.*;

import net.wabum.facedetection.model.ImageCoordinator;
public class ImageCoordinatorFactory extends BasePooledObjectFactory<ImageCoordinator>  {

	@Override
	public ImageCoordinator create() throws Exception {
		return new ImageCoordinator();
	}

	@Override
	public PooledObject<ImageCoordinator> wrap(ImageCoordinator obj) {
		// TODO Auto-generated method stub
		return new ImageCoordinatorPooled(obj);
	}
	
	@Override
	public void destroyObject(PooledObject<ImageCoordinator> p) throws Exception {
		p.getObject().release();
		super.destroyObject(p);
	}
}
