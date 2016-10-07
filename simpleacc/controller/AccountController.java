package simpleacc.controller;
import simpleacc.model.AccountModel;
import simpleacc.model.Model;
import simpleacc.view.AccountsView;
import simpleacc.view.JFrameView;
import simpleacc.view.AccountView;
import simpleacc.model.AccountsModel;

import java.util.HashMap;

/**
 * Created by andrew on 4/13/16.
 */
public class AccountController extends AbstractController {


    private AccountModel accountModel;
    private AccountView accountView;
    public AccountController(AccountsModel database, Integer accountID, String state){
        accountModel = new AccountModel(database, accountID, state);
        accountView = new AccountView(database,this, accountID, state);
        setModel(database);
        System.out.println("Account Controller model is " + System.identityHashCode(database));
        accountView.setVisible(true);

    }

    public void action(String selection, String descriptor){


        if(selection.equals(AccountView.WITHDRAW)){
            accountModel.withdraw(descriptor);
        }
        else if(selection.equals(AccountView.DEPOSIT)){
            accountModel.deposit(descriptor);
        }

        else if(selection.equals(AccountView.DISPLAYACCOUNT)){ //displays unique account
            accountModel.displayAccount();

        }



    }

}
