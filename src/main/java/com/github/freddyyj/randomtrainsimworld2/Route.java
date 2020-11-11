package com.github.freddyyj.randomtrainsimworld2;

import java.io.File;
import java.util.ArrayList;

/**
 * A route class
 * @author FreddyYJ_
 */
public class Route {
	private String name;
	private ArrayList<Locomotive> locomotives;
	/**
	 * If true, this route can be selected by random pick.
	 */
	public boolean isSelected;

	/**
	 * Constructor with route ID and route text file.
	 * @deprecated Use {@link Route#Route(String, boolean)} instead.
	 * @param num route ID
	 * @param file route text file
	 */
	@Deprecated
	public Route(int num, File file)
	{
		String[] name=file.getName().split(".txt");
		this.name=name[0];
	}
	/**
	 * Constructor with route ID and route name.
	 * @deprecated Use {@link Route#Route(String, boolean)} instead.
	 * @param num route ID
	 * @param name route name
	 */
	@Deprecated
	public Route(int num,String name) {
		this.name=name;
	}

	/**
	 * Constructor with route ID, route name and locomotive list.
	 * @deprecated Use {@link Route#Route(String, boolean)} instead.
	 * @param num route ID
	 * @param name route name
	 * @param locos locomotive list
	 */
	@Deprecated
	public Route(int num,String name, ArrayList<Locomotive> locos) {
		this.name=name;
		locomotives=locos;
	}

	/**
	 * Constructor with route name, locomotive list and selection of this route.
	 * @param name route name
	 * @param locos locomotive list
	 * @param select initial {@link Route#isSelected} value
	 */
	public Route(String name, ArrayList<Locomotive> locos,boolean select){
		this.name=name;
		locomotives=locos;
		isSelected=select;
	}

	/**
	 * Constructor with route name and selection.
	 * @param name route name
	 * @param select initial {@link Route#isSelected} value
	 */
	public Route(String name,boolean select){
		this.name=name;
		isSelected=select;
	}

	/**
	 * Constructor with route name. {@link Route#isSelected} sets to true.
	 * @param name route name
	 */
	public Route(String name){
		this(name,true);
	}
	public String getName()
	{
		return name;
	}
	public ArrayList<Locomotive> getLocomotives()
	{
		return locomotives;
	}
}
