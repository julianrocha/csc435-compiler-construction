package ast.type;

import ast.visitors.Visitor;

public class FloatType extends Type {

    public FloatType(int l, int o) {
        super(l, o);
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}