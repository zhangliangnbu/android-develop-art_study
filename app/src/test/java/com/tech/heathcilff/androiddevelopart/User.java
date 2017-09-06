package com.tech.heathcilff.androiddevelopart;

import java.io.Serializable;

/**
 * Created by zhangliang on 29/08/2017.
 */
public class User implements Serializable {

	private static final long serialVersionUID = -2792102093959769440L;
	public String name;
	public int age;
	public boolean isMale;

	public User(String name, int age, boolean isMale) {
		this.name = name;
		this.age = age;
		this.isMale = isMale;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", age=" + age +
				", isMale=" + isMale +
				'}';
	}
}
