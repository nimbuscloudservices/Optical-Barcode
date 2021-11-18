interface BarcodeIO
{
   boolean scan(BarcodeImage bc);

   boolean readText(String text);

   boolean generateImageFromText();

   boolean translateImageToText();

   void displayTextToConsole();

   void displayImageToConsole();
}

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
    *
    */
   public BarcodeImage()
   {
      this.imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
   }

   /**
    * Constructs
    *
    * @param strData
    */
   public BarcodeImage(String[] strData)
   {

   }

   boolean getPixel(int row, int col)
   {
      return false;
   }

   boolean setPixel(int row, int col, boolean value)
   {
      return false;
   }

   private int checkSize(String[] data)
   {
      return 0;
   }

   public String displayToConsole()
   {
      return null;
   }

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
    * @param image of
    */
   public DataMatrix(BarcodeImage image)
   {

   }

   /**
    * Constructor that sets the text but leaves the image at its default value.
    *
    * @param text
    */
   public DataMatrix(String text)
   {

   }

   /**
    * A mutator for text
    *
    * @param text
    * @return
    */
   public boolean readText(String text)
   {
      return false;
   }

   /**
    * Generates image from text
    *
    * @return true if succesful, false if not
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
    * Sets image, calls helper methods to computer actual width/height and
    * catches cloning errors.
    *
    * @param image
    * @return true if successfully clones image, false if not successful
    */
   public boolean scan(BarcodeImage image)
   {
      return false;
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

   }

   /**
    * Aligns image to lower left.
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

}