import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ListaNomes {
    public static void main(String[] args) {

        List<String> names = List.of("Eve", "Alice", "Bob", "Charlie", "David");
        List<String> namesComplet2 = names.stream().filter(name -> name.length() > 3).map(null)

        Stream<String> streamNames = names.stream();
        Stream<String> namesFiltered = streamNames.filter(name -> name.length() > 3);
        Stream<String> namesFilteredUp = namesFiltered.map(name -> name.toUpperCase());
        Stream<String> namesFilteredUpSorted = namesFilteredUp.sorted();
        List<String> nameComplet = namesFilteredUpSorted.toList();

        System.out.println(nameComplet);
    }
}
