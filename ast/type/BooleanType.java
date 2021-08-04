package ast.type;

import ast.visitors.Visitor;

public class BooleanType extends Type {

    public BooleanType(int l, int o) {
        super(l, o);
    }

    public BooleanType() {
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "boolean";
    }

    @Override
    public String toShortString() {
        return "Z";
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