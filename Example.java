package moti;

import gov.nasa.jpf.jdart.Debug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Example {
	public static String str = "1111111111111111111111111111111111111111111111111111111"; 
	
	public void start() {
		int m = Debug.makeConcolicInteger("sym_m", "1");
		int n = Debug.makeConcolicInteger("sym_n", "1");
		int tag = Debug.makeConcolicInteger("sym_tag", "5");
		try {
			InputStreamReader w = new InputStreamReader(new StringBufferInputStream(str));
			int sum = 0;
			int k = 0;
			while (k++ < m) {
				int i = w.read();
				if (i == -1) {
					break;
				}
				sum += i;
			}
			if (tag == 0) {
				w.close();
			}
			k = 0;
			while (k++ < n) {
				int i = w.read();
				if (i == -1) {
					break;
				}
				sum += i;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Example().start();
	} 
    
}