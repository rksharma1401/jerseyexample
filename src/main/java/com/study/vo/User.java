/**
 * 
 */
package com.study.vo;

/**
 * @author ravikant.sharma
 * 02-Dec-2016
 */
public class User {

	private String name; 
	private String company;	
	private String post;
	public User(){}
	public User(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	
}
