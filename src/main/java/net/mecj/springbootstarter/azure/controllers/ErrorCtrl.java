package net.mecj.springbootstarter.azure.controllers;

import net.mecj.springbootstarter.azure.AzureStarterApplication;
import net.mecj.springbootstarter.azure.apiresponse.errordetail.ErrorDetail;
import net.mecj.springbootstarter.azure.util.ClassFinder;
import net.mecj.springbootstarter.azure.util.ClassUtil;
import net.mecj.springbootstarter.azure.util.EnumUtil;
import net.mecj.springbootstarter.azure.util.YmlUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "/internal/error")
@ConditionalOnExpression("${application.development.endpoints.errorctrl:false}")
public class ErrorCtrl {
    private static Object lock = new Object();
    private static final String STATUS_CODE_FILE = "rest-status-codes.yml";

    private List errorCodes = null;
    private List<Map<String, Object>> statusCodes;

    private void initErrorCodes() {
        String packageName = AzureStarterApplication.class.getPackage().getName();
        Set<String> errorDetailImplementationClass = new ClassFinder(ErrorDetail.class, packageName).getClasses();
        Set<Class> classSet = ClassUtil.forStringValues(errorDetailImplementationClass);
        this.errorCodes = EnumUtil.values(classSet);
    }

    private void initStatusCodes() {
        try {
            List<Map<String, Object>> _conf = YmlUtil.loadAsList(STATUS_CODE_FILE);
            for (Map<String, Object> map : _conf) {
                if (map.containsKey("whenToUse")) {
                    map.remove("whenToUse");
                }
            }
            this.statusCodes = _conf;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @GetMapping(path = "/codes")
    public List errorCodes() {
        if (errorCodes == null) {
            synchronized (lock) {
                if (errorCodes == null) {
                    this.errorCodes = new LinkedList();
                    initErrorCodes();
                }
            }
        }
        return errorCodes;
    }

    @GetMapping(path = "/status")
    public List<Map<String, Object>> statusCodes() {
        if (statusCodes == null) {
            synchronized (lock) {
                if (statusCodes == null) {
                    initStatusCodes();
                }
            }
        }
        return statusCodes;
    }
}
