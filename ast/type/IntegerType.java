package ast.type;

import ast.visitors.Visitor;

public class IntegerType extends Type {

    public IntegerType(int l, int o) {
        super(l, o);
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}