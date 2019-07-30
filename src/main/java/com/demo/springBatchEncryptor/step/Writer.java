package com.demo.springBatchEncryptor.step;

import org.springframework.batch.item.ItemWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class Writer implements ItemWriter<String> {

	private String outputPath;

	private FileWriter writer;

	public Writer(String outputPath) {
		this.outputPath = outputPath;
		try {
			 writer = new FileWriter(new File(outputPath + "encrypted_" + LocalTime.now().getNano() + ".enc"));
		}
		catch(IOException ex){
			ex.printStackTrace(System.out);
		}
	}

	public void write(List<? extends String> items) throws Exception {

		for (String line : items) {
			writer.append(line + "\n");
		}
	}

	@Override
	protected void finalize() throws Throwable {
		writer.close();
	}
}