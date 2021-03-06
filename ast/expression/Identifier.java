package ast.expression;

import ast.visitors.Visitor;

public class Identifier extends Expression {

    public String id_string;

    public Identifier(String id_string, int l, int o) {
        super(l, o);
        this.id_string = id_string;
    }

    public String toString() {
        return id_string;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
