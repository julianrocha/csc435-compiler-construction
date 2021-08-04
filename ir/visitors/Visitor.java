package ir.visitors;

import ir.*;
import ir.irinstruction.*;
import ir.irinstruction.assignment.*;

public interface Visitor {

	Object visit(IRProgram irProgram);

	Object visit(IRFunction irFunction);

	Object visit(Label label);

	Object visit(TempVar tempVar);

	Object visit(IRCallInstruction irCallInstruction);

	Object visit(IRConditionalGotoInstruction irConditionalGotoInstruction);

	Object visit(IRGotoInstruction irGotoInstruction);

	Object visit(IRLabelInstruction irLabelInstruction);

	Object visit(IRReturnInstruction irReturnInstruction);

	Object visit(PrintIRInstruction printIRInstruction);

	Object visit(PrintlnIRInstruction printlnIRInstruction);

	Object visit(IRArrayAssign irArrayAssign);

	Object visit(IRArrayInit irArrayInit);

	Object visit(IRBinaryOp irBinaryOp);

	Object visit(IRConstantAssign irConstantAssign);

	Object visit(IRTempAssign irTempAssign);

	Object visit(IRTempAssignArrayRef irTempAssignArrayRef);

	Object visit(IRUnaryOp irUnaryOp);

}
