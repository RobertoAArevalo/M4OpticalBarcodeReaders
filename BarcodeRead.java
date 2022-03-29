
public class BarcodeRead
{

    interface BarcodeIO
{
   public boolean scan(BarcodeImage bc);
   public boolean readText(String text);
   public boolean generateImageFromText();
   public boolean translateImageToText();
   public void displayTextToConsole();
   public void displayImageToConsole();
   }

    
    
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

    }

}
class BarcodeImage implements Cloneable
{
    public static final int MAX_HEIGHT = 30;    
    public static final int MAX_WIDTH = 65; 
    private boolean[][] imageData;
    
    
    BarcodeImage()
    {
        imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
        for(int i = 0; i < MAX_HEIGHT; i++)
            for(int j = 0; j < MAX_WIDTH; j++)
                imageData[i][j] = false;
            
    }
    BarcodeImage(String[] strData)
    {
     int row = 0;
      int col = 0;
      
      //bottom right of 2d array is MAX_HEIGHT - strData.length
      //most left position in 2d array is [0][col]
      //my guess for lower left is below
      int lowerLeft = 0; 
      for (int i = 0; i < strData.length; i++)
      {
  
         for (int z = 0; z < strData[i].length();z++)
         {
            if ( strData[row].charAt(col) == ' ')
            {
               imageData[lowerLeft + (MAX_HEIGHT - strData.length)][col] = false;
               col++;
            }
            else if ( strData[row].charAt(col) == '*')
            {
               imageData[lowerLeft + (MAX_HEIGHT - strData.length)][col] = true;
               col++;
            }
         }
         row++;
         col=0;
      }
      //end of BarcodeImage(String[] strData)     
    }
    boolean getPixel(int row, int col)
    {
        boolean sendPixel = false;
      
       if(row <MAX_HEIGHT && col < MAX_WIDTH) //ADDING IF STATEMENT TO CHECK IF ROW AND COL ARE GOOD (within array)
      {    
               sendPixel = imageData[row][col];        
      }
     return sendPixel;
   }
    boolean setPixel(int row, int col, boolean value)
    {
       this.imageData[row][col] = value;
    }
    private boolean checkSize(String[] data)//optional 
    {
        //is to check the string size
        //max width is identical
    }
    public void displayToConsole()// optional
    {
        
    }
    public Object clone() throws CloneNotSupportedException
    {
          return (Object)super.clone();
        /*  create an object 
         *  set the variables to that objects
         *  return object
        */
        
    }
}
interface BarcodeIO
{
    public boolean scan(BarcodeImage bc)
    {
        
    
    }
    public boolean readText(String text)
    {
        if(text.length() <= BarcodeImage.Max_WIDTH -1)
        {
           this.text = '*';
           return true;
        }
        return false; 
    }
    public boolean generateImageFromText()
    {
       actualWidt = this.text.length() + 2; //don't forget the border
       actualHeight = 10; // 8 bits, plus the spine
       this.clearImage();
       // add borders to image
       //set top and bottom border
       for (int i = 0; i < actualWidt; i++)
       {
          this.image.setPixel(image.MAX_HEIGHT - 1, i, true);
          if (i % 2 == 0)
             this.image.setPixel(image.MAX_HEIGHT - this.actualHeight, i, true);
       }
       //set left and right border
       for (int i = 0; i < 10; i++)
       {
          this.image.setPixel(image.MAX_HEIGHT - this.actualHeight + i, 0, true);
          if (i % 2 == 0)
             this.image.setPixel(image.MAX_HEIGHT - this.actualHeight + i,
                   this.actualWidt - 1, true);
       }

       //get ascii value of string
       for (int index = 0; index < text.length(); index++)
          writeCharToCol(index, (int) text.charAt(index)); //write to array col

       return true;
    }

   public boolean translateImageToText()
   {
      String imageText = "  ";
      for (int col = 1; col < getActualWidth() - 1; col++)
         imageText += readCharFromCol(col);

      //set text to object
      this.text = imageText;
      return true;
   }
   public void displayTextToConsole()
   {
      System.out.println(this.text);
   }
   public void displayImageToConsole()
   {
      String dash = "-";
      //print top border
      System.out.print(dash.repeat(getActualWidth() + 2) + "\n");

      for (int row = (image.MAX_HEIGHT - getActualHeight());
           row < image.MAX_HEIGHT; row++)
      {
         //print left side border
         System.out.print("|");
         for (int col = 0; col < getActualWidth(); col++)
            if (image.getPixel(row, col) == true)
               System.out.print(BLACK_CHAR);
            else
               System.out.print(WHITE_CHAR);

         //print right side border and move to next line
         System.out.print("|\n");

      }
      //print bottom border
      System.out.print(dash.repeat(getActualWidth() + 2) + "\n");
   }
    
}

class DataMatrix implements BarcodeIO
{
    public static final char BLACK_CHAR = '*';
    public static final char WHITE_CHAR = ' ';
    private BarcodeImage image;
    private String text = " ";
    private int actualWidt = 0; 
    private int actualHeight = 0;

   DataMatrix(****)
   {
      {
         imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
         for(int i = 0; i < MAX_HEIGHT; i++)
            for(int j = 0; j < MAX_WIDTH; j++)
               imageData[i][j] = false;
      }
   }

   }

    DataMatrix(BarcodeImage image)
    {
        
    }
    DataMatrix(String text)
    {
        
    }
    readText(String text)// mutator
    {
    
    }
    scan(BarcodeImage image)
    {
        
    }
    private int computeSignalWidth()
    {
        
    }
    private int computeSignalHeight()
    {
        
    }
    private void cleanImage() 
    {
        
    }
    public void displayRawImage() 
    {
        
    }
    private void clearImage()
    {
        
    }
}
