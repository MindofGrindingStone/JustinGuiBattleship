package Controller;

import View.GridPanel;
import Model.TargetGrid;

public class TargetGridController {
    private GridPanel view;
    private TargetGrid model;

    public TargetGridController(GridPanel view, TargetGrid model) {
        this.view = view;
        this.model = model;
    }
}
