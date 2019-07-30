package com.demo.springBatchEncryptor.step;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Reader implements ItemReader<String> {

	private static BufferedReader reader = null;

	String fileName;

	public Reader(String fileName) {
		this.fileName = fileName;
		if (null == reader) {
			try{
				reader = new BufferedReader(new FileReader(this.fileName));
			}catch(FileNotFoundException ex){
				ex.printStackTrace(System.out);
			}
		}
	}

	@Override
	public synchronized String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return reader.readLine();
	}

}