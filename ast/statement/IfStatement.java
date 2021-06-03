package ast.statement;

import java.util.List;

import ast.expression.Expression;
import ast.visitors.Visitor;

public class IfStatement extends Statement {

    public Expression expr;
    public List<Statement> block;

    public IfStatement(Expression expr, List<Statement> block) {
        this.expr = expr;
        this.block = block;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
