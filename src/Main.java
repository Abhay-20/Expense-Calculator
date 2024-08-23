import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args)
    {
        JFrame frame=new JFrame("Expense Calculator");
        Font font =new Font (	"Ariel",Font.BOLD,20);
        Connection conn;

        JLabel expense_title_label=new JLabel(" Expenses ");
        expense_title_label.setBounds(190,20,120,50);
        frame.add(expense_title_label);
        expense_title_label.setForeground(Color.RED);

        JLabel expense_type=new JLabel("Expense Type :");
        expense_type.setBounds(20,80,120,50);
       frame.add(expense_type);

       JTextField expense_type_field=new JTextField();
       expense_type_field.setBounds(135,93,250,25);
       frame.add(expense_type_field);

        JLabel expense_amount=new JLabel("Expense Amount :");
        expense_amount.setBounds(20,110,120,50);
        frame.add(expense_amount);

        JTextField expense_amount_field=new JTextField();
        expense_amount_field.setBounds(135,123,250,25);
        frame.add(expense_amount_field);

        JLabel expense_Income_label=new JLabel(" Income ");
        expense_Income_label.setBounds(190,160,120,50);
        frame.add(expense_Income_label);
        expense_Income_label.setForeground(Color.BLUE);

        JLabel income_label=new JLabel("Add Income : ");
        income_label.setBounds(20,220,120,50);
        frame.add(income_label);

        JTextField income_field=new JTextField();
        income_field.setBounds(135,230,250,25);
        frame.add(income_field);

        expense_title_label.setFont(font);
        expense_Income_label.setFont(font);

        JButton add_button=new JButton("Add");
        add_button.setBounds(150,280,80,30);
        frame.add(add_button);
        add_button.setForeground(Color.BLUE);
        add_button.setBackground(Color.green);

        JButton clear_button=new JButton("Clear");
        clear_button.setBounds(240,280,80,30);
        frame.add(clear_button);
        clear_button.setBackground(Color.GREEN);

//        add action to the clear button
        clear_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                expense_amount_field.setText("");
                income_field.setText("");
                expense_type_field.setText("");
            }
        });

        //Insert the record to mySql
        String url="jdbc:mysql://localhost:3306/ExpenseCalculator";
        String username="root";
        String password="";
        try {
            conn= DriverManager.getConnection(url,username,password);
            System.out.println("DB Connected");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String expense_type=expense_type_field.getText();
                int expense_amount=expense_amount_field.getText().isEmpty()?0:Integer.valueOf(expense_amount_field.getText());
                if(expense_amount!=0 && !expense_type.isEmpty())
                {
                    String insert_data="INSERT INTO expenseTB (expense_type,expense_amount,income_amount) VALUES(?,?,?)";

                    try {
                        PreparedStatement statement = conn.prepareStatement(insert_data);
                        statement.setString(1, expense_type);
                        statement.setInt(2, Integer.parseInt(expense_amount_field.getText()));
                        statement.setInt(3,Integer.parseInt(income_field.getText()));
                        statement.execute();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"The Value cant't be 0 or empty");
                }
            }
        } );



        frame.setSize(500,400);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
    }
}