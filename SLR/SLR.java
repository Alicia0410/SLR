package examples.SLR;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
  @author Alicia Serna
 */


public class SLR extends Agent{
    
    private static double[][] dataSet = null;
    private static double beta0 = 0.0;
    private static double beta1 = 0.0;
    private String xNewValue;

    private SLRGui myGui;
    protected void setup() {
    
    myGui = new SLRGui(this);
    myGui.showGui();

    DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Simple Linear Regression");
		sd.setName("JADE-SLR");
		dfd.addServices(sd);

    try {
			DFService.register(this, dfd);
	    }
		catch (FIPAException fe) {
			fe.printStackTrace();
        }
	}
    // Put agent clean-up operations here
	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		// Close the GUI
		myGui.dispose();
		// Printout a dismissal message
		System.out.println("SLR-agent "+getAID().getName()+" terminating.");
	}   
    
    public void requestX(String x){
        addBehaviour(new OneShotBehaviour() {
			public void action() {
				xNewValue = x;
                print();
                System.out.println(xNewValue + ":  "  + (beta0 + Double.parseDouble(xNewValue) * beta1));
                
               /* SP example = new SP("Scatter Chart Example | BORAJI.COM");
                example.setSize(800, 400);
                example.setLocationRelativeTo(null);
                example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                example.setVisible(true);*/
			}

		} );
	}
public static void print() {
        ConjuntoDatos();
        System.out.println("La ecuacion linear es: y = " + beta0 + " + " + beta1 + " * x");
    }

    private static void ConjuntoDatos() {
        dataSet = new double[2][12];
        dataSet[0][0] = 36; dataSet[0][1] = 28; dataSet[0][2] = 35;
        dataSet[0][3] = 39; dataSet[0][4] = 30; dataSet[0][5] = 30;
        dataSet[0][6] = 31; dataSet[0][7] = 38; dataSet[0][8] = 36;
        dataSet[0][9] = 38; dataSet[0][10] = 29; dataSet[0][11] = 26;
        //--
        dataSet[1][0] = 31;  dataSet[1][1] = 29;  dataSet[1][2] = 34;
        dataSet[1][3] = 35; dataSet[1][4] = 29; dataSet[1][5] = 30;
        dataSet[1][6] = 30; dataSet[1][7] = 38; dataSet[1][8] = 34;
        dataSet[1][9] = 33; dataSet[1][10] = 29; dataSet[1][11] = 26;
        calcularBeta0(12);
        calcularBeta1(12);
    }

    private static double simpleSumFrom1ToN(int row, int i, int n) {
        double suma = 0.0;
        for(int j = i - 1; j <= n - 1; j++) 
            suma += dataSet[row][j];
        return suma;
    }

    private static double sumOfProductsFrom1ToN(int row1, int row2, int i, int n) {
        double suma = 0.0;
        for(int j = i - 1; j <= n - 1; j++) 
            suma += dataSet[row1][j] * dataSet[row2][j];
        return suma;
    }

    private static double calcularBeta0(int n) {
        beta0 = (simpleSumFrom1ToN(1, 1, 12) / n) - (calcularBeta1(n) * (simpleSumFrom1ToN(0, 1, 12) / n));
        return beta0;
    }

    private static double calcularBeta1(int n) {
        beta1 = (n * sumOfProductsFrom1ToN(0, 1, 1, 12) - simpleSumFrom1ToN(0, 1, 12) * simpleSumFrom1ToN(1, 1, 12)) / (n * sumOfProductsFrom1ToN(0, 0, 1, 12) - Math.pow(simpleSumFrom1ToN(0, 1, 12), 2.0));
        return beta1;
    }
    
}

