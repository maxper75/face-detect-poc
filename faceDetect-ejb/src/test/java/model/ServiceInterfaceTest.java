package model;

import static org.junit.Assert.*;

import java.io.File;


import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;

import net.wabum.facedetection.dto.DtoDetection;
import net.wabum.facedetection.statelessBeans.ServiceInterface;

public class ServiceInterfaceTest {
	ServiceInterface service;
	
	
	@Before
	public void setUp() throws Exception {
		service = new ServiceInterface();
	}

	@Test
	public void testDetectString() {
		File folder = new File("/ssd-data/ebarba/immagini/immagini_multithread");
		DtoDetection firstDto, secondDto;
		for(File img : folder.listFiles()){
			firstDto = service.detect(img.getAbsolutePath());
			secondDto = service.detect(img.getAbsolutePath());
			assertTrue(equalsDto(firstDto, secondDto));
		}
	}
	
	public boolean equalsDto(DtoDetection first, DtoDetection second){
		return first.getFace().equals(second);
	}
}
