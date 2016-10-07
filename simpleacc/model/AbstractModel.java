package simpleacc.model;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by andrew on 4/13/16.
 */
public class AbstractModel implements Model {

    private ArrayList listeners = new ArrayList(5);

    public void notifyChanged(ModelEvent event){

        ArrayList list = (ArrayList)listeners.clone();
        Iterator it = list.iterator();
        while(it. hasNext()){
            ModelListener ml = (ModelListener)it.next();
            System.out.println("notifying " + System.identityHashCode(ml) + " size is " + listeners.size());
            ml.modelChanged(event);
        }
    }

    public void addModelListener(ModelListener l){
        listeners.add(l);

    }

    public void removeModelListener(ModelListener l){
        try {
            listeners.remove(1);
        }catch(Exception e){
        }
    }
}
