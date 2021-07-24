import java.util.concurrent.RecursiveTask;

public class Sum extends RecursiveTask<Integer> {

    int[] arr;
    int start, end;

    public Sum(int[] arr) {
        this(arr, 0, arr.length);
    }

    private Sum(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        switch (end - start) {
            case 0:
                return 0;
            case 1:
                return arr[start];
            case 2:
                return arr[start] + arr[start + 1];
            default:
                return divideToParts();
        }
    }

    private int divideToParts() {
        int middle = (end + start) / 2;
        Sum sum1 = new Sum(arr, start, middle);
        Sum sum2 = new Sum(arr, middle, end);
        sum1.fork();
        return sum2.compute() + sum1.join();

    }

}
