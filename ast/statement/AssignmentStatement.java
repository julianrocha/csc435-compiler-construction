package ast.statement;

import ast.expression.Expression;
import ast.expression.Identifier;
import ast.visitors.Visitor;

public class AssignmentStatement extends Statement {

    public Identifier id;
    public Expression expr;

    public AssignmentStatement(Identifier id, Expression expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
