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

}