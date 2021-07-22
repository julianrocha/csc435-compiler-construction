package ast.type;

import ast.visitors.Visitor;

public class FloatType extends Type {

    public FloatType(int l, int o) {
        super(l, o);
    }

    public FloatType() {
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "float";
    }

    @Override
    public String toShortString() {
        return "F";
    }

}