import java.util.*;

public class Main {
    public static void main(String[] args) {
        //task1
        String[] arrayOfString = {"one", "two",   "three", "four",  "five",
                                  "six", "seven", "eight", "nine",  "ten",
                                  "two", "two",   "six",   "eight", "one"};

        //список уникальных слов
        Set<String> setOfString = new HashSet<>(Arrays.asList(arrayOfString));
        System.out.println(setOfString);

        //подсчет количества вхождений слов
        Map<String, Integer> mapOfString = new HashMap<>();
        for (String s : arrayOfString) {
            if (!mapOfString.containsKey(s))
                mapOfString.put(s, 1);
            else
                mapOfString.replace(s, mapOfString.get(s)+1);
        }
        System.out.println(mapOfString);

        //task 2
        PhoneBook phoneBook = new PhoneBook();
        System.out.println(phoneBook.getBook());

        phoneBook.add("Petrov", "89990000000");
        System.out.println(phoneBook.getBook());

        ArrayList<String> phonesList = new ArrayList<>(
                Arrays.asList("89999990000", "89999999900"));
        phoneBook.add("Smirnov", phonesList);
        System.out.println(phoneBook.getBook());

        ArrayList<String> phones = phoneBook.get("Petrov");
        System.out.println(phones);
    }
}
