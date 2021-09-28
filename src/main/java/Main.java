import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    final static List<String> DEFAULT_LIST = Arrays.asList("Bob", "Alice", "Joe", "bob", "alice", "dEN");

    public static void main(String[] args) {
        assembleList(DEFAULT_LIST);
    }

    public static void assembleList(List<String> list) {
        Map<String, Long> namesCount = list.stream()
                .filter(name -> Objects.nonNull(name) && !name.trim().isEmpty())
                .map(String::trim)
                .map(name -> name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, TreeMap::new));
        namesCount.forEach((name, count) -> System.out.println(name + " : " + count));
    }
}
