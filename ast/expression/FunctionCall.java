package ast.expression;

import java.util.List;

import ast.visitors.Visitor;

public class FunctionCall extends Expression {

    public List<Expression> exprList;
    public Identifier id;

    public FunctionCall(Identifier id, List<Expression> exprList) {
        super(id.line, id.offset);
        this.id = id;
        this.exprList = exprList;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
