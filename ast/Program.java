package ast;

import java.util.List;

import ast.visitors.Visitor;

public class Program extends ASTNode {

    public List<Function> funcList; // contains at least one func as per lexer assumption

    public Program(List<Function> funcList) {
        this.funcList = funcList;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
