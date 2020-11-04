package WhiteBoard;

import javax.swing.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import chat.ChatHandler;
import chat.PrintDebugMessage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class WhiteboardClient extends JFrame implements Runnable, ActionListener, Printable {
	public static final int NONE = 0;
	 public static final int LINE = 1;
	 public static final int CIRCLE = 2;
	 public static final int RECT = 3;
	private WhiteboardCanvas canvas;
	private JButton line, oval, clear, rect, save;
	private Thread listener;
	private String h;
	private BufferedReader i;
	private PrintWriter o;
	private Box verticalBox;
	private JTextArea output;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JLabel label;
	private JTextField input;
	private Vector<Drawable> pictures;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem open, print;
	
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		 if (page > 0) {
		  return NO_SUCH_PAGE;
		  }
		 
		  Graphics2D g2d = (Graphics2D)g;
		  g2d.translate(pf.getImageableX(), pf.getImageableY());
		  
		  for(int i = 0; i < canvas.pictures.size(); i++) {
			  String data = canvas.pictures.elementAt(i).toString();
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
				g.drawLine(d[1], d[2], d[3],d[4]);
				 break;
			 case CIRCLE:
				 g.drawOval(d[1], d[2], d[3],d[4]);
				 break;
			 case RECT:
				 g.drawRect(d[1], d[2], d[3],d[4]);
				 break;
			 }
		  }
			 
			 return PAGE_EXISTS;
		 }

	public WhiteboardClient(String host, int port) {
		super("화이트보드 클라이언트");
		h = host;
		listener = new Thread(this);
		listener.start();
		
		JToolBar tools = new JToolBar();
		line = new JButton(" 선 ");
		line.addActionListener(this);
		oval = new JButton(" 원 ");
		oval.addActionListener(this);
		rect = new JButton("사각형");
		rect.addActionListener(this);
		clear = new JButton("지우기");
		clear.addActionListener(this);
		save = new JButton("저장");
		save.addActionListener(this);
		tools.add(line);
		tools.add(oval);
		tools.add(rect);
		tools.addSeparator();
		tools.add(clear);
		tools.add(save);

		canvas = new WhiteboardCanvas (host, port);

		getContentPane().add ("North", tools);
		
		menuBar = new JMenuBar();
		tools.add(menuBar);
		
		mnNewMenu = new JMenu("\uD30C\uC77C");
		menuBar.add(mnNewMenu);
		
		open = new JMenuItem("\uC5F4\uAE30");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.open();
			}
		});
		mnNewMenu.add(open);
		
		
		print = new JMenuItem("\uCD9C\uB825");
		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 PrinterJob job = PrinterJob.getPrinterJob();
				 job.setPrintable(WhiteboardClient.this);
				 boolean ok = job.printDialog();
				 if (ok) {
					 try {
						 	job.print();
					 } catch (PrinterException ex) {
						 /* The job did not successfully complete */
					 	}
				 }
			}
		});
		mnNewMenu.add(print);
		
		getContentPane().add ("Center", canvas);
		canvas.setLayout(new BoxLayout(canvas, BoxLayout.X_AXIS));
		
		verticalBox = Box.createVerticalBox();
		getContentPane().add(verticalBox, BorderLayout.WEST);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		verticalBox.add(scrollPane);
		
		output = new JTextArea();
		output.setEditable(false);
		scrollPane.setViewportView(output);
		
		panel = new JPanel();
		panel.setMaximumSize(new Dimension(500, 500));
		verticalBox.add(panel);
		
		label = new JLabel("사용자 이름");
		panel.add(label);
		
		input = new JTextField();
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object c = e.getSource();
				
				if(c == input) {
					label.setText("메시지");
					o.println(input.getText());
					input.setText("");
				}
			}
		});
		panel.add(input);
		input.setColumns(10);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 400);
		setVisible(true);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("resource")
			Socket s = new Socket(h, 9830);
			 o = new PrintWriter(s.getOutputStream(), true);
			 i = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(true) {
				String line = i.readLine();
				output.append(line+"\n");
			}
		} catch(IOException ex) {
			PrintDebugMessage.print(ex);
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object c = e.getSource();
		
		if(c == line) {
			canvas.setMode(WhiteboardCanvas.LINE);
		} else if(c == oval) {
			canvas.setMode(WhiteboardCanvas.CIRCLE);
		} else if(c == rect) {
			canvas.setMode(WhiteboardCanvas.RECT);
		} else if(c == clear) {
			canvas.send("!x");
			canvas.clear();
		} else if(c == save) {
			canvas.save();
	    }
	}

	
	public static void main(String args[]) {
			new WhiteboardClient("localhost", 9850);
	}
 }