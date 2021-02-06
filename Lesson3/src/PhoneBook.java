import java.util.*;

public class PhoneBook {
    private Map<String, ArrayList<String>> book;

    public PhoneBook() {
        book = new HashMap<>();
        book.put("Petrov", new ArrayList<>(Arrays.asList("89000000000")));
        book.put("Ivanov", new ArrayList<>(Arrays.asList("89000000001", "89000000002")));
        book.put("Smirnov", new ArrayList<>(Arrays.asList("89000000003")));
    }

    public Map<String, ArrayList<String>> getBook() {
        return book;
    }

    public void add(String name, String phone){
        if (!book.containsKey(name))
            book.put(name, new ArrayList<>(Arrays.asList(phone)));
        else {
            ArrayList<String> list = book.get(name);
            list.add(phone);
            book.replace(name, list);
        }
    }

    public void add(String name, ArrayList<String> phones) {
        if (!book.containsKey(name))
            book.put(name, phones);
        else {
            ArrayList<String> list = book.get(name);
            list.addAll(phones);
            book.replace(name, list);
        }
    }

    public ArrayList<String> get(String name){
        return book.get(name);
    }
}
