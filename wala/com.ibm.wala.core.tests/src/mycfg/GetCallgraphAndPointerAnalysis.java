package mycfg;


import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import target.Container;

import com.ibm.wala.analysis.pointers.BasicHeapGraph;
import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.core.tests.callGraph.CallGraphTestUtil;
import com.ibm.wala.examples.drivers.PDFTypeHierarchy;
import com.ibm.wala.examples.properties.WalaExamplesProperties;
import com.ibm.wala.ipa.callgraph.AnalysisCache;
import com.ibm.wala.ipa.callgraph.AnalysisOptions;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.CallGraphBuilderCancelException;
import com.ibm.wala.ipa.callgraph.Context;
import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.callgraph.propagation.HeapModel;
import com.ibm.wala.ipa.callgraph.propagation.PointerAnalysis;
import com.ibm.wala.ipa.callgraph.propagation.PointerKey;
import com.ibm.wala.ipa.callgraph.propagation.SSAPropagationCallGraphBuilder;
import com.ibm.wala.ipa.callgraph.propagation.cfa.CallStringContext;
import com.ibm.wala.ipa.callgraph.propagation.cfa.nCFABuilder;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.properties.WalaProperties;
import com.ibm.wala.ssa.IR;
import com.ibm.wala.ssa.ISSABasicBlock;
import com.ibm.wala.ssa.SSACFG;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.types.MethodReference;
import com.ibm.wala.types.Selector;
import com.ibm.wala.types.TypeReference;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaException;
import com.ibm.wala.util.config.AnalysisScopeReader;
import com.ibm.wala.util.debug.Assertions;
import com.ibm.wala.util.graph.Graph;
import com.ibm.wala.util.io.FileProvider;
import com.ibm.wala.viz.DotUtil;
import com.ibm.wala.viz.PDFViewUtil;

/**
 * construct call graph
 * @author Yuffon
 *
 */


public class GetCallgraphAndPointerAnalysis {
  
  private final static String PDF_FILE = "cg.pdf";
   
  
	public static void getCFG() throws WalaException, CancelException, InterruptedException {
//	  Thread.sleep((long)300000);
	  String appPath = "bin/target";
		try {
			//analysis scope
		  File exclusive = new File("D:\\eclipse4.2workspace2\\com.ibm.wala.core.tests\\dat\\YufengJava60RegressionExclusions.txt");
		  String include = "D:\\eclipse4.2workspace2\\com.ibm.wala.core.tests\\dat\\YufengIncludeUseConnection.txt";
//		  AnalysisScope scope = AnalysisScopeReader.makeJavaBinaryAnalysisScope(appPath, exclusive);
		   
		  AnalysisScope  scope = AnalysisScopeReader.readJavaScope(include, exclusive, GetCallgraphAndPointerAnalysis.class.getClassLoader());
			//load classes
			ClassHierarchy cha = ClassHierarchy.make(scope);
//			
			Container c = new Container("cc");
//			Class klass = c.getClass();
//			System.out.println(klass.toString());
			System.out.println(c.getClass().getName());
			
			//test sparse class
//			String name = "Ltarget/Container";
//			IClass conClass = cha.lookupClass(TypeReference.findOrCreate(ClassLoaderReference.Application, name));
//			Collection<IMethod> methods = conClass.getAllMethods();
//			conClass.getAllFields();
			      
			
			 Iterable<Entrypoint> entrypoints = com.ibm.wala.ipa.callgraph.impl.Util.makeMainEntrypoints(scope, cha/*, "Ltarget/UseContainer"*/);
			 AnalysisOptions options = new AnalysisOptions(scope, entrypoints);
			 AnalysisCache cache = new AnalysisCache();
			 
			 //important
			 com.ibm.wala.ipa.callgraph.impl.Util.addDefaultSelectors(options, cha);
			 com.ibm.wala.ipa.callgraph.impl.Util.addDefaultBypassLogic(options, scope, Util.class.getClassLoader(), cha);
			 
			 SSAPropagationCallGraphBuilder builder = new nCFABuilder(5, cha, options, cache, null,null);

			 com.ibm.wala.ipa.callgraph.CallGraph  g = builder.makeCallGraph(options);
			 System.out.println("Nodes: " + g.getNumberOfNodes());
			 
			 Iterator<CGNode> iter = g.iterator();
			 while(iter.hasNext()) {
			   CGNode node = iter.next();
			   Context context = node.getContext();
			   IMethod method = node.getMethod();
			   if(method.getSignature().contains("method(")) {
			     CallStringContext callContext = (CallStringContext)context;
			     
			     System.out.println("+++++++++++++++++++ get test1 CGNode++++++++++++++++++");
			     System.out.println("context is " + context);
			     IR ir = node.getIR();
			     
			     ISSABasicBlock b = null;
			     SSACFG cfg = ir.getControlFlowGraph();
			     com.ibm.wala.ssa.SSACFG.BasicBlock bb = cfg.getBasicBlock(0);
			     
			     
			     System.out.println("++++++++++++++++ cfg is ++++++++++++++++++++++");
			     System.out.println(cfg.toString());
			   }
			 }
			 
//			 System.out.println("Graph is:==========\n" + g.toString());
			 
//			 Collection<CGNode> entryPointsNodes = g.getEntrypointNodes();
//			 CGNode entry = null;
//			 System.out.println("entry node:");
//			 for(CGNode n : entryPointsNodes) {
//			   entry = n;
//			   System.out.println(n);
//			 }
		   
			 //get pointer analysis results
		    PointerAnalysis pointerAnalysis = builder.getPointerAnalysis();
		    HeapModel heapModel = pointerAnalysis.getHeapModel();
//		    //get points to set
		    String name = "Ltarget/UseContainer";
		    IClass conClass = cha.lookupClass(TypeReference.findOrCreate(ClassLoaderReference.Application, name));

		    Collection<IMethod> methods = conClass.getAllMethods();
		    IMethod test1 = null;
		    for(IMethod m : methods) {
		      System.out.println(m.getSignature());
		      if(m.getName().toString().contains("test1"))
		        test1 = m;
		    }
		    
//		    MethodReference mr = MethodReference.findOrCreate(tref, selector);
		    
		    BasicHeapGraph heapGraph = new BasicHeapGraph(pointerAnalysis, g);
//		      
		    System.out.println("Heap Graph:=============\n" + heapGraph.toString());
			 
//      System.out.println("entry node ------> :");
//      // Collection<CGNode> succOfEntry =
//      CGNode suc = null;
//      Iterator<CGNode> iter = g.getSuccNodes(entry);
//      while (iter.hasNext()) {
//        suc = iter.next();
//        System.out.println(suc);
//      }
//      System.out.println("entry node ------> ----> :");
//      CGNode sucsuc = null;
//      iter = g.getSuccNodes(suc);
//      while (iter.hasNext()) {
//        sucsuc = iter.next();
//        System.out.println(sucsuc);
//        sucsuc.toString(); // ExplicitCallGraph
//      }
			 
      
			     
			     
			 //output call graph to pdf
			 Properties p = null;
	      try {
	        p = WalaExamplesProperties.loadProperties();
	        p.putAll(WalaProperties.loadProperties());
	      } catch (WalaException e) {
	        e.printStackTrace();
	        Assertions.UNREACHABLE();
	      }
			 
			 String pdfFile = p.getProperty(WalaProperties.OUTPUT_DIR) + File.separatorChar + PDF_FILE;

	      String dotExe = p.getProperty(WalaExamplesProperties.DOT_EXE);
	      DotUtil.dotify(g, null, PDFTypeHierarchy.DOT_FILE, pdfFile, dotExe);

	      String gvExe = p.getProperty(WalaExamplesProperties.PDFVIEW_EXE);
	     PDFViewUtil.launchPDFView(pdfFile, gvExe);
			 
//		    Thread.sleep((long)300000);
			 
//			 com.ibm.wala.ipa.callgraph.CallGraphBuilder builder = Util.makeZeroCFABuilder(options, new AnalysisCache(), cha, scope);
//			 CallGraph cg = builder.makeCallGraph(options, null);
//			 System.out.println(cg.toString());
//			 System.out.println("#Nodes: " + cg.getNumberOfNodes());
//			 System.out.println("#edges: " + cg.getNumberOfNodes());
//			 Iterator<CGNode> it = cg.iterator();
//			 while(it.hasNext()) {
//			   System.out.println(it.next().getContext());
//			 }

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassHierarchyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CallGraphBuilderCancelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	
	
	
	public static void main(String[] args) {
		try {
      getCFG();
    } catch (WalaException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (CancelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
	}
}
