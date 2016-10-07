package simpleacc.controller;
import simpleacc.model.Model;
import simpleacc.view.View;

/**
 * Created by andrew on 4/13/16.
 */
public abstract class AbstractController implements Controller {

    private View view;
    private Model model;

    public void setModel(Model model){this.model = model;}

    public void setView(View view){this.view = view;}

    public Model getModel(){return model;}

    public View getView(){return view;}




}
