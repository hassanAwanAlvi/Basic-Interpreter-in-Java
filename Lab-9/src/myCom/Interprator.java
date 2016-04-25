package myCom;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Interprator 
{
	private HashMap<String, Integer> variables;
	private int lineNo,result;
	
	
	public Interprator()
	{
		variables = new HashMap<String,Integer>();
		lineNo = 0;
	}
	public List<String> readLines(File file) throws IOException
	{
		return Files.readAllLines(Paths.get(file.getAbsolutePath()));
	}
	
	public int solveIt(List<String> file)
	{
		
		for(String s : file)
		{
			lineNo++;
			evaluate(s);
		}
		return 0;
	}	
	
	private void evaluate(String s) 
	{
		if(s.startsWith("var"))
		{
			String[] data = s.split(" ");
			if(data.length >= 2)
			{
				String[] varValue = s.split("var")[1].split("=");
				if(varValue.length == 2)
				{
					if(checkVar(varValue[0]))
					{
						int value = getValue(varValue[1]);
						this.variables.put(varValue[0].trim(), value);
					}
					else
						errorFunc();
						
				}
				else
					errorFunc();
			}
			else
				errorFunc();
			
		}
		else if(s.startsWith("print"))
		{
			String []data = s.split(" ");
			if(data.length >= 2)
			{
				String equation = s.split("print")[1].trim() +" ";
				String equationMath = getMathEquation(equation);
				System.out.println(toSolve(equationMath));
			}
			else
				errorFunc();
		}
		else
			{
				String[] varValue = s.split("=");
				if(varValue.length == 2)
				{
					if(checkVar(varValue[0]))
					{
						int value = getValue(varValue[1]);
						this.variables.put(varValue[0].trim(), value);
					}
					else
						errorFunc();
						
				}
				else
					errorFunc();
			}
		
		
	}
	private int getValue(String string) 
	{
		return toSolve(getMathEquation(string + " "));
	}
	
	
	private String getMathEquation(String equation)
	{
		String equationMath = "",var = "";
		
		
		for(int i = 0 ; i < equation.length(); i++)
		{
			if((equation.charAt(i) + "").equalsIgnoreCase(" "))
			{
				if(var == "")
					continue;
				else
				{
					if( this.variables.containsKey(var)){
						equationMath+= this.variables.get(var);
						var = "";
						continue;
					}
					try
					{
						Integer.parseInt(var);
						equationMath+=var;
						var = "";
					}
					catch(NumberFormatException e)
					{
						errorFunc();
					}
					
				}
			}
			else if((equation.charAt(i) + "").equalsIgnoreCase("+") || (equation.charAt(i) + "").equalsIgnoreCase("-")|| (equation.charAt(i) + "").equalsIgnoreCase("*")|| (equation.charAt(i) + "").equalsIgnoreCase("/"))
			{
				if(!var.equalsIgnoreCase(""))
				if( this.variables.containsKey(var))
				{
					equationMath+= this.variables.get(var);
					equationMath+=equation.charAt(i);
					var = "";
				}
				else
					errorFunc();
				else
					equationMath+=equation.charAt(i);
				
				
			}
			else
			{
				var += equation.charAt(i);
			}
		}
	return equationMath;	
	}
	
	
	private int toSolve(String var)
	{
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine en = mgr.getEngineByName("JavaScript");
		try {
			int value = (int) en.eval(var);
			result = value;
			return value;
		} catch (ScriptException e) {
			
			e.printStackTrace();
		}
		return 0;	
	}

	private boolean checkValue(String string) {
		try
		{
			Integer.parseInt(string.trim());
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;	
		}
	}
	private boolean checkVar(String string) 
	{
		if(checkValue(string))
			return false;
		else
		{
			if(string.equalsIgnoreCase("var") || string.equalsIgnoreCase("="))
				return false;
			else
				return true;
		}
	}

	private void errorFunc()
	{
		System.out.println("There is Syntax Error in your code at line no " + lineNo);
		throw new IllegalArgumentException();	
	}
	
	
	public void executeFile(File file)
	{
		try 
		{
			solveIt(readLines(file));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	public int getLast() {
		
		return result;
	}
			

	
	
	/*
	 * package myCom;

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

	 * */
}
