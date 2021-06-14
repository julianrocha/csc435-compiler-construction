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

}