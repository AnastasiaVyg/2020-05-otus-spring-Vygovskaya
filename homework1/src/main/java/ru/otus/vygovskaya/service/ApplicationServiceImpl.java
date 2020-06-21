package ru.otus.vygovskaya.service;

import org.springframework.context.MessageSource;
import ru.otus.vygovskaya.config.ApplicationProperties;
import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Student;

import java.io.PrintStream;
import java.util.Scanner;

public class ApplicationServiceImpl implements ApplicationService{

    private final ExaminationService examinationService;

    private final StudentService studentService;

    private final ApplicationProperties properties;

    private final MessageSource messageSource;

    private final ScannerService scannerService;

    private final PrintStream output;

    public ApplicationServiceImpl(ExaminationService examinationService, StudentService studentService, ApplicationProperties properties,
                                  MessageSource messageSource, ScannerService scannerService, PrintStream output){
        this.examinationService = examinationService;
        this.studentService = studentService;
        this.properties = properties;
        this.messageSource = messageSource;
        this.scannerService = scannerService;
        this.output = output;
    }

    @Override
    public void testing() {
        output.print(messageSource.getMessage("input.surname", null, properties.getLocale()));
        String surname = scannerService.nextLine();
        output.print(messageSource.getMessage("input.name", null, properties.getLocale()));
        String name = scannerService.nextLine();

        Student student = studentService.create(name, surname);

        Examination examination = examinationService.create(student);
        examination.getQuestions().stream().forEach(question -> {
            output.println(question.getQuestion());
            String answer = scannerService.nextLine();
            examination.addAnswerToMap(question, answer);
        });

        if (examination.checkTest()){
            output.println(messageSource.getMessage("pass.test", getStudentInfo(examination.getStudent()), properties.getLocale()));
        } else {
            output.println(messageSource.getMessage("not.pass.test", getStudentInfo(examination.getStudent()), properties.getLocale()));
        }

    }

    private String[] getStudentInfo(Student student){
        return new String[]{student.getName(), student.getSurname()};
    }
}
