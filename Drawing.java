/**
 * This program draws a business card using graphics in Java
 *
 * @ Alex Porter
 * Business Card Project
 * @Version Sept 2017
 */

import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
public class Drawing extends JPanel
{
public static void main(String[] a) {
 //Sets up and displays the panel on screen
 JFrame f = new JFrame();
 f.setContentPane(new Drawing());
 f.setSize(600, 400);
 f.setVisible(true);
 }
public void paintComponent(Graphics g)
   {
    
    // this statement is required
    super.paintComponent(g);
    
    /*Changing these variables shift the logo and picture around
     * logoVarx and logoVary shift the logo horizontally and vertically respectfully
     * pictureVarx and pictureVary shift the picture horizontally and vertically respectfully
     * textVarx and textVary do the same thing to the contact information
     * headingVarx and headingVary do the same thing to the heading
     * Use negative numbers to shift the image/logo/text/heading up (-y) or to the left(-x).
     */
    int logoVarx = 0;
    int logoVary = 0;
    int pictureVarx = 0;
    int pictureVary = 0;
    int textVarx = 0;
    int textVary = 0;
    int headingVarx = 0;
    int headingVary = 0;
    
    //create the black backround for the card at the correct size with variables
    int horizontalBorder = 500;
    int verticalBorder = 300;
    
    g.setColor(Color.BLACK);
    g.drawRect(10,10,horizontalBorder,verticalBorder);
    g.fillRect(10,10,horizontalBorder,verticalBorder);

    // display my fake contact information
    g.setColor(Color.WHITE);
    g.drawString("Alex Porter", textVarx + 30, textVary + 250);
    g.drawString("Astronaut Advisor", textVarx + 30, textVary + 265);
    g.drawString("portera@mail.nasa.gov", textVarx + 30, textVary + 280);
    g.drawString("(845)-635-5300", textVarx + 30, textVary + 295);

    //Create some stars to fill the backround and make it look like space
    g.setColor(Color.WHITE);
    g.fillOval(260, 10, 5, 5);
    g.fillOval(250, 60, 5, 5);
    g.fillOval(278, 13, 5, 5);
    g.fillOval(413, 60, 5, 5);
    g.fillOval(453, 205, 5, 5);
    g.fillOval(401, 162, 5, 5);
    g.fillOval(375, 173, 5, 5);
    g.fillOval(394, 181, 5, 5);
    g.fillOval(387, 122, 5, 5);
    g.fillOval(362, 138, 5, 5);
    g.fillOval(216, 170, 5, 5);
    g.fillOval(389, 114, 5, 5);
    g.fillOval(323, 64, 5, 5);
    g.fillOval(219, 84, 5, 5);
    g.fillOval(310, 112, 5, 5);
    g.fillOval(210, 36, 5, 5);
    g.fillOval(400, 280, 5, 5);
    g.fillOval(452, 295, 5, 5);
    g.fillOval(380, 270, 5, 5);
    g.fillOval(300, 270, 5, 5);
    g.fillOval(275, 261, 5, 5);
    g.fillOval(250, 281, 5, 5);
    g.fillOval(480, 70, 5, 5);
    g.fillOval(479, 160, 5, 5);

    //Draws the orbital of Earth
    g.setColor(Color.WHITE);
    g.drawOval(logoVarx + 310, logoVary + 130, 160, 130);
    
    //Draws our own little marble
    g.setColor(Color.BLUE);
    g.fillOval(logoVarx + 435, logoVary + 170, 50, 50);
    
    //Draws the planet Venus
    g.setColor(Color.ORANGE);
    g.fillOval(logoVarx + 410, logoVary + 170, 50, 50);
    
    //Draws the planet Mercury
    g.setColor(Color.GRAY);
    g.fillOval(logoVarx + 395, logoVary + 175, 40, 40);
    
    //Draws the sun
    g.setColor(Color.YELLOW);
    g.fillOval(logoVarx + 320, logoVary + 145, 100, 100);
    
    //Draws a red arrow moving through the planets and out of the sun into space
    g.setColor(Color.RED);
    g.drawLine(logoVarx + 280, logoVary + 195, logoVarx + 480, logoVary + 195);
    g.drawLine(logoVarx + 280, logoVary + 195, logoVarx + 310, logoVary + 170);
    g.drawLine(logoVarx + 280, logoVary + 195, logoVarx + 310, logoVary + 220);
    
    //Adds a catchy heading over the rest of the card in a new italic font
    Font myFont = new Font("serif", Font.ITALIC, 20);
    g.setFont(myFont);
    g.drawString("From the classroom to the stars...", headingVarx + 130, headingVary + 30);
 
    BufferedImage photo = null;
    try {
        File file = new File("MyFace2.jpg");
        photo = ImageIO.read(file);
    } catch (IOException e){
        g.drawString("Problem reading the file", 100, 100);
    }
    g.drawImage(photo, pictureVarx + 20,pictureVary + 50, 150, 180, null);


    }
}