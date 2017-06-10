package test.bmp;

import java.io.File;
import java.io.FileInputStream;

import bmp.BMPDecoder;
import bmp.BMPDecoder1;

public class TestBMPDecoderWithoutSym {
	BMPDecoder1 decoder = new BMPDecoder1();
	
	public static void main(String[] args) throws Exception {
		new TestBMPDecoderWithoutSym().start();
	}
	
	public void start() throws Exception{
		FileInputStream is = new FileInputStream(new File("src/example-BMPDecoder/test.bmp"));
		decoder.read(is);
	}

}
