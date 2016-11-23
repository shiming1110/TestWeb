package com.demo.jaxrs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class TestJaxRsApplication extends Application{

    @Override  
    public Set<Class<?>> getClasses() {  

          Set<Class<?>> classes = new HashSet<Class<?>>();  

          classes.add(TestJaxRsService.class);  

          return classes;  

    }  
    
}
