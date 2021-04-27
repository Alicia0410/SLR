package examples.SLR;

import jade.core.AID;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
  @author Alicia Serna 
 */

class SLRGui extends JFrame {	
	private SLR agent;
	private JTextField newx;

    SLRGui(SLR a){
        super(a.getLocalName());

        agent = a;

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1,1));
        p.add(new JLabel("valor de x:"));
        newx = new JTextField(15);
		p.add(newx);
		getContentPane().add(p, BorderLayout.CENTER);

        JButton btnAdd = new JButton("Agregar");
        btnAdd.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String x = newx.getText().trim();
					agent.requestX(x);
					newx.setText("");
					
				}
				catch (Exception e) {
                    System.out.println("El error fue "+e.getMessage());
				}
            }
		} );
		p = new JPanel();
		p.add(btnAdd);
		getContentPane().add(p, BorderLayout.SOUTH);
		
			
		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				agent.doDelete();
			}
		} );
		
		setResizable(false);
	}
    public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}	
}

