package ast.type;

import ast.visitors.Visitor;

public class StringType extends Type {

    public StringType(int l, int o) {
        super(l, o);
    }

    public StringType() {
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "string";
    }

}