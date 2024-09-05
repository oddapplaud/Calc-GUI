import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
class calculator{
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        JFrame frame = new JFrame();
        Font text = new Font("Arial",Font.BOLD, 72);
        Font text2 = new Font("Arial",Font.BOLD, 48);
        DefaultListModel<String> hist_array = new DefaultListModel<>();
        hist_array.addElement("History:");
        JTextField first_num= new JTextField();

        JTextField Last_Hist = new JTextField("");
        Last_Hist.setEditable(false);
        Last_Hist.setFont(text2);
        Last_Hist.setBounds(0,0,499,70);
        frame.add(Last_Hist);

        JList<String> history = new JList<>(hist_array);
        history.setBounds(501,70,225,475);
        history.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2){
                    int sel = (int) history.getSelectedIndex();
                    if(sel>0&&sel<(hist_array.size()-1)){
                        String[] selected = hist_array.get(sel).split(" = ");
                        Last_Hist.setText(selected[0] + " = ");
                        first_num.setText(selected[1]);
                        hist_array.removeRange(sel+1,hist_array.size()-1);
                    }
                    
                }
            }
        });
        frame.add(history);

        JLabel operator= new JLabel("+",SwingConstants.CENTER);
        operator.setFont(text);
        operator.setText("+");
        operator.setBounds(175,70,135,150);
        frame.add(operator);

        first_num.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent a){
                char c = a.getKeyChar();
                if(!((c>='0')&&(c<='9')||(c=='.'))){
                    a.consume();
                }
            
            }
        });
        first_num.setBounds(0, 70, 175, 150);
        first_num.setFont(text);
        frame.add(first_num);
    
        JTextField second_num= new JTextField();
        second_num.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent a){
                char c = a.getKeyChar();
                if(!((c>='0')&&(c<='9')||(c=='.'))){
                    a.consume();
                }
            }
        });
        second_num.setBounds(313, 70, 186, 150);
        second_num.setFont(text);
        frame.add(second_num);

        JButton add_button = new JButton("+");
        add_button.addActionListener((ActionEvent click) -> {
            changeOperator("+", operator);
        });
        add_button.setFont(text2);
        add_button.setBounds(0,220,125,150);
        frame.add(add_button);

        JButton sub_button = new JButton("-");
        sub_button.addActionListener((ActionEvent click) -> {
            changeOperator("-", operator);
        });
        sub_button.setBounds(125,220,125,150);
        sub_button.setFont(text2);
        frame.add(sub_button);

        JButton mult_button = new JButton("x");
        mult_button.addActionListener((ActionEvent click) ->{
            changeOperator("x", operator);
        });
        mult_button.setFont(text2);
        mult_button.setBounds(250,220,125,150);
        frame.add(mult_button);

        JButton div_button = new JButton("÷");
        div_button.addActionListener((ActionEvent click) ->{
            changeOperator("÷", operator);
        });
        div_button.setBounds(375,220,125,150);
        div_button.setFont(text2);
        frame.add(div_button);

        JButton exp_button = new JButton("^");
        exp_button.addActionListener((ActionEvent click) ->{
            changeOperator("^", operator);
        });
        exp_button.setBounds(0,370,125,150);
        exp_button.setFont(text2);
        frame.add(exp_button);

        JButton remain_button = new JButton("%");
        remain_button.addActionListener((ActionEvent click) ->{
            changeOperator("%", operator);
        });
        remain_button.setBounds(125,370,125,150);
        remain_button.setFont(text2);
        frame.add(remain_button);

        JButton cls = new JButton("C");
        cls.addActionListener((ActionEvent click) ->{
            first_num.setText("");
            second_num.setText("");
            Last_Hist.setText("");
            hist_array.clear();
            hist_array.addElement("History:");
        });
        cls.setBounds(250,370,125,150);
        cls.setFont(text2);
        frame.add(cls);


        JButton equalButton = new JButton("=");
        equalButton.addActionListener((ActionEvent click) ->{
            calc(first_num,second_num,operator.getText(),hist_array,Last_Hist);
        });
        equalButton.setBounds(375,370,125,150);
        equalButton.setFont(text);
        frame.add(equalButton);



        frame.setSize(650,560);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
    }   
    public static void changeOperator(String a,JLabel Label){
        Label.setText(a);
        }
    public static void calc(JTextField fnum, JTextField snum, String oper,DefaultListModel<String> hist,JTextField last){
        Double a = Double.valueOf(fnum.getText());
        Double b = Double.valueOf(snum.getText());
        String c;
        switch (oper){
            case "+" -> c=Double.toString(a+b);
            case "-" -> c=Double.toString(a-b);
            case "x" -> c=Double.toString(a*b);
            case "÷" -> c=Double.toString((double)(a/b));
            case "^" -> c=power(a,b);
            case "%" -> c=Double.toString(a%b)

            default -> c="ERR";
        }
        if(b==0&&"÷".equals(oper)){
            c = "NaN";
        }
        if (hist.size()>20){
            hist.remove(1);
        }
        hist.addElement((Double.toString(a) + " " + oper + " " + Double.toString(b) + " = " + c));
        last.setText(Double.toString(a) + " " + oper + " " + Double.toString(b) + " = ");
        fnum.setText(c);
        snum.setText("");
    }
    public static String power(Double base, Double exp){
        Double c=1.0;
        while (exp>0){
                c = c * base;
                exp--;
            }
        return Double.toString(c);
    }
}
