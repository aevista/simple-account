package simpleacc.model;

import simpleacc.view.AccountsView;
import java.text.DecimalFormat;


/**
 * Created by andrew on 4/13/16.
 */
public class AccountModel extends AbstractModel {

    public static final double USD = 1;
    public static final double Euro = USD / 0.88;
    public static final double Yaun = USD / 6.47;


    private String state;// = "USD";
    private Integer accountID;
    private String accountFunds = "";
    private String accountName;
    private AccountsModel database;

    public AccountModel(AccountsModel database, Integer accountID, String state){
        this.database = database;
        this.accountID = accountID;
        this.state = state;
        accountName = database.getAccountName(accountID);
        updateFunds();
    }

    public void displayAccount(){

        System.out.println("state is " + state);
        String tempState = state;
        String usdCurrency = convertAmount(convertToUSD(accountFunds));
        state = AccountsView.EUROEDIT;
        String euroCurrency = convertAmount(usdCurrency);
        state = AccountsView.YAUNEDIT;
        String yaunCurrency = convertAmount(usdCurrency);
        state = tempState;
        System.out.println(usdCurrency + " " + euroCurrency + " " + yaunCurrency);
        database.displayAccount(accountID,usdCurrency, euroCurrency, yaunCurrency);
    }


    public String convertAmount(String amount){
        String tempCurrency = "";
        if(state.compareTo(AccountsView.USDEDIT) == 0)
            tempCurrency = String.valueOf(Double.parseDouble(amount));

        if(state.compareTo(AccountsView.EUROEDIT) == 0)
            tempCurrency = String.valueOf((Double.parseDouble(amount) * Euro));

        if(state.compareTo(AccountsView.YAUNEDIT) == 0)
            tempCurrency = String.valueOf((Double.parseDouble(amount) * Yaun));

        return new DecimalFormat("#.00").format(Double.parseDouble(tempCurrency));

    }

    public String convertToUSD(String amount){
        String tempCurrency = "";
        if(state.compareTo(AccountsView.USDEDIT) == 0)
            tempCurrency = String.valueOf(Double.parseDouble(amount));

        if(state.compareTo(AccountsView.EUROEDIT) == 0)
            tempCurrency = String.valueOf((Double.parseDouble(amount) * (1 / Euro)));

        if(state.compareTo(AccountsView.YAUNEDIT) == 0)
            tempCurrency = String.valueOf((Double.parseDouble(amount) * (1 / Yaun)));

        System.out.println("converting currency" + tempCurrency);
        return new DecimalFormat("#.00").format(Double.parseDouble(tempCurrency));

    }

    public void withdraw(String amount){
        String newAmount;
        if(Double.parseDouble(amount) >= 0 && Double.parseDouble(convertAmount(accountFunds)) - Double.parseDouble(amount) >= 0){
            updateFunds();
            double temp = Double.parseDouble(accountFunds) - Double.parseDouble(convertToUSD(amount));
            newAmount = new DecimalFormat("#.00").format(temp);
            database.updateFunds(newAmount, accountID);
            updateFunds();
            displayAccount();
        }
        else{
            String message;
            if(Double.parseDouble(amount) < 0)
                message = "Value must not be negative";
            else
                message = "Not enough available funds to withdraw " + amount;
            //ModelEvent me = new ModelEvent(this,1, "", newAmount, null, "", message);
            //notifyChanged(me);

        }
    }

    public void deposit(String amount){
        String newAmount;
        if(Double.parseDouble(amount) >= 0 ){
            updateFunds();
            double temp = Double.parseDouble(accountFunds) + Double.parseDouble(convertToUSD(amount));
            newAmount = new DecimalFormat("#.00").format(temp);
            database.updateFunds(newAmount, accountID);
            updateFunds();
            System.out.println(System.identityHashCode(this) + " account with database" + System.identityHashCode(database));
            displayAccount();
        }
        else{
            String message = "Value must not be negative";
            //ModelEvent me = new ModelEvent(this,1, "", newAmount, null, "", message);
            //notifyChanged(me);
        }
    }

    public void updateFunds(){
        accountFunds = database.getAccountFunds(accountID);
    }


}
