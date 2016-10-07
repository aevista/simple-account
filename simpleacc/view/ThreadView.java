package simpleacc.view;

import simpleacc.controller.Controller;
import simpleacc.controller.ThreadController;
import simpleacc.model.Model;
import simpleacc.model.ModelEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by andrew on 5/8/16.
 */

public class ThreadView extends JFrameView {

    public static final String STARTTHREAD = "start thread";
    public static final String DISMISS = "dismiss";
    public static final Integer JTEXTFIELDSIZE = 15;
    public static final String STOPTHREAD  = "Stop thread";

    private String agentAction;
    private Integer accountID;

    private JTextField agentIDField;
    private JTextField amountField;
    private JTextField opsPerSecField;
    private JTextField amountTransferredField;
    private JTextField operationsCompletedField;

    private JButton performActionButton;
    private JButton dismissButton;

    public ThreadView(Model model, Controller controller, Integer accID, String agentAct){
        super(model, controller);
        agentAction = agentAct;
        accountID = accID;


        setTitle(agentAction + " " + accountID);

        agentIDField = new JTextField(JTEXTFIELDSIZE);
        agentIDField.setText("set agent Id");

        amountField = new JTextField(JTEXTFIELDSIZE);
        amountField.setText("set double amount");

        opsPerSecField = new JTextField(JTEXTFIELDSIZE);
        opsPerSecField.setText("set integer ops/sec");

        amountTransferredField = new JTextField(JTEXTFIELDSIZE);

        performActionButton = new JButton(STARTTHREAD);
        dismissButton = new JButton(DISMISS);

        performActionButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                final JFrame threadFrame = new JFrame(agentAction + " " + accountID);
                threadFrame.setVisible(true);

                final String agentid = agentIDField.getText();
                final Integer opsPerSec;
                final Double amount;
                try{

                    opsPerSec = Integer.parseInt(opsPerSecField.getText());
                    amount = Double.parseDouble(amountField.getText());

                    if( opsPerSec < 0 || amount < 0){
                        throw new Exception("negative number");
                    }

                }catch(NumberFormatException exception){
                    System.out.println("Invalid value set");
                    return;
                }catch(Exception exception){
                    System.out.println(exception);
                    return;
                }


                JPanel controlPanel = new JPanel();
                operationsCompletedField = new JTextField(JTEXTFIELDSIZE);
                JButton stopThreadButton = new JButton(STOPTHREAD);

                stopThreadButton.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e){
                       //((ThreadController)getController()).action(e.getActionCommand(),accountID,opsPerSec, amount,agentAction);
                       ((ThreadController)getController()).action(STOPTHREAD, 0,0,0.0,"");
                   }
                });

                dismissButton.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e){
                       ((ThreadController)getController()).action(STOPTHREAD, 0,0,0.0,"");
                       removeWithModel();
                        threadFrame.dispose();
                   }
                });

                agentIDField.setEditable(false);
                opsPerSecField.setEditable(false);
                operationsCompletedField.setEditable(false);
                amountTransferredField.setEditable(false);




                controlPanel.setLayout(new GridLayout(2, 1, 5, 5));
                threadFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);

                controlPanel.add(agentIDField);
                controlPanel.add(opsPerSecField);
                controlPanel.add(amountTransferredField);
                controlPanel.add(stopThreadButton);
                controlPanel.add(dismissButton);


                threadFrame.setLayout(new FlowLayout());
                threadFrame.add(controlPanel);
                threadFrame.pack();

                threadFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        ((ThreadController) getController()).action(STOPTHREAD, 0, 0, 0.0, "");
                        removeWithModel();
                        threadFrame.dispose();
                    }
                });
                dispose();

                ((ThreadController)getController()).action(e.getActionCommand(),accountID,opsPerSec, amount,agentAction);


            }
        });


        dismissButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });


        JPanel controlPanel = new JPanel();

        controlPanel.setLayout(new GridLayout(2, 1, 5, 5));
        getContentPane().add(controlPanel, BorderLayout.CENTER);

        controlPanel.add(agentIDField);
        controlPanel.add(opsPerSecField);
        controlPanel.add(amountField);
        controlPanel.add(performActionButton);
        controlPanel.add(dismissButton);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new FlowLayout());
        add(controlPanel);
        pack();
    }

    public void modelChanged(ModelEvent event){
        System.out.println(event.getAccountID());
        if(accountID == Integer.parseInt(event.getAccountID())){
            amountTransferredField.setText(event.getAccountFunds(AccountsView.USDEDIT));
        }
    }


}
