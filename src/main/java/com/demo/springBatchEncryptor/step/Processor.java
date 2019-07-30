package com.demo.springBatchEncryptor.step;

import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<String, String> {

	@Override
	public String process(String data) throws Exception {
		return encrypt(data,4).toString();
	}

	public static StringBuffer encrypt(String text, int shift)
	{
		StringBuffer result= new StringBuffer();

		for (int i=0; i<text.length(); i++)
		{
			if (Character.isUpperCase(text.charAt(i)))
			{
				char ch = (char)(((int)text.charAt(i) +
						shift - 65) % 26 + 65);
				result.append(ch);
			}
			else
			{
				char ch = (char)(((int)text.charAt(i) +
						shift - 97) % 26 + 97);
				result.append(ch);
			}
		}
		return result;
	}
}
