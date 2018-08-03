package com.company.extractor.service;

import com.company.extractor.api.dto.ApiPair;
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
public class Extractor {

    public ApiPair extract() {
        return splitClasses(getClasses(getPathToJar()));
    }

    // adapt to service
    private ApiPair splitClasses(Set<Class<?>> classes) {
        return new ApiPair(
                classes.stream()
                        .filter(c -> c.getName().contains("Controller"))
                        .collect(Collectors.toList()),
                classes.stream()
                        .filter(c -> c.getPackage().getName().contains("dto") & !c.getName().contains("Controller"))
                        .collect(Collectors.toList()));
    }

    private Set<Class<?>> getClasses(String jarFileName) {
        Set<Class<?>> classes = new HashSet<>();
        try {
            JarFile file = new JarFile(new File(jarFileName));
            URL[] urls = {new URL("jar:file:" + jarFileName + "!/BOOT-INF/classes/")};
            URLClassLoader cl = URLClassLoader.newInstance(urls);

            for (Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements(); ) {
                JarEntry jarEntry = entry.nextElement();
                String name = jarEntry.getName().replace("/", ".");

                if (name.contains("api.") && name.endsWith(".class")) {
                    String str = name.substring(17);
                    Class c = cl.loadClass(str.substring(0, str.length() - 6));
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