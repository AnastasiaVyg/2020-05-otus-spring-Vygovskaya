package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IoServiceImpl implements IoService {

    private final Scanner scanner;

    private final PrintStream printStream;

    public IoServiceImpl(@Value("#{ T(java.lang.System).in}") InputStream inputStream, @Value("#{ T(java.lang.System).out}") PrintStream printStream){
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public void println(String text) {
        printStream.println(text);
    }
}
