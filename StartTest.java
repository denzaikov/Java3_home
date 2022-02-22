package ru.denzaikov.geekbrains;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StartTest {
    public static void start(Class c){
        List<Method> methodList = new ArrayList<>();
        Method[] classMethods = c.getDeclaredMethods();

        for(Method m: classMethods) {
            if (m.isAnnotationPresent(Test.class)) {
                methodList.add(m);
            }
        }
        methodList.sort(Comparator
                .comparingInt((Method i) -> i.getAnnotation(Test.class).priority())
                .reversed());

        for (Method m : classMethods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                if (methodList.size() > 0 && methodList.get(0).isAnnotationPresent(BeforeSuite.class)){
                    throw new RuntimeException("@BeforeSuite annotation method > 1");
                }
                methodList.add(0, m);
            }
        }
        for (Method m : classMethods) {
            if (m.isAnnotationPresent(AfterSuite.class)) {
                if (methodList.size() > 0 && methodList.get(methodList.size() - 1).isAnnotationPresent(AfterSuite.class)){
                    throw new RuntimeException("@AfterSuite annotation method > 1");
                }
                methodList.add(m);
            }
        }

        for (Method m : methodList) {
            try {
                m.invoke(null);
            } catch (IllegalAccessException e){
                e.printStackTrace();
            } catch (InvocationTargetException e){
                e.printStackTrace();
            }
        }
    }
}
