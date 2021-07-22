package ir;

import ast.type.Type;
import ir.TempVar.TempSet;

public class TempAllocator {
	public int MAX_LOCALS = 65536;
	public TempVar temps[];
	public int next;

	public TempAllocator() {
		temps = new TempVar[MAX_LOCALS];
		next = 0;
	}

	public TempVar allocate(Type t, String label, TempSet s) {
		TempVar newTemp = new TempVar(t, next, label, s);
		temps[next] = newTemp;
		next++;
		return newTemp;
	}

	public TempVar allocate(Type t) {
		return allocate(t, null, null);
	}
}
