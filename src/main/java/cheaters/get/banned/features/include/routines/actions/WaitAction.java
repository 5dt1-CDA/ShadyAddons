package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineException;
import cheaters.get.banned.utils.ThreadUtils;

/**
 * {
 *     "name": "WaitAction",
 *     "ms": int
 * }
 */

public class WaitAction extends Action {

    private int ms;

    public WaitAction(RoutineElementData data) throws RoutineException {
        super(data);
        ms = data.keyAsInt("ms", true);
    }

    @Override
    public void doAction() {
        ThreadUtils.sleep(ms);
    }

}
