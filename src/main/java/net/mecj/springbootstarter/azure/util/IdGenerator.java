package net.mecj.springbootstarter.azure.util;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class IdGenerator {
    public static String id() {
        return Generators.timeBasedGenerator().generate().toString();
    }

    public static String id(int length) {
        String temp = UUID.randomUUID().toString();
        while (temp.length() < length) {
            temp += UUID.randomUUID().toString();
        }
        return temp.replaceAll("-", "").substring(0, length);
    }

    public static String testId() {
        String id = id();
        return "(TEST)" + IdGenerator.id().substring(id.length() - 25);
    }
}
