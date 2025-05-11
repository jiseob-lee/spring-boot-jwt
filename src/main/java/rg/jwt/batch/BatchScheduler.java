package rg.jwt.batch;

import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rg.jwt.controller.AuthApiController;

@Configuration
@RequiredArgsConstructor
@Component
@Slf4j
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    /*
    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor processor = new JobRegistryBeanPostProcessor();
        processor.setJobRegistry(jobRegistry);
        return processor;
    }
    */

    @Bean
    //@Lazy
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(){
        JobRegistryBeanPostProcessor jobProcessor = new JobRegistryBeanPostProcessor();
        jobProcessor.setJobRegistry(jobRegistry);
        return jobProcessor;
    }
    
    /*
    @Bean
    @DependsOn("jobRegistryBeanPostProcessor")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/rg?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true")
                .username("rg")
                .password("jiseob9123")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
	*/
	
    @Scheduled(cron = "0 0/5 * * * *") // 5분마다 실행
    public void runJob() {
        String time = LocalDateTime.now().toString();
        log.info("time : " + time);
        try {
            Job job = jobRegistry.getJob("testJob"); // job 이름
            JobParametersBuilder jobParam = new JobParametersBuilder().addString("time", time);
            jobLauncher.run(job, jobParam.toJobParameters());
        } catch (NoSuchJobException e) {
            throw new RuntimeException(e);
        } catch (JobInstanceAlreadyCompleteException |
                 JobExecutionAlreadyRunningException |
                 JobParametersInvalidException |
                 JobRestartException e
        ) {
            throw new RuntimeException(e);
        }
    }
}
