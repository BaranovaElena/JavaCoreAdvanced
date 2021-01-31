public class Main {
    public static void main(String[] args) {
        //String[][] arrayString = {{"1","2","3","4"},{"1","2","3","4"}};
        //String[][] arrayString = {{"1","2"},{"3","4"},{"1","2"},{"3","4"}};
        String[][] arrayString = {{"1","2","3","4"},{"5","6","7","aaa"},
                                  {"1","2","3","4"},{"5","6","7","8"}};
        //String[][] arrayString = {{"1","2","3","4"},{"5","6","7","8"},
        //                          {"1","2","3","4"},{"5","6","7","8"}};
        try {
            System.out.println( sumUp(arrayString) );
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }

    }

    public static int sumUp(String[][] arrayOfStrings) throws MyArraySizeException, MyArrayDataException {
        if (arrayOfStrings.length != 4 || arrayOfStrings[0].length != 4) {
            throw new MyArraySizeException();
        }

        int sum = 0;
        for (int i=0; i<arrayOfStrings.length; i++) {
            for (int j=0; j<arrayOfStrings[0].length; j++) {
                try {
                    sum += Integer.parseInt(arrayOfStrings[i][j]);
                } catch (NumberFormatException nfe) {
                    throw new MyArrayDataException(i,j);
                }
            }
        }
        return sum;
    }
}
