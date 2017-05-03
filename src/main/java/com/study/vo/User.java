/**
 * 
 */
package com.study.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author ravikant.sharma
 * 02-Dec-2016
 */
@Entity
@Table(name="User", 
	   uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class User implements Serializable{
  
	private static final long serialVersionUID = 3479690702597127117L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable=false, unique=true, length=11)
	private int id;
	@Column(name="NAME", length=25, nullable=true)
	private String name; 
	
	@Column(name="company", length=50, nullable=true)
	private String company;	
	
	@Column(name="post", length=25, nullable=true)
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	} 
	
}
