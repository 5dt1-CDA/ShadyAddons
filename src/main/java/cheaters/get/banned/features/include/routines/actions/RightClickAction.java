package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.utils.KeybindUtils;

public class RightClickAction extends Action {

    public RightClickAction(RoutineElementData data) {
        super(data);
    }

    @Override
    public void doAction() {
        KeybindUtils.rightClick();
    }

}
