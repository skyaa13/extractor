package com.company.extractor.entity;

import java.lang.reflect.Method;
import java.util.List;

public class ControllerMethods {
    private List<Method> getMethods;
    private List<Method> postMethods;
    private List<Method> putMethods;
    private List<Method> patchMethods;

    public List<Method> getGetMethods() {
        return getMethods;
    }

    public void setGetMethods(List<Method> getMethods) {
        this.getMethods = getMethods;
    }

    public List<Method> getPostMethods() {
        return postMethods;
    }

    public void setPostMethods(List<Method> postMethods) {
        this.postMethods = postMethods;
    }

    public List<Method> getPutMethods() {
        return putMethods;
    }

    public void setPutMethods(List<Method> putMethods) {
        this.putMethods = putMethods;
    }

    public List<Method> getPatchMethods() {
        return patchMethods;
    }

    public void setPatchMethods(List<Method> patchMethods) {
        this.patchMethods = patchMethods;
    }
}
