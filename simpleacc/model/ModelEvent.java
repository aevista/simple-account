package simpleacc.model;
import simpleacc.view.AccountsView;
import java.awt.event.ActionEvent;
import java.util.List;
/**
 * Created by andrew on 4/13/16.
 */
public class ModelEvent extends ActionEvent{
    private List<String> accounts;
    private String accountID;
    private String accountName;
    private String fundsUSD;
    private String fundsEuro;
    private String fundsYaun;
    private String error;

    public ModelEvent(Object obj, int id, String message, List<String> accounts, String accountID, String accountName, String fundsUSD, String fundsEuro, String fundsYaun, String error){
        super(obj, id, message);
        this.accounts = accounts;
        this.accountID = accountID;
        this.accountName = accountName;
        this.fundsUSD = fundsUSD;
        this.fundsEuro = fundsEuro;
        this.fundsYaun = fundsYaun;
        this.error = error;
    }

    public List<String> getAccounts(){return accounts;}

    public String getAccountID(){return accountID;}

    public String getAccountName(){return accountName;}

    public String getAccountFunds(String currency){
        if(currency.compareTo(AccountsView.USDEDIT) == 0)
            return fundsUSD;
        else if(currency.compareTo(AccountsView.EUROEDIT) == 0)
            return fundsEuro;
        else if(currency.compareTo(AccountsView.YAUNEDIT) == 0)
            return fundsYaun;

        return fundsUSD;
    }

    public String getErrorMessage(){return error;}



}
