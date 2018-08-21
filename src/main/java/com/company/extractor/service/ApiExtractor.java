package com.company.extractor.service;

import com.company.extractor.api.dto.ApiData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

@Service
public class ApiExtractor {

    public ApiData extract() {
        return splitClasses(getClasses(getPathToJar()));
    }

    private ApiData splitClasses(Set<Class<?>> classes) {
        List<Class<?>> controller = classes.stream()
                .filter(c -> c.getName().contains("Controller"))
                .distinct()
                .collect(Collectors.toList());
        List<Class<?>> dtos = classes.stream()
                .filter(c -> c.getPackage().getName().contains("dto") & !c.getName().contains("Controller"))
                .distinct()
                .collect(Collectors.toList());
        List<Class<?>> enums = classes.stream()
                .filter(Class::isEnum)
                .distinct()
                .collect(Collectors.toList());
        return new ApiData(controller, dtos, enums);
    }

    private Set<Class<?>> getClasses(String jarFileName) {
        Set<Class<?>> classes = new HashSet<>();
        int indexOfClassExpansion = 6;
        int indexOfSpringClassPath = 17;

        try {
            JarFile file = new JarFile(new File(jarFileName));
            URL[] urls = {new URL("jar:file:" + jarFileName + "!/BOOT-INF/classes/")};
            URLClassLoader cl = URLClassLoader.newInstance(urls);

            Enumeration entry = file.entries();
            while (entry.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) entry.nextElement();
                String name = jarEntry.getName().replace("/", ".");
                if (name.contains("api.") && name.endsWith(".class")
                        || name.contains("controller.") && name.endsWith(".class")
                        || name.contains("dto.") && name.endsWith(".class")
                        || name.contains("enum") && name.endsWith(".class")) {
                    String str = name.substring(indexOfSpringClassPath);
                    Class c = cl.loadClass(str.substring(0, str.length() - indexOfClassExpansion));
                    classes.add(c);
                }
            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    private String getPathToJar() {
        Path path = Paths.get("").toAbsolutePath();
        String strPath = path.toString();
        String cleanPath = strPath.replaceAll("\\\\", "/");
        String projectName = cleanPath.substring(cleanPath.lastIndexOf("/") + 1);
        return cleanPath + "/build/libs/" + projectName + ".jar";
    }
}