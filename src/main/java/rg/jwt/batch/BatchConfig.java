package rg.jwt.batch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Component
@Slf4j
public class BatchConfig {
	
	private final JobRegistry jobRegistry;
	
    @Bean
    public Job testJob(JobRepository jobRepository,PlatformTransactionManager transactionManager) throws DuplicateJobException {
       
    	Job job = new JobBuilder("testJob",jobRepository)
               .start(testStep(jobRepository,transactionManager))
               .build();
       
       ReferenceJobFactory factory = new ReferenceJobFactory(job);
       jobRegistry.register(factory);
       
       //출처: https://meteorkor.tistory.com/87 [Meteor:티스토리]
       return job;
    }

    @Bean
    public Step testStep(JobRepository jobRepository,PlatformTransactionManager transactionManager){
        Step step = new StepBuilder("testStep",jobRepository)
                .tasklet(testTasklet(null),transactionManager)
                .build();
        return step;
    }

    @Bean
    @StepScope
    public Tasklet testTasklet(@Value("#{jobParameters[time]}") String time) {
        return ((contribution, chunkContext) -> {

        	LocalDateTime now = LocalDateTime.now();
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        	String formattedDate = now.format(formatter);
        	
        	log.info(formattedDate + " : ***** hello batch! ***** : " + time);
        	
        	// 원하는 비지니스 로직 작성
        	
        	return RepeatStatus.FINISHED;
        });
    }
}
