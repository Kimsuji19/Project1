package chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import WhiteBoard.WhiteboardCanvas;

import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.Canvas;
import java.awt.Dimension;

public class ChatClient extends JFrame implements Runnable, ActionListener {
	private BufferedReader i;
	private PrintWriter o;
	private JTextArea output;
	private JTextField input;
	private JLabel label;
	private Thread listener;
	private String host;
	private JPanel panel;
	private JButton line, oval, clear, rect;
	private WhiteboardCanvas canvas;
	private int port = 9850;
	
	public ChatClient (String server) {
		super("채팅 프로그램");
		host = server;
		listener = new Thread(this);
		listener.start();
		
		output = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(output);
		scrollPane.setPreferredSize(new Dimension(6, 10));
		scrollPane.setMinimumSize(new Dimension(10, 10));
		scrollPane.setMaximumSize(new Dimension(10000, 10000));
		getContentPane().add(scrollPane, "Center");
		output.setEditable(false);
		
		panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);
		
		
		JToolBar tools = new JToolBar();
		line = new JButton(" 선 ");
		line.addActionListener(this);
		oval = new JButton(" 원 ");
		oval.addActionListener(this);
		rect = new JButton("사각형");
		rect.addActionListener(this);
		clear = new JButton("지우기");
		clear.addActionListener(this);
		tools.add(line);
		tools.add(oval);
		tools.add(rect);
		tools.addSeparator();
		tools.add(clear);
		
		canvas = new WhiteboardCanvas (host, port);
		
		Panel bottom = new Panel(new BorderLayout());
		label = new JLabel("사용자 이름");
		bottom.add(label, "West");
		input = new JTextField();
		input.addActionListener(this);
		bottom.add(input, "Center");
		
		getContentPane().add(bottom, "South");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}
	
	public void run() {
		try {
			@SuppressWarnings("resource")
			Socket s = new Socket(host, 9830);
			InputStream ins = s.getInputStream();
			OutputStream os = s.getOutputStream();
			i = new BufferedReader(new InputStreamReader(ins));
			o = new PrintWriter(new OutputStreamWriter(os), true);
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
		if(c == input) {
			label.setText("메시지");
			o.println(input.getText());
			input.setText("");
		}
	}
	
	public static void main(String[] args) {
		if(args.length > 0) {
			new ChatClient(args[0]);
		} else {
			new ChatClient("localhost");
		}
	}
}
	
	