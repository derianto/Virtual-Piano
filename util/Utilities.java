package util;

import java.net.URL;

/**
 * A class containing utility classes.
 * 
 * @author Derianto Kusuma
 */
/* Copied verbatim from the code of Bunny World (final project CS 108).
 * trace and traceErr created by Keith Schwarz.
 */
public class Utilities {
	
	/*
	 * Given two arrays, return the concatenation of the array.
	 * a's elements will have lower indexes than b's elements.
	 * @param <T>
	 * @param a
	 * @param b
	 * @return the new array
	 */
	/*public static <T> T[] concatArray(T[] a, T[] b) {
		T[] concat = (T[]) new Object[a.length + b.length];
		
		for (int i = 0; i < a.length; i++)
			concat[i] = a[i];
		for (int i = 0; i < b.length; i++)
			concat[a.length + i] = b[i];
		
		return concat;
	}*/

	/**
	 * Given two string arrays, return the concatenation of the array.
	 * a's elements will have lower indexes than b's elements.
	 * @param a
	 * @param b
	 * @return the new array
	 */
	public static String[] concatStringArray(String[] a, String[] b) {
		String[] concat = new String[a.length + b.length];
		
		for (int i = 0; i < a.length; i++)
			concat[i] = a[i];
		for (int i = 0; i < b.length; i++)
			concat[a.length + i] = b[i];
		
		return concat;
	}
	
	/**
	 * Unified system for printing debugging messages, which can be enabled and disabled as need be.
	 * 
	 * @param message The message to print out.
	 */
	public static void trace(String message)
	{
		System.out.println(message);
	}
	
	/**
	 * Unified system for printing error messages, which can be enabled and disabled as need be.
	 * 
	 * @param message The message to print out.
	 */
	public static void traceErr(String message)
	{
		System.err.println(message);
	}
	
	/**
	 * Returns the resource URL.  It is claimed that by using URL, a resource
	 * can be read in Java program of any form: application, applet,
	 * JAR, etc.
	 * @param fileName
	 * @return the URL
	 */
	public static URL getResourceURL(String fileName) {
		Utilities.trace("fileName filtered = " + "/" + fileName.replace('\\', '/'));
		Utilities.trace("URL on Utilities = " + Utilities.class.getResource("/" + fileName.replace('\\', '/')));
		
		// wow this works
		//Utilities.trace("Obvious URL on Utilities = " + Utilities.class.getResource("Utilities.java"));
		//Utilities.trace("Wrong package URL on Utilities = " + Utilities.class.getResource("util/Utilities.class"));
		
		// / <- absolute.  go from the base (no directory)
		return Utilities.class.getResource("/" + fileName.replace('\\', '/'));
	}
}
