package ast.type;

import ast.visitors.Visitor;

public class BooleanType extends Type {

    public BooleanType(int l, int o) {
        super(l, o);
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}