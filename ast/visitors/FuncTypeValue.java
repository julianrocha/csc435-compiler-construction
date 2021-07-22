package ast.visitors;

import java.util.List;

import ast.type.Type;

public class FuncTypeValue {

	public Type rType;
	public List<Type> paramTypes;

	public FuncTypeValue(Type rType, List<Type> paramTypes) {
		this.rType = rType;
		this.paramTypes = paramTypes;
	}
}