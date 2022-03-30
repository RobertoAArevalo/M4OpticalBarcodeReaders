/*
M4 - Java Program - Optical Barcode Readers
@author Roberto Arevalo
@author Ali Bakr
@author David Delacalzada
@author Richard Garcia
3/29/22

This program allows for a barcode to be read, collected,
converted, and printed into a string.
 */

import java.util.*;
public class BarcodeRead
{

   public static void main(String[] args)
   {
      String[] sImageIn =
            {
                  "                                               ",
                  "                                               ",
                  "                                               ",
                  "     * * * * * * * * * * * * * * * * * * * * * ",
                  "     *                                       * ",
                  "     ****** **** ****** ******* ** *** *****   ",
                  "     *     *    ****************************** ",
                  "     * **    * *        **  *    * * *   *     ",
                  "     *   *    *  *****    *   * *   *  **  *** ",
                  "     *  **     * *** **   **  *    **  ***  *  ",
                  "     ***  * **   **  *   ****    *  *  ** * ** ",
                  "     *****  ***  *  * *   ** ** **  *   * *    ",
                  "     ***************************************** ",
                  "                                               ",
                  "                                               ",
                  "                                               "

            };



      String[] sImageIn_2 =
            {
                  "                                          ",
                  "                                          ",
                  "* * * * * * * * * * * * * * * * * * *     ",
                  "*                                    *    ",
                  "**** *** **   ***** ****   *********      ",
                  "* ************ ************ **********    ",
                  "** *      *    *  * * *         * *       ",
                  "***   *  *           * **    *      **    ",
                  "* ** * *  *   * * * **  *   ***   ***     ",
                  "* *           **    *****  *   **   **    ",
                  "****  *  * *  * **  ** *   ** *  * *      ",
                  "**************************************    ",
                  "                                          ",
                  "                                          ",
                  "                                          ",
                  "                                          "

            };

      BarcodeImage bc = new BarcodeImage(sImageIn);
      DataMatrix dm = new DataMatrix(bc);

      // First secret message
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();

      // second secret message
      bc = new BarcodeImage(sImageIn_2);
      dm.scan(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();

      // create your own message
      dm.readText("What a great resume builder this is!");
      dm.generateImageFromText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
      //My groups sample
      BarcodeImage bc007 = new BarcodeImage();
      DataMatrix dm4 = new DataMatrix("This is a good SAMPLE to look at");
      dm4.generateImageFromText();
      dm4.displayTextToConsole();
      dm4.displayImageToConsole();

   }

}

//duplicates the barcode image
class BarcodeImage implements Cloneable
{
   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;
   private boolean[][] imageData;


   public BarcodeImage()
   {
      imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
      for(int i = 0; i < MAX_HEIGHT; i++)
         for(int j = 0; j < MAX_WIDTH; j++)
            imageData[i][j] = false;

   }
   public BarcodeImage(String[] strData)
   {
      if (checkSize(strData))
      {
         imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];

         for (int i = 0; i < strData.length; i++)//hori
         {
            for (int j = 0; j < strData[i].length(); j++)//vert
               if (strData[i].charAt(j) == '*')
                  setPixel(i, j, true);
         }
      }
   }
   boolean getPixel(int row, int col)
   {
      if (imageData != null || row < MAX_HEIGHT && col < MAX_WIDTH)
         return imageData[row][col];
      return false;
   }
   boolean setPixel(int row, int col, boolean value)
   {
      if (row < MAX_HEIGHT && col < MAX_WIDTH)
      {
         imageData[row][col] = value;
         return true;
      }
      return false;

   }
   private boolean checkSize(String[] data)//optional
   {
      //is to check the string size
      //max width is identical
      if (data != null)
         return data.length < MAX_HEIGHT && data[0].length() < MAX_WIDTH;
      return false;
   }
   public void displayToConsole()// optional
   {
      for (int i = 0; i < MAX_HEIGHT; i++)
      {

         for (int j = 0; j < MAX_WIDTH; j++)
         {

            if (imageData[i][j])
            {
               System.out.print('*');
            } else
            {
               System.out.print(' ');
            }
         }
         System.out.println();
      }
   }
   public Object clone()
   {
      /*  create an object
       *  set the variables to that objects
       *  return object
       */
      try
      {
         BarcodeImage copy = (BarcodeImage) super.clone();
         copy.imageData = imageData.clone();
         for (int i = 0; i < MAX_HEIGHT; i++)
         {
            copy.imageData[i] = imageData[i];
         }
         return copy;
      }
      catch (CloneNotSupportedException e)
      {
         return null;
      }
   }
}
interface BarcodeIO
{
   public boolean scan(BarcodeImage bc);

   public boolean readText(String text);

   public boolean generateImageFromText();

   public boolean translateImageToText();

   public void displayTextToConsole();

   public void displayImageToConsole();
}
class DataMatrix implements BarcodeIO
{
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
   private BarcodeImage image;
   private String text = " ";
   private int actualWidt;
   private int actualHeight;

   DataMatrix()
   {
      this.image = new BarcodeImage();
      this.text = "";
      this.actualWidt = 0;
      this.actualHeight = 0;
   }
   DataMatrix(BarcodeImage image)
   {
      scan(image);
      this.text = "";
   }
   DataMatrix(String text)
   {
      this.image = new BarcodeImage();
      readText(text);
      this.actualWidt = 0;
      this.actualHeight = 0;
   }
   public boolean readText(String text)// mutator
   {
      if (text == null || (text.length() > BarcodeImage.MAX_WIDTH - 2))
      {
         return false;
      }
      this.text = text;
      return true;
   }
   public boolean scan(BarcodeImage image)
   {
      if(image == null)//Checks if object is null before cloning.
      {
         return false;
      }
      //clone
      this.image =(BarcodeImage)image.clone();
      if(this.image == null) //Checks that cloned image is valid.
      {
         return false;
      }
      cleanImage();
      this.actualWidt = computeSignalWidth();
      this.actualHeight = computeSignalHeight();

      return true;
   }
   public int getActualWidth()
   {
      return this.actualWidt;
   }
   public int getActualHeight()
   {
      return this.actualHeight;
   }
   private int computeSignalWidth()
   {
      int imagWidt = 0;
      for (int j = 0; j < image.MAX_WIDTH; j++)
         if (image.getPixel(image.MAX_HEIGHT - 1, j))
            imagWidt++;
      return imagWidt;
   }
   private int computeSignalHeight()
   {
      int imagHeigh = 0;
      for (int i = 0; i < image.MAX_HEIGHT; i++)
         if (image.getPixel(i, 0))
            imagHeigh++;
      return imagHeigh;
   }
   private void cleanImage()
   {
      boolean dataCK = false;
      int imageWidth = 0;
      int imageHeight = 0;

      //check vertical placement
      for (int j = 0; j < image.MAX_WIDTH; j++)
         //if the bottom row has any data
         if (image.getPixel(image.MAX_HEIGHT - 1, j))
            dataCK = true;

      if (dataCK == false)
      {
         shiftImageDown();
         cleanImage();
      }

      //check horizontal placement
      dataCK = false;
      for (int i = 0; i < image.MAX_HEIGHT; i++)
         //if the left column has any data
         if (image.getPixel(i, 0))
            dataCK = true;

      if (dataCK == false)
      {
         moveImageToLowerLeft();
         cleanImage();
      }
   }
   private void moveImageToLowerLeft()
   {
      for (int i = 0; i < this.image.MAX_HEIGHT; i++)
         for (int j = 0; j < this.image.MAX_WIDTH - 1; j++)
            this.image.setPixel(i, j, (this.image.getPixel(i, j + 1)));
   }
   private void shiftImageDown()
   {
      for (int i = this.image.MAX_HEIGHT - 1; i > 0; i--)
         for (int j = 0; j < this.image.MAX_WIDTH; j++)
            this.image.setPixel(i, j, (this.image.getPixel(i - 1, j)));
   }
   public void displayImageToConsole()
   {
      String dash = "-";

      System.out.print(dash.repeat(getActualWidth() + 2) + "\n");

      for (int i = (image.MAX_HEIGHT - getActualHeight());
           i < image.MAX_HEIGHT; i++)
      {
         //print left side border
         System.out.print("|");
         for (int j = 0; j < getActualWidth(); j++)
            if (image.getPixel(i, j) == true)
               System.out.print(BLACK_CHAR);
            else
               System.out.print(WHITE_CHAR);

         System.out.print("|\n");
      }
      System.out.print(dash.repeat(getActualWidth() + 2) + "\n");
   }

   public boolean generateImageFromText()//recommendation
   {
      actualWidt = this.text.length() + 2;
      actualHeight = 10;
      this.clearImage();

      for (int i = 0; i < actualWidt; i++)
      {
         this.image.setPixel(image.MAX_HEIGHT - 1, i, true);
         if (i % 2 == 0)
            this.image.setPixel(image.MAX_HEIGHT - this.actualHeight, i,
                  true);
      }
      for (int i = 0; i < 10; i++)
      {
         this.image.setPixel(image.MAX_HEIGHT - this.actualHeight + i,
               0, true);
         if (i % 2 == 0)
            this.image.setPixel(image.MAX_HEIGHT - this.actualHeight + i,
                  this.actualWidt - 1, true);
      }
      for (int i = 0; i < text.length(); i++)
         writeCharToCol(i, (int) text.charAt(i));

      return true;
   }
   public boolean translateImageToText()//recommendation
   {
      String imageText = "  ";
      for (int col = 1; col < getActualWidth() - 1; col++)
         imageText += readCharFromCol(col);

      //set text to object
      this.text = imageText;
      return true;
   }
   private char readCharFromCol(int col)//recommendation
   {
      int counter = 0;
      for (int row = this.image.MAX_HEIGHT - 2; row >= this.image.MAX_HEIGHT -
            getActualHeight() + 2; row--)
         if (this.image.getPixel(row, col))
            counter += (Math.pow(2, ((image.MAX_HEIGHT - 2) - row)));


      return (char) (counter);
   }
   private boolean writeCharToCol(int col, int code)//recommendation
   {
      int a = 49;
      String binData = Integer.toString(code, 2);

      for (int i = 0; i < binData.length(); i++)
         if ((int) (binData.charAt(i)) == a)
            this.image.setPixel((this.image.MAX_HEIGHT -
                  this.actualHeight - 1 +
                  (this.actualHeight - binData.length()) + i),
                  col + 1, true);

      return true;
   }


   public void displayTextToConsole()
   {
      System.out.println(this.text);
   }
   private void clearImage()
   {
      for (int i = 0; i < this.image.MAX_HEIGHT - 1; i++)
         for (int j = 0; j < this.image.MAX_WIDTH - 1; j++)
            this.image.setPixel(i, j, false);
   }
}

/***************************Output*****************************************
  CSUMB CSIT online program is top notch.
-------------------------------------------
|* * * * * * * * * * * * * * * * * * * * *|
|*                                       *|
|****** **** ****** ******* ** *** *****  |
|*     *    ******************************|
|* **    * *        **  *    * * *   *    |
|*   *    *  *****    *   * *   *  **  ***|
|*  **     * *** **   **  *    **  ***  * |
|***  * **   **  *   ****    *  *  ** * **|
|*****  ***  *  * *   ** ** **  *   * *   |
|*****************************************|
-------------------------------------------
  You did it!  Great work.  Celebrate.
----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|**** *** **   ***** ****   *********  |
|* ************ ************ **********|
|** *      *    *  * * *         * *   |
|***   *  *           * **    *      **|
|* ** * *  *   * * * **  *   ***   *** |
|* *           **    *****  *   **   **|
|****  *  * *  * **  ** *   ** *  * *  |
|**************************************|
----------------------------------------
What a great resume builder this is!
----------------------------------------
|* * * * * * * * * * * * * * * * * * **|
|*                                     |
|***** * ***** ****** ******* **** ** *|
|* *********************************** |
|**  *    *  * * **    *    * *  *  * *|
|* *               *    **     **  *   |
|**  *   * * *  * ***  * ***  *       *|
|**      **    * *    *     *    *  *  |
|** *  * * **   *****  **  *    ** ****|
|**************************************|
----------------------------------------
This is a good SAMPLE to look at
------------------------------------
|* * * * * * * * * * * * * * * * **|
|*                                 |
|***** ** * **** ****** ** **** ***|
|* **************      *********** |
|**  *  *        *  *   *        **|
|* **  *     **    * *   * ****    |
|**         ****   * ** ** ***   **|
|*   *  *   ***  *       *  ***    |
|*  ** ** * ***  ***  *  *  *** * *|
|**********************************|
------------------------------------

Process finished with exit code 0

 */