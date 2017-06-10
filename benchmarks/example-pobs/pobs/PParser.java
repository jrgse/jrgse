/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs;

import java.util.Vector;

import pobs.directive.PDirectiveControl;

/**
 * Abstract base class of all parsers. It handles directives and optional
 * actions. Concrete subclasses must implement
 * {@link #parse(PScanner, long, PContext)}.<p/>In order to make parsers
 * thread safe the following points must be fulfilled:
 * <ul>
 * <li>{@link #setMatchAction(PAction)}and {@link #setMismatchAction(PAction)}
 * must be called <em>before</em> the first invocation of
 * {@link #process(PScanner, long, PContext)}.</li>
 * <li>These {@link PAction PActions}must be stateless objects.</li>
 * <li>The implementation of {@link #parse(PScanner, long, PContext)}never
 * changes the internal state of a <code>PParser</code> object.</li>
 * </ul>
 * 
 * @author Martijn W. van der Lee
 * @author Franz-Josef Elmer
 */
public abstract class PParser implements PObject {
	private Vector controls = new Vector(1);

	private PAction matchAction;

	private PAction mismatchAction;
	
	private String errorInfo;
	
	public final PParser addControl(PDirectiveControl control) {
		controls.addElement(control);
		return this;
	}

	/**
	 * Defines the action used in the case of matching.
	 * 
	 * @param matchAction
	 *            Action invoked in case of a successful match. Can be
	 *            <code>null</code>.
	 * @return    this instance.
	 */
	public final PParser setMatchAction(PAction matchAction) {
		this.matchAction = matchAction;
		return this;
	}

	/**
	 * Defines the action used in the case of missmatching.
	 * 
	 * @param mismatchAction
	 *            Action invoked in case of a unsuccessful match. Can be
	 *            <code>null</code>.
	 * @return    this instance.
	 */
	public final PParser setMismatchAction(PAction mismatchAction) {
		this.mismatchAction = mismatchAction;
		return this;
	}
	
	public String getErrorInfo() {
		return errorInfo;
	}
	
	/**
	 * Sets an error info.
	 * 
	 * @param errorInfo
	 *            Error info which will be used by {@link PErrorHandler}. 
	 *            Can be <code>null</code>.
	 * @return    this instance.
	 */
	public PParser setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
		return this;
	}

	/**
	 * Parses specified input and invokes actions if defined. First,
	 * {@link #parse(PScanner, long, PContext)}is invoked. Depending on the
	 * result the matching or missmatching action is invoked with the
	 * {@link PTarget}of <code>context</code>.
	 */
	public PMatch process(PScanner input, long begin, PContext context) {
//		System.out.println("begin");
		
		PMatch result = parse(input, begin, context, controls.size());

		boolean match = result.isMatch();
		if (match && matchAction != null) {
			matchAction.perform(context.getTarget(), input.substring(begin,
					begin + result.getLength()));
		} else if (!match && mismatchAction != null) {
			mismatchAction.perform(context.getTarget(), input.substring(begin,
					begin + result.getLength()));
		}
        if (!match) {
        	PErrorHandler errorHandler = context.getErrorHandler();
        	if (errorHandler != null) {
        		errorHandler.notify(begin, this);
        	}
        }
        
//        System.out.println("end");
        
		return result;
	}
	
	private PMatch parse(PScanner input, long begin, PContext context, int level) {
		PMatch result;
		if (level == 0) {
			result = parse(input, begin, context);
		} else {
			--level;
			PDirectiveControl control = (PDirectiveControl) controls.elementAt(level);
			PDirective directive = context.getDirective();
			Object currentState = control.modifyState(directive);
			result = parse(input, begin, context, level);
			control.reestablishPreviousState(directive, currentState);
		}
		return result;
	}

	/**
	 * Parses the specified input starting at <code>begin</code>. Usually
	 * only the {@link PDirective}of the specified {@link PContext}will be
	 * used. The complete context is necessary if parser has to call a wrapped
	 * parser.
	 * 
	 * @param input
	 *            Input to be parsed.
	 * @param begin
	 *            Index of first character of <code>input</code> to be parsed.
	 * @param context
	 *            Parsing context.
	 * @return Parsing result.
	 */
	protected abstract PMatch parse(PScanner input, long begin, PContext context);
}