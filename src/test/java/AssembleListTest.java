import Utils.CustomPrintStream;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.*;

public class AssembleListTest {

    @DataProvider
    public static Object[][] countingData() {
        return new Object[][] {
                new Object[] {Arrays.asList("Bob", "den", "DEN", "bob", "DeN", "dEN"),
                        Arrays.asList("Den : 4", "Bob : 2")},
                new Object[] {Arrays.asList("Foo", "BAR", "bAR", "oZZy", "baR", "OZzY", "bAr"),
                        Arrays.asList("Foo : 1", "Bar : 4", "Ozzy : 2")}
        };
    }

    @DataProvider
    public static Object[][] lexicographicData() {
        return new Object[][]{
                new Object[]{Arrays.asList("Ash", " AsH", "ASH", "ash", "aSH ", "BOB", "        boB", "aSHly", "Ashly"),
                        Arrays.asList("Ash : 5", "Ashly : 2", "Bob : 2")},
                new Object[]{Arrays.asList("Ange ","Foo", "BAR", " bAR", "aNGE ", "oZZy ", "baR", "OZzY", "bAr", "AnGE"),
                        Arrays.asList("Ange : 3", "Bar : 4", "Foo : 1", "Ozzy : 2")}
        };
    }

    @DataProvider
    public static Object[][] bigArrData() {
        return new Object[][]{
                new Object[]{Arrays.asList("Bob", "Alice", "Joe", "bob", "alice", "dEN",
                        "Bob", "Alice", "Joe", "bob", "alice", "dEN", "Bob", "Alice", "Joe", "bob", "alice", "dEN",
                        "Bob", "Alice", "Joe", "bob", "alice", "dEN", "Joe", "Bob", "Alice", "Joe", "bob", "alice",
                        "dEN", "Bob", "Alice", "Joe", "bob", "alice", "dEN", "Joe", "Bob", "Alice", "Joe", "bob",
                        "alice", "dEN", "Bob", "Alice", "Joe", "bob", "alice", "dEN", "Joe"),
                        Arrays.asList("Alice : 16", "Joe : 11", "Bob : 16")}
        };
    }

    @DataProvider(name = "invalidData")
    public Object[][] invalidData() {
        return new Object[][] {
                new Object[] {Arrays.asList("Alice", null, "bob", "bOB", null), Arrays.asList("Alice : 1", "Bob : 2")},
                new Object[] {Arrays.asList("graM", "", "Joe", null), Arrays.asList("Gram : 1", "Joe : 1")},
                new Object[] {Arrays.asList("MannY", "", "", "fEn"), Arrays.asList("Manny : 1", "Fen : 1")},
                new Object[] {Collections.emptyList(), Collections.emptyList()}
        };
    }

    @Test(dataProvider = "lexicographicData", description = "Check that sort and case are correct")
    void lexicographicTest(List<String> input, List<String> expected) {
        TreeSet<String> actual = getAssembledListAsSet(input);
        int[] index = {0};
        expected.forEach(name -> {
            String actualName = actual.first();
            String expectedName = expected.get(index[0]);
            actual.remove(actualName);
            Assert.assertEquals(actualName, expectedName,
                    "Actual result: " + actualName + " doesn't equal " + expectedName);
            index[0]++;
        });
    }

    @Test(dataProvider = "countingData", description = "Check that counting is correct")
    void countingTest(List<String> input, List<String> expected) {
        execMethodCheckContentContains(input, expected);
    }

    @Test(dataProvider = "bigArrData", description = "Check that counting is correct")
    void bigArrayTest(List<String> input, List<String> expected) {
        execMethodCheckContentContains(input, expected);
    }

    @Test(dataProvider = "invalidData", description = "Check working with null/empty objects")
    void invalidDataTest(List<String> input, List<String> expected) {
        execMethodCheckContentContains(input, expected);
    }

    private void execMethodCheckContentContains(List<String> input, List<String> expected) {
        String actual = getAssembledListAsString(input);
        expected.forEach(exp -> Assert.assertTrue(actual.contains(exp),
                "Actual result: \n" + actual + " doesn't contain " + exp));
    }

    
    private String getAssembledListAsString(List<String> input) {
        OutputStream out = new ByteArrayOutputStream();
        PrintStream oldPs = System.out;
        PrintStream newPs = new PrintStream(out);
        System.setOut(newPs);
        Main.assembleList(input);
        System.out.flush();
        System.setOut(oldPs);
        return out.toString();
    }

    private TreeSet<String> getAssembledListAsSet(List<String> input) {
        PrintStream oldPs = System.out;
        CustomPrintStream newPs = new CustomPrintStream();
        System.setOut(newPs);
        Main.assembleList(input);
        System.out.flush();
        System.setOut(oldPs);
        return newPs.getList();
    }
}
