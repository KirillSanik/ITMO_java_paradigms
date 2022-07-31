package expression;

import expression.generic.GenericTabulator;
import expression.generic.Tabulator;

import javax.management.ObjectName;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) {
        Tabulator tabulatorRes = new GenericTabulator();
        Object[][][] result;
        try {
            result = tabulatorRes.tabulate(args[0], args[1], -2, 2, -2, 2, -2, 2);
        } catch (Exception e) {
            System.out.println("error parse " + e);
            return;
        }
        for (int i = 0; i <=4; i++) {
            for (int j = 0; j <=4; j++) {
                for (int k = 0; k <= 4; k++) {
                    System.out.printf("x: %d y: %d z: %d value: %d" + System.lineSeparator(), i, j, k, result[i][j][k]);
                }
            }
        }
    }
}
