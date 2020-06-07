package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Student;

import java.util.Scanner;

@Service
public class ApplicationServiceImpl implements ApplicationService{

    private ExaminationService examinationService;

    private StudentService studentService;

    @Autowired
    public ApplicationServiceImpl(ExaminationService examinationService, StudentService studentService){
        this.examinationService = examinationService;
        this.studentService = studentService;
    }

    @Override
    public void testing() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input surname: ");
        String surname = in.nextLine();
        System.out.print("Input name: ");
        String name = in.nextLine();

        Student student = studentService.create(name, surname);

        Examination examination = examinationService.create(student);
        examination.getQuestions().stream().forEach(question -> {
            System.out.println("question: " + question.getQuestion());
            String answer = in.nextLine();
            examination.addAnswerToMap(question, answer);
        });

        if (examination.checkTest()){
            System.out.println(getStudentInfo(examination.getStudent())  + " passed the test");
        } else {
            System.out.println(getStudentInfo(examination.getStudent()) + " not passed the test");
        }

        in.close();
    }

    private String getStudentInfo(Student student){
        return "Student " + student.getName() + " " + student.getSurname();
    }
}
