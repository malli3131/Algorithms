package com.intuit.sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeCommentCount {
	public static void main(String[] args) {
		String line = "";
		int comment_count = 0;
		int literal_count = 0;
		int code_lines = 0;
		Pattern pattern = Pattern.compile("\".*?\"");
		Matcher matcher = null;
		try {
			BufferedReader br = new BufferedReader(
					new FileReader("/Users/nagainelu/Documents/workspace/Problems/src/com/intuit/sample/code"));
			while ((line = br.readLine()) != null) {
				if (line.contains("//")) {
					comment_count++;
				} else if (line.contains("/*")) {
					comment_count++;
					while (!line.contains("*/") && !(line = br.readLine()).contains("*/"))
						;
				} else {
					matcher = pattern.matcher(line);
					if (matcher.find()) {
						literal_count++;
					} else {
						System.out.println(line);
						code_lines++;
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Comment count: " + comment_count);
		System.out.println("Literal Count: " + literal_count);
		System.out.println("Code count: " + code_lines);
	}
}
