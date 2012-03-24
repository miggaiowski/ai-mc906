package algoritmo;

import java.io.*;

public final class Console {
	public static double readDouble() {
	try {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String s = br.readLine();
	Double d = new Double(s);
	return d.doubleValue();
	} catch (IOException e) {
	return 0;
	} catch (NumberFormatException e) {
	return 0;
	}
	}

	public static int readInteger() {
	try {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String s = br.readLine();
	return Integer.parseInt(s);
	} catch (IOException e) {
	return 0;
	} catch (NumberFormatException e) {
	return 0;
	}
	}

	public static String readString() {
	try {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String s = br.readLine();
	return s;
	} catch (IOException e) {
	return "";
	}
	}
	}
