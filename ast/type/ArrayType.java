package ast.type;

import ast.visitors.Visitor;

public class ArrayType extends Type {

    public Type element_type;
    public int array_size;

    public ArrayType(Type element_type, int array_size) {
        this.element_type = element_type; // asummed from lexer that this is not another ArrayType
        this.array_size = array_size;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}