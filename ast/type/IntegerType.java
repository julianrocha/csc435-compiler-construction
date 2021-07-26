package ast.type;

import ast.visitors.Visitor;

public class IntegerType extends Type {

    public IntegerType(int l, int o) {
        super(l, o);
    }

    public IntegerType() {
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public String toShortString() {
        return "I";
    }

    @Override
    public String toJasminString() {
        return toShortString();
    }

    @Override
    public String toJasminPrefix() {
        return "i";
    }

}