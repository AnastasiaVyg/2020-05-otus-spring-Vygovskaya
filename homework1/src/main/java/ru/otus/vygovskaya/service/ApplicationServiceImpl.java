package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Student;

import java.io.PrintStream;
import java.util.Scanner;

public class ApplicationServiceImpl implements ApplicationService{

    private final ExaminationService examinationService;

    private final StudentService studentService;

    private final Scanner scanner;

    private final PrintStream output;

    public ApplicationServiceImpl(ExaminationService examinationService, StudentService studentService, Scanner scanner, PrintStream output){
        this.examinationService = examinationService;
        this.studentService = studentService;
        this.scanner = scanner;
        this.output = output;
    }

    @Override
    public void testing() {
        output.print("Input surname: ");
        String surname = scanner.nextLine();
        output.print("Input name: ");
        String name = scanner.nextLine();

        Student student = studentService.create(name, surname);

        Examination examination = examinationService.create(student);
        examination.getQuestions().stream().forEach(question -> {
            output.println("question: " + question.getQuestion());
            String answer = scanner.nextLine();
            examination.addAnswerToMap(question, answer);
        });

        if (examination.checkTest()){
            output.println(getStudentInfo(examination.getStudent())  + " passed the test");
        } else {
            output.println(getStudentInfo(examination.getStudent()) + " not passed the test");
        }

    }

    private String getStudentInfo(Student student){
        return "Student " + student.getName() + " " + student.getSurname();
    }
}
