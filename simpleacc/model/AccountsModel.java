package simpleacc.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

/**
 * Created by andrew on 5/7/16.
 */
public class AccountsModel extends AbstractModel {

    private HashMap<Integer, List<String>> accounts;
    private String dbFile;

    public AccountsModel(String dbFile){

        accounts = new HashMap<>();
        this.dbFile = dbFile;
        loadFile();

    }

    public String getAccountFunds(Integer id){
        return (accounts.get(id)).get(2).trim();
    }

    public String getAccountName(Integer id){
        return (accounts.get(id)).get(0).trim();
    }

    public void updateFunds(String updatedFund, Integer id){
        (accounts.get(id)).set(2, updatedFund).trim();
        System.out.println("account Funds are " + (accounts.get(id)).get(2));
        ModelEvent me = new ModelEvent(this, 1, "", null, String.valueOf(id), (accounts.get(id)).get(0), (accounts.get(id)).get(2), "", "", "");
        notifyChanged(me);
    }

    public void displayAccount(Integer id, String currencyUSD, String currencyEuro, String currencyYaun){
        ModelEvent me = new ModelEvent(this, 1, "", null, String.valueOf(id), (accounts.get(id)).get(0), currencyUSD, currencyEuro, currencyYaun, "");
        notifyChanged(me);
    }

    public void DisplayAccounts(){
        List<String> accountsDisplay = new ArrayList<>();
        Iterator it = accounts.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Integer, List<String>> pair = (Map.Entry)it.next();
            accountsDisplay.add((pair.getValue()).get(1) + " " + (pair.getValue()).get(0));
        }
        System.out.println("accounts to Display");
        System.out.println(accountsDisplay);
        ModelEvent me = new ModelEvent(this,1,"", accountsDisplay, "","","","","","");
        notifyChanged(me);

    }

    public void loadFile(){

        try{
            File file = new File(dbFile);
            Scanner fileReader = new Scanner(file);
            String line;
            List<String> items;

            while(fileReader.hasNextLine()){
                line = fileReader.nextLine().trim();
                items = Arrays.asList(line.split("\\|"));
                items.set(0,items.get(0).trim());
                items.set(1,items.get(1).trim());
                items.set(2,items.get(2).trim());
                System.out.println(items.get(1));
                try {
                    accounts.put(Integer.parseInt(items.get(1).trim()), items);
                    //accounts.add(items.get(1) + " " + items.get(0));
                }catch(NumberFormatException e){
                    //jdialog account error
                    System.out.println("Unable to put into hash");
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("file not found");
            //notify jdialogue
        }
        System.out.println(accounts);
    }
    public void saveFile(){

        try {
            Iterator it = accounts.entrySet().iterator();
            String data = "";
            while(it.hasNext()){
                Map.Entry<Integer, List<String>> pair = (Map.Entry)it.next();
                data += (pair.getValue()).get(0) + " | " + (pair.getValue()).get(1) + " | " + (pair.getValue()).get(2) + "\n";

            }

            FileOutputStream fileOut = new FileOutputStream(dbFile);
            System.out.println("Successfully saved");
            fileOut.write(data.getBytes());
            fileOut.close();

        }catch(Exception e){
            System.out.println("Unable to save file");
            e.printStackTrace();
        }

    }


}
