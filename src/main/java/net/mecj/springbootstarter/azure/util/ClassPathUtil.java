package net.mecj.springbootstarter.azure.util;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ClassPathUtil {
    public static String readAsString(String name) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:" + name);

        try {
            try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
                return FileCopyUtils.copyToString(reader);
            } catch (IOException e) {

            }
        } catch (Exception ex) {

        }
        return "";
    }
}
