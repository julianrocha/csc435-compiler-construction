package ast.type;

import ast.visitors.Visitor;

public class CharType extends Type {

    public CharType(int l, int o) {
        super(l, o);
    }

    public CharType() {
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return "char";
    }

    @Override
    public String toShortString() {
        return "C";
    }

    @Override
    public String toJasminString() {
        return toShortString();
    }

    @Override
    public String toJasminPrefix() {
        return "i";
    }

}