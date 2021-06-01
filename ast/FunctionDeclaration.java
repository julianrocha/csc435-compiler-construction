package ast;

import java.util.List;

import type.*;

public class FunctionDeclaration extends ASTNode {
    Type type;
    Identifier id;
    List<FormalParameter> formalParameterList;

    public FunctionDeclaration(Type type, Identifier id, List<FormalParameter> formalParameterList){
        this.type = type;
        this.id = id;
        this.formalParameterList = formalParameterList;
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }
}
