package ru.otus.vygovskaya.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    private Utils() {
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
