package com.siliconorchard.database;

import java.io.UnsupportedEncodingException;

import android.util.Base64;

public class User {
	
	private String name;
	private int age;
	private String active;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setActive(String active) {
		byte[] data = null;
		try {
			data = active.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.active  = Base64.encodeToString(data, Base64.DEFAULT);

	
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", active=" + active
				+ "]";
	}
	public String getActive() {
		// TODO Auto-generated method stub
		byte[] data = Base64.decode(active, Base64.DEFAULT);
		try {
			String text = new String(data, "UTF-8");
			return text;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "URL REMOVED";
	}
}
