package expression;

public class Variable implements FullExpression {

    private final String arg;

    public Variable(String arg) {
        this.arg = arg;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public String toString() {
        return getArg();
    }

    public String getArg() {
        return arg;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            return arg.equals(((Variable) obj).getArg());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return arg.hashCode();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (arg) {
            case "x" -> evaluate(x);
            case "y" -> evaluate(y);
            default -> evaluate(z);
        };
    }
}
