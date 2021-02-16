import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Main {

    static final int SIZE = 10000000;

    public static void main(String[] args) {
        System.out.println("Time of serial method: " + countArraySerial());
        System.out.println("Time of parallel method: " + countArrayParallel());
    }

    private static long countArraySerial() {
        float[] arr = new float[SIZE];
        for (int i=0; i<SIZE; i++)
            arr[i] = 1;

        long beginTiming = System.currentTimeMillis();

        for (int i=0; i<SIZE; i++) {
            arr[i] = (float)(arr[i] * sin(0.2f+i/5.0f) * cos(0.2f+i/5.0f) * cos(0.4f+i/2.0f));
        }

        return System.currentTimeMillis() - beginTiming;
    }

    private static long countArrayParallel() {
        float[] arr = new float[SIZE];
        float[] a1 = new float[SIZE/2];
        float[] a2 = new float[SIZE/2];
        int h = SIZE/2;
        for (int i=0; i<SIZE; i++)
            arr[i] = 1;

        long beginTiming = System.currentTimeMillis();

        System.arraycopy(arr,0,a1,0,h);
        System.arraycopy(arr,h,a2,0,h);

        //создаем пул с двумя потоками
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Future<float[]>> list = new ArrayList<>();
        CallableCounter thread1 = new CallableCounter(a1,0);
        CallableCounter thread2 = new CallableCounter(a2,1);
        //вычисляем результаты свободными потоками
        list.add(executor.submit(thread1));
        list.add(executor.submit(thread2));

        //соединяем результаты
        try {
            System.arraycopy(list.get(0).get(),0,arr,0,h);
            System.arraycopy(list.get(1).get(),0,arr,h,h);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();

        return System.currentTimeMillis() - beginTiming;
    }
}
