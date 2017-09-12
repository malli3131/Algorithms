package com.intuit.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeCommentCount {

	public static void main(String[] args) throws IOException {
		int comment_count = 0;
		int literal_count = 0;
		int code_count = 0;
		Pattern pattern = Pattern.compile("\".*?\"");
		Matcher matcher = null;
		BufferedReader reader = new BufferedReader(
				new FileReader(new File("/Users/nagainelu/Documents/workspace/Problems/src/com/intuit/sample/code")));
		String line = "";
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("//")) {
				comment_count++;
			} else if (line.startsWith("/*")) {
				comment_count++;
				while (!line.endsWith("*/") && !(line = reader.readLine()).endsWith("*/"))
					;
			} else {
				matcher = pattern.matcher(line);
				if (matcher.find()) {
					System.out.println(line);
					literal_count++;
				} else {
					code_count++;
				}
			}
		}
		reader.close();
		System.out.println("Comment Count: " + comment_count);
		System.out.println("Literal Count: " + literal_count);
		System.out.println("Code Count: " + code_count);
	}
}
