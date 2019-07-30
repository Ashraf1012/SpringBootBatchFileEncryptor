package com.demo.springBatchEncryptor.config;

import com.demo.springBatchEncryptor.listener.JobCompletionListener;
import com.demo.springBatchEncryptor.step.Processor;
import com.demo.springBatchEncryptor.step.Reader;
import com.demo.springBatchEncryptor.step.Writer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Value("${demo.springBatchEncryptor.fileName}")
	private String fileName;

	@Value("${demo.springBatchEncryptor.threads}")
	private String numOfThreads;

	@Value("${demo.springBatchEncryptor.encryptedFile}")
	private String outputPath;

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(Integer.parseInt(numOfThreads));
		threadPoolTaskExecutor.setMaxPoolSize(Integer.parseInt(numOfThreads));
		threadPoolTaskExecutor.setThreadNamePrefix("encrypt_task_executor_thread");
		threadPoolTaskExecutor.initialize();
		return threadPoolTaskExecutor;
	}

	@Bean
	public Job processJob() {
		return jobBuilderFactory.get("processJob")
				.incrementer(new RunIdIncrementer()).listener(listener())
				.flow(orderStep1()).end().build();
	}

	@Bean
	public Step orderStep1() {
		return stepBuilderFactory.get("orderStep1").<String, String	> chunk(1)
				.reader(new Reader(fileName))
				.processor(new Processor())
				.writer(new Writer(outputPath))
				.taskExecutor(taskExecutor())
				.listener(listener())
				.throttleLimit(Integer.parseInt(numOfThreads))
				.build();
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}

}
