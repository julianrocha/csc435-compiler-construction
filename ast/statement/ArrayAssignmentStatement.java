package ast.statement;

import ast.expression.Expression;
import ast.expression.Identifier;
import ast.visitors.Visitor;

public class ArrayAssignmentStatement extends Statement {

    public Identifier id;
    public Expression index_expr;
    public Expression assign_expr;

    public ArrayAssignmentStatement(Identifier id, Expression index_expr, Expression assign_expr) {
        this.id = id;
        this.index_expr = index_expr;
        this.assign_expr = assign_expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
