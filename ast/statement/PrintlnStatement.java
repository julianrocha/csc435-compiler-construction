package ast.statement;

import ast.expression.Expression;
import ast.visitors.Visitor;

public class PrintlnStatement extends Statement {

    public Expression expr;

    public PrintlnStatement(Expression expr) {
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
