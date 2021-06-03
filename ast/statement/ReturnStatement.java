package ast.statement;

import ast.expression.Expression;
import ast.visitors.Visitor;

public class ReturnStatement extends Statement {

    public Expression expr;

    public ReturnStatement(Expression expr) {
        this.expr = expr; // can be null for void return functions
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
