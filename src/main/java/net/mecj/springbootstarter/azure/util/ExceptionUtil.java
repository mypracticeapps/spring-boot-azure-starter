package net.mecj.springbootstarter.azure.util;

public class ExceptionUtil {

    public interface RunnableWithException {
        void run() throws Exception;
    }

    public static void suppress(RunnableWithException runnableWithException) {

    }
}