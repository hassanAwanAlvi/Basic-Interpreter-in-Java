package myCom;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class Tests {

	@Test
	public void test() {
		PrintWriter writer;
		try {
			writer = new PrintWriter("myFile.txt", "UTF-8");
			writer.println("var x = 10");
			writer.println("var y = 20");
			writer.println("print x+y");
			writer.close();
			
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		Interprator interprator = new Interprator();
		interprator.executeFile(new File("myFile.txt"));
		int value = interprator.getLast();
		
		assertEquals(value, 30);
		
		
	}

}
