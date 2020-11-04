package WhiteBoard;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
 
 public class WhiteboardCanvas extends JPanel implements Runnable, MouseListener, MouseMotionListener, Printable{
	 public static final int NONE = 0;
	 public static final int LINE = 1;
	 public static final int CIRCLE = 2;
	 public static final int RECT = 3;
 
	 private PrintWriter o;
	 private BufferedReader i;
	 private int mode;
	 public Vector<Drawable> pictures;

	 private int tempX, tempY;
	 private Thread listener;
	 private WhiteboardCanvas canvas;

 
	 public WhiteboardCanvas(String host, int port) {
		 pictures = new Vector<Drawable>();
		 addMouseListener(this);
		 addMouseMotionListener(this);
		 try {
			 Socket s = new Socket(host, port);
			 o = new PrintWriter(s.getOutputStream(), true);
			 i = new BufferedReader(new InputStreamReader(s.getInputStream()));
			 listener = new Thread(this);
			 listener.start();
		 } catch (Exception e) {
			 PrintDebugMessage.print(e);
		 }
	 }
 
	 public void paint(Graphics g) {
		 super.paint(g);
 
		 int n = pictures.size();
		 for(int i=0; i < n; i++) {
			 Drawable d = (Drawable)pictures.elementAt(i);
			 d.paint(g);
			
		 }
	 }
 
	 public void run () {
		 try {
			 while (true) {
				 String line = i.readLine();
				 if(line.equals("!x")) {
					 clear();
				 } else if(line != null) {
					 draw (line);
				 }
			 }
		 }catch (Exception e) {
			 PrintDebugMessage.print(e);
		 }
	 }
	 
	 public void save() {
		 File f=new File("C:\\Users\\admin\\Desktop\\a.txt");
	      try {
	            FileWriter fileWriter = new FileWriter(f);
	            for(int i=0; i<pictures.size(); i++) {
	               fileWriter.write(pictures.elementAt(i).toString());
	               fileWriter.write("\n");
	            }
	            
	            
	            fileWriter.close();
	           } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	           }
	 }
	 
	 
	 
	 public void open(){
		 	Path path = Paths.get("C:\\Users\\admin\\Desktop\\a.txt");
		 	Charset cs = StandardCharsets.UTF_8;
		 	List<String> list = new ArrayList<String>();
		 	try {
		 		list = Files.readAllLines(path,cs);
		 	} catch(IOException e) { 
		 		e.printStackTrace();
		 	}
		 	for(String readLine : list) {
		 		draw(readLine);
		 	}
	 }
	 
	 public void print() {
		
	 }
	 
	 public void send(String msg) {
		 o.println(msg);
	 }
 
	 public void clear() {
		 pictures.removeAllElements();
		 repaint();
	 }
 
	 public void draw(String data) {
		 PrintDebugMessage.print(data);
 
		 int d[] = new int[5];
		 StringTokenizer st = new StringTokenizer(data, ":", false);
		 int index = 0;
		 while(st.hasMoreTokens()) {
			 d[index] = Integer.parseInt(st.nextToken());
			 index++;
		 }
		 switch(d[0]) {
		 case NONE:
			 break;
		 case LINE:
			 pictures.addElement(new Line(d[1], d[2], d[3],d[4]));
			 break;
		 case CIRCLE:
			 pictures.addElement(new Circle(d[1], d[2], d[3],d[4]));
			 break;
		 case RECT:
			 pictures.addElement(new Rect(d[1], d[2], d[3],d[4]));
			 break;
		 }
		 repaint();
	 }
 
	 public void setMode(int m) {
		 mode = m;
	 }
 
	 public void mouseClicked(MouseEvent e) { }
	 public void mouseEntered(MouseEvent e) { }

	 public void mouseExited(MouseEvent e) { }
	 public void mouseReleased(MouseEvent e) { }
 
	 public void mousePressed(MouseEvent e) {
		 int x = e.getX();
		 int y = e.getY();
		 String msg;
		 switch(mode) {
		 default:
			 return;
		 case LINE:
			 tempX = x;
			 tempY = y;
			 break;
		 case CIRCLE:
			 msg=CIRCLE +":" +x + ":" + y + ":" + "10" + ":" + "10";
			 send(msg);
			 break;
		 case RECT:
			 msg=RECT +":" + x +":" + y + ":" + "10" + ":" + "10";
			 send(msg);
			 break;
		 }
	 }
 
	 public void mouseMoved(MouseEvent e) { }
	 public void mouseDragged(MouseEvent e) {
		 int x = e.getX();
		 int y = e.getY();
		 String msg;
		 switch(mode) {
		 case NONE:
			 return;
		 case LINE:
			 msg=LINE +":"+tempX + ":" + tempY + ":" + x + ":"+ y;
			 send(msg);
			 tempX = x;
			 tempY = y;
			 break;
		 default:
			 return;

		 }
	 }

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		// TODO Auto-generated method stub
		return 0;
	}
	}