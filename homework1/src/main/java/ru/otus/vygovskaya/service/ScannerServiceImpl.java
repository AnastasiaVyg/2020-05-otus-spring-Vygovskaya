package ru.otus.vygovskaya.service;

import java.io.InputStream;
import java.util.Scanner;

public class ScannerServiceImpl implements ScannerService{

    private final Scanner scanner;

    public ScannerServiceImpl(InputStream inputStream){
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }
}
