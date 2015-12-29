/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processdata;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
/**
 *
 * @author Wild
 */
public class depthDataProcessingUtilities {
    private static Scanner inDepthDataFile;
    /**
	 * converts depth data to opencv Mat object leaving depth values that are only within min and max thresholds
	 * @param path
	 * @param minThreshold
	 * @param maxThreshold
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Mat processDepthDataFile(String path,int minThreshold, int maxThreshold) throws FileNotFoundException{
		File depthData = new File(path);
		
		double[][]depthDataArray = new double[1][217088];
		
		//read depth data into array
		int count = 0;
		
		inDepthDataFile = new Scanner(depthData);//.useDelimiter(",\\s*");

		while(inDepthDataFile.hasNext()){
			String currentStr=inDepthDataFile.nextLine();
			if(!currentStr.isEmpty())
				depthDataArray[0][count++] = Double.parseDouble(currentStr);
		}
		
		double depthDataMatrix[][] = new double [512][424];
		
		depthDataMatrix= reshape(depthDataArray,512,424);
		
		Mat matDepthDataMatrix = new Mat(512,424, CvType.CV_64F);
		
		
		//cut-off the remaining depth values
		for(int i = 0;i<depthDataMatrix.length;i++){
            for(int j = 0;j<depthDataMatrix[0].length;j++){
            	if(depthDataMatrix[i][j]>maxThreshold || depthDataMatrix[i][j]<minThreshold)
            		depthDataMatrix[i][j]=0;
            }
		}
		
		
		
		//find max value
		double max = 0;
		
		for(int i = 0;i<depthDataMatrix.length;i++){
            for(int j = 0;j<depthDataMatrix[0].length;j++){
            	if(depthDataMatrix[i][j]>max)
            		max=depthDataMatrix[i][j];
            }
		}
		
		
		//FILL THE DEPTH MATRIX
		//System.out.println("Max Element "+ max);
		
		for(int i = 0;i<depthDataMatrix.length;i++){
		    for(int j = 0;j<depthDataMatrix[0].length;j++){
		    	matDepthDataMatrix.put(i, j, depthDataMatrix[i][j]/max*255.0);
		    }
		}
		
//		//printout the depth matrix
//		for(int i = 0;i<depthDataMatrix.length;i++){
//		    for(int j = 0;j<depthDataMatrix[0].length;j++){
//		        System.out.print(depthDataMatrix[i][j]+"\t");
//		    }
//        System.out.println();
//		}
//		
		
		//apply colormap to visualize
		Mat processedMathDepthImage= new Mat(matDepthDataMatrix.size(),CvType.CV_8U);
		matDepthDataMatrix.convertTo(processedMathDepthImage,CvType.CV_8UC1);
		
		Core.transpose(processedMathDepthImage, processedMathDepthImage);
		org.opencv.contrib.Contrib.applyColorMap(processedMathDepthImage,processedMathDepthImage,org.opencv.contrib.Contrib.COLORMAP_JET);

		return processedMathDepthImage;
	}
	
	/**
	 * reshaping for depthDataArray to make it 512X424 
	 * @param A
	 * @param m
	 * @param n
	 * @return
	 */
	public static double[][] reshape(double[][] A, int m, int n) {
        int origM = A.length;
        int origN = A[0].length;
        if(origM*origN != m*n){
            throw new IllegalArgumentException("New matrix must be of same area as matix A");
        }
        double[][] B = new double[m][n];
        double[] A1D = new double[A.length * A[0].length];

        int index = 0;
        for(int i = 0;i<A.length;i++){
            for(int j = 0;j<A[0].length;j++){
                A1D[index++] = A[i][j];
            }
        }

        index = 0;
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                B[j][i] = A1D[index++];
            }

        }
        return B;
    }
}
