package core.ref;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class Junit3TestRunner {
    @Test
    void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Arrays.stream(clazz.getMethods())
            .filter(method -> method.getName().startsWith("test"))
            .forEach(method -> {
                try {
                    method.invoke(clazz.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
    }
}
