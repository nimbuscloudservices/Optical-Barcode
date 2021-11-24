/**
 * Optical Barcode Reader and Writer
 *
 * @author Blake, Layla, Saul, Yavik
 * @version 11-17-2021
 */
public class OpticalBarcode
{
   public static void main(String[] args)
   {
      String[] sImageIn = { "                                               ",
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

      String[] sImageIn_2 = { "                                          ",
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
      dm.displayRawImage();

   }
}

class BarcodeImage implements Cloneable
{
   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;
   private boolean[][] imageData;

   /**
    * Creates a basic BarcodeImage Object using maximum width and height.
    */
   public BarcodeImage()
   {
      this.imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
   }

   /**
    * Constructs a barcode object using strData which is a 1D array of strings
    * and converts it to a 2D array of booleans
    *
    * @param strData 1D array of strings to convert
    */
   public BarcodeImage(String[] strData)
   {
     this.imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];

     if(checkSize(strData))
     {
        int index = 0;
        int found = 0;

        for (int i = 0; i < strData.length; i++)
        {
           if (strData[i].contains("*"))
           {
              found++;
           }
        }

        String[] tempString = new String[found];

        for (int i = 0; i < strData.length; i++)
        {
           if (strData[i].contains("*"))
           {

              tempString[index] = strData[i];
              tempString[index] = tempString[index].substring(
                    tempString[index].indexOf("*"),
                    tempString[index].lastIndexOf(" "));

              index++;
           }
        }

        index = 0;

        for (int j = MAX_HEIGHT - tempString.length; j < MAX_HEIGHT; j++)
        {
           for (int k = 0; k < tempString[index].length(); k++)
           {

              if (tempString[index].charAt(k) == '*')
              {
                 this.imageData[j][k] = true;
              }
              else
              {
                 this.imageData[j][k] = false;
              }
           }
           index++;
        }
     }
     else
     {
        System.out.println("Error: Height and width of String does not meet the requirements.");
     }
   }

   /**
    * checks row and col values if they are within MAX height and width
    *
    * @param row in ImageData array
    * @param col in ImageData array
    * @return true if pixel value or false if error
    */
   public boolean getPixel(int row, int col)
   {
      if (row < 0 || row >= MAX_HEIGHT || col < 0 || col >= MAX_WIDTH)
      {
         return false;
      }
      else
      {
         return this.imageData[row][col];
      }
   }

   /**
    * @param row   in ImageData array
    * @param col   in ImageData array
    * @param value that pixel is being set to
    * @return true if row/col value is proper and sets value, false if it is not
    * proper
    */
   public boolean setPixel(int row, int col, boolean value)
   {
      if (row < 0 || row >= MAX_HEIGHT || col < 0 || col >= MAX_WIDTH)
      {
         return false;
      }
      else
      {
         this.imageData[row][col] = value;
         return true;
      }
   }

   /**
    * helper method that verifies string data does not exceed max values
    *
    * @param data that is being checked for size
    * @return true if within max values, false if invalid
    */
   private boolean checkSize(String[] data)
   {
      return data.length <= MAX_HEIGHT && data[0].length() <= MAX_WIDTH && data != null;
   }

   /**
    * Displays contents of imageData in "*" or " "
    */
   public void displayToConsole()
   {
      System.out.print(" ");

      for (int i = 0; i < this.MAX_WIDTH; i++)
      {
         System.out.print("-");
      }

      System.out.println();

      for (int i = 0; i < MAX_HEIGHT; i++)
      {
         System.out.print("|");
         for (int j = 0; j < MAX_WIDTH; j++)
         {
            if ((this.imageData[i][j]))
            {
               System.out.print("*");
            }
            else
            {
               System.out.print(" ");
            }
         }
         System.out.println("|");
      }

      System.out.print(" ");

      for (int i = 0; i < this.MAX_WIDTH; i++)
      {
         System.out.print("-");
      }

      System.out.println();
   }

   /**
    * clones the object, used ch 13.1 - figure 13.8 as an example.
    *
    * @return cloned object
    */

   public BarcodeImage clone()
   {
      try
      {
         BarcodeImage clonedImage = (BarcodeImage) super.clone();
         //cloning by copying inner data by looping through outer array
         clonedImage.imageData = this.imageData.clone();
         for (int i = 0; i < this.imageData.length; i++)
         {
            clonedImage.imageData[i] = this.imageData[i].clone();
         }

         return clonedImage;
      } catch (CloneNotSupportedException e)
      {
         e.printStackTrace();
         throw new RuntimeException();
      }

   }

}

/**
 * This class is a pseudo Datamatrix data structure, not a true Datamatrix,
 * because it does not contain any error correction or encoding.  However, it
 * does have the 2D array format and a left and bottom BLACK "spine" as well as
 * an alternating right and top BLACK-WHITE pattern as seen in the image.
 */
class DataMatrix implements BarcodeIO
{
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
   private BarcodeImage image;
   private String text;
   private int actualWidth;
   private int actualHeight;

   /**
    * Default constructor for DataMatrix Object
    */
   public DataMatrix()
   {
      this.actualHeight = 0;
      this.actualWidth = 0;
      this.text = "undefined";
      this.image = new BarcodeImage();

   }

   /**
    * Constructor that sets the image but leaves the text at its default value.
    *
    * @param image that is being scanned and set
    */
   public DataMatrix(BarcodeImage image)
   {
      this();
      scan(image);
   }

   /**
    * Constructor that sets the text but leaves the image at its default value.
    *
    * @param text that is being read and set
    */
   public DataMatrix(String text)
   {
      this();
      this.readText(text);

   }

   /**
    * A mutator for text
    *
    * @param text being read
    * @return true if text is read
    */
   public boolean readText(String text)
   {
      if (text != null || text != "" || text.length() < BarcodeImage.MAX_WIDTH)
      {
         this.text = text;
         return true;
      }
      else
      {
         return false;
      }
   }

   /**
    * Sets image, calls helper methods to computer actual width/height and
    * catches cloning errors.
    *
    * @param image that is being scanned
    * @return true if successfully clones image, false if not successful for no
    * drama.
    */
   public boolean scan(BarcodeImage image)
   {
      try
      {
         this.image = image.clone();
         this.cleanImage();
         this.actualWidth = this.computeSignalWidth();
         this.actualHeight = this.computeSignalHeight();
         return true;
      }
      catch (Exception e)
      {
         return false;
      }
   }

   /**
    * Generates image from text
    *
    * @return true if successful, false if not
    */
   @Override public boolean generateImageFromText()
   {
      this.actualHeight = 10;
      this.actualWidth = text.length() + 2;
      this.clearImage();

      for (int c = 0; c < actualWidth; c++)
      {
         image.setPixel(this.actualHeight-1, c, true);
         if(c % 2 == 0)
            image.setPixel(0, c, true);
      }

      for (int r = 0; r < actualHeight; r++)
      {
         if(r % 2 == 0)
            image.setPixel(r, actualWidth, true);
         image.setPixel(r, 0, true);
      }

      for (int i = 0; i < text.length(); i++)
      {
         int asciiValue = text.charAt(i);
         int value = 128;

         while (value > 0)
         {
            if (asciiValue - value >= 0)
            {
               WriteCharToCol(i + 1, value);
               asciiValue -= value;
            }
            value /= 2;
         }
      }
      cleanImage();
      return true;
   }

   /**
    * Converts image to text
    *
    * @return true if successful, false if not.
    */
   @Override public boolean translateImageToText()
   {
      String imageText = "  ";
      for (int col = 1; col < getActualWidth() - 1; col++)
         imageText += readCharFromCol(col);

      this.text = imageText;
      return true;
   }

   /**
    * displays trimmed text in console
    */
   @Override public void displayTextToConsole()
   {
      System.out.println(this.text);
   }

   /**
    * displays trimmed barcode image in console
    */
   @Override public void displayImageToConsole()
   {
      System.out.print(" ");
      for (int i = 0; i < this.actualWidth; i++)
      {
         System.out.print("-");
      }

      System.out.println();

      for (int i = BarcodeImage.MAX_HEIGHT - this.actualHeight; i < 30; i++)
      {
         System.out.print("|");
         for (int j = 0; j < this.actualWidth; j++)
         {
            if (image.getPixel(i, j))
            {
               System.out.print(BLACK_CHAR);
            }
            else
            {
               System.out.print(WHITE_CHAR);
            }
         }
         System.out.println("|");
      }

      System.out.print(" ");

      for (int i = 0; i < this.actualWidth; i++)
      {
         System.out.print("-");
      }

      System.out.println();
   }

   /**
    * Displays full image data including blank top and right, useful for
    * debugging
    */
   public void displayRawImage()
   {
      System.out.print(" ");

      for (int i = 0; i < BarcodeImage.MAX_WIDTH; i++)
      {
         System.out.print("-");
      }

      System.out.println();

      for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++)
      {
         System.out.print("|");
         for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++)
         {
            if (image.getPixel(i, j))
            {
               System.out.print(BLACK_CHAR);
            }
            else
            {
               System.out.print(WHITE_CHAR);
            }
         }
         System.out.println("|");
      }

      System.out.print(" ");

      for (int i = 0; i < BarcodeImage.MAX_WIDTH; i++)
      {
         System.out.print("-");
      }

      System.out.println();

   }

   /**
    * sets the image to white = false
    */
   private void clearImage()
   {
      for (int r = 0; r < BarcodeImage.MAX_HEIGHT; r++)
      {
         for (int c = 0; c < BarcodeImage.MAX_WIDTH; c++)
         {
            this.image.setPixel(r, c, false);
         }
      }
   }

   /**
    * Accessor for actualWidth
    *
    * @return value of actualWidth
    */
   public int getActualWidth()
   {
      return this.actualWidth;
   }

   /**
    * Accessor for actualHeight
    *
    * @return value of actualHeight
    */
   public int getActualHeight()
   {
      return this.actualHeight;
   }

   /**
    * Private method that uses the bottom BLACK spine of image to calculate
    * actualWidth
    *
    * @return calculated actualWidth
    */
   private int computeSignalWidth()
   {
      int width = 0;
      for (int i = 0; i < BarcodeImage.MAX_WIDTH; i++)
      {
         if (image.getPixel(BarcodeImage.MAX_HEIGHT - 1, i))
         {
            width++;
         }
      }
      return width;
   }

   /**
    * Private method that uses the left BLACK spine of image to calculate
    * actualWidth
    *
    * @return calculated actualHeight
    */
   private int computeSignalHeight()
   {
      int height = 0;
      for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++)
      {
         if (image.getPixel(i, 0))
         {
            height++;
         }
      }
      return height;
   }

   /**
    * Private method that lower left justifies incoming BarcodeImage This method
    * is called from within scan() and would move the signal to lower-left of
    * the larger 2D array.
    */
   private void cleanImage()
   {
      this.moveImageToLowerLeft();
   }

   /**
    * A helper method for cleanImage() Aligns image to lower left.
    */
    private void moveImageToLowerLeft()
    {
      int row = 0, column = 0, height = computeSignalHeight();
      boolean found = false;

      for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++)
      {
         for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++)
         {
            if (!found)
            {
               if (image.getPixel(i, j))
               {
                  found = true;
                  row = i;
                  column = j;
               }
            }
         }
      }

      row += computeSignalHeight();

      if(BarcodeImage.MAX_HEIGHT!= row && column == 0)
      {
         shiftImageDown(BarcodeImage.MAX_HEIGHT-row);
      }
      else if(BarcodeImage.MAX_HEIGHT == row && column!=0)
      {
         shiftImageLeft(column);
      }
      else if(BarcodeImage.MAX_HEIGHT!= row && column !=0)
      {
         shiftImageLeft(column);
         shiftImageDown(BarcodeImage.MAX_HEIGHT - row);
      }

    }
   /**
    * Shifts image down
    *
    * @param offset value that image needs to shift
    */
   private void shiftImageDown(int offset)
   {
     int currentRow = BarcodeImage.MAX_HEIGHT-offset;
     boolean temp;

     for (int i = BarcodeImage.MAX_HEIGHT; i >=this.actualHeight; i--)
     {
        for (int col = 0; col < BarcodeImage.MAX_WIDTH; col++)
        {
           temp = image.getPixel(currentRow, col);
           image.setPixel(i, col, temp);
           image.setPixel(currentRow, col, false);
        }
        currentRow--;
     }
   }

   /**
    * This private method shifts image to the left
    *
    * @param offset value that image needs to shift
    */
   private void shiftImageLeft(int offset)
   {
      boolean temp;
      int row = 0;
      while (row < BarcodeImage.MAX_HEIGHT)
      {
         int col = offset;
         while (col > BarcodeImage.MAX_WIDTH)
         {
            temp = image.getPixel(row, col);
            image.setPixel(row, offset - col, temp);
            image.setPixel(row, col, false);
            col++;
         }
         row++;
      }

   }

   /**
    * Helper method to decode a char from a specific column
    *
    * @param col of dataMatrix
    * @return decoded char
    */
   private char readCharFromCol(int col)
   {
      int startRow = 0;
      int asciiValue = 0;
      int value = 128;

      while (!image.getPixel(startRow, 0))
         startRow++;

      for (int row = startRow + 1; row < startRow + getActualHeight() - 1; row++)
      {
         if (image.getPixel(row, col))
            asciiValue += value;
         value /= 2;
      }
      return (char) asciiValue;
   }

   /**
    * Helper method to write and encode char to specific column
    *
    * @param col  of dataMatrix
    * @param code being written
    * @return true if successful
    */
   private boolean WriteCharToCol(int col, int code)
   {
      int value = 128;
      int row = 1;

      while (value > 0)
      {
         if (code == value)
            image.setPixel(row, col, true);
         value /= 2;
         row++;
      }
      return true;
   }

}
/**
 * Interface for Barcode Operations
 */
interface BarcodeIO
{
   boolean scan(BarcodeImage bc);

   boolean readText(String text);

   boolean generateImageFromText();

   boolean translateImageToText();

   void displayTextToConsole();

   void displayImageToConsole();
}
