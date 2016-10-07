package simpleacc.view;
import javax.swing.*;
import simpleacc.model.Model;
import simpleacc.model.AbstractModel;
import simpleacc.model.ModelListener;
import simpleacc.controller.Controller;


/**
 * Created by andrew on 4/13/16.
 */
public abstract class JFrameView extends JFrame implements View, ModelListener {

    private Model model;
    private Controller controller;

    public JFrameView(Model model, Controller controller){
        setModel(model);
        setController(controller);
    }

    public void registerWithModel(){((AbstractModel)model).addModelListener(this);
        System.out.println("View object " + System.identityHashCode(this) + " registering with model " + System.identityHashCode(model));
    }

    public void removeWithModel(){((AbstractModel)model).removeModelListener(this);}

    public void setModel(Model model){
        this.model = model;
        registerWithModel();
        System.out.println("registering with model");
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public Model getModel(){
        return model;
    }

    public Controller getController(){
        return controller;
    }


}
