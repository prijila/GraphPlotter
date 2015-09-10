package org.graph.ui;

import java.io.Serializable;

public class Coordinates implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double x;
	private double y;
	
	public Coordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "x = "+this.x+" , y = "+this.y;
	}
	public String getxValue() {
		return String.valueOf(x);
	}
	
	public String getyValue() {
		return String.valueOf(y);
	}
	

}
