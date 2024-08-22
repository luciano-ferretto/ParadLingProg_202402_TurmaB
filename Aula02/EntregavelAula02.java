import java.util.List;

public class EntregavelAula02 {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 3, 2, 8, 7, 6, 5, 4, 9, 10);
        System.out.println("Lista Original: " + numbers);

        List<Integer> numbersEven = numbers.stream().filter(num -> num % 2 == 0).toList();
        System.out.println("Números Pares: " + numbersEven);

        List<Integer> numbersDoubled = numbers.stream().map(num -> num * 2).toList();
        System.out.println("Números dobrados: "+ numbersDoubled);

        List<Integer> numbersOrdened = numbers.stream().sorted().toList();
        System.out.println("Números ordenados: " + numbersOrdened);
        //numbers.stream().sorted().forEach(num -> System.out.println(num));

        System.out.println("Lista original - um item por linha:");
        numbers.stream().forEach(num -> System.out.println(num));
    }
    
}