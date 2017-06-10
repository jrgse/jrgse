package test.bmp;

import gov.nasa.jpf.jdart.Debug;

import java.io.File;
import java.io.FileInputStream;

import bmp.BMPDecoder;
import bmp.BMPDecoder1;

public class TestBMPDecoder {
	//BMPDecoder1 decoder = new BMPDecoder1();
	BMPDecoder decoder = new BMPDecoder();
	
	public static void main(String[] args) throws Exception {
		new TestBMPDecoder().start();
	}
	
	public void start() throws Exception{
		FileInputStream is = new FileInputStream(new File("src/example-BMPDecoder/test.bmp"));
		decoder.read(is);
	}

}