/**
 * 
 */
package com.bartley.calculator.expression.service.util;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;


/**
 * @author Tony
 *
 */
public class WorkerMatcherCarrier<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5657226860204357272L;
	private Matcher matcher;
	private List<? extends T> workers;
	
	public Matcher getMatcher() {
		return matcher;
	}
	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
	}
	public List<? extends T> getWorkers() {
		return workers;
	}
	public void setWorkers(List<? extends T> workers) {
		this.workers = workers;
	}

}
