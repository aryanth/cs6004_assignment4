import soot.*;
import soot.jimple.*;
import soot.util.Chain;

public class DeadCodeEliminator extends BodyTransformer {

    @Override
    protected void internalTransform(Body body, String phaseName, Map<String, String> options) {
        PatchingChain<Unit> units = body.getUnits();
        boolean changed;

        do {
            changed = false;
            Iterator<Unit> unitIt = units.snapshotIterator();
            while (unitIt.hasNext()) {
                Unit unit = unitIt.next();
                if (unit instanceof AssignStmt) {
                    AssignStmt stmt = (AssignStmt) unit;
                    Value rightOp = stmt.getRightOp();
                    if (isDeadCode(stmt, body)) {
                        units.remove(stmt);
                        changed = true;
                    }
                }
            }
        } while (changed);
    }

    private boolean isDeadCode(AssignStmt stmt, Body body) {
        // Simple usage check (improvable with more complex analysis)
        for (Unit u : body.getUnits()) {
            if (u instanceof Stmt) {
                Stmt s = (Stmt) u;
                if (s.getUseBoxes().stream().anyMatch(box -> box.getValue().equivTo(stmt.getLeftOp()))) {
                    return false;
                }
            }
        }
        return true;
    }
}