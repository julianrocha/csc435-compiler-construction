package ast.type;

import ast.visitors.Visitor;

public class CharType extends Type {

    public CharType(int l, int o) {
        super(l, o);
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}