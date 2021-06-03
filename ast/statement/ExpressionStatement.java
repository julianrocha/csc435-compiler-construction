package ast.statement;

import ast.visitors.Visitor;
import ast.expression.*;

public class ExpressionStatement extends Statement {

    public Expression expr;

    public ExpressionStatement(Expression expr) {
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
