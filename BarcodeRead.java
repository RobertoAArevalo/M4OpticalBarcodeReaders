
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
        
        
    }
    boolean getPixel(int row, int col)
    {
        
    }
    boolean setPixel(int row, int col, boolean value)
    {
        
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
        
    }
    public boolean generateImageFromText()
    {
        
    }
    public boolean translateImageToText()
    {
        
    }
    public void displayTextToConsole()
    {
        
    }
    public void displayImageToConsole()
    {
        
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