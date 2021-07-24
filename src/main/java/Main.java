import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;

public class Main {

    final static int ARRAY_SIZE = 1_000;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int[] arr = new int[ARRAY_SIZE];
        int sum;
        long start, end;
        fillArray(arr);
        System.out.println(Arrays.toString(arr));

        System.out.println("\nпосчиатем сумму в одном потоке");
        start = System.currentTimeMillis();
        sum = oneThreadSum(arr);
        System.out.println("Сумма = " + sum);
        end = System.currentTimeMillis();
        System.out.println("это заняло " + (end - start) + " мил сек");

        System.out.println("\nпосчиатем сумму в стриме");
        start = System.currentTimeMillis();
        sum = IntStream.of(arr).sum();
        end = System.currentTimeMillis();
        System.out.println("Сумма = " + sum);
        end = System.currentTimeMillis();
        System.out.println("это заняло " + (end - start) + " мил сек");

        System.out.println("\nпосчиатем сумму в нескольких потоках");
        start = System.currentTimeMillis();
        final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        Sum sumArr = new Sum(arr);
        final ForkJoinTask<Integer> result = forkJoinPool.submit(sumArr);
        System.out.println("Сумма = " + result.join());
        end = System.currentTimeMillis();
        System.out.println("это заняло " + (end - start) + " мил сек");

    }

    private static int oneThreadSum(int[] arr) {
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            result += arr[i];
        }
        return result;
    }

    private static void fillArray(int[] arr) {
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
        }
    }
}
