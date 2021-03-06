package ast.statement;

import ast.expression.Expression;
import ast.visitors.Visitor;

public class PrintStatement extends Statement {

    public Expression expr;

    public PrintStatement(Expression expr) {
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
