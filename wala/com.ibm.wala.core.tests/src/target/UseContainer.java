package target;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UseContainer {

	public static void method(Container c) {
//		c.print();
		method2();
	}
	
	public static void method2(){
	  method3();
	}
	public static void method3(){
	  
	}
	
	public static void test1() {
		Container c1 = new Container("c1");
		Container c2 = new Container("c2");
		method(c1);
		method(c2);
	}
	
	public static void main(String[] args) {
	  //just test for exclusion in analysis scope
//	  List<Container> list = new ArrayList<Container>();
//	  list.add(new Container("new list item"));
//	  File f = new File("d:\\test.txt");
	  
	  test1();
	}
	
}
