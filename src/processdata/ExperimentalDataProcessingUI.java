/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processdata;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.spi.ServiceRegistry;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


/**
 *
 * @author Wild
 */
public class ExperimentalDataProcessingUI extends javax.swing.JFrame {
    static int idx =0;
    static String folderName = "";
    static String initialImagePath="D://SHOEPRINTS_2//";
    String depthFrameFileName = "";
    String  videoImageFileName = "";
    static int frameIndex =0;
    static int datasetIndex;
    static int currentFrameIndex=0;
    
     Mat rgbFrame = new Mat();
     Mat depthFrame = new Mat();
     Mat depthBackgroundFrame = new Mat();
     Mat rgbBackgroundFrame = new Mat();
    
     ArrayList<Mat> finalImages = new ArrayList<Mat>();
    //temporary place depth data thresholds
    //TODO:
    //replace with slider
    static int MIN_DEPTH_THRESHOLD = 515;//515;
    static int MAX_DEPTH_THRESHOLD = 540;//535;
    
//    int num=47130;
//    String fileLeft="";//initialImagePath+"undistLeft"+num+".jpg";
//    String fileRight=initialImagePath+"undistRight"+num+".jpg";
//    Mat imgR = new Mat();
//    Mat imgL = new Mat();
    
    
    /**
     * Creates new form TestOpenCVUI
     */
    public ExperimentalDataProcessingUI() {
        initComponents();
        
    }

    public static void displayImage(Image img2, JLabel lbl)
	{   
		//source: http://answers.opencv.org/question/31505/how-load-and-display-images-with-java-using-opencv/
	    ImageIcon icon=new ImageIcon(img2);
	    lbl.setIcon(icon);
	}
  
	
	/**
	 * to display images overloaded function to take BufferedImage as argument
	 * modified from initial source http://answers.opencv.org/question/31505/how-load-and-display-images-with-java-using-opencv/
	 * @param img2 -- image to display
	 * @param str -- description of image will appear as title of image frame
	 */
	public static void displayImage(BufferedImage img2, String str)
	{   
		MarvinImagePanel	imagePanel;
		imagePanel = new MarvinImagePanel();
		
	    JFrame frame=new JFrame();
	    //frame.setLayout(new FlowLayout());        
	    frame.setSize(img2.getWidth()+10, img2.getHeight()+50);     
	    //Container l_c = new Container(); 
        //l_c.setLayout(new BorderLayout()); 
        //l_c.add(imagePanel, BorderLayout.NORTH);
	    frame.add(imagePanel, BorderLayout.CENTER);
	    frame.setVisible(true);
	    frame.setTitle(str);
	    imagePanel.setImage(new MarvinImage(img2));
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    

	}
	

	
	public static Mat BufferedImage2Mat(BufferedImage image){
		//source: http://stackoverflow.com/questions/18581633/fill-in-and-detect-contour-rectangles-in-java-opencv
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(image.getHeight(),image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }
	

	
    public static BufferedImage Mat2BufferedImage(Mat m){
    // source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
    // Fastest code
    // The output can be assigned either to a BufferedImage or to an Image

        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( m.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte [] b = new byte[bufferSize];
        m.get(0,0,b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);  
        return image;

    }
	
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jSlider1 = new javax.swing.JSlider();
        jLabel4 = new javax.swing.JLabel();
        textField1 = new java.awt.TextField();
        jLabel5 = new javax.swing.JLabel();
        textField2 = new java.awt.TextField();
        jButtonProcessVideo = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButtonNext = new javax.swing.JButton();
        initialFrameIndexBox = new java.awt.TextField();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        depthFramesPanel = new javax.swing.JLabel();
        depthBckgFramesPanel = new javax.swing.JLabel();
        depthBckgSubtractedFrames = new javax.swing.JLabel();
        depthCleanedFramesPanel = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        videoFramesPanel = new javax.swing.JLabel();
        videoBckgFramesPanel = new javax.swing.JLabel();
        videoBckgSubtractedFrames = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        frameNumberIndicator = new javax.swing.JLabel();
        jButtonBackground = new javax.swing.JButton();
        jSlider2 = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButtonShowBackground = new javax.swing.JButton();
        jButtonProcessImage = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        extractedContoursPanel = new javax.swing.JLabel();
        maskedContoursPanel = new javax.swing.JLabel();
        videoMaskedPanel = new javax.swing.JLabel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        extractedShoePanel1 = new javax.swing.JLabel();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        extractedShoePanel2 = new javax.swing.JLabel();
        saveToDSjButton1 = new javax.swing.JButton();
        saveForManualProcButton1 = new javax.swing.JButton();
        saveToDSjButton2 = new javax.swing.JButton();
        saveForManualProcButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        jFileChooser1.setDialogTitle("Please specify left and right images");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSlider1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jSlider1.setMajorTickSpacing(50);
        jSlider1.setMaximum(1000);
        jSlider1.setMinimum(450);
        jSlider1.setPaintLabels(true);
        jSlider1.setPaintTicks(true);
        jSlider1.setSnapToTicks(true);
        jSlider1.setValue(MAX_DEPTH_THRESHOLD);
        jSlider1.setName("Depth Max threshold chooser");
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jLabel4.setText("Enter dataset  index:");

        textField1.setText("95");
        textField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Select the dataset label:");

        textField2.setText("1_RGB_LIGHT_95");
        textField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField2ActionPerformed(evt);
            }
        });

        jButtonProcessVideo.setText("Process Video");
        jButtonProcessVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProcessVideoActionPerformed(evt);
            }
        });

        jLabel6.setText("Initial frame index:");

        jButtonNext.setText("Next");
        jButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextActionPerformed(evt);
            }
        });

        initialFrameIndexBox.setText("0");
        initialFrameIndexBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initialFrameIndexBoxActionPerformed(evt);
            }
        });

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTabbedPane2.setToolTipText("");

        depthFramesPanel.setName(""); // NOI18N
        jTabbedPane2.addTab("Depth Data", depthFramesPanel);

        depthBckgFramesPanel.setName(""); // NOI18N
        jTabbedPane2.addTab("Depth Backg", depthBckgFramesPanel);

        depthBckgSubtractedFrames.setName(""); // NOI18N
        jTabbedPane2.addTab("Backg Subtracted", depthBckgSubtractedFrames);

        depthCleanedFramesPanel.setName(""); // NOI18N
        jTabbedPane2.addTab("Depth Image Cleaned", depthCleanedFramesPanel);

        jTabbedPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTabbedPane3.setToolTipText("");

        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        jTabbedPane3.addTab("Video Frames", videoFramesPanel);

        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        jTabbedPane3.addTab("Video Background", videoBckgFramesPanel);

        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        jTabbedPane3.addTab("Background Subtracted", videoBckgSubtractedFrames);

        jLabel1.setText("Current Frame # ");

        frameNumberIndicator.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        frameNumberIndicator.setForeground(new java.awt.Color(255, 0, 0));
        frameNumberIndicator.setText("0");

        jButtonBackground.setText("Save Background");
        jButtonBackground.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackgroundActionPerformed(evt);
            }
        });

        jSlider2.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jSlider2.setMajorTickSpacing(50);
        jSlider2.setMaximum(600);
        jSlider2.setMinimum(50);
        jSlider2.setPaintLabels(true);
        jSlider2.setPaintTicks(true);
        jSlider2.setSnapToTicks(true);
        jSlider2.setValue(MIN_DEPTH_THRESHOLD);
        jSlider2.setName(""); // NOI18N
        jSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider2StateChanged(evt);
            }
        });

        jLabel2.setText("Depth Max threshold chooser");

        jLabel3.setText("Depth Min threshold chooser");

        jTextField1.setText("540");

        jTextField2.setText("515");

        jButtonShowBackground.setText("Show Background");
        jButtonShowBackground.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShowBackgroundActionPerformed(evt);
            }
        });

        jButtonProcessImage.setText("Process Image");
        jButtonProcessImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProcessImageActionPerformed(evt);
            }
        });

        jTabbedPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTabbedPane4.setToolTipText("");

        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        jTabbedPane4.addTab("Extracted Contours", extractedContoursPanel);

        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        jTabbedPane4.addTab("Masked Contours", maskedContoursPanel);

        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        jTabbedPane4.addTab("Masked from Video", videoMaskedPanel);

        jTabbedPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTabbedPane5.setToolTipText("");

        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        jTabbedPane5.addTab("First Shoe Extracted", extractedShoePanel1);

        jTabbedPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTabbedPane6.setToolTipText("");

        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        jTabbedPane6.addTab("Second Shoe Extracted", extractedShoePanel2);

        saveToDSjButton1.setText("Save to Dataset");
        saveToDSjButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveToDSjButton1ActionPerformed(evt);
            }
        });

        saveForManualProcButton1.setText("Save for Manual Procesing");
        saveForManualProcButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveForManualProcButton1ActionPerformed(evt);
            }
        });

        saveToDSjButton2.setText("Save to Dataset");
        saveToDSjButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveToDSjButton2ActionPerformed(evt);
            }
        });

        saveForManualProcButton2.setText("Save for Manual Procesing");
        saveForManualProcButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveForManualProcButton2ActionPerformed(evt);
            }
        });

        jMenu2.setText("File");

        jMenuItem1.setText("Open");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(initialFrameIndexBox, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jButtonProcessVideo)
                            .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(27, 27, 27)
                                    .addComponent(jTextField1))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jButtonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel1)
                                    .addGap(18, 18, 18)
                                    .addComponent(frameNumberIndicator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButtonProcessImage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jButtonBackground)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButtonShowBackground))))
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(saveToDSjButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(saveForManualProcButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(saveForManualProcButton2)
                            .addComponent(saveToDSjButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(textField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButtonProcessVideo)
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(initialFrameIndexBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonNext)
                            .addComponent(jLabel1)
                            .addComponent(frameNumberIndicator))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane5)
                            .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTabbedPane6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(saveToDSjButton1)
                            .addComponent(saveToDSjButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(saveForManualProcButton1)
                            .addComponent(saveForManualProcButton2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonBackground)
                            .addComponent(jButtonShowBackground))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonProcessImage)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane2.getAccessibleContext().setAccessibleName("Video Frames");
        jTabbedPane2.getAccessibleContext().setAccessibleDescription("Video Frames");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    /**
     * 
     * @param imgL
     * @param imgR
     * @param disparity 
     */
    public void processOnSliderAction() throws FileNotFoundException{
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        folderName = textField2.getText();
        datasetIndex = Integer.parseInt(textField1.getText());
        int currentFrameIndex = Integer.parseInt(initialFrameIndexBox.getText())-1;
        
        String depthFrameFileName = initialImagePath +datasetIndex+"//"+folderName+"//" + "depthData//"+"outDepthByte_"+currentFrameIndex;

        int maxDepthThreshold = jSlider1.getValue();
        int minDepthThreshold = jSlider2.getValue();
        
        jTextField1.setText(String.valueOf(maxDepthThreshold));
        jTextField2.setText(String.valueOf(minDepthThreshold));
        
        depthFrame = depthDataProcessingUtilities.processDepthDataFile(depthFrameFileName, minDepthThreshold, maxDepthThreshold);

        displayImage(Mat2BufferedImage(videoProcessingUtilities.resizeImage(depthFrame, new Size(448,234))),depthFramesPanel);

        depthFramesPanel.revalidate();
        depthFramesPanel.repaint();
        }
    /**
     * 
     * @return 
     */
    public Mat[] readBackground(){
        folderName = textField2.getText();
        datasetIndex = Integer.parseInt(textField1.getText());
        String backgroundsFolder = ".//backgrounds//"+folderName;
        String rgbBackgroundFileName="";
        String depthBackgroundFileName="";
        
        File[] backgrounds=new File(backgroundsFolder).listFiles();
        for(File currentFile:backgrounds){
            String currentFileName = currentFile.getName();
            System.out.println(currentFileName);
            System.out.println(currentFileName.split("\\_")[0]);
            if(currentFileName.split("\\_")[0].equals("videoBackground")){
                rgbBackgroundFileName=backgroundsFolder+"//"+currentFileName;
                System.out.println(rgbBackgroundFileName);
            }
             if(currentFileName.split("\\_")[0].equals("depthBackground")){
                depthBackgroundFileName=backgroundsFolder+"//"+currentFileName;
                System.out.println(depthBackgroundFileName);
             }   
        }
        
        //String rgbBackgroundFileName=backgroundsFolder+"//"+"videoBackground_"+currentFrameIndex+".jpg";
        //String depthBackgroundFileName=backgroundsFolder+"//"+"depthBackground_"+currentFrameIndex+".jpg";
        
        rgbBackgroundFrame = Highgui.imread(rgbBackgroundFileName, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
        depthBackgroundFrame = ProcessImages.reconstructImage(new File(depthBackgroundFileName),2);
        
        Mat result[] = {rgbBackgroundFrame,depthBackgroundFrame};
        return result;
    }
    
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        int returnVal = jFileChooser1.showOpenDialog(this);
        jFileChooser1.setCurrentDirectory(new java.io.File("D:\\SHOEPRINTS_2\\"));
        jFileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            
            System.out.println("getCurrentDirectory(): " +  jFileChooser1.getCurrentDirectory());
            System.out.println();
            
//            File file = jFileChooser1.getSelectedFile();
//            if(file.getName().contains("Left")){
//                 fileLeft = file.getPath();
//                 imgL  = Highgui.imread(fileLeft, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
//                 displayImage(Mat2BufferedImage(imgL),jLabel1);
//            }
//                
//            if(file.getName().contains("Right")){
//                fileRight = file.getPath();
//                imgR  = Highgui.imread(fileRight, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
//                displayImage(Mat2BufferedImage(imgR),jLabel2);
//            }
                
    } else {
        System.out.println("File access cancelled by user.");
    }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    final Mat disparity = new Mat();
    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        try {
            // TODO add your handling code here:
            processOnSliderAction();
            
            //SwingUtilities.updateComponentTreeUI(this);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExperimentalDataProcessingUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_jSlider1StateChanged

    private void textField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField1ActionPerformed

    private void textField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField2ActionPerformed

    private void jButtonProcessVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProcessVideoActionPerformed
        try {
            // TODO add your handling code here:
            System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
            folderName = textField2.getText();
            idx = Integer.parseInt(textField1.getText());
            String dataFolder = initialImagePath+idx+"//"+folderName+"//";
            processVideo.extractAllFrames(idx,folderName);
        } catch (IOException ex) {
            Logger.getLogger(ExperimentalDataProcessingUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_jButtonProcessVideoActionPerformed

    private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextActionPerformed
        try {
            //load library
	    System.loadLibrary(Core.NATIVE_LIBRARY_NAME );
            // TODO add your handling code here:
            folderName = textField2.getText();
            int numOfFiles = new File("./videoFrames//"+folderName+"//").listFiles().length;
            //System.out.println(numOfFiles);
            if(currentFrameIndex<numOfFiles-1){
                int currentFrameIndex = Integer.parseInt(initialFrameIndexBox.getText());
                datasetIndex = Integer.parseInt(textField1.getText());
                String videoImageFileName="./videoFrames//"+folderName+"//"+"frame_outVideo_"+currentFrameIndex+".jpg";
            
                String depthFrameFileName = initialImagePath +datasetIndex+"//"+folderName+"//" + "depthData//"+"outDepthByte_"+currentFrameIndex;

                rgbFrame  = Highgui.imread(videoImageFileName, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
                displayImage(Mat2BufferedImage(videoProcessingUtilities.resizeImage(rgbFrame, new Size(448,234))),videoFramesPanel);

                depthFrame = depthDataProcessingUtilities.processDepthDataFile(depthFrameFileName, jSlider2.getValue(), jSlider1.getValue());

                displayImage(Mat2BufferedImage(videoProcessingUtilities.resizeImage(depthFrame, new Size(448,234))),depthFramesPanel);

                frameNumberIndicator.setText(String.valueOf(currentFrameIndex));

                currentFrameIndex++;
                jLabel6.setText("Next frame index: ");
                initialFrameIndexBox.setText(String.valueOf(currentFrameIndex));
                
                
            }
            else{
                currentFrameIndex=numOfFiles-2;
                initialFrameIndexBox.setText(String.valueOf(currentFrameIndex-2));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExperimentalDataProcessingUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButtonNextActionPerformed

    private void initialFrameIndexBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initialFrameIndexBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_initialFrameIndexBoxActionPerformed

    private void jButtonBackgroundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackgroundActionPerformed
        try {
            // TODO add your handling code here:
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME );
            folderName = textField2.getText();
            datasetIndex = Integer.parseInt(textField1.getText());
            depthBackgroundFrame = depthFrame.clone();
            rgbBackgroundFrame= rgbFrame.clone();
            int currentFrameIndex = Integer.parseInt(initialFrameIndexBox.getText())-1;
            String backgroundsFolder = ".//backgrounds//"+folderName;
            
            File backgDir = new File(backgroundsFolder);
            System.out.println(backgDir.getAbsolutePath());
            if(!backgDir.exists()){
                if(backgDir.mkdir())
                    System.out.println(backgDir.getAbsolutePath()+" is created");
                else
                    System.out.println("Failed to create a directory...");
            }
            
            String rgbBackgroundFileName=backgroundsFolder+"//"+"videoBackground_"+currentFrameIndex+".jpg";
            String depthBackgroundFileName=backgroundsFolder+"//"+"depthBackground_"+currentFrameIndex;
            
            MatOfInt parameters = new MatOfInt();
            parameters.fromArray(Highgui.CV_IMWRITE_JPEG_QUALITY, 100);
            
            Highgui.imwrite(rgbBackgroundFileName, rgbBackgroundFrame, parameters);
            //Highgui.imwrite(depthBackgroundFileName+".jpg", depthBackgroundFrame, parameters);
            ProcessImages.saveByteData(ProcessImages.Mat2BufferedImage(depthBackgroundFrame), depthBackgroundFileName);
            
        } catch (IOException ex) {
            Logger.getLogger(ExperimentalDataProcessingUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButtonBackgroundActionPerformed

    private void jSlider2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider2StateChanged
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            processOnSliderAction();
            
            //SwingUtilities.updateComponentTreeUI(this);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExperimentalDataProcessingUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSlider2StateChanged

    //for illustration only
    private void jButtonShowBackgroundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShowBackgroundActionPerformed
        // TODO add your handling code here:
        
        Mat [] backgroundFrames = readBackground();
                //Highgui.imread(rgbBackgroundFileName, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
        rgbBackgroundFrame=backgroundFrames[0];
        depthBackgroundFrame=backgroundFrames[1];
        displayImage(Mat2BufferedImage(videoProcessingUtilities.resizeImage(rgbBackgroundFrame, new Size(448,234))),videoBckgFramesPanel);
        displayImage(Mat2BufferedImage(videoProcessingUtilities.resizeImage(depthBackgroundFrame, new Size(448,234))),depthBckgFramesPanel);

    }//GEN-LAST:event_jButtonShowBackgroundActionPerformed

    private void jButtonProcessImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProcessImageActionPerformed
        try {
            // TODO add your handling code here:
            //load library
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME );
            // TODO add your handling code here:
            folderName = textField2.getText();
            int currentFrameIndex = Integer.parseInt(initialFrameIndexBox.getText())-1;
            datasetIndex = Integer.parseInt(textField1.getText());
            String videoImageFileName="./videoFrames//"+folderName+"//"+"frame_outVideo_"+currentFrameIndex+".jpg";
            
            String depthFrameFileName = initialImagePath +datasetIndex+"//"+folderName+"//" + "depthData//"+"outDepthByte_"+currentFrameIndex;
            
            rgbFrame  = Highgui.imread(videoImageFileName, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
            
            depthFrame = depthDataProcessingUtilities.processDepthDataFile(depthFrameFileName, jSlider2.getValue(), jSlider1.getValue());

            Mat [] backgroundFrames = readBackground();
            rgbBackgroundFrame=backgroundFrames[0];
            depthBackgroundFrame=backgroundFrames[1];
            
            //subtract depth background
            Mat depthFrameBackgroundSubtracted = new Mat();
            Core.subtract(depthBackgroundFrame,depthFrame, depthFrameBackgroundSubtracted);
	    Imgproc.threshold(depthFrameBackgroundSubtracted,depthFrameBackgroundSubtracted,0,255,Imgproc.THRESH_BINARY);
            displayImage(Mat2BufferedImage(videoProcessingUtilities.resizeImage(depthFrameBackgroundSubtracted, new Size(448,234))),depthBckgSubtractedFrames);
            
            
            //remove the red-colored elements from depth image and leave only blue ones
            Mat depthImageCleaned= new Mat();
            Core.inRange(depthFrameBackgroundSubtracted, new Scalar(253, 0, 0), new Scalar(255, 0, 0), depthImageCleaned);
            
            //apply morphologic opening to remove noise
            Imgproc.morphologyEx(depthImageCleaned, depthImageCleaned,2, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3)));
            displayImage(Mat2BufferedImage(videoProcessingUtilities.resizeImage(depthImageCleaned, new Size(448,234))),depthCleanedFramesPanel);
            
            //apply the homographic transform to cleaned depth image
            Mat hDepthImageCleaned = videoProcessingUtilities.performHomographyTransformation(depthImageCleaned, new Size(1920,1080));
		
            //extract all contours
            //sort all extracted contours and choose top 2
            //overlay top 2 contours on the image and fill them with white color
            //mask the rgb frame
            // do all necessary rotation operations
            //offer user to save the image
            
            //extract all suitable contours between MIN and MAX areas:
            MatOfPoint[]contours = videoProcessingUtilities.extractLargestContours(hDepthImageCleaned, 100000, 160000);
            System.out.println("Number of contorus extracted "+contours.length);
            
            //draw contours
            List<MatOfPoint> tempContours = new ArrayList<MatOfPoint>();
            Mat hDepthImageCleanedContours= hDepthImageCleaned.clone();
            for(MatOfPoint cnt:contours){
                System.out.println("Extracted Contour Area is "+Imgproc.contourArea(cnt));
                tempContours.add(cnt);
            }
            Imgproc.cvtColor(hDepthImageCleanedContours, hDepthImageCleanedContours, Imgproc.COLOR_GRAY2BGR);
            Imgproc.drawContours(hDepthImageCleanedContours, tempContours, -1, new Scalar(0,0,255),5);
            displayImage(Mat2BufferedImage(videoProcessingUtilities.resizeImage(hDepthImageCleanedContours, new Size(448,234))),extractedContoursPanel);
            
            //prepare final mask
            Mat hDepthImageFilledContours = new Mat(hDepthImageCleaned.rows(),hDepthImageCleaned.cols(),hDepthImageCleaned.type());
            Imgproc.drawContours(hDepthImageFilledContours, tempContours, -1, new Scalar(255,255,255),-1);
            displayImage(Mat2BufferedImage(videoProcessingUtilities.resizeImage(hDepthImageFilledContours, new Size(448,234))),maskedContoursPanel);
            
            
            
            //subtract video background
//            Mat rgbFrameBackgroundSubtracted = new Mat();
//            Core.subtract(rgbBackgroundFrame,rgbFrame, rgbFrameBackgroundSubtracted, hDepthImageCleaned);
//            displayImage(Mat2BufferedImage(videoProcessingUtilities.resizeImage(rgbFrameBackgroundSubtracted, new Size(448,234))),videoBckgSubtractedFrames);
//            
            //mask
            Mat preMaskedRGBFrame= new Mat();
            rgbFrame.copyTo(preMaskedRGBFrame, hDepthImageCleaned); 
            displayImage(Mat2BufferedImage(videoProcessingUtilities.resizeImage(preMaskedRGBFrame, new Size(448,234))),videoBckgSubtractedFrames);
            
            //postmask
            Mat betterMaskedRGBFrame = new Mat();
            rgbFrame.copyTo(betterMaskedRGBFrame, hDepthImageFilledContours); 
            displayImage(Mat2BufferedImage(videoProcessingUtilities.resizeImage(betterMaskedRGBFrame, new Size(448,234))),videoMaskedPanel);
            
            //clear ArrayList containig all processed images
            finalImages.clear();
            javax.swing.JLabel [] jLabArray ={extractedShoePanel1,extractedShoePanel2};
            //segment all images
            int panelIdx=0;
            for(MatOfPoint contour:tempContours){
                MatOfPoint2f newMatOfPoint2fContour = new MatOfPoint2f(contour.toArray() );
                RotatedRect finalROI = Imgproc.minAreaRect(newMatOfPoint2fContour);
                Mat newMask = videoProcessingUtilities.getContourMasked(hDepthImageFilledContours.clone(),contour);
                Mat imageROIRegistred= new Mat();
                betterMaskedRGBFrame.copyTo(imageROIRegistred, newMask); 
                Mat maskedRGBFrameROI = videoProcessingUtilities.rotateExtractedShoeprint(imageROIRegistred, finalROI, new Size(500,750), 2);
                finalImages.add(maskedRGBFrameROI);
                displayImage(Mat2BufferedImage(videoProcessingUtilities.resizeImage(maskedRGBFrameROI, new Size(203,250))),jLabArray[panelIdx]);
                panelIdx++;
            }
            
            
            //MatOfInt parameters = new MatOfInt();
            //parameters.fromArray(Highgui.CV_IMWRITE_JPEG_QUALITY, 100);
            //Highgui.imwrite(".//backgrounds//"+"test.jpg", depthFrameBackgroundSubtracted, parameters);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExperimentalDataProcessingUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonProcessImageActionPerformed

    private void saveToDSjButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveToDSjButton1ActionPerformed
        // TODO add your handling code here:
        saveImage(0, "Dataset");
    }//GEN-LAST:event_saveToDSjButton1ActionPerformed

    private void saveToDSjButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveToDSjButton2ActionPerformed
        // TODO add your handling code here:
        saveImage(1, "Dataset");
    }//GEN-LAST:event_saveToDSjButton2ActionPerformed

    private void saveForManualProcButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveForManualProcButton1ActionPerformed
        // TODO add your handling code here:
        saveImage(0, "Manual");
    }//GEN-LAST:event_saveForManualProcButton1ActionPerformed

    private void saveForManualProcButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveForManualProcButton2ActionPerformed
        // TODO add your handling code here:
        saveImage(1, "Manual");
    }//GEN-LAST:event_saveForManualProcButton2ActionPerformed

    /**
     * 
     * @param idx
     * @param category 
     */
    public  void saveImage(int idx, String category){
        try {
            // TODO add your handling code here:
            //load library
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME );
            // TODO add your handling code here:
            folderName = textField2.getText();
            int datasetIndex = Integer.parseInt(textField1.getText());
            int currentFrameIndex = Integer.parseInt(initialFrameIndexBox.getText())-1;
            String device = folderName.split("\\_")[0];
            String mode ="";
            if(folderName.contains("RGB_LIGHT"))
                mode = "rgbLight";
            else if(folderName.contains("RGB"))
                mode = "rgb";
            else if(folderName.contains("GRAY_LIGHT"))
                mode = "grayLight";
            else if(folderName.contains("GRAY"))
                mode = "gray";
            datasetIndex = Integer.parseInt(textField1.getText());
            
            
            String savePath2DSFolder = "D://SHOEPRINTS_EXTRACTED//"+category+"//"+folderName;
            File mainDir = new File(savePath2DSFolder);
		System.out.println(mainDir.getAbsolutePath());
		if(!mainDir.exists()){
			if(mainDir.mkdir())
				System.out.println(mainDir.getAbsolutePath()+" is created");
			else
				System.out.println("Failed to create a directory...");
		}
            
            
            
            String videoImageFileName=savePath2DSFolder+"//"+datasetIndex+"_"+mode+"_"+currentFrameIndex+"_"+idx+".png";
            
            //
            //write to file
            /////////////////////////////////////////////////////////////////////////////
            // use IIORegistry to get the available services
            JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
            jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(1f);
            
            IIORegistry registry = IIORegistry.getDefaultInstance();
            // return an iterator for the available ImageWriterSpi for jpeg images
            Iterator<ImageWriterSpi> services = registry.getServiceProviders(ImageWriterSpi.class,
                    new ServiceRegistry.Filter() {
                        @Override
                        public boolean filter(Object provider) {
                            if (!(provider instanceof ImageWriterSpi)) return false;
                            
                            ImageWriterSpi writerSPI = (ImageWriterSpi) provider;
                            String[] formatNames = writerSPI.getFormatNames();
                            for (int i = 0; i < formatNames.length; i++) {
                                if (formatNames[i].equalsIgnoreCase("PNG")) {
                                    return true;
                                }
                            }
                            
                            return false;
                        }
                    },
                    true);
            //...assuming that servies.hasNext() == true, I get the first available service.
            ImageWriterSpi writerSpi = services.next();
            ImageWriter writer = writerSpi.createWriterInstance();
            
            // specifies where the jpg image has to be written
            writer.setOutput(new FileImageOutputStream(
                    new File(videoImageFileName)));
            
            // writes the file with given compression level
            // from your JPEGImageWriteParam instance
            writer.write(null, new IIOImage(Mat2BufferedImage(finalImages.get(idx)), null, null), jpegParams);
            writer.dispose();
            /////////////////////////////////////////////////////////////////////////////
           
        } catch (IOException ex) {
            Logger.getLogger(ExperimentalDataProcessingUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static  void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExperimentalDataProcessingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExperimentalDataProcessingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExperimentalDataProcessingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExperimentalDataProcessingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExperimentalDataProcessingUI().setVisible(true);
                
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel depthBckgFramesPanel;
    private javax.swing.JLabel depthBckgSubtractedFrames;
    private javax.swing.JLabel depthCleanedFramesPanel;
    private javax.swing.JLabel depthFramesPanel;
    private javax.swing.JLabel extractedContoursPanel;
    private javax.swing.JLabel extractedShoePanel1;
    private javax.swing.JLabel extractedShoePanel2;
    private javax.swing.JLabel frameNumberIndicator;
    private java.awt.TextField initialFrameIndexBox;
    private javax.swing.JButton jButtonBackground;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonProcessImage;
    private javax.swing.JButton jButtonProcessVideo;
    private javax.swing.JButton jButtonShowBackground;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel maskedContoursPanel;
    private javax.swing.JButton saveForManualProcButton1;
    private javax.swing.JButton saveForManualProcButton2;
    private javax.swing.JButton saveToDSjButton1;
    private javax.swing.JButton saveToDSjButton2;
    private java.awt.TextField textField1;
    private java.awt.TextField textField2;
    private javax.swing.JLabel videoBckgFramesPanel;
    private javax.swing.JLabel videoBckgSubtractedFrames;
    private javax.swing.JLabel videoFramesPanel;
    private javax.swing.JLabel videoMaskedPanel;
    // End of variables declaration//GEN-END:variables
}
