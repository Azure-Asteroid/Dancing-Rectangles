/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.sound.midi.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author doctordoom
 */
public class MusicVideo {
    
    static JFrame f = new JFrame("Dance of the Rectangle!");
    static MyDrawPanel m1;
        
    public static void main(String[] args){
        MusicVideo mini = new MusicVideo();
        mini.go();
    }
    
    public void setUpGui(){
        m1 = new MyDrawPanel();
        f.setContentPane(m1);
        f.setBounds(30,30,300,300);
        f.setVisible(true);
    }

    
    public void go() {
        // first set up the GUI
        setUpGui();
        
        try{
            Sequencer sqncr = MidiSystem.getSequencer();
            sqncr.open();
            sqncr.addControllerEventListener(m1, new int[] {127});
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();
            
            int r = 0;
            
            for(int i = 0; i < 60; i+= 4){
                
               r = (int) ((Math.random() * 50) +1);
               track.add(makeEvent(144,1,r,100,i));
               track.add(makeEvent(176,1,127,0,i));
               track.add(makeEvent(128,1,r,100,i+2));                
            } // Kill for loop
            
            sqncr.setSequence(seq);
            sqncr.start();
            sqncr.setTempoInBPM(120);
            
        } /*Ends the try body*/ catch(Exception ex){
            ex.printStackTrace();
        }
    } // Main method done!
    
    public void controlChange(ShortMessage event){
        System.out.println("la");
    }
    
    /* Now we create the makeEvent method to easily create the 
     * midid event.
     *@return returns the midi event after we add the message 
    */
    public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick){
        MidiEvent event = null;
        try{
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
            
        }/*Ends the try body*/catch(Exception e){e.printStackTrace();}
        return event;
    }
    
    /*
     *
    */
    public class MyDrawPanel extends JPanel implements ControllerEventListener{
        boolean msg = false; 
        
        public void controlChange(ShortMessage event){
            msg = true;
            repaint();
        }
        
        public void paintComponent(Graphics g){
            if(msg){
                Graphics2D g2 = (Graphics2D) g;
                
                int r = (int) (Math.random() * 250);
                int gr = (int) (Math.random() * 250);
                int b = (int) (Math.random() * 250);
                
                g.setColor(new Color(r,gr,b));
                
                int ht = (int) ((Math.random() *120)+10);
                int width = (int) ((Math.random() *120)+10);
                int x = (int) ((Math.random() *40)+10);
                int y = (int) ((Math.random() *40)+10);
                g.fillRect(x, y, ht, width);
                msg = false;
            }
        }
    }
}
