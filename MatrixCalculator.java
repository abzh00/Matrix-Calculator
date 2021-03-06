import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class MatrixCalculator
{
private boolean DEBUG = false;
private boolean INFO = true;
private static int max = 100;
private static int decimals = 3;
private JLabel statusBar;
private JTextArea taA, taB, taC;
private int iDF = 0;
private int n = 4;
private int m;
private static NumberFormat nf;

public Component createComponents() 
{
    
    /*== MATRICES ==*/
    taA = new JTextArea();
    taB = new JTextArea();
    taC = new JTextArea();
    
   
    JPanel paneMs = new JPanel();
    paneMs.setLayout(new BoxLayout(paneMs, BoxLayout.X_AXIS));
    paneMs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    paneMs.add(MatrixPane("Matrix A", taA));
    paneMs.add(Box.createRigidArea(new Dimension(10, 0)));
    paneMs.add(MatrixPane("Matrix B", taB));
    paneMs.add(Box.createRigidArea(new Dimension(10, 0)));
    paneMs.add(MatrixPane("Matrix C", taC));
   

    /*== OPERATION BUTTONS ==*/
    JPanel paneBtn = new JPanel();
    paneBtn.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    paneBtn.setLayout(new GridLayout(4,4));
    JButton btnApB = new JButton("A + B = C");
    JButton btnAmB = new JButton("A * B = C");
    JButton btnBmA = new JButton("B * A = C");
    JButton btnAdjA = new JButton("Adjoint(A) = C");
    JButton btnInvA = new JButton("Inverse(A) = C");
    JButton btnInvB = new JButton("Inverse(B) = C");
    JButton btnTrnsA = new JButton("Transpose(A) = C");
    JButton btnDetA = new JButton("Determ(A) = C");
    JButton btnDetB = new JButton("Determ(B) = C");
    JButton btnCmA = new JButton("constant(B)*A = C");
    JButton btnAsB = new JButton("A - B = C");
    JButton btnTrA = new JButton("Trace(A) = C");
    JButton btnColl = new JButton("Coll_or_Not(A)");
    JButton btnArea = new JButton("Area of Triangle(A)");
    JButton btnC = new JButton("C");
    JButton btnCof = new JButton("Cofactor(A)");
    paneBtn.add(btnApB);
    paneBtn.add(btnAsB);
    paneBtn.add(btnAmB);
    paneBtn.add(btnBmA);
    paneBtn.add(btnCmA);
    paneBtn.add(btnTrnsA);
    paneBtn.add(btnCof);
    paneBtn.add(btnAdjA);
    paneBtn.add(btnInvA);
    paneBtn.add(btnInvB);    
    paneBtn.add(btnDetA);
    paneBtn.add(btnDetB);
    paneBtn.add(btnTrA);
    paneBtn.add(btnColl);
    paneBtn.add(btnArea);
    paneBtn.add(btnC);
     
    /*== ADD BUTTON Listeners ==*/
    btnApB.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try { DisplayMatrix(AddMatrix(ReadInMatrix(taA), ReadInMatrix(taB)), taC); }
            catch(Exception e) { System.err.println("Error: " + e); }
        }
    });
    
    btnAsB.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try { DisplayMatrix(SubtractMatrix(ReadInMatrix(taA), ReadInMatrix(taB)), taC); }
            catch(Exception e) { System.err.println("Error: " + e); }
        }
    });
    
    btnCmA.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try { DisplayMatrix(MultiplyMatrixToC(ReadInMatrix(taA), ReadInMatrix(taB)), taC); }
            catch(Exception e) { System.err.println("Error: " + e); }  
        }
    });
    
    btnAmB.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try { DisplayMatrix(MultiplyMatrix(ReadInMatrix(taA), ReadInMatrix(taB)), taC); }
            catch(Exception e) { System.err.println("Error: " + e); }  
        }
    });

    btnBmA.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
            try { DisplayMatrix(MultiplyMatrix(ReadInMatrix(taB), ReadInMatrix(taA)), taC); }
            catch(Exception e) { System.err.println("Error: " + e); }
        }
    });

    btnInvA.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try { DisplayMatrix(Inverse(ReadInMatrix(taA)), taC); }
            catch(Exception e) { System.err.println("Error: " + e); } 
        }
    });

    btnInvB.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try { DisplayMatrix(Inverse(ReadInMatrix(taB)), taC); }
            catch(Exception e) { System.err.println("Error: " + e); }
        }
    });

    btnAdjA.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try { DisplayMatrix(Adjoint(ReadInMatrix(taA)), taC); }
            catch(Exception e) { System.err.println("Error: " + e); } 
        }
    });       
    
    btnTrnsA.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try { DisplayMatrix(Transpose(ReadInMatrix(taA)), taC); }
            catch(Exception e) { System.err.println("Error: " + e); } 
        }
    }); 

    btnCof.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try { DisplayMatrix(Cofactor(ReadInMatrix(taA)), taC); }
            catch(Exception e) { System.err.println("Error: " + e); } 
        }
    }); 
    
    btnDetA.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try { taC.setText("Determinant A: " + nf.format(Determinant(ReadInMatrix(taA)))); }
            catch(Exception e) { System.err.println("Error: " + e); } 
        }
    }); 
    
    btnDetB.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try { taC.setText("Determinant B: " + nf.format(Determinant(ReadInMatrix(taB)))); }
            catch(Exception e) { System.err.println("Error: " + e); } 
        }
    });  
    
    btnTrA.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try { taC.setText("Trace A: " + nf.format(Trace(ReadInMatrix(taA)))); }
            catch(Exception e) { System.err.println("Error: " + e); } 
        }
    });
    
    btnArea.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try { taC.setText("Area is: " + nf.format(AreaofTri(ReadInMatrix(taA)))); }
            catch(Exception e) { System.err.println("Error: " + e); } 
        }
    });
    
    btnColl.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try { taC.setText((CollOrNot(ReadInMatrix(taA)))); }
            catch(Exception e) { System.err.println("Error: " + e); } 
        }
    });
    
    btnC.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) 
        { 
            try {taA.setText(""); taB.setText(""); taC.setText("");}
            catch(Exception e) { System.err.println("Error: " + e); } 
        }
    });

    /*== MAIN PANEL ==*/
    JPanel pane = new JPanel();
    pane.setBorder(BorderFactory.createEmptyBorder(5,5, 5, 5));
    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
    pane.add(paneMs);
    pane.add(paneBtn);
    
    JPanel fpane = new JPanel();
    fpane.setLayout(new BorderLayout());
    fpane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    fpane.add("Center", pane);
    statusBar = new JLabel("Ready");
    fpane.add("South", statusBar);
    
    return fpane;
}
/*== Setup Invidual Matrix Panes ==*/
private JPanel MatrixPane(String str, JTextArea ta)
{
    JScrollPane scrollPane= new JScrollPane(ta);
    int size = 200;
        
    scrollPane.setPreferredSize(new Dimension(size, size));
    JLabel label = new JLabel(str);
    label.setLabelFor(scrollPane);
    
    JPanel pane = new JPanel();
    pane.setBorder(BorderFactory.createEmptyBorder(5,5, 5, 5));
    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
    pane.add(label);
    pane.add(scrollPane);
    
    return pane;
}    
public static void main(String[] args) 
{
    JFrame frame = new JFrame("Matrix Calculator");
    frame.setSize(new Dimension(725,200));
    MatrixCalculator app = new MatrixCalculator();  
    
    Component contents = app.createComponents();
    frame.getContentPane().add(contents, BorderLayout.CENTER);
    frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    });
    frame.pack();
    frame.setVisible(true);
    
    nf = NumberFormat.getInstance();
    nf.setMinimumFractionDigits(1);
    nf.setMaximumFractionDigits(decimals); 
    
} 
// ------------------------------------------------------------------------------ 
public float[][] ReadInMatrix(JTextArea ta) throws Exception
{
    if (DEBUG) { System.out.println("Reading In Matrix"); }

    /*== Parse Text Area ==*/
    String rawtext = ta.getText();
    String val = "";
    int i=0; int j=0;
    int[] rsize = new int[max];
    
    /*== Determine Matrix Size/Valid ==*/
    StringTokenizer ts = new StringTokenizer(rawtext, "\n");
    while (ts.hasMoreTokens()) 
    {
        StringTokenizer ts2 = new StringTokenizer(ts.nextToken());
        while (ts2.hasMoreTokens()) { ts2.nextToken(); j++; }
        rsize[i] = j; i++; j=0;
    }
    statusBar.setText("Matrix Size: " + i);
    if ((DEBUG) || (INFO)) { System.out.println("Matrix Size: " + i); }
    
    for (int c=0; c < i; c++)
    {
        if (DEBUG) { System.out.println("i="+i+ "  j="+rsize[c] + "   Column: "+c); }
            
        if (rsize[c] != rsize[0]) { 
            statusBar.setText("Invalid Matrix Entered. Size Mismatch."); 
            throw new Exception("Invalid Matrix Entered. Size Mismatch.");
        }
    }
    /*== set matrix size ==*/
    n = i;
    m = rsize[0];
     
    float matrix[][] = new float[n][m];
    i = j = 0; val="";
      
    /*== Do the actual parsing of the text now ==*/
    StringTokenizer st = new StringTokenizer(rawtext, "\n");
    while (st.hasMoreTokens()) 
    {
        StringTokenizer st2 = new StringTokenizer(st.nextToken());
        while (st2.hasMoreTokens())
        {
            val = st2.nextToken();
            try { matrix[i][j] = Float.valueOf(val).floatValue(); }
            catch (Exception exception) { statusBar.setText("Invalid Number Format"); }
            j++;
        }
        i++; j=0;
    }
 
    if (DEBUG)
    {
        System.out.println("Matrix Read::");
        for (i=0; i<n; i++)
        {
            for (j=0; j<m; j++)
                { System.out.print("m["+i+"]["+j+"] = " + matrix[i][j] + "   "); }
            System.out.println();
        }
    }
 
    return matrix;
}
//--------------------------------------------------------------
// Display Matrix in TextArea
//--------------------------------------------------------------
public void DisplayMatrix(float[][] matrix, JTextArea ta)
{

/*== TODO: Better Formatting of Resultant Matrix ==*/

    if (DEBUG) { System.out.println("Displaying Matrix"); }
    int tms = matrix.length;
    int tmr = matrix[0].length;
    
    String rstr = ""; String dv = "";
    
    for (int i=0; i < tms; i++)
    {
        for (int j=0; j < tmr; j++)
        {
            dv = nf.format(matrix[i][j]);
            rstr = rstr.concat(dv + "  ");
        }
        
        rstr = rstr.concat("\n");
    }
    
    ta.setText(rstr);
}
//--------------------------------------------------------------
public float[][] AddMatrix(float[][] a, float[][] b) throws Exception
{
    int tmsA = a.length; int tmsB = b.length; int tmsAr = a[0].length; int tmsBr = b[0].length;
    if (tmsA != tmsB || tmsAr != tmsBr) { statusBar.setText("Matrix Size Mismatch"); }
    
    float matrix[][] = new float[tmsA][tmsAr];
    float matrix1[][] = new float[tmsA][tmsAr];
    
    if (tmsA >= tmsB && tmsAr >= tmsBr) {
    	for (int i=0; i < tmsA; i++)  
    	for (int j=0; j < tmsAr; j++)
    	{
    		matrix[i][j] = a[i][j] + b[i][j];
    	}
    	for (int i=0; i<tmsA; i++) {
        	for (int j = 0; j < tmsAr; j++) {
        		matrix1[i][j] = matrix[i][j];
        	}
    	}
    	return matrix1;
    }else {
    	if (INFO) { System.out.println("Invalid input"); }
    	return null;
    }
}
//--------------------------------------------------------------
public float[][] SubtractMatrix(float[][] a, float[][] b) throws Exception
{
    int tmsA = a.length; int tmsB = b.length; int tmsAr = a[0].length; int tmsBr = b[0].length;
    if (tmsA != tmsB || tmsAr != tmsBr) { statusBar.setText("Matrix Size Mismatch"); }
    
    float matrix[][] = new float[tmsA][tmsAr];
    float matrix1[][] = new float[tmsA][tmsAr];
    
    if (tmsA >= tmsB && tmsAr >= tmsBr) {
    	for (int i=0; i < tmsA; i++)  
    	for (int j=0; j < tmsAr; j++)
    	{
    		matrix[i][j] = a[i][j] - b[i][j];
    	}
    	for (int i=0; i<tmsA; i++) {
        	for (int j = 0; j < tmsAr; j++) {
        		matrix1[i][j] = matrix[i][j];
        	}
    	}
    	return matrix1;
    }else {
    	if (INFO) { System.out.println("Invalid input"); }
    	return null;
    }
}
//--------------------------------------------------------------
public float[][] MultiplyMatrix(float[][] a, float[][] b) throws Exception
{

	int tmsA = a.length; int tmsB = b.length; int tmsAr = a[0].length; int tmsBr = b[0].length;
    if (tmsAr != tmsB) { statusBar.setText("Matrix Size Mismatch"); }
    
    float matrix[][] = new float[tmsA][tmsBr];
	float matrix1[][] = new float[tmsA][tmsBr];
	
    if (tmsAr == tmsB) {
    	for (int i=0; i < tmsA; i++) {
    		for (int j=0; j < tmsBr; j++) { matrix[i][j]=0;}
    	}

    	for (int i=0; i < tmsA; i++){
    		for (int j=0; j < tmsBr; j++){
    			for (int p=0; p < tmsAr; p++){
    				matrix[i][j] += a[i][p]*b[p][j];
    			}
    		}
    	}
    	for (int i=0; i<tmsA; i++) {
        	for (int j = 0; j <tmsBr; j++) {
        		matrix1[i][j] = matrix[i][j];
        	}
    	}
    	return matrix1;
    }else {
    	if (INFO) { System.out.println("Invalid input"); }
    	return null;
    }
}
//--------------------------------------------------------------
public float[][] MultiplyMatrixToC(float[][] a, float[][] b) throws Exception
{

	int tmsA = a.length; int tmsB = b.length; int tmsAr = a[0].length; int tmsBr = b[0].length;
    if (tmsBr != tmsB) { statusBar.setText("Matrix Size Mismatch"); }
    
    if(tmsB==tmsBr) {
    	float matrix[][] = new float[tmsA][tmsAr];
    	for (int i=0; i<tmsA; i++) {
    		for (int j=0; j<tmsAr; j++) {
    			matrix[i][j] = a[i][j]*b[0][0];
    		}
    	}
    	return matrix;
    }else {
    	if (INFO) { System.out.println("Invalid input"); }
    	return null;
    }
}
//--------------------------------------------------------------
public float[][] Transpose(float[][] a)
{
    if (INFO) { System.out.println("Performing Transpose..."); }
    int tms = a.length;
    int tmr = a[0].length;
    
    float m[][] = new float[tmr][tms];

    for (int i=0; i<tmr; i++)
    for (int j=0; j<tms; j++)
    {
        m[i][j] = a[j][i];
    }
    return m;
}
//--------------------------------------------------------------
public float[][] Inverse(float[][] a) throws Exception
{
    // Formula used to Calculate Inverse:
    // inv(A) = 1/det(A) * adj(A)
    if (INFO) { System.out.println("Performing Inverse..."); }
    int tms = a.length;

    float m[][] = new float[tms][tms];
    float mm[][] =  Adjoint(a);

    float det = Determinant(a);
    float dd = 0;

    if (det == 0) 
    { 
        statusBar.setText("Determinant Equals 0, Not Invertible."); 
        if (INFO) { System.out.println("Determinant Equals 0, Not Invertible."); }
    }
    else
    {
        dd = 1/det;
    }
    
    for (int i=0; i < tms; i++)
    for (int j=0; j < tms; j++)
    {   m[i][j] = dd * mm[i][j]; }

    return m;
}
//--------------------------------------------------------------
public float[][] Adjoint(float[][] a) throws Exception
{
    if (INFO) { System.out.println("Performing Adjoint..."); }
    int tms = a.length;
    int tmsr = a[0].length;
    
    float m[][] = new float[tms][tms];
    float n[][] = new float[tmsr][tmsr];
    int ii, jj, ia, ja;
    float det;
    if (tms >= tmsr) {
    for (int i=0; i < tms; i++)
    for (int j=0; j < tms; j++)
    {
        ia = ja = 0;

        float ap[][] = new float[tms-1][tms-1];

        for (ii=0; ii < tms; ii++)
        {
            for (jj=0; jj < tms; jj++)
            {
    
                if ((ii != i) && (jj != j))
                {
                    ap[ia][ja] = a[ii][jj];
                    ja++;
                }

            }
            if ((ii != i ) && (jj != j)) { ia++; } 
            ja=0; 
        }

        det = Determinant(ap);
        m[i][j] = (float)Math.pow(-1, i+j) * det;
    }
    m = Transpose(m);
    for (int i=0; i<tms; i++) {
    	for (int j = 0; j < tms; j++) {
    		n[i][j] = m[i][j];
    	}
    }
    }else {
    	for (int i=0; i < tms; i++)
    	    for (int j=0; j < tms; j++)
    	    {
    	        ia = ja = 0;

    	        float ap[][] = new float[tms-1][tms-1];

    	        for (ii=0; ii < tms; ii++)
    	        {
    	            for (jj=0; jj < tms; jj++)
    	            {
    	    
    	                if ((ii != i) && (jj != j))
    	                {
    	                    ap[ia][ja] = a[ii][jj];
    	                    ja++;
    	                }

    	            }
    	            if ((ii != i ) && (jj != j)) { ia++; } 
    	            ja=0; 
    	        }

    	        det = Determinant(ap);
    	        m[i][j] = (float)Math.pow(-1, i+j) * det;
    	    }
    	    m = Transpose(m);
    	    for (int i=0; i<tmsr; i++) {
    	    	for (int j = 0; j < tmsr; j++) {
    	    		n[i][j] = m[i][j];
    	    	}
    	    }
    }
    

    return n;
}
//--------------------------------------------------------------
public float[][] UpperTriangle(float[][] m)
{
    if (INFO) { System.out.println("Converting to Upper Triangle..."); }

    float f1 = 0;   float temp = 0;
    int tms = m.length;  // get This Matrix Size (could be smaller than global)
    int v=1;

    iDF=1;


    for (int col=0; col < tms-1; col++)
    {
        for (int row=col+1; row < tms; row++)
        {
            v=1;
    
            outahere:
            while(m[col][col] == 0)             // check if 0 in diagonal
            {                                   // if so switch until not
                if (col+v >= tms)               // check if switched all rows
                {   iDF=0;
                    break outahere; 
                }
                else
                {
                    for(int c=0; c < tms; c++)
                    {  temp = m[col][c];
                       m[col][c]=m[col+v][c];       // switch rows
                       m[col+v][c] = temp;   
                    }
                    v++;                            // count row switchs
                    iDF = iDF * -1;                 // each switch changes determinant factor
                }
            }

            if ( m[col][col] != 0 )
            {
                if (DEBUG) { System.out.println("tms = " + tms + "   col = " + col + "   row = " + row); }

try {
                f1 = (-1) * m[row][col] / m[col][col];
                for (int i=col; i < tms; i++) { m[row][i] = f1*m[col][i] + m[row][i]; }
}
catch(Exception e) { System.out.println("Still Here!!!"); }

            }
            
        }
    }

    return m;
}
//--------------------------------------------------------------
public float Determinant(float[][] matrix)
{
    if (INFO) { System.out.println("Getting Determinant..."); }
    int tms = matrix.length;
    int tmsr = matrix[0].length;
    
    if (tms==tmsr) {
    	float det=1;

    	matrix = UpperTriangle(matrix);

    	for (int i=0; i < tms; i++)
    	{  det = det * matrix[i][i]; }      // multiply down diagonal

    	det = det * iDF;                    // adjust w/ determinant factor

    	if (INFO) { System.out.println("Determinant: " + det); }
    	return det;
    }else {
    	if (INFO) { System.out.println("Invalid input"); }
    	return 1/0;
    }
}
//--------------------------------------------------------------
public float Trace(float[][] matrix) {
	
	if (INFO) { System.out.println("Getting Trace..."); }
    int tms = matrix.length;
    int tmsr = matrix[0].length;
    float tr = 0;
    if (tms==tmsr) {
    	for (int i=0; i<tms; i++) {
    		tr += matrix[i][i];
    	}
    	return tr;
    }else {
    	if (INFO) { System.out.println("Invalid input"); }
    	return 1/0;
    }	
}
//--------------------------------------------------------------
public String CollOrNot(float[][] matrix) {
	int tms = matrix.length;
	int tmsr = matrix[0].length;
	
	if (tms == 3 && tmsr == 2) {
		float n = AreaofTri(matrix);
		if (n==0) {
			return "Is Collinear";
		}else {
			return "Is Not Collinear";
		}
	}else {
		if (INFO) { System.out.println("Enter 3 point"); }
		return "Enter 3 point";
	}
	
}
//--------------------------------------------------------------
public float AreaofTri(float[][] matrix) {
	int tms = matrix.length;
	int tmsr = matrix[0].length;
	float area[][] = new float[3][3];
	if (tms == 3 && tmsr == 2) {
		for(int i = 0; i<tms; i++) {
			for(int j = 0; j<tmsr; j++) {
				area[i][j] = matrix[i][j];
			}
		}
		for (int i=0; i<3; i++) {
			area[i][2] = 1;
		}
		float n = Determinant(area);
		float m = java.lang.Math.abs(n)/2;
		return m;
	}else {
		if (INFO) { System.out.println("Enter 3 point"); }
		return 1/0;
	}
}
//--------------------------------------------------------------
public float[][] Cofactor(float[][] matrix){
	int tms = matrix.length;
	int tmsr = matrix[0].length;
	float n[][] = null;
	if (tms==tmsr) {
		try {
			n = Transpose(Adjoint(matrix));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}else {
		if (INFO) { System.out.println("Invalid input"); }
		return null;
	}
	return n;
}
}
