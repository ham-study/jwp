package core.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());
        logger.debug(Arrays.toString(clazz.getDeclaredFields()));
        logger.debug(Arrays.toString(clazz.getDeclaredConstructors()));
        logger.debug(Arrays.toString(clazz.getDeclaredMethods()));

    }
    
    @Test
    void newInstanceWithConstructorArgs() throws Exception {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        Constructor<User> declaredConstructor = clazz.getDeclaredConstructor(String.class, String.class, String.class, String.class);
        User user = declaredConstructor.newInstance("id", "password", "name", "email");
        logger.debug("User = {}", user);
    }
    
    @Test
    void privateFieldAccess() throws Exception {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
        Student student = clazz.getDeclaredConstructor().newInstance();

        Field nameField = clazz.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(student, "name");

        Field ageField = clazz.getDeclaredField("age");
        ageField.setAccessible(true);
        ageField.set(student, 10);

        logger.debug("Student = {}", student);
    }
}
