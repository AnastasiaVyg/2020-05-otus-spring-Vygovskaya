package ru.otus.vygovskaya.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class BatchCommands {

    private static final String RUN_ID_KEY = "run.id";

    private final Job importEntities;
    private final JobLauncher jobLauncher;

    public BatchCommands(JobLauncher jobLauncher,  Job importEntities){
        this.jobLauncher = jobLauncher;
        this.importEntities = importEntities;
    }

    @ShellMethod(value = "startMigrationJob", key = {"startMigration", "sm"})
    public void startMigrationJob() throws Exception {
        JobExecution execution = jobLauncher
                .run(importEntities, new JobParametersBuilder().addLong(RUN_ID_KEY, System.currentTimeMillis()).toJobParameters());
        System.out.println(execution);
    }

}
