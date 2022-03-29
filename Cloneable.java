public interface Cloneable
{
   boolean getPixel(int row, int col);
   boolean setPixel(int row, int col, boolean value);
   void checkSize(String[] data);
   void displayToConsole();
   Object clone() throws CloneNotSupportedException;
}
