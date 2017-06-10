package schroeder.test;


import gov.nasa.jpf.jdart.Debug;

import java.io.File;

import schroeder.SoundModel;
import schroeder.adapter.WAVEFileAdapter;


public class TestWAVEFileRead {

	public static File f = new File("src/example-schroeder/test.wav");
	
	public static void main(String[] args) {
		new TestWAVEFileRead().start();
	}
	
	public void start(){
		WAVEFileAdapter r = new WAVEFileAdapter();
		r.setFile(TestWAVEFileRead.f);
		SoundModel model = r.createSoundFromFile();
		System.out.println(model);
	}

}
