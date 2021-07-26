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

    @Override
    public String toJasminString() {
        return toShortString();
    }

    @Override
    public String toJasminPrefix() {
        return ""; // TODO: this should never get called
    }

}