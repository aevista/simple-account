package simpleacc.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import simpleacc.controller.AccountsController;
import simpleacc.model.AccountsModel;
import simpleacc.model.ModelEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by andrew on 4/13/16.
 */
public class AccountsView extends JFrameView {

    //buttons
    public static final String USDEDIT = "Edit in USD";
    public static final String EUROEDIT = "Edit in Euro";
    public static final String YAUNEDIT = "Edit in Yaun";
    public static final String SAVE = "save";
    public static final String EXIT = "exit";
    public static final String DEPOSITTHREAD = "deposit thread";
    public static final String WITHDRAWTHREAD = "withdraw thread";


    public static final String DISPLAYALL = "display all";


    private String selectedAccount;
    private String selectedAccountID;
    private String selectedAccountName;
    private List<String> accounts;


    List<String> accountCredentials = new ArrayList<String>();


    private JComboBox accountList;
    private JButton dismissButton;

    JDialog errorDialog;

    //private Handler
    //Handler handler;

    public AccountsView(final AccountsModel model, AccountsController controller) {
        super(model, controller);
        ((AccountsController) getController()).action(DISPLAYALL, 0);
       // handler = new Handler();
        JPanel buttonPanel = new JPanel();

        accountList = new JComboBox(accounts.toArray());
        accountList.setSelectedIndex(0);
        accountList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e ){
                JComboBox cb = (JComboBox) e.getSource();
                selectedAccount = (String)cb.getSelectedItem();
                accountCredentials = Arrays.asList(selectedAccount.trim().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"));
                selectedAccountID = accountCredentials.get(0);
                System.out.println(accountCredentials);
            }
        });

        selectedAccount = (String) accountList.getSelectedItem();
        accountCredentials = Arrays.asList(selectedAccount.trim().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"));
        selectedAccountID = accountCredentials.get(0).trim();
        selectedAccountName = accountCredentials.get(1);


        JButton usdEditButton = new JButton(USDEDIT);
        usdEditButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ((AccountsController)getController()).action(e.getActionCommand(),Integer.parseInt(selectedAccountID));
            }
        });
        JButton euroEditButton = new JButton(EUROEDIT);
        euroEditButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ((AccountsController)getController()).action(e.getActionCommand(),Integer.parseInt(selectedAccountID));
            }
        });
        JButton yaunEditButton = new JButton(YAUNEDIT);
        yaunEditButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ((AccountsController)getController()).action(e.getActionCommand(),Integer.parseInt(selectedAccountID));
            }
        });

        JButton saveButton = new JButton(SAVE);
        saveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ((AccountsController)getController()).action(e.getActionCommand(), 0);
            }
        });
        JButton exitButton = new JButton(EXIT);
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setVisible(false);
                dispose();
            }
        });


        JButton withThreadButton = new JButton(WITHDRAWTHREAD);

        withThreadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((AccountsController)getController()).action(e.getActionCommand(),Integer.parseInt(selectedAccountID));
            }
        });

        JButton depThreadButton = new JButton(DEPOSITTHREAD);
        depThreadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((AccountsController)getController()).action(e.getActionCommand(),Integer.parseInt(selectedAccountID));
            }
        });



        buttonPanel.setLayout(new GridLayout(2, 1, 1, 2));
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.add(usdEditButton);
        buttonPanel.add(euroEditButton);
        buttonPanel.add(yaunEditButton);
        buttonPanel.add(depThreadButton);
        buttonPanel.add(withThreadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);
        add(accountList);


        setLayout(new FlowLayout());
        setSize(400, 200);
        setTitle("List Accounts");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
    public void modelChanged(ModelEvent event){


        if(event.getAccounts() != null){
            System.out.println("in here");
            accounts = event.getAccounts();

        }
        if(!event.getErrorMessage().isEmpty()){
            errorDialog = new JDialog();
            JPanel messagePane = new JPanel();
            messagePane.add(new JLabel(event.getErrorMessage()));
            errorDialog.getContentPane().add(messagePane);
            dismissButton = new JButton("Dismiss");
            dismissButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    errorDialog.setVisible(false);
                    errorDialog.dispose();
                }
            });
            errorDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            errorDialog.getContentPane().add(dismissButton, BorderLayout.SOUTH);
            errorDialog.pack();
            errorDialog.setVisible(true);

        }

    }

}