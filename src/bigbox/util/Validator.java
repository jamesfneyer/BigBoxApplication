package bigbox.util;

import java.util.Scanner;

public class Validator {
	public static String getChoice(Scanner sc, String prompt) {
		String s = "";
		boolean isValid = false;
		while (isValid == false) {
			System.out.print(prompt);
			s = sc.next(); // read user entry
			sc.nextLine();
			if (s.equalsIgnoreCase("y") || s.equalsIgnoreCase("n"))
				isValid = true;
			else
				System.out.println("Error! Please enter a y or n.");
		}
		return s;
	}

	public static String getLine(Scanner sc, String prompt) {
		System.out.print(prompt);
		String s = sc.nextLine(); // read the whole line
		return s;
	}

	public static String getString(Scanner sc, String prompt) {
		System.out.print(prompt);
		String s = sc.nextLine(); // read the first string on the line
		return s;
	}

	public static String getString(Scanner sc, String prompt, int length) {
		String s = "";
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			s = sc.next(); // read the first string on the line
			if (s.length() == length) {
				isValid = true;
			} else
				System.out.println("Error! length. Should be " + length + " characters.  Try again.");
			sc.nextLine(); // discard the rest of the line
		}
		return s;
	}

	public static String getStringChoice(Scanner sc, String prompt, String s1, String s2, String s3) {
		boolean isValid = false;
		String s = "";
		while (isValid == false) {
			System.out.print(prompt);
			s = sc.next(); // read the first string on the line
			sc.nextLine();
			if (s.equalsIgnoreCase(s1) || s.equalsIgnoreCase(s2) || s.equalsIgnoreCase(s3))
				isValid = true;
			else
				System.out.println("Error! Invalid answer. Must be " + s1 + " or " + s2 + " or " + s3 + ".");

		}
		return s;
	}

	public static String getStringChoice(Scanner sc, String prompt, String s1, String s2) {
		boolean isValid = false;
		String s = "";
		while (isValid == false) {
			System.out.print(prompt);
			s = sc.next(); // read the first string on the line
			sc.nextLine();
			if (s.equalsIgnoreCase(s1) || s.equalsIgnoreCase(s2))
				isValid = true;
			else
				System.out.println("Error! Invalid answer. Must be " + s1 + " or " + s2 + ".");

		}
		return s;
	}

	public static String getStringNumeric(Scanner sc, String prompt, int length) {
		String s = "";
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			if (sc.hasNextInt()) { // even though this is a String, the values
									// should be numeric
				s = sc.next(); // read the first string on the line
				int negativeCheck = Integer.parseInt(s);

				if (s.length() == length) {
					if (negativeCheck >= 0)
						isValid = true;
					else
						System.out.println("Error! Should be a positive integer.");
				}

				else
					System.out.println("Error! length. Should be " + length + " characters.  Try again.");
			} else
				System.out.println("Error! Should be a numeric value.  Try again.");
			sc.nextLine(); // discard the rest of the line
		}
		return s;
	}

	public static int getInt(Scanner sc, String prompt) {
		boolean isValid = false;
		int i = 0;
		while (isValid == false) {
			System.out.print(prompt);
			if (sc.hasNextInt()) {
				i = sc.nextInt();
				isValid = true;
			} else {
				System.out.println("Error! Invalid integer value. Try again.");
			}
			sc.nextLine(); // discard any other data entered on the line
		}
		return i;
	}

	public static String getIndividuality(Scanner sc, String prompt) {
		String s = "";
		boolean isValid = false;
		while (isValid == false) {
			System.out.print(prompt);
			s = sc.nextLine(); // read user entry
			if (s.equalsIgnoreCase("individual store") || s.equalsIgnoreCase("district total"))
				isValid = true;
			else
				System.out.println("Error! Please enter individual store or district total.");
		}
		return s;

	}

	public static int getInt(Scanner sc, String prompt, int min, int max) {
		int i = 0;
		boolean isValid = false;
		while (isValid == false) {
			i = getInt(sc, prompt);
			if (i <= min)
				System.out.println("Error! Number must be greater than " + min);
			else if (i >= max)
				System.out.println("Error! Number must be less than " + max);
			else
				isValid = true;
		}
		return i;
	}

	public static double getDouble(Scanner sc, String prompt) {
		boolean isValid = false;
		double d = 0;
		while (isValid == false) {
			System.out.print(prompt);
			if (sc.hasNextDouble()) {
				d = sc.nextDouble();
				isValid = true;
			} else {
				System.out.println("Error! Invalid decimal value. Try again.");
			}
			sc.nextLine(); // discard any other data entered on the line
		}
		return d;
	}

	public static double getDouble(Scanner sc, String prompt, double min, double max) {
		double d = 0;
		boolean isValid = false;
		while (isValid == false) {
			d = getDouble(sc, prompt);
			if (d <= min)
				System.out.println("Error! Number must be greater than " + min);
			else if (d >= max)
				System.out.println("Error! Number must be less than " + max);
			else
				isValid = true;
		}
		return d;
	}

}