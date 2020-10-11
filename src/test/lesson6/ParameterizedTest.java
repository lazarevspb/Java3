package test.lesson6;

import lesson6.Main;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

public class ParameterizedTest {


    @RunWith(Parameterized.class)
    public static class MainTest {
        private final int[] actual;
        private final int[] expected;
        private final int[] actualArray2;
        private final boolean boolExpected;
        private final int[] actualArrayException;
        int[] actualArrayExceptionExpected;

        public MainTest(int[] actual, int[] expected, int[] actualArray2, boolean boolExpected,
                        int[] actualArrayException, int[] actualArrayExceptionExpected) {

            this.actual = actual;
            this.expected = expected;
            this.actualArray2 = actualArray2;
            this.boolExpected = boolExpected;
            this.actualArrayException = actualArrayException;
            this.actualArrayExceptionExpected = actualArrayExceptionExpected;
        }

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}, new int[]{1, 7}, new int[]{0, 0, 0}, false, new int[]{0, 3, 2, 1}, new int[]{0, 3, 2, 1},},  //0
                    {new int[]{4, 3, 2, 1}, new int[]{3, 2, 1}, new int[]{3, 3, 4}, true, new int[]{1, 3, 2, 1}, new int[]{3, 2, 1},},                  //1
                    {new int[]{1, 2, 4, 3}, new int[]{3}, new int[]{2, 2, 2}, false, new int[]{1, 1, 1, 1}, new int[]{1, 1, 1, 1},},                    //2
                    {new int[]{1, 1, 4}, new int[]{1, 1, 4}, new int[]{3, 2, 1}, true, new int[]{0, 3, 2, 1}, new int[]{0, 3, 2, 1},},                  //3
                    {new int[]{4}, new int[]{4}, new int[]{1}, true, new int[]{1, 2, 2, 1}, new int[]{1, 2, 2, 1},},                                    //4
                    {new int[]{1, 2, 4, 4, 2, 3, 5, 1, 7}, new int[]{2, 3, 5, 1, 7}, new int[]{}, false, new int[]{1, 1, 1, 1}, new int[]{1, 1, 1, 1},},//5
            });
        }

        @Test
        public void makeAnArrayAfterFour() {
            assertArrayEquals(expected, Main.makeAnArrayAfterFour(actual));
        }

        @Ignore
        @Test
        public void makeAnArrayAfterFourTestIgnore() {
            assertArrayEquals(actualArray2, Main.makeAnArrayAfterFour(actual));
        }

        @Test
        public void makeAnArrayAfterFourException() {
            try {
                assertArrayEquals(actualArrayExceptionExpected, Main.makeAnArrayAfterFour(actualArrayException));
                Assert.fail("Expected RuntimeException Test notPassed");
            } catch (RuntimeException thrown) {
                Assert.assertNotEquals("Expected Test", thrown.getMessage());
            }
        }

        @Test
        public void lookingForOneOrFour() {
            Assert.assertEquals(Main.lookingForOneOrFour(actualArray2), boolExpected);
        }
    }
}


