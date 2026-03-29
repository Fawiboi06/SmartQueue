package Controller;

import View.GUImainBody;

public class ControllerAll {

    private final GUImainBody view;

    public ControllerAll() {
        this.view = new GUImainBody();
        initController();
    }

    private void initController() {
        view.setVisible(true);
    }
}