package com.github.freddyyj.randomtrainsimworld2;

/**
 * A weather class
 * @author FreddyYJ_
 */
public class Weather {
	private String name;

	/**
	 * If true, this locomotive can be selected by random pick.
	 */
	public boolean isSelected;

	/**
	 * Constructor with weather ID and name
	 * @deprecated Use {@link Weather#Weather(String)} instead.
	 * @param id weather ID
	 * @param name weather name
	 */
	@Deprecated
	public Weather(int id,String name) {
		this.setName(name);
	}

	/**
	 * Constructor with weather name and {@link Weather#isSelected}
	 * @param name weather name
	 * @param select initial {@link Weather#isSelected} value
	 */
	public Weather(String name,boolean select) {
		this.setName(name);
		isSelected=select;
	}

	/**
	 * Constructor with weather name. {@link Weather#isSelected} sets to true
	 * @param name weather name
	 */
	public Weather(String name){
		this(name,true);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
