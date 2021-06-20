package net.mecj.springbootstarter.azure.webrest.internal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class InstanceCtrl {
    private static String id;

    static {
        id = UUID.randomUUID().toString();
    }

    @GetMapping(path = "/internal/id")
    public String id() {
        return id;
    }
}
