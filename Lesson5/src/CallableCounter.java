import java.util.concurrent.Callable;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class CallableCounter implements Callable<float[]>{
    private float[] array;
    //так как результат зависит от индекса массива, а массив разбился на части,
    //чтобы корректно считался результат, введем понятие "часть массива"
    private int part;
    public CallableCounter(float[] array,int part){
        this.array = array;
        this.part = part;
    }

    @Override
    public float[] call() {
        for (int i=0; i<array.length; i++)
            array[i] = (float)(array[i] *
                    sin(0.2f+(i+part*array.length)/5.0f) *
                    cos(0.2f+(i+part*array.length)/5.0f) *
                    cos(0.4f+(i+part*array.length)/2.0f));
        return array;
    }
}
