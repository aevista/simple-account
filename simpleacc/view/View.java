package simpleacc.view;
import simpleacc.model.Model;
import simpleacc.controller.Controller;

/**
 * Created by andrew on 4/13/16.
 */
public interface View {

    void setModel(Model model);
    void setController(Controller controller);

    Model getModel();
    Controller getController();

}
