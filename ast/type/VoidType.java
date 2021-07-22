package ast.type;

import ast.visitors.Visitor;

public class VoidType extends Type {

    public VoidType(int l, int o) {
        super(l, o);
    }

    public VoidType() {
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "void";
    }

    @Override
    public String toShortString() {
        return "V";
    }

}