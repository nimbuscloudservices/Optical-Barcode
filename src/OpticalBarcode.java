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
      /**
       * This is going to be difficult
       */
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
      if(row < 0 || row > MAX_HEIGHT || col < 0 || col > MAX_WIDTH)
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
    * @return true if row/col value is proper and sets value, false if it is
    * not proper
    */
   public boolean setPixel(int row, int col, boolean value)
   {
      if(row < 0 || row > MAX_HEIGHT || col < 0 || col > MAX_WIDTH)
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
      return data.length <= MAX_HEIGHT && data[0].length() <=
            MAX_WIDTH && data != null;
   }

   /**
    * Displays contents of imageData in "*" or " "
    *
    * @return
    */
   public void displayToConsole()
   {
      for (int i = 0; i < MAX_HEIGHT; i++)
      {
         System.out.print("|");
         for (int j = 0; j < MAX_WIDTH; j++)
         {
            if((this.imageData[i][j]))
            {
               System.out.print("*");
            }
            else
            {
               System.out.print(" ");
            }
            System.out.println("|");
         }
      }
   }

   /**
    * clones the object
    *
    * @return cloned object
    */

   public Object clone()
   {
      return null;
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
      this.text = text;
      return true;
   }

   /**
    * Sets image, calls helper methods to computer actual width/height and
    * catches cloning errors.
    *
    * @param image that is being scanned
    * @return true if successfully clones image, false if not successful for
    * no drama.
    */
   public boolean scan(BarcodeImage image)
   {
      try
      {
         this.image = (BarcodeImage) image.clone();
         this.cleanImage();
         this.actualWidth =  this.computeSignalWidth();
         this.actualHeight = this.computerSignalHeight();
         return true;
      }
      catch( Exception e)
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
      return false;
   }

   /**
    * Converts image to text
    *
    * @return true if successful, false if not.
    */
   @Override public boolean translateImageToText()
   {
      return false;
   }

   /**
    * displays trimmed text in console
    */
   @Override public void displayTextToConsole()
   {

   }

   /**
    * displays trimmed barcode image in console
    */
   @Override public void displayImageToConsole()
   {

   }

   /**
    * Displays full image data including blank top and right, useful for
    * debugging
    */
   public void displayRawImage()
   {

   }

   /**
    * sets the image to white = false
    */
   private void clearImage()
   {

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
      return 0;
   }

   /**
    * Private method that uses the left BLACK spine of image to calculate
    * actualWidth
    *
    * @return calculated actualHeight
    */
   private int computerSignalHeight()
   {
      return 0;
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

   }
   /**
    * Shifts image down
    *
    * @param offset value that image needs to shift
    */
   private void shiftImageDown(int offset)
   {

   }

   /**
    * This private method shifts image to the left
    *
    * @param offset value that image needs to shift
    */
   private void shiftImageLeft(int offset)
   {

   }

   /**
    * Helper method to decode a char from a specific column
    * @param col of dataMatrix
    * @return decoded char
    */
   private char readCharFromCol(int col)
   {
      return '.';
   }
   /**
    * Helper method to write and encode char to specific column
    * @param col of dataMatrix
    * @param code being written
    * @return true if successful
    */
   private boolean WriteCharToCol(int col, int code)
   {
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