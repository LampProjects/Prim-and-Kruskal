import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Reads a file find the shortest paths
 *
 * @author Patrick Lam
 * @version 08/17/2023
 */
public class Lab07CostAdjacencyMatrixPXL
{
    final static boolean DEBUG = true;
    static String header = "";
    static String data = "";
    static int numRows = 0, numCols = 0, maxwidth = 0;
    static String[] values = null;
    static List<String[]> matrixdata = new ArrayList<>();
    static int[][] matrix = null;
    static String[] strSplit; // Ends up having the names of the airports
    static ArrayList<String> strList;

    public static void main(String[] args)
    {
        Lab07CostAdjacencyMatrixPXL lb = new Lab07CostAdjacencyMatrixPXL();
        String sChoice, hed;
        String[] names = null;
        int iChoice;
        int[][] graph = null;
        boolean file = false;   // if there is a file or not
        MCSTKruskals krusk;
        MCSTPrim prim;
        Scanner scan = new Scanner(System.in);
        header();

        do{
            menu();
            sChoice = scan.nextLine();
            iChoice = Integer.parseInt(sChoice);

            switch(iChoice)
            {
                // populate the matrix
                case(1):
                    // Read the file in the directory
                    System.out.println("Enter the name of the file: "); //Should be Airports.txt
                    sChoice = scan.nextLine();
                    try {
                        graph = readFile(sChoice);
                        names = heads(sChoice);
                        System.out.println("File Entered Successfully");
                        file = true;
                    } catch (IOException e)
                    {
                        System.out.println("ERROR: File not found");
                    }
                    break;
                case(2):
                    // If it is not populated yet ie no input
                    if (!file)
                    {
                        System.out.println("Matrix not populated");
                        break;
                    }
                    printMatrix();
                    break;
                case(3):
                    //Find using the prim code translate it
                    if (!file)
                    {
                        System.out.println("Error there is no file");
                        break;
                    }
                    // grab from geeks for geeks code
                    prim = new MCSTPrim(graph, strSplit);
                    break;
                case(4):
                    // Find using the kruskal code
                    if(!file)
                    {
                        System.out.println("Error there is no file");
                        break;
                    }
                    //tax evasion
                    krusk = new MCSTKruskals(graph, strSplit);
                    System.out.println("Pizza Pizza"); // THis code is put in so that the compiler does not scream at me
                    break;
                case(5):
                    //Quit
                    System.out.println("Are you sure you want to exit? Enter Y for yes and N for no: ");
                    char cData = scan.nextLine().toUpperCase().charAt(0);
                    if(cData == 'Y')
                    {
                        System.out.println("Quitting");
                        scan.close();
                        System.exit(0);
                        break;
                    }
                    break;
                default:
                    System.out.println("Please input a valid choice from the menu");
                    break;
            }
        } while (true);
    } // End of PSVM

    public static void header()
    {
        System.out.println("Welcome to the Cost Adjacency Matrix Map Program");
        System.out.println("Author: Patrick Lam");

    }

    public static void menu()
    {
        System.out.println("Select an Option: \n");
        System.out.println("1. Populate the matrix");
        System.out.println("2. Print the matrix");
        System.out.println("3. Find the MCST via Prim");
        System.out.println("4. Find the MCST via Kruskal");
        System.out.println("5. Quit");
    }

    public static int[][] readFile(String file) throws IOException
    {
        FileReader fileread = new FileReader(file);


        BufferedReader buffread = new BufferedReader(fileread);
        if (buffread.ready())
        {
            header = buffread.readLine();
        }

        while (buffread.ready())
        {
            data = buffread.readLine();
            values = data.trim().split(",\\s+");
            matrixdata.add(values);
        }

        numRows = matrixdata.size();
        numCols = matrixdata.get(0).length;
        matrix = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++)
        {
            for(int j = 0; j < numCols; j++)
            {
                if (matrixdata.get(i)[j].trim().equals("-1"))
                {
                    matrix[i][j] = Integer.MAX_VALUE;
                }
                else
                {
                    matrix[i][j] = Integer.parseInt(matrixdata.get(i)[j]);
                }
            }
        }

        fileread.close();


        return matrix;
    }

    public static String[] heads(String fileName) throws IOException
    {
        BufferedReader buffread = new BufferedReader(new FileReader(fileName));

        if (buffread.ready())
        {
            header = buffread.readLine();
            values = header.split(",");
            matrixdata.add(values);
        }
        strSplit = header.split(", "); // splits in comma
        strList = new ArrayList<>(Arrays.asList(strSplit));
        for (int i = 0; i < strList.size(); i++)
        {
            if(maxwidth < strList.get(i).length())
            {
                maxwidth = strList.get(i).length();
            }
        }
        return strSplit;
    }

    public int[][] getMatrix()
    {
        return matrix;
    }

    public String[] getHeader()
    {
        return strSplit;
    }

    public static void printMatrix()
    {

        System.out.printf("%-" + (maxwidth) + "s", "");
        maxwidth = maxwidth + 5;
        for(String vertex : strSplit)
        {
            System.out.printf("%" + (maxwidth + 1) + "s", vertex);
        }
        System.out.println();

        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("%s", strSplit[i]);
            for (int j = 0; j < matrix[i].length; j++)
            {
                System.out.printf("%" + (maxwidth + 1) + "d", matrix[i][j]);

            }
            System.out.println();
        }
    }

}
