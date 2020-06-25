package ru.otus.vygovskaya.service;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.config.ApplicationProperties;
import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Student;

@Service
public class ApplicationServiceImpl implements ApplicationService{

    private final ExaminationService examinationService;

    private final StudentService studentService;

    private final ApplicationProperties properties;

    private final MessageSource messageSource;

    private final IoService ioService;

    private Student student;

    private Examination examination;

    @Autowired
    public ApplicationServiceImpl(ExaminationService examinationService, StudentService studentService, ApplicationProperties properties,
                                  MessageSource messageSource, IoService ioService){
        this.examinationService = examinationService;
        this.studentService = studentService;
        this.properties = properties;
        this.messageSource = messageSource;
        this.ioService = ioService;
    }

    @Override
    public Student login(String surname, String name) {
        Preconditions.checkNotNull(surname, "Not null surname");
        Preconditions.checkNotNull(name, "not null name");
        student = studentService.create(name, surname);
        return student;
    }

    @Override
    public void testing() {
        examination = examinationService.create(student);
        examination.getQuestions().stream().forEach(question -> {
            ioService.println(question.getQuestion());
            String answer = ioService.nextLine();
            examination.addAnswerToMap(question, answer);
        });

        if (examination.checkTest()){
            ioService.println(messageSource.getMessage("pass.test", getStudentInfo(examination.getStudent()), properties.getLocale()));
        } else {
            ioService.println(messageSource.getMessage("not.pass.test", getStudentInfo(examination.getStudent()), properties.getLocale()));
        }

    }

    private String[] getStudentInfo(Student student){
        return new String[]{student.getName(), student.getSurname()};
    }
}
