package ast;

import java.util.List;

import ast.statement.*;
import ast.visitors.Visitor;

public class FunctionBody extends ASTNode {

    public List<VariableDeclaration> vlist; // may be empty
    public List<Statement> slist; // may be empty

    public FunctionBody(List<VariableDeclaration> vlist, List<Statement> slist) {
        this.vlist = vlist;
        this.slist = slist;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
