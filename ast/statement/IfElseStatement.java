package ast.statement;

import java.util.List;

import ast.expression.Expression;
import ast.visitors.Visitor;

public class IfElseStatement extends Statement {

    public Expression ifExpr;
    public List<Statement> ifBlock;
    public List<Statement> elseBlock;

    public IfElseStatement(Expression ifExpr, List<Statement> ifBlock, List<Statement> elseBlock) {
        this.ifExpr = ifExpr;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
