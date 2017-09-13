package com.intuit.sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Base64;

public class Main {
	public static void main(String[] args) throws Exception {
		PipedWriter producer = new PipedWriter();
		PipedReader consumer = new PipedReader(producer);

		PipedOutputStream producer1 = new PipedOutputStream();
		PipedInputStream consumer1 = new PipedInputStream(producer1);

		StringProducer np = new StringProducer(producer);
		StringConsumer nc = new StringConsumer(consumer, producer1);
		EncodedConsumer ec = new EncodedConsumer(consumer1);

		np.start();
		nc.start();
		ec.start();
	}
}

class StringProducer extends Thread {
	BufferedWriter bw;

	public StringProducer(Writer w) {
		this.bw = new BufferedWriter(w);
	}

	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					new File("/Users/nagainelu/Documents/workspace/Problems/src/com/intuit/sample/code")));
			String line = "";
			while ((line = reader.readLine()) != null) {
				bw.write(line);
				bw.newLine();
				bw.flush();
				Thread.sleep(1000);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class StringConsumer extends Thread {
	BufferedReader br;
	OutputStream bas;

	public StringConsumer(Reader r, OutputStream bas) {
		br = new BufferedReader(r);
		this.bas = bas;
	}

	public void run() {
		try {
			String line;
			while ((line = br.readLine()) != null) {
				byte[] data = Base64.getEncoder().encode(line.getBytes());
				System.out.println(data);
				bas.write(data);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}

class EncodedConsumer extends Thread {
	InputStream bas;

	public EncodedConsumer(InputStream bas) {
		this.bas = bas;
	}

	public void run() {
		try {
			int bytes;
			while ((bytes = bas.read()) != -1) {
				System.out.println(bytes);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
