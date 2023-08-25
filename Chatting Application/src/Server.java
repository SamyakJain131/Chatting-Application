package chatting.application;
import javax.swing.*;        //javax == java extended package
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener{
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    
    Server()     //constructor
    {
        f.setLayout(null);                        //default layout null
        
        //Panel
        JPanel p1= new JPanel();                      //create panel p1
        p1.setBackground(new Color(7,94,84));  //set background color
        p1.setBounds(0, 0, 450, 70); //set the panel manually because setLayout is Null
        p1.setLayout(null);
        f.add(p1);                   // 32,44,51 //set the panel onto the frame,add function is used to set any component to the frame
        
        //back image with exit functionality
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png")); //get the image from the system
        Image i2=i1.getImage().getScaledInstance(20,25,Image.SCALE_DEFAULT);  //SCALE THE IMAGE i1 using i2
        ImageIcon i3 = new ImageIcon(i2);              //pass the scaled image i2 to the new object of imageicon
        JLabel back = new JLabel(i3);           // create new Label back and pass the image
        back.setBounds(5, 20, 25, 25); // set label coordinates
        p1.add(back);                             // set the label onto the panel
        
        back.addMouseListener(new MouseAdapter(){       //add functionallity to back button
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });
        
        
        //profile image
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5=i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);
        
        
        //video icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);
        
        
        //phone icon
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11=i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 30, 30);
        p1.add(phone);
        
        
        //morevert
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14=i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(410, 20, 10, 25);
        p1.add(morevert);
        
        
        //profile name
        JLabel name = new JLabel("Samyak");  
        name.setBounds(110, 15, 100, 18);
        name.setForeground(new Color(255,255,255));
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);
        
        //status
        JLabel status = new JLabel("Active Now");  
        status.setBounds(110, 35, 100, 18);
        status.setForeground(new Color(255,255,255));
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(status);
        
        
        //New Panel for Text Area
        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        //a1.setBackground(new Color(11,20,26));
        f.add(a1);
        
        
        //Text Field
        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        text.setBackground(new Color(42,57,66));
        text.setForeground(Color.WHITE);
        f.add(text);
        
        
        //Send Button
        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.addActionListener(this);                //action on click of send button
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        send.setBackground(new Color(7,94,84));
        send.setForeground(new Color(255,255,255));
        f.add(send);
        
        
        
        f.setSize(450,700);               //set the size of frame
        f.setLocation(200,50);                   //loction of frame when it opens
        f.setUndecorated(true);
        f.getContentPane().setBackground(new Color(17,27,33)); //set the background color of whole frame
        
        
        
        f.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        try{
        String out = text.getText();
        
        JPanel p2 = formatLabel(out);
        
        a1.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        
        a1.add(vertical,BorderLayout.PAGE_START);
        dout.writeUTF(out);           //SEND msg
        
        text.setText("");
        f.repaint();
        f.invalidate();
        f.validate();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width:110px\">"+ out+ "</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(32,44,51));
        output.setForeground(new Color(255,255,255));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        
        return panel;
    }
    public static void main(String[] args)
    {
       new Server();
       
       try{
            ServerSocket skt = new ServerSocket(6001);   //create a server
           while(true)                                      //infinte loop
           {
               Socket s = skt.accept();                     //accept msg
               DataInputStream din = new DataInputStream(s.getInputStream()); //recieve msg using DataInputStream
               dout = new DataOutputStream(s.getOutputStream());  //send msg
               
               while(true)
               {
                   String msg = din.readUTF();      //READ THE MESSAGE
                   JPanel panel = formatLabel(msg);  //display the msg send by client
                   
                   JPanel left = new JPanel(new BorderLayout());          //add the msg to the left of the frame
                   left.add(panel,BorderLayout.LINE_START);
                   vertical.add(left);
                   
                   f.validate();
                }
           }
       }catch(Exception e){
           e.printStackTrace();
       }
    }
}