
package chat.application;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
public class Server extends JFrame implements ActionListener{
    
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JTextArea a1;
    
    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    Boolean typing;
    Server(){
        
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0, 0, 400, 66); //400 is size of frame 70 will be height
        //of the green part
        add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chat/application/icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        //image is in i2
        //i2 cant go in JLabel directly
        //convert i2 to imageIcon
        ImageIcon i3= new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);  
         l1.setBounds(5,17,30,30);
         p1.add(l1);
         
         l1.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent ae){
            System.exit(0);
        }
    });
         
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chat/application/icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(60,60,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);      
         l2.setBounds(40,5,60,60);
         p1.add(l2);
         
         
         
         ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chat/application/icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l5 = new JLabel(i9);      
         l5.setBounds(280,20,30,30);
         p1.add(l5);
         
         ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("chat/application/icons/phone.png"));
        Image i12 = i11.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i13 = new ImageIcon(i12);
        JLabel l6 = new JLabel(i13);      
         l6.setBounds(320,20,35,30);
         p1.add(l6);
         
         ImageIcon i14 = new ImageIcon(ClassLoader.getSystemResource("chat/application/icons/3icon.png"));
        Image i15 = i14.getImage().getScaledInstance(15,20,Image.SCALE_DEFAULT);
        ImageIcon i16 = new ImageIcon(i15);
        JLabel l7 = new JLabel(i16);      
         l7.setBounds(360,20,13,23);
         p1.add(l7);
         
         
         
        JLabel l3 = new JLabel("Gaitonde");
        l3.setBounds(110,15,100,18);
        l3.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        l3.setForeground(Color.WHITE);
        p1.add(l3);
        
        JLabel l4 = new JLabel("Active Now");
        l4.setBounds(110,35,100,20);
        l4.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
        l4.setForeground(Color.WHITE);
        p1.add(l4);
          
        Timer t = new Timer(1,new ActionListener(){
          public void actionPerformed(ActionEvent ae){
              if(!typing){
                 l4.setText("Active Now");
                
              }
          } 
        });
        
        t.setInitialDelay(2000); //milisecond
        a1=new JTextArea();
        a1.setBounds(5,69,390,470);
        a1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        a1.setBackground(Color.WHITE);
        a1.setEditable(false); //so you cant edit the texts sent
        a1.setLineWrap(true);
        a1.setWrapStyleWord(true);
        add(a1);
        
        
        b1 = new JButton("Send");
        b1.setBounds(320,555,70,40);
        b1.setBackground(new Color(7,94,84));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        add(b1);
        
        t1= new JTextField();
        t1.setBounds(5,555,310,40);
        t1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
         add(t1); 
         
         t1.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                l4.setText("typing..");
                
                t.stop();
                
                typing=true;
            }
            
            public void keyReleased(KeyEvent ke){
                typing=false;
                
                if(!t.isRunning()){
                    t.start();
                }
            }
         });
        setLayout(null);
        setSize(400,600);
        setLocation(200,0); 
        
        setUndecorated(true);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
       try{
        String out = t1.getText(); //get whats written in textfield
       a1.setText(a1.getText()+"\n\t\t\t"+out); //we use this to get the
       //contnent written in the a1 field, and append the out text in the
       //next line
       dout.writeUTF(out);
       t1.setText(""); //after typing & sending textfield will be empty
       }
       catch(Exception e){}
    }
    public static void main(String[] args){
        new Server().setVisible(true);
        
        String msginput = "";
        try{
            skt = new ServerSocket(6003);
            s = skt.accept();
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            
            msginput = din.readUTF();
            a1.setText(a1.getText()+"\n"+msginput);
            
            skt.close();
            s.close();
        }catch(Exception e){
            
        }
    }
}
