package expression.generic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericParser<T> {
    String mode;
    OpGeneric<T> op;

    public GenericParser(String mode, OpGeneric <T> op) {
        this.mode = mode;
        this.op = op;
    }

    private static final Map<Character, Integer> CHAR_TO_PRIORITY = Map.of(
            'a', 0,
            'i', 0,
            '+', 1,
            '-', 1,
            '*', 2,
            '/', 2
    );

    private int globCnt = 0;
    private int skobPos;

    private GenericConst<T> createNum(String num) throws IllegalArgumentException {
        return new GenericConst<>(num, op);
    }

    private GenericExpression<T> getExp(GenericExpression<T> ex, StringBuilder unarOp) {
        GenericExpression<T> res = ex;
        for (int i = unarOp.length() - 1; i >= 0; i--) {
            if (unarOp.charAt(i) != '+') {
                res = new GenericOneExpression<>(res, unarOp.charAt(i), op);
            }
        }
        return res;
    }

    private boolean isValidSymb(char symb) {
        return Character.isWhitespace(symb) || symb == '(' || symb == ')' || symb == '-' || symb == '+';
    }

    private GenericExpression<T> prParse(String expression) throws IllegalArgumentException, ParseException {
        //System.err.println("start");
        GenericExpression<T> ex1 = null;
        GenericExpression<T> ex2 = null;

        Character operation = null;
        StringBuilder unarOperation = new StringBuilder();

        List<GenericExpression<T>> exs = new ArrayList<>();
        List<Character> operations = new ArrayList<>();

        int currCnt = 0;
        for (int i = 0; i < expression.length() && expression.charAt(i) != ')'; i++, currCnt++) {
            //System.err.print(expression.charAt(i));
            if ('0' <= expression.charAt(i) && expression.charAt(i) <= '9') {
                int currI = i;
                while (i < expression.length() &&
                        ('0' <= expression.charAt(i) && expression.charAt(i) <= '9' ||
                        expression.charAt(i) == '.' && mode.equals("d"))) {
                    i++;
                }
                String constTemp = expression.substring(currI, i);
                GenericExpression<T> tempC;
                if (unarOperation.length() > 0 && unarOperation.charAt(unarOperation.length() - 1) == '-') {
                    tempC = createNum('-' + constTemp);
                    tempC = getExp(tempC, new StringBuilder(unarOperation.substring(0, unarOperation.length() - 1)));
                } else {
                    tempC = createNum(constTemp);
                    tempC = getExp(tempC, unarOperation);
                }
                if (ex1 == null) {
                    ex1 = tempC;
                } else {
                    ex2 = tempC;
                }
                unarOperation = new StringBuilder();
                i--;
            } else {
                switch (expression.charAt(i)) {
                    case 'l':
                    case 't':
                        if (i + 2 >= expression.length()) {
                            throw new ParseException("ошибка с L0 T0 index: " + i, 0);
                        }
                        if (!(i < 1 || isValidSymb(expression.charAt(i - 1))) || !isValidSymb(expression.charAt(i + 2))) {
                            throw new ParseException("Неправильно стоит унарная операция index: " + i, 0);
                        }
                        unarOperation.append(expression.charAt(i));
                        i++;
                        break;
                    case 'c':
                        if (i + 5 >= expression.length()) {
                            throw new ParseException("ошибка c Count index: " + i, 0);
                        }
                        if (!(i < 1 || isValidSymb(expression.charAt(i - 1))) || !isValidSymb(expression.charAt(i + 5))) {
                            throw new ParseException("Неправильно стоит унарная операция index: " + i, 0);
                        }
                        unarOperation.append(expression.charAt(i));
                        i += 4;
                        break;
                    case 'm':
                        if (i - 1 < 0 || i + 3 >= expression.length()) {
                            throw new ParseException("Ошибка с MinMax index: " + i, 0);
                        }
                        if (!(isValidSymb(expression.charAt(i - 1))) || !isValidSymb(expression.charAt(i + 3))) {
                            throw new ParseException("Неправильно стоит операция index: " + i, 0);
                        }
                        if (ex1 == null) {
                            throw new ParseException("Нету первого аргумента index: " + i, 0);
                        }
                        exs.add(ex1);
                        ex1 = null;
                        operations.add(expression.charAt(++i));
                        i++;
                        break;
                    case '-':
                    case '+':
                        if (operation == null) {
                            if (ex1 != null) {
                                exs.add(ex1);
                                operations.add(expression.charAt(i));
                                ex1 = null;
                            } else {
                                if (expression.charAt(i) == '+') {
                                    throw new ParseException("неверный унарный знак index: " + i, 0);
                                }
                                unarOperation.append(expression.charAt(i));
                            }
                        } else {
                            if (expression.charAt(i) == '+') {
                                throw new ParseException("неверный унарный знак index: " + i, 0);
                            }
                            unarOperation.append(expression.charAt(i));
                        }
                        break;
                    case '*':
                    case '/':
                        if (operation != null || ex1 == null) {
                            throw new ParseException("вторая подряд операция index: " + i, 0);
                        }
                        operation = expression.charAt(i);
                        break;
                    case 'x':
                    case 'y':
                    case 'z':
                        if (ex1 == null) {
                            //сhekOp(operation);
                            ex1 = new GenericVariable<>(expression.substring(i, i + 1));
                            ex1 = getExp(ex1, unarOperation);
                        } else {
                            ex2 = new GenericVariable<>(expression.substring(i, i + 1));
                            ex2 = getExp(ex2, unarOperation);
                        }
                        unarOperation = new StringBuilder();
                        break;
                    case '(':
                        skobPos++;
                        int currSk = ++i;
                        GenericExpression<T> tempEx = prParse(expression.substring(currSk));
                        i += globCnt;

                        if (tempEx == null) {
                            throw new ParseException("Строчка без аргументов", 0);
                        }

                        if (ex1 == null) {
                            ex1 = tempEx;
                            ex1 = getExp(ex1, unarOperation);
                        } else {
                            ex2 = tempEx;
                            ex2 = getExp(ex2, unarOperation);
                        }
                        unarOperation = new StringBuilder();
                        break;
                    default:
                        if (!Character.isWhitespace(expression.charAt(i))) {
                            //System.err.print("errSymb");
                            throw new ParseException("неверный символ index: " + i, 0);
                        }
                        break;
                }
            }

            if (ex1 != null && ex2 != null) {
                if (operation == null) {
                    throw new ParseException("(((((", 0);
                }
                ex1 = createEx(ex1, ex2, operation);
                ex2 = null;
                operation = null;
                ex1 = getExp(ex1, unarOperation);
                unarOperation = new StringBuilder();
            }
            currCnt = i;
        }
        if (currCnt < expression.length() && expression.charAt(currCnt) == ')') {
            skobPos--;
        }
        globCnt = currCnt;
        //System.err.println(ex1 + " " + operation + ":" + unarOperation + ":");
        if (ex1 != null && ex2 == null && operation != null) {
            throw new ParseException("Лишняя операция", 0);
        }

        //System.err.println("end");
        if (exs.size() == 0) {
            //System.err.println(ex1.toString());
            return ex1;
        } else {
            exs.add(ex1);
            if (ex1 == null) {
                throw new ParseException("Лишняя операция", 0);
            }
            //System.err.println(exs + " " + operations);
            //System.err.println(priorityElem(exs, operations, 1));
            return priorityElem(exs, operations, 1);
        }
    }

    private GenericExpression<T> priorityElem(List<GenericExpression<T>> exs, List<Character> operations, int currPriority) {
        GenericExpression<T> resEx = null;

        List<GenericExpression<T>> secExs = new ArrayList<>();
        List<Character> secOperations = new ArrayList<>();

        for (int i = 0; i < exs.size() - 1; i++) {
            int currOpPriority = CHAR_TO_PRIORITY.get(operations.get(i));
            if (currOpPriority == currPriority) {
                if (resEx != null) {
                    resEx = createEx(resEx, exs.get(i + 1), operations.get(i));
                } else {
                    resEx = createEx(exs.get(i), exs.get(i + 1), operations.get(i));
                }
                if (i == operations.size() - 1 && secExs.size() > 0) {
                    secExs.add(resEx);
                }
            } else {
                if (resEx != null) {
                    secExs.add(resEx);
                    resEx = null;
                } else {
                    secExs.add(exs.get(i));
                }
                secOperations.add(operations.get(i));
                if (i == operations.size() - 1) {
                    secExs.add(exs.get(i + 1));
                }
            }
        }
        //System.err.println(secExs + " " + secOperations);
        if (secExs.size() > 0) {
            resEx = priorityElem(secExs, secOperations, currPriority - 1);
        }
        return  resEx;
    }

    private GenericExpression<T> createEx(GenericExpression<T> ex1, GenericExpression<T> ex2, char oper) {
        return switch (oper) {
            case '+' -> new GenericAdd<>(ex1, ex2, op);
            case '-' -> new GenericSubtract<>(ex1, ex2, op);
            case '*' -> new GenericMultiply<>(ex1, ex2, op);
            case '/' -> new GenericDivide<>(ex1, ex2, op);
            case 'a' -> new GenericMax<>(ex1, ex2, op);
            default -> new GenericMin<>(ex1, ex2, op);
        };
    }

    public GenericExpression<T> parse(String expression) throws IllegalArgumentException, ParseException {
        /*String test = new String(" min 1");
        TripleExpression testR = prParse(test);
        System.err.println("dsfdf");
        System.err.println(testR);
        System.err.println("dsfdf");*/
        //System.err.println("\n***S");
        //System.err.println(expression);
        //System.err.println(":");
        /*System.err.println(prParse(expression).toString());
        System.err.println("***E");*/
        skobPos = 0;
        GenericExpression<T> res = prParse(expression);
        //System.err.println("\n" + res.toString());
        //System.err.println(":"+skobPos);
        if (res == null) {
            throw new ParseException("Строчка без аргументов", 0);
        }
        if (skobPos != 0) {
            throw new ParseException("Неправильная скобочная последовательность", 0);
        }
        return res;
    }
}
