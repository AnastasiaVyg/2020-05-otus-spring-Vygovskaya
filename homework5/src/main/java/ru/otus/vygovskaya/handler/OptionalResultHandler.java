package ru.otus.vygovskaya.handler;

import org.springframework.shell.result.TerminalAwareResultHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OptionalResultHandler extends TerminalAwareResultHandler<Optional> {

    @Override
    protected void doHandleResult(Optional result) {
        terminal.writer().println(result.orElse("don't get entity"));
    }
}
