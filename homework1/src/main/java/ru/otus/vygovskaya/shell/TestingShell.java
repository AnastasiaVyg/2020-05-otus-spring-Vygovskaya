package ru.otus.vygovskaya.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.vygovskaya.domain.Student;
import ru.otus.vygovskaya.service.ApplicationService;

@ShellComponent
public class TestingShell {

    private final ApplicationService applicationService;

    private Student student;

    public TestingShell(ApplicationService applicationService){
        this.applicationService = applicationService;
    }

    @ShellMethod(key = {"login", "l"}, value = "login command")
    public String login(@ShellOption(defaultValue = "Krasilov") String surname,@ShellOption(defaultValue = "Aleksey") String name){
        student = applicationService.login(surname, name);
        return "Student " + student.getSurname() + " " + student.getName() + " is loging";
    }

    @ShellMethod(key = {"testing", "t"}, value = "test command")
    @ShellMethodAvailability(value = "isCanTesting")
    public String testing(){
        applicationService.testing();
        return "test is complete";
    }

    private Availability isCanTesting(){
        return student == null ? Availability.unavailable("сначала залогиньтесь") : Availability.available();
    }

}
