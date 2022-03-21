package search;

public class BinarySearch {

    // Контракт
    // Pred: A i array[i] >= array[i + 1] && ((left == -1 && right == array.size()) ||
    // || ((right : array[right] <= num || right == array.size()) &&
    // && (left : array[left] > num || left == -1) && right - 1 >= left))

    // Post: (right : array[right] <= num || right == array.size()) &&
    // && right - 1 == left && (left : array[left] > num || left == -1)
    public static int recurBinSearch(int[] array, int num, int left, int right) {
        // ((left == -1 && right == array.size()) ||
        // (right : array[right] <= num || right == array.size()) &&
        // && (left : array[left] > num || left == -1) && right - 1 >= left))
        if (right - left <= 1) {
            // right - 1 == left
            // (right : array[right] <= num || right == array.size()) &&
            // && right - 1 && (left : array[left] > num || left == -1)
            return right;
        }
        // ((left == -1 && right == array.size()) ||
        // (right : array[right] <= num || right == array.size()) &&
        // && (left : array[left] > num || left == -1) && right - 1 >= left))
        int m = (left + right) / 2;
        // ((left == -1 && right == array.size()) ||
        // (right : array[right] <= num || right == array.size()) &&
        // && (left : array[left] > num || left == -1) && right - 1 >= left))
        if (array[m] > num) {
            // (right : array[right] <= num || right == array.size()) && (left : array[m] > num) && right - 1 >= left
            return recurBinSearch(array, num, m, right);
        }
        // (right : array[m] <= num) && (left : array[left] > num || left == -1) && right - 1 >= left
        return recurBinSearch(array, num, left, m);
    }

    // Контракт
    // Pred: A i array[i] >= array[i + 1]
    // Post: (right : array[right] <= num || right == array.size()) &&
    // && right - 1 == left && (left : array[left] > num || left == -1)
    public static int iterBinSearch(int[] array, int num) {
        //  A i array[i] >= array[i + 1]
        int left = -1;
        // left : array[left] > num || left == -1
        //  A i array[i] >= array[i + 1]
        int right = array.length;
        // (right : array[right] <= num || right == array.size()) &&
        // && (left : array[left] > num || left == -1)
        while (right - 1 > left) {
            // Inv: right - 1 > left
            
            // (right : array[right] <= num || right == array.size()) &&
            // && (left : array[left] > num || left == -1)
            int m = (left + right) / 2;
            // (right : array[right] <= num || right == array.size()) &&
            // && (left : array[left] > num || left == -1)
            if (array[m] > num) {
                // (right : array[right] <= num || right == array.size()) && (left : array[m] > num)
                left = m;
                // (right : array[right] <= num || right == array.size()) && (left : array[left] > num)
            }
            else {
                // (right : array[m] <= num) && (left : array[left] > num || left == -1)
                right = m;
                //(right : array[right] <= num) && (left : array[left] > num || left == -1)
            }
            // (right : array[right] <= num || right == array.size()) && (left : array[left] > num || left == -1)
        }
        // right - 1 == left
        // (right : array[right] <= num || right == array.size()) &&
        // && right - 1 && (left : array[left] > num || left == -1)
        return right;
    }

    public static void main(String[] args) {
        int findNum = Integer.parseInt(args[0]);
        int[] array = new int[args.length - 1];
        for(int i = 1; i < args.length; i++) {
            array[i - 1] = Integer.parseInt(args[i]);
        }
        //System.out.println(recurBinSearch(array, findNum, -1, array.length));
        System.out.println(iterBinSearch(array, findNum));
    }
}
