package simpleacc.controller;

import simpleacc.model.AccountModel;
import simpleacc.model.AccountsModel;
import simpleacc.thread.ThreadManage;
import simpleacc.view.AccountView;
import simpleacc.view.ThreadView;

/**
 * Created by andrew on 5/8/16.
 */
public class ThreadController extends AbstractController {


    private ThreadView threadView;
    private AccountsModel database;
    private Thread thread;
    public ThreadController(AccountsModel database, Integer accountID, String actionPerformed){
        this.database = database;
        threadView = new ThreadView(database,this, accountID, actionPerformed);
        setModel(database);
        System.out.println("Thread Controller model is " + System.identityHashCode(database));
        threadView.setVisible(true);

    }

    public void action(String selection, final Integer accountID, final Integer opsPerSecond, final Double amount, final String agentAction){

        if(selection.equals(ThreadView.STARTTHREAD)){

            thread  = new Thread(new Runnable(){
               public void run(){
                    new ThreadManage(database, accountID, opsPerSecond, amount, agentAction);
               }
            });
            thread.start();
        }

        else if(selection.equals(ThreadView.STOPTHREAD)){
            thread.interrupt();
        }
        /*
        else if(selection.equals(AccountView.DEPOSIT)){
            accountModel.deposit(descriptor);
        }

        else if(selection.equals(AccountView.DISPLAYACCOUNT)){ //displays unique account
            accountModel.displayAccount();

        }
        */


    }
}
