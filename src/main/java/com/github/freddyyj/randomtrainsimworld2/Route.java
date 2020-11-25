package com.github.freddyyj.randomtrainsimworld2;

import java.io.File;
import java.util.ArrayList;

/**
 * A route class
 * @author FreddyYJ_
 */
public class Route {
	private String code;
	private String name;
	/**
	 * If true, this route can be selected by random pick.
	 */
	public boolean isSelected;

	/**
	 * Constructor with route ID and route text file.
	 * @deprecated Use {@link Route#Route(String, String,boolean)} instead.
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
	 * @deprecated Use {@link Route#Route(String, String,boolean)} instead.
	 * @param num route ID
	 * @param name route name
	 */
	@Deprecated
	public Route(int num,String name) {
		this.name=name;
	}

	/**
	 * Constructor with route ID, route name and locomotive list.
	 * @deprecated Use {@link Route#Route(String, String,boolean)} instead.
	 * @param num route ID
	 * @param name route name
	 * @param locos locomotive list
	 */
	@Deprecated
	public Route(int num,String name, ArrayList<Locomotive> locos) {
		this.name=name;
	}

	/**
	 * Constructor with route name and selection.
	 * @param name route name
	 * @param select initial {@link Route#isSelected} value
	 */
	public Route(String name,String code,boolean select){
		this.name=name;
		isSelected=select;
		this.code=code;
	}

	/**
	 * Constructor with route name. {@link Route#isSelected} sets to true.
	 * @param name route name
	 */
	public Route(String name,String code){
		this(name,code,true);
	}
	public String getName()
	{
		return name;
	}
	public String getCode(){return code;}
}
