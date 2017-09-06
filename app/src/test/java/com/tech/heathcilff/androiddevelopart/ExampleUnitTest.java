package com.tech.heathcilff.androiddevelopart;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
	@Test
	public void addition_isCorrect() throws Exception {
		assertEquals(4, 2 + 2);
	}

	@Test
	public void writeFile() throws Exception{
		File file = new File("test.txt");
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		String str = "hello world 哈哈";
		fileOutputStream.write(str.getBytes("utf-8"));
		fileOutputStream.close();
	}

	@Test
	public void readFile() throws Exception {
		File file = new File("test.txt");
		FileInputStream fis = new FileInputStream(file);
		byte[] bytes = new byte[1024];
		int count = 0;
		StringBuilder sb= new StringBuilder();
		while ((count = fis.read(bytes, 0, bytes.length)) != -1) {
			sb.append(new String(bytes, 0 , count, "utf-8"));
		}
		fis.close();
		System.out.print(sb.toString());
	}

	@Test
	public void storeObject1(){
		User user = new User("zhang san", 24, true);
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream("userCache1.txt"));
			os.writeObject(user);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void restoreObject1() {
		ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(new FileInputStream("userCache1.txt"));
			User user = (User) is.readObject();
			System.out.print(user.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}