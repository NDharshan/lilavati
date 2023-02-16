package iks;
import java.io.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.text.DecimalFormat;
import java.awt.geom.AffineTransform;




public class Solution extends JPanel implements ActionListener, MouseWheelListener, MouseListener,MouseMotionListener {


    private static final DecimalFormat df = new DecimalFormat("0.00000000");
    private static final DecimalFormat lbf = new DecimalFormat("0.00");
  private double zoomFactor =250;
  public String equation = new String();;
  public JLabel equationLabel;
  public JTextField graphFunctionBox;
  private JFrame graph;
  private boolean isZooming;
  private Point dragStart;
  private Point dragEnd;
  private Point mouse;
  private double targetZoom ;
  private boolean isMouseDown;
  private AffineTransform coordTransform = new AffineTransform();
  private double xOffset, yOffset=0;
  public float[] dataPoints ; // DataPoints on Screen with length of w * n   N being how precise the data is.
  Font font1 = new Font("SansSerif", Font.BOLD, 20);
  private Timer time = new Timer(50,this);
  public Solution(){
    Equation a = new Equation("(-x)^x" , 0, true);
    System.out.println("Debug " + a.value + " "+ Math.pow(0, 0));
    //System.exit(ABORT);
    JPanel UI = new JPanel();
    UI.setBounds(0,0,400,150);
    graph = new JFrame();
    setFocusable(true);
    setBounds(0, 20, 400, 200);
    graphFunctionBox = new JTextField(25);
    graphFunctionBox.setFont(font1);
    equationLabel = new JLabel("Enter the equation within this box");
    equationLabel.setFont(font1);
    UI.add(equationLabel);
    UI.add(graphFunctionBox);
    graph.addMouseWheelListener(this);
    graph.setTitle("Graphing your math functions");
    graph.setSize(400,300);
    graph.setVisible(true);
    //graph.setLayout((LayoutManager) new FlowLayout());
    graph.add(this);
    this.add(UI);
    repaint();
    addMouseListener(this);
    addMouseMotionListener(this);
    targetZoom = zoomFactor;
    addMouseWheelListener(this);
    getRootPane().setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, Color.RED));
    graph.getContentPane().setBackground(new Color(250,211,211));
    time.start();
  }
  public void paintComponent(Graphics g){
  
    this.setBounds(0,7,graph.getWidth(), graph.getHeight()-40);
    zoomFactor =lerp(zoomFactor ,targetZoom , 0.3 );
    super.paintComponent(g);
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    
    if(mouse==null) {
      mouse = new Point(0,0);
    }
    Equation mouseX =new Equation(graphFunctionBox.getText().trim() ,(mouse.x -xOffset*-zoomFactor   )/zoomFactor,true) ;
    g.setColor(Color.BLACK);
    if(mouse!=null)
    g.drawString("x :" +  (mouse.x -xOffset*-zoomFactor   )/zoomFactor+ " " + mouseX.value , 200, 200);
    try{
      g.setColor(Color.blue);
      //y axis
      g.drawLine( (int)(xOffset*-zoomFactor) , this.getHeight(),(int)(xOffset*-zoomFactor) , 0   );
      //x axis
      g.setColor(Color.RED);
      g.drawLine( 0,this.getHeight()+ (int)(yOffset*-zoomFactor) , this.getWidth() ,  this.getHeight()+(int)(yOffset*-zoomFactor));
      //double[][] data =loadDataPoints( graphFunctionBox.getText()  , xOffset , xOffset + (this.getWidth())*(1/zoomFactor) , 1/zoomFactor);
      double x01= 0;
      double y1 = 0;
      double y2;
      double a =((0 - (yOffset*-zoomFactor)-(this.getHeight() ))/(- zoomFactor))- ((0 - (yOffset*-zoomFactor) ))/(- zoomFactor) ;
      g.setColor(Color.black);
      double scale= Math.pow(10,(double)((int)(Math.log10(a)-1)));
      int incre =  this.getHeight()/scale/zoomFactor >10? 5:1 ;
      for(int i = 0 ; scale*i *zoomFactor< this.getHeight()-zoomFactor*yOffset ; i+= incre) {
        g.drawString( lbf.format(scale * i) , (int)(xOffset*-zoomFactor)  + 20 , (int)(this.getHeight() - i*scale* zoomFactor+ (int)(yOffset*-zoomFactor)));
      }
      for(int i = 0 ; -scale*i *zoomFactor< this.getHeight()-zoomFactor*yOffset ; i-= incre) {
        g.drawString( lbf.format(scale * i) , (int)(xOffset*-zoomFactor)  + 20 , (int)(this.getHeight() - i*scale* zoomFactor+ (int)(yOffset*-zoomFactor)));
      }
      
      
      for(int i = 0 ; scale*i *zoomFactor< this.getWidth() -zoomFactor*xOffset; i+= incre) {
        g.drawString( lbf.format(scale * i) , (int)(i*scale* zoomFactor+ (int)(xOffset*-zoomFactor)) ,this.getHeight()+(int)(yOffset*-zoomFactor) +20 ) ;
      }
      for(int i = 0 ; -scale*i *zoomFactor< this.getWidth() -zoomFactor*xOffset  ; i-= incre) {
        g.drawString( lbf.format(scale * i) , (int)(i*scale* zoomFactor+ (int)(xOffset*-zoomFactor)) ,this.getHeight()+(int)(yOffset*-zoomFactor) +20 ) ;
      }
      for(int x = 0 ; x+1 < this.getWidth(); x++){
        Equation x2 =new Equation(graphFunctionBox.getText().trim() ,(x+1 -xOffset*-zoomFactor   )/zoomFactor,true) ;
        if(x == 0) {
          
          Equation x1 =new Equation(graphFunctionBox.getText() ,(x -xOffset*-zoomFactor   )/zoomFactor,true) ;
          y1 = x1.value;
        }
        y2= x2.value;
        if(  y2 == Double.NaN){

          continue;
        }
        g.setColor(Color.BLACK);
        g.drawLine((int) x01, (int)(this.getHeight() - y1* zoomFactor+ (int)(yOffset*-zoomFactor)) , x+1, ((int)(this.getHeight() - y2*zoomFactor + (int)(yOffset*-zoomFactor))) < 0 ? -1 :((int)(this.getHeight() - y2*zoomFactor + (int)(yOffset*-zoomFactor)))   );
        y1= y2;
        x01= x+1;
        
      }
      Point pot = new Point((int)(xOffset*-zoomFactor),this.getHeight()+ (int)(yOffset*-zoomFactor) );
      
      
    }catch(Exception e ){
      e.printStackTrace();
      // Equation Provided is Invalid
    }

  }
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() ==time)
      repaint();
  }

  public static void main(String[]args)
  {
    Solution s1 = new Solution();


  }
  public void mousePressed(MouseEvent e) {
    isMouseDown = true;
  }
  public void mouseReleased(MouseEvent e)
  {
    p1 = null;
    isMouseDown = false;
  }
  Point p1 = null;
  public void mouseMoved(MouseEvent e)
  {
    mouse = e.getPoint();
  }
  double zoom =0;
  public void mouseWheelMoved(MouseWheelEvent e)
  {
    zoom += e.getPreciseWheelRotation();
    targetZoom =500 *Math.pow(1.2,zoom);
  }
  public static double[][] loadDataPoints(String input , double min , double max , double inc) throws Exception  {
    input=input.replaceAll(" ", "");
    String[] equation= input.trim().split("=");


    if(equation.length >2) 
    {
      return null; 
    }/*
    if(equation.length ==1) {

      return compileNRun(equation[0].trim() , min, max, inc);
    }
    if(equation[0].trim().toLowerCase().equals("y")) {

      return compileNRun(equation[1].trim() , min, max, inc);

    }
    if(equation[1].trim().toLowerCase().equals("y")) {

      return compileNRun(equation[0].trim() , min, max, inc);

    }*/

    //Equation a = new Equation("(5/20)*10^4 +21",1,true);

    if(equation.length ==1) {

      return compileNRun(equation[0].trim() , min, max, inc);
    }
    System.err.println("Not A valid Equation " + input+"  "+ equation[1] + " "+ equation[0]);
    return null;



  }
  static String previous = " ";
  public static double[][] compileNRun(String input , double min , double max , double inc) throws Exception {
    String code =formatToScript(input);
    if(previous.equals(code)) {
      return run(min , max , inc);

    }
    if(code.length()==0)code ="Double.NaN";
    previous= code;
    String script = 
        "import java.math.RoundingMode;\r\n"
            + "import java.text.DecimalFormat;\r\n"
            + "\r\n"
            + "public class Snippet {\r\n"
            + "    private static final DecimalFormat df = new DecimalFormat(\"0.00000\");\r\n"
            + "  public static void main(String[] args) {\r\n"
            + "    df.setRoundingMode(RoundingMode.HALF_EVEN);\r\n"
            + "    double min =0;\r\n"
            + "    double max = 100;\r\n"
            + "    double increment= 0.25;\r\n"
            + "    if(args.length>0) {\r\n"
            + "      min = Double.parseDouble(args[0].trim());\r\n"
            + "      if(args.length >1) {\r\n"
            + "        max = Double.parseDouble(args[1].trim());\r\n"
            + "      }if(args.length >2) {\r\n"
            + "        increment = Double.parseDouble(args[2].trim());\r\n"
            + "      }\r\n"
            + "    }\r\n"
            + "    for(int i = 0 ; i <(int) ((max - min)/increment ) ; i++) {\r\n"
            + "      double result = solve((double) i * increment + min);\r\n"
            + "      System.out.println(df.format(((double) i * increment + min))+ \" \" + df.format(result));\r\n"
            + "      \r\n"
            + "    }\r\n"
            + "\r\n"
            + "  }\r\n"
            + "  public static double solve(double x) {\r\n"
            + "    double y=Double.NaN;\r\n"
            + "    ";



    script +=  "y = "+ code+ ";\n" ;



    script +="return y;\r\n"
        + "  }\r\n"
        + "  public double factorial(int a) {\r\n"
        + "    double fact =1;\r\n"
        + "    int number=5;//It is the number to calculate factorial    \r\n"
        + "    for(int i=1;i<=number;i++){    \r\n"
        + "      if(Double.MAX_VALUE / i <= fact) {\r\n"
        + "        return Double.NaN;\r\n"
        + "      }\r\n"
        + "      fact=fact*i;    \r\n"
        + "      \r\n"
        + "    }\r\n"
        + "    return fact;    \r\n"
        + "  }\r\n"
        + "}\r\n"
        + "";

    File scr = new File("Snippet.java");
    scr.createNewFile();
    PrintWriter scriptStream = new PrintWriter(scr);
    scriptStream.print(script);
    scriptStream.close();
    Runtime rt = Runtime.getRuntime();
    Process pr = rt.exec("javac Snippet.java");
    BufferedWriter pr_writer = new BufferedWriter(
        new OutputStreamWriter(pr.getOutputStream()));

    BufferedReader br = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
    while(br.readLine() != null);
    br.close();
    pr_writer.close();
    return run(min , max , inc);
  }
  public static double lerp(double a, double b, double f) {

    return a + f * (b - a);
}
  public class Equation{
    public double value;
    String a = "";

    public Equation(String string,double xVal, boolean init) {
      a = string;
      a= string.replaceAll(" ", "");
      for(int i = 0 ; i < a.length(); i++ ) {
        if( a.charAt(i)=='(' || a.charAt(i)=='x') {
          if(i !=0) {
            if(isNum(a.charAt(i-1)) || a.charAt(i-1) == ')'){
              a =a.substring(0, i) + "*" +a.substring( i) ;
            }


          }
        }
        if( a.charAt(i)==')'||a.charAt(i)=='x') {
          if(i !=a.length()-1) {
            if(isNum(a.charAt(i+1)) || a.charAt(i+1) == '(' ){
              a =a.substring(0, i+1) + "*" +a.substring( i+1) ;
            }


          }
        }
      }


      a= a.replaceAll("x", "("+xVal+ ")");

      try {
        sove(xVal);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        value = Double.NaN;
      }
    }
    public void sove(double xVal) throws Exception{

      while(a.contains("(") &&a.contains(")")) {
          int num =0;
          int start=0;
          
          for(int i = a.indexOf("(") ; i < a.length() ; i++ ) {
            if( a.charAt(i) == '(') {
              if(num == 0)
              {
                start = i;
              }
              num ++;

            }else if( a.charAt(i) == ')') {
              if(num == 1)
              {

                Equation b = new Equation(a.substring(start+1,i ) ,xVal, false);
                if(i+2 < a.length()) {
                  if(a.charAt(i+1) == '^' ) {
                    if(a.charAt(i+2)!='(') {

                      int s ;

                      for(s =i +2 ; s < a.length() ; s++) {
                        if(a.charAt(s-1) == 'E') 
                          continue;
                        if(a.charAt(s) == '-'&& s==i+2 )continue;
                        if(isBasicOperator(a.charAt(s)))break;
                      }
                      
                      a = a.substring(0, start )  
                          +df.format( Math.pow(b.value,  Double.parseDouble(a.substring(i+2, s)) ) )
                          +a.substring(s);
                      break;
                    }else {

                    }
                  }
                }
                a = a.substring(0, start )  
                    +df.format(b.value )
                    +a.substring(i+1);
                break;
              }
              num --;

            }
          }
          
      }
      while(a.contains("^")) {
        int inde = a.indexOf("^");

        int e ;
        for(e=inde-1 ; e >=0 ; e --) {
          
          if(isBasicOperator(a.charAt(e)))break;

        }
        int s ;

        for(s =inde +1 ; s < a.length() ; s++) {
          if(a.charAt(s-1) == 'E') 
            continue;
          if(a.charAt(s) == '-'&& s==inde+1 )continue;
          if(isBasicOperator(a.charAt(s)))break;
        }

        a = a.substring(0, e+1 )  
            +df.format(Math.pow(Double.parseDouble(a.substring(e+1, inde))
                , Double.parseDouble(a.substring(inde+1, s)))) 
            +a.substring(s);

      }

      while(a.contains("*")) {
        int inde = a.indexOf("*");

        int e ;
        for(e=inde-1 ; e >=0 ; e --) {
          

          if(a.charAt(e) == '-') {

            if(e!=0 &&a.charAt(e-1) == 'E') 
              continue;
            if(e-1 <0) {
              continue;
            }
            if( !isNum(a.charAt(e-1)) )
              continue;
          }
          if(isBasicOperator(a.charAt(e)))break;

        }
        int s ;

        for(s =inde +1 ; s < a.length() ; s++) {
          if(a.charAt(s-1) == 'E') 
            continue;
          if(a.charAt(s) == '-'&& s==inde+1 )continue;
          if(isBasicOperator(a.charAt(s)))break;
        }

        a = a.substring(0, e+1 )  
            +df.format(Double.parseDouble(a.substring(e+1, inde))
                * Double.parseDouble(a.substring(inde+1, s))) 
            +a.substring(s);

      }

      while(a.contains("/")) {
        int inde = a.indexOf("/");

        int e ;
        for(e=inde-1 ; e >=0 ; e --) {
          
          if(a.charAt(e) == '-') {
            

            if(e!=0 &&a.charAt(e-1) == 'E') 
              continue;
            if(e-1 <0) {
              continue;
            }
            if( !isNum(a.charAt(e-1)) )
              continue;
          }
          if(isBasicOperator(a.charAt(e)))break;

        }
        int s ;

        for(s =inde +1 ; s < a.length() ; s++) {
          if(a.charAt(s-1) == 'E') 
            continue;
          if(a.charAt(s) == '-'&& s==inde+1 )continue;
          if(isBasicOperator(a.charAt(s)))break;
        }

        a = a.substring(0, e+1 )  
            +df.format(Double.parseDouble(a.substring(e+1, inde))
                / Double.parseDouble(a.substring(inde+1, s))) 
            +a.substring(s);

      }
      while(a.contains("+")) {
        int inde = a.indexOf("+");

        int e ;
        for(e=inde-1 ; e >=0 ; e --) {

          if(a.charAt(e) == '-') {

            if(e!=0 &&a.charAt(e-1) == 'E') 
              continue;
            if(e-1 <0) {
              continue;
            }
            if( !isNum(a.charAt(e-1)) )
              continue;
          }
          if(isBasicOperator(a.charAt(e)))break;

        }
        int s ;

        for(s =inde +1 ; s < a.length() ; s++) {
          if(a.charAt(s-1) == 'E') 
            continue;
          if(a.charAt(s) == '-'&& s==inde+1 )continue;
          if(isBasicOperator(a.charAt(s)))break;
        }
        
        a = a.substring(0, e+1 )  
            +df.format(Double.parseDouble(a.substring(e+1, inde))
                + Double.parseDouble(a.substring(inde+1, s))) 
            +a.substring(s);
        
      }
      while(a.contains("-")) {
        int inde = a.indexOf("-");

        int e ;
        for(e=inde-1 ; e >=0 ; e --) {

          if(a.charAt(e) == '-') {
            if(e-1 <0) {
              continue;
            }
            if(e!=0 &&a.charAt(e-1) == 'E') 
              continue;
            
            if( !isNum(a.charAt(e-1)) )
              continue;
          }
          if(isBasicOperator(a.charAt(e)))break;

        }
        int s ;

        for(s =inde +1 ; s < a.length() ; s++) {
          if(a.charAt(s-1) == 'E') 
            continue;
          if(a.charAt(s) == '-'&& s==inde+1 )continue;
          if(isBasicOperator(a.charAt(s)))break;
        }
        if(inde ==0) {
          value -= Double.parseDouble(a.substring(1, s)) ;
          a=a.substring(s);
          continue;
        }

        a = a.substring(0, e+1 )  
            +df.format(Double.parseDouble(a.substring(e+1, inde))
                - Double.parseDouble(a.substring(inde+1, s))) 
            +a.substring(s);

      }
      if(a.isEmpty()) {
        return ;
      }
      value += Double.parseDouble(a);
      return ;
    }

  }

  public static double[][] run(double min , double max , double inc) throws IOException {
    double[][] result = new double[2][ (int) ((max - min)/inc )];
    Runtime rt = Runtime.getRuntime();
    Process pr = rt.exec("java Snippet " +  min + " " + max + " " +inc );
    BufferedWriter pr_writer = new BufferedWriter(
        new OutputStreamWriter(pr.getOutputStream()));

    BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
    String ch;
    int data =0;
    for(double i = min ; i <=max ; i+= inc) {

      if ((ch = br.readLine()) != null)
      {
        if(data >= result[0].length)break;
        String [] a = ch.trim().split("\\s");
        result[0][data] = Double.parseDouble(a[0]);
        result[1][data] = Double.parseDouble(a[1]);

        data++;
      }

    }
    br.close();
    pr_writer.close();
    return result;
  }
  public static String formatToScript(String stored) throws Exception {
    stored = stored.replaceAll(" ", "");
    while(stored.contains("^")) {
      int index =stored.indexOf("^");
      int replacedBottom = index;
      int replacedTop = index;
      if(stored.charAt(index-1) == ')') {// If it's (Equation )^123
        int startBracket = index-2;// Index of (
        while(startBracket>0 && stored.charAt(startBracket) != '(') {
          startBracket--;
        }
        replacedBottom=startBracket;
      }else {
        int startBracket = index;// index of first number
        while( startBracket>0&&isNum(stored.charAt(startBracket-1))) {
          startBracket--;
        }
        replacedBottom=startBracket;

      }
      if(stored.charAt(index+1) == '(') {// If it's Equation^(123
        int startBracket = index+2;// Index of (
        while(startBracket<stored.length() && stored.charAt(startBracket) != ')') {
          startBracket++;
        }
        replacedTop=startBracket;
      }else {
        int startBracket = index;// index of first number
        while( startBracket+1<stored.length()&&isNum(stored.charAt(startBracket+1))) {
          startBracket++;
        }
        replacedTop=startBracket;

      }


      String temp ="(double)Math.pow(" + stored.substring(replacedBottom, index) +", "+ stored.substring(index+1, replacedTop+1)  + ")";
      stored =stored.substring(0, replacedBottom) +temp +stored.substring( replacedTop+1) ;
    }while(stored.contains("!")) {// Factorial
      int index =stored.indexOf("!");

      int replacedBottom = index;
      if(stored.charAt(index-1) == ')') {// If it's (Equation )^123
        int startBracket = index-2;// Index of (
        while(startBracket>0 && stored.charAt(startBracket) != '(') {
          startBracket--;
        }
        replacedBottom=startBracket;
      }else {
        int startBracket = index;// index of first number
        while( startBracket>0&&isNum(stored.charAt(startBracket-1))) {
          startBracket--;
        }
        replacedBottom=startBracket;

      }


      String temp ="factorial(" + stored.substring(replacedBottom, index) + ")";
      stored =stored.substring(0, replacedBottom) +temp +stored.substring( index+1) ;
    }while(stored.contains("|")) {// Factorial
      int index =stored.indexOf("|");

      int top = index;

      // If it's (Equation )^123
      int startBracket = index+1;// Index of (
      while(startBracket< stored.length()&& stored.charAt(startBracket) != '|') {
        startBracket++;
      }
      top=startBracket;




      String temp ="abs(" + stored.substring(index+1, top) + ")";
      stored =stored.substring(0, index) +temp +stored.substring( top+1) ;

    }





    stored =stored.replaceAll("sqrt", "(double)Math.sqrt");
    stored =stored.replaceAll("floor", "(double)Math.floor");
    stored =stored.replaceAll("abs", "(double)Math.abs");

    for(int i = 0 ; i < stored.length(); i++ ) {
      if( stored.charAt(i)=='(' || stored.charAt(i)=='x') {
        if(i !=0) {
          if(isNum(stored.charAt(i-1)) || stored.charAt(i-1) == ')'){
            stored =stored.substring(0, i) + "*" +stored.substring( i) ;
          }


        }
      }
      if( stored.charAt(i)==')'||stored.charAt(i)=='x') {
        if(i !=stored.length()-1) {
          if(isNum(stored.charAt(i+1)) || stored.charAt(i+1) == '(' ){
            stored =stored.substring(0, i+1) + "*" +stored.substring( i+1) ;
          }


        }
      }
    }


    return stored ;
  }
  public static boolean isBasicOperator(char a) {
    return (a == '^'||a == '+' || a== '-' || a =='%' || a == '*' || a=='/');
  }
  public static boolean isNum(char a) { // including Periods
    return(((int) a >=46 && (int)a<=57 && (int) a != 57 ) || a=='x' || a=='E') ;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // TODO Auto-generated method stub

    isMouseDown = true;
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub

  }
  @Override
  public void mouseDragged(MouseEvent e) {
    // TODO Auto-generated method stub

    if(p1 == null) {
      p1 = e.getPoint();

    }
    else {
      xOffset -= (e.getPoint().getX() - p1.getX() )/ zoomFactor;
      yOffset -= (e.getPoint().getY() - p1.getY() )/ zoomFactor;

      p1 = e.getPoint();
    }

  }
}