package simpleacc.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import simpleacc.controller.AccountController;
import simpleacc.model.AbstractModel;
import simpleacc.model.AccountsModel;
import simpleacc.model.ModelEvent;

/**
 * Created by andrew on 4/13/16.
 */
public class AccountView extends JFrameView {

    //buttons
    public static final String DISPLAYACCOUNT  = "display account";
    public static final String WITHDRAW = "withdraw";
    public static final String DEPOSIT = "deposit";
    public static final String DISMISS = "dismiss";

    private JTextField amountField;

    private JTextField setAmountField;
    private JTextField accountIDField;
    private Integer accountID;
    private String state;


    public AccountView(AccountsModel database, AccountController controller, Integer accountID, String state){
        super(database, controller);
        this.accountID = accountID;
        this.state = state;


        accountIDField = new JTextField();
        amountField = new JTextField(10);
        setAmountField = new JTextField(10);

        JButton withdrawButton = new JButton(WITHDRAW);
        withdrawButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String enteredAmount = setAmountField.getText();
                System.out.println("entered in amount is " + enteredAmount);
                ((AccountController)getController()).action(e.getActionCommand(),enteredAmount);
                setAmountField.setText("0");
            }
        });
        JButton depositButton = new JButton(DEPOSIT);
        depositButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String enteredAmount = setAmountField.getText();
                System.out.println("entered in amount is " + enteredAmount);
                ((AccountController)getController()).action(e.getActionCommand(),enteredAmount);
                setAmountField.setText("0");
            }
        });

        JButton dismissButton = new JButton(DISMISS);
        dismissButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });

        accountIDField.setText(String.valueOf(accountID));
        accountIDField.setEditable(false);
        ((AccountController)getController()).action(DISPLAYACCOUNT,"");

        amountField.setEditable(false);
        setAmountField.setText("0");

        setVisible(true);

        JPanel controlPanel = new JPanel();

        controlPanel.setLayout(new GridLayout(2, 1, 5, 5));
        getContentPane().add(controlPanel, BorderLayout.CENTER);
        controlPanel.add(accountIDField);
        controlPanel.add(amountField);
        controlPanel.add(setAmountField);
        controlPanel.add(withdrawButton);
        controlPanel.add(depositButton);
        controlPanel.add(dismissButton);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new FlowLayout());
        add(controlPanel);
        pack();
    }

    public void modelChanged(ModelEvent event){
        System.out.println("in account gui " + System.identityHashCode(this));
        if(accountID == Integer.parseInt(event.getAccountID())) {
            amountField.setText(event.getAccountFunds(state));
            setTitle(event.getAccountName());
            revalidate();
            repaint();
        }


    }
    @Override
    public void dispose(){
        removeWithModel();
        super.setVisible(false);
        super.dispose();
    }
}
