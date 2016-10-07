package simpleacc.controller;
import simpleacc.model.Model;
import simpleacc.view.View;

/**
 * Created by andrew on 4/13/16.
 */
public interface Controller {

    void setModel(Model model);
    void setView(View view);
    Model getModel();
    View getView();
}
