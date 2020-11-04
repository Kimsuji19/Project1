package hello;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.View;
import java.awt.Dimension;

@SuppressWarnings({ "unused", "serial" })
public class Paint extends JFrame{
   
   private JFrame frame;
   private JPanel contentPane;
   private MyCanvas mycanvas;
  
   
   static int num;
   int x, y, X, Y, mx, my= 0;
   boolean isDragged = false;
   boolean copy = false;
   int offX, offY;
   Shape m,c;



   
   
   public static void main(String[] args) {  // 메인함수
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               Paint frame = new Paint();
               frame.frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }
   
   public Paint() {
      frame = new JFrame();  // JFrame 생성
      frame.setTitle("그림판"); 
      frame.setBounds(100, 100, 400, 400);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      

      JMenuBar menuBar = new JMenuBar();
      frame.setJMenuBar(menuBar);
      
      JPanel panel = new JPanel();
      panel.setBorder(new LineBorder(new Color(0, 0, 0)));
      frame.getContentPane().add(panel, BorderLayout.WEST);
      
      Box verticalBox = Box.createVerticalBox();
      panel.add(verticalBox);
       
      JButton btnNewButton = new JButton("");
      btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            System.out.println("타원");
            num = 1;
            }
      });
      
      JButton btnNewButton_3 = new JButton("Line");
      btnNewButton_3.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		System.out.println("선");
      		num = 0;
      	}
      });
      btnNewButton_3.setMinimumSize(new Dimension(57, 50));
      btnNewButton_3.setMaximumSize(new Dimension(100, 50));
      btnNewButton_3.setBorderPainted(false);
      btnNewButton_3.setBorder(null);
      verticalBox.add(btnNewButton_3);
      btnNewButton.setBorderPainted(false);
      btnNewButton.setIcon(new ImageIcon("C:\\Users\\admin\\Desktop\\\uC6D02.png"));
      btnNewButton.setAlignmentY(Component.TOP_ALIGNMENT);
      btnNewButton.setContentAreaFilled(false);
      btnNewButton.setVerticalAlignment(SwingConstants.TOP);
      verticalBox.add(btnNewButton);
      
      JButton btnNewButton_1 = new JButton("");
      btnNewButton_1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            System.out.println("사각");
               num = 2;
            }
      });
      btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\admin\\Desktop\\\uC0AC\uAC01\uD615.PNG"));
      btnNewButton_1.setBorderPainted(false);
      btnNewButton_1.setContentAreaFilled(false);
      verticalBox.add(btnNewButton_1);
      
      JButton btnNewButton_2 = new JButton("");
      btnNewButton_2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            num = 3;
            }
         });
      btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\admin\\Desktop\\\uD654\uC0B4\uD45C.PNG"));
      btnNewButton_2.setBorderPainted(false);
      btnNewButton_2.setAlignmentY(Component.BOTTOM_ALIGNMENT);
      btnNewButton_2.setContentAreaFilled(false);
      btnNewButton_2.setVerticalAlignment(SwingConstants.BOTTOM);
      verticalBox.add(btnNewButton_2);
      
   
      mycanvas = new MyCanvas();
      mycanvas.setBackground(Color.WHITE);
      mycanvas.addMouseMotionListener(new MouseMotionAdapter() {
         
         
		@Override
         public void mouseDragged(MouseEvent e) {
        	 Graphics g = mycanvas.getGraphics();
        	 
        	 if(num == 0) {
        		 g.drawLine(x, y, X, Y);
        		 
        	 } else if(num == 1) {
            	g.setXORMode(Color.BLACK);
               if(x <= e.getX()) {
            	   if(y <= e.getY()) {
            		   g.drawOval(x, y, e.getX() - x, e.getY() - y); //4
            	   } else {
            		   g.drawOval(e.getX(), e.getY(), e.getX() - x, y - e.getY());  //1
            	   }
               }else {
            	   if(y <= e.getY()) {
            		   g.drawOval(e.getX(), e.getY(), x - e.getX(), e.getY() - y);  //3
            	   } else {
            		   g.drawOval(e.getX(), e.getY(), x - e.getX(), y - e.getY());  //2
            	   }
                  }
             
            } else if(num == 2) {
            	
            	
            	g.setXORMode(Color.BLACK);
            	if(x <= e.getX()) {
             	   if(y <= e.getY()) {
             		   g.drawRect(x, y, e.getX() - x, e.getY() - y);
             	   } else {
             		   g.drawRect(e.getX(), e.getY(), e.getX() - x, y - e.getY());
             	   }
                }else {
             	   if(y <= e.getY()) {
             		   g.drawRect(e.getX(), e.getY(), x - e.getX(), e.getY() - y);
             	   } else {
             		   g.drawRect(e.getX(), e.getY(), x - e.getX(), y - e.getY());
             	   }
                   }
            	 g.setPaintMode();
            	 
            } else if(num == 3) {
            	if(isDragged) {
            		  x=e.getX()-mx;
                      y=e.getY()-my;
                      
                      m.moveTo(x, y);
                      repaint();
            	}
            }
         }
      });

      
      mycanvas.addMouseListener(new MouseAdapter() {
         
	
		public void mousePressed(MouseEvent e) {
			x = X = e.getX();
		    y = Y = e.getY();
		    
		    if(num == 6) {
		    	Graphics g = mycanvas.getGraphics();
		    	
		    	x = e.getX();
		    	y = e.getY();
		    	repaint();
		    }
         
            if(num == 3) {
            	m = mycanvas.position(x, y);
            	if(m !=null) {
                    mx = e.getX()-m.x;
                    my = e.getY()-m.y;
            		isDragged = true; 
            	}      	
            } else if(num == 4) {
            		c = mycanvas.position(x, y);
            		copy = true;
            		System.out.println("copy");
            	
       
            } else if(num == 5) {
            	if(copy) {
            		 if(c.select==1) {
                         mycanvas.add(new Circle(e.getX(), e.getY(), c.X - c.x, c.Y - c.y));
                      }
                      else if(c.select==2) {
                         mycanvas.add(new Rect(e.getX(), e.getY(), c.X - c.x, c.Y - c.y));
                      }
            	}
            }
		}
      
         public void mouseReleased(MouseEvent e) {
        	 X = e.getX();
             Y = e.getY();
             
        	 if(num == 0) {
        		 mycanvas.add(new Line(x, y, X, Y));
             }
                            
            if(num == 1) {
            	if(x <= X) {
            		if(y <= Y) {
                		mycanvas.add(new Circle(x, y, X - x, Y - y));
        			} else {
        				mycanvas.add(new Circle(X, Y, X - x, y - Y));
        			}
            	} else {
    			if(y <= Y) {
    				mycanvas.add(new Circle(X, Y, x - X, Y - y));
    			} else {
    				mycanvas.add(new Circle(X, Y, x - X, y - Y));
    				}
            	}
            } else if(num == 2) {
            	if(x <= X) {
            		if(y <= Y) {
            			mycanvas.add(new Rect(x, y, X - x, Y - y));
        			} else {
        				mycanvas.add(new Rect(X, Y, X - x, y - Y));
        			}
            	} else {
    			if(y <= Y) {
    				mycanvas.add(new Rect(X, Y, x - X, Y - y));
    			} else {
    				mycanvas.add(new Rect(X, Y, x - X, y - Y));
    				}
            	}  
            } else if(num == 3) {
            	isDragged = false;
            }
         }
      
      });
      frame.getContentPane().add(mycanvas, BorderLayout.CENTER);
      
      Canvas canvas_1 = new Canvas();
      mycanvas.add(canvas_1);
      
      ////////////////////////////////////////////////////////////////////////////////////////////////
      JMenu NewMenu_1 = new JMenu("File");  //"파일 메뉴바 추가"
      menuBar.add(NewMenu_1);   // 파일 메뉴바 적용
      
      
      JMenuItem mntmExit = new JMenuItem("Exit");   // 끝내기 메뉴 추가
      mntmExit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });
      
      JMenuItem mntmNewMenuItem_4 = new JMenuItem("Save");
      mntmNewMenuItem_4.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      	  try { 
      		String resultStr;
			resultStr = JOptionPane.showInputDialog(null,
					"저장할 파일의 이름을 지정하세요.\r\n", "저장",
					JOptionPane.INFORMATION_MESSAGE);
			String a = resultStr;
			FileOutputStream f = new FileOutputStream(a); 
			ObjectOutputStream of = new ObjectOutputStream(f); 
			Color c = mycanvas.getBackground();
			of.writeObject(c);
			for(int i = 0 ;i < mycanvas.s.size(); i++)
			{
				Shape s = mycanvas.s.get(i);
				of.writeObject(s);
			}
			of.close();
		} 
		catch(IOException e1)
	{ //파일 오류에 관한 예외처리
			System.out.println(e1.getMessage());
	}   
      	}
      });
      
      JMenuItem mntmNewMenuItem_5 = new JMenuItem("Load");
      mntmNewMenuItem_5.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		for(int q = 0 ; q < mycanvas.s.size();)
      		{
      			mycanvas.s.remove(q);
      		}//이전에있는 객체들 모두 초기화 ( 그림판 화면 초기화 시키는 부분 )
    		Graphics g = (Graphics) mycanvas.getGraphics();
      		int i = 0;
			JFileChooser jfc = new JFileChooser("C:\\Users\\admin\\eclipse-workspace\\2020");
			jfc.showDialog(null, "확인");
			File file = jfc.getSelectedFile(); //파일 선택창 선언
			try  
			{
				FileInputStream f = new FileInputStream(file);					
				ObjectInputStream of = new ObjectInputStream(f);
				
				Color c = (Color) of.readObject(); //읽은 파일을 ArrayList 타입으로 casting
				mycanvas.setBackground(c);
				Shape li = (Shape) of.readObject();
				while(li != null)
				{
					mycanvas.s.add(i, li);
					li = (Shape) of.readObject();
				}
				of.close();
			} 
	        catch(IOException e1)
	        {
	            for(int a = 0; a<mycanvas.s.size(); a++)
	            {
	            	mycanvas.paint(g); //읽어온 데이터들을 그림판에 그려주는 부분
	            }
	        	return;
	        } catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
      	}
      });
      
      JMenuItem mntmNewMenuItem_6 = new JMenuItem("New");
      mntmNewMenuItem_6.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		 mycanvas.clearRect(0, 0, WIDTH, HEIGHT);
      	}
      });
      NewMenu_1.add(mntmNewMenuItem_6);
      NewMenu_1.add(mntmNewMenuItem_5);
      NewMenu_1.add(mntmNewMenuItem_4);
      NewMenu_1.add(mntmExit);
      
      JMenu mnNewMenu = new JMenu("Edit");
      menuBar.add(mnNewMenu);
      
      JMenuItem mntmNewMenuItem_1 = new JMenuItem("Copy");
      mntmNewMenuItem_1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            num = 4;
         }
      });
      mnNewMenu.add(mntmNewMenuItem_1);
      
      JMenuItem mntmNewMenuItem_2 = new JMenuItem("Paste");
      mntmNewMenuItem_2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            num = 5;
         }
      });
      mnNewMenu.add(mntmNewMenuItem_2);
   
      
      JMenu mnNewMenu_1 = new JMenu("Help");
      menuBar.add(mnNewMenu_1);
      
      JMenuItem mntmNewMenuItem_3 = new JMenuItem("Information");
      mntmNewMenuItem_3.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		JOptionPane info =new JOptionPane();
      		JOptionPane.showMessageDialog(null, "제작자:193606 김수지");
      	}
      });
      mnNewMenu_1.add(mntmNewMenuItem_3);
   }


   public MyCanvas getMycanvas() {
      return mycanvas;
   }
}

