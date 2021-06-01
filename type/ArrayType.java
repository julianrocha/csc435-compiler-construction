package type;

import ast.*;

public class ArrayType extends Type {

    Type element_type;
    int array_size;

    public ArrayType(Type element_type, int array_size){
        this.element_type = element_type; // TODO: ensure this is not another ArrayType
        this.array_size = array_size;
    }

    public String toString(){
        return element_type.toString() + "[" + Integer.toString(array_size) + "]";
    }

}