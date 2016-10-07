
package simpleacc;

import simpleacc.controller.AccountsController;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by andrew on 4/13/16.
 */
public class Main {
    public static String databaseFile = "accounts.txt";
    public static void main(String [] args) throws InvocationTargetException { new AccountsController(databaseFile);}
}
