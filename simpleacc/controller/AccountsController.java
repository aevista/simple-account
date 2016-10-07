package simpleacc.controller;
import simpleacc.model.AccountModel;
import simpleacc.view.JFrameView;
import simpleacc.view.AccountsView;
import simpleacc.model.AccountsModel;

import java.util.HashMap;

/**
 * Created by andrew on 4/13/16.
 */
public class AccountsController extends AbstractController {
    //private HashMap<Integer, AccountController> accountList;

    public AccountsController(String databaseFile){
        //accountList = new HashMap<>();

        setModel(new AccountsModel(databaseFile));
        setView(new AccountsView((AccountsModel)getModel(),this));
        ((JFrameView)getView()).setVisible(true);

        System.out.println("Accounts Controller model id " + System.identityHashCode(getModel()));
    }

    public void action(String selection, Integer accountID){

        if(selection.equals(AccountsView.USDEDIT) || selection.equals(AccountsView.EUROEDIT) || selection.equals(AccountsView.YAUNEDIT)){

            new AccountController((AccountsModel)getModel(), accountID, selection);
        }

        else if(selection.equals(AccountsView.SAVE)){
            ((AccountsModel)getModel()).saveFile();
        }

        else if(selection.equals(AccountsView.DISPLAYALL)) {
            ((AccountsModel) getModel()).DisplayAccounts();

        }

        else if(selection.equals(AccountsView.WITHDRAWTHREAD) || selection.equals(AccountsView.DEPOSITTHREAD)){
            new ThreadController(((AccountsModel)getModel()), accountID, selection);
        }

    }

}
