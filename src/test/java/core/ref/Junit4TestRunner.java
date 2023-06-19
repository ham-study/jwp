package core.ref;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class Junit4TestRunner {
    @Test
    void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Arrays.stream(clazz.getDeclaredMethods())
            .filter(method -> method.getAnnotation(MyTest.class) != null)
            .forEach(method -> {
                try {
                    method.invoke(clazz.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
    }
}
