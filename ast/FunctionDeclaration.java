package ast;

import java.util.List;

import ast.type.*;
import ast.visitors.Visitor;

public class FunctionDeclaration extends ASTNode {
    public Type type;
    public Identifier id;
    public List<FormalParameter> formalParameterList;

    public FunctionDeclaration(Type type, Identifier id, List<FormalParameter> formalParameterList) {
        this.type = type;
        this.id = id;
        this.formalParameterList = formalParameterList;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
