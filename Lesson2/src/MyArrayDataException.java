public class MyArrayDataException extends Exception{
    public MyArrayDataException(int i, int j) {
        super("Not a number in Array["+i+"]["+j+"]");
    }
}
