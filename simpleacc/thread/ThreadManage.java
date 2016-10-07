package simpleacc.thread;

import simpleacc.controller.AccountController;
import simpleacc.model.AccountModel;
import simpleacc.model.AccountsModel;
import simpleacc.view.AccountView;
import simpleacc.view.AccountsView;

import java.text.DecimalFormat;

/**
 * Created by andrew on 5/7/16.
 */
public class ThreadManage {


    private AccountsModel database;
    private Integer accountID;
    private long opsPerSecond;
    private Double amount;
    private String agentAction;

    public ThreadManage(AccountsModel database, Integer accountID, Integer opsPerSecond, Double amount, String agentAction){
        this.database = database;
        this.accountID = accountID;
        this.opsPerSecond = (1000*opsPerSecond.longValue());
        this.amount = amount;
        this.agentAction = agentAction;
        startAgent();

    }

    public void startAgent(){
        if(agentAction.equals(AccountsView.DEPOSITTHREAD)){
            deposit();
        }
        else if(agentAction.equals(AccountsView.WITHDRAWTHREAD)){
            withdraw();
        }
    }

    public void deposit(){
        while(true) {
            synchronized(database) {
                String newAmount = database.getAccountFunds(accountID);
                newAmount = new DecimalFormat("#.00").format(Double.parseDouble(newAmount) + amount);
                database.updateFunds(newAmount, accountID);
                database.notifyAll();
            }

            try{
                Thread.sleep(opsPerSecond);
            }catch(InterruptedException e){
                break;
            }
        }
    }

    public void withdraw() {
        while(true) {
            synchronized (database) {
                while (Double.parseDouble(database.getAccountFunds(accountID)) - amount < 0)
                    try {
                        database.wait();
                    } catch (InterruptedException e) {

                    }
                String newAmount = database.getAccountFunds(accountID);
                newAmount = new DecimalFormat("#.00").format(Double.parseDouble(newAmount) - amount);
                database.updateFunds(newAmount, accountID);
            }

            try{
                Thread.sleep(opsPerSecond);
            }catch(InterruptedException e){
                break;
            }

        }

    }

}
