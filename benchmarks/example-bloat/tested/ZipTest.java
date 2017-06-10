package tested;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ZipFile f = new ZipFile("/System/Library/Frameworks/JavaVM.framework/Versions/1.6.0/Classes/classes.jar");
		//InflaterInputStream input = new InflaterInputStream();
		DataInputStream stream = new DataInputStream(f.getInputStream(new ZipEntry("java/lang/Object.class")));
		System.out.println(stream.readInt());
	}

}
