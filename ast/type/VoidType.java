package ast.type;

import ast.visitors.Visitor;

public class VoidType extends Type {

    public VoidType(int l, int o) {
        super(l, o);
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}