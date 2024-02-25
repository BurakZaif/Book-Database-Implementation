import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CengTreeParser
{
    public static ArrayList<CengBook> parseBooksFromFile(String filename)
    {
        ArrayList<CengBook> bookList = new ArrayList<CengBook>();

        // You need to parse the input file in order to use GUI tables.
        // TODO: Parse the input file, and convert them into CengBooks
        Scanner oku = null;
        String metin;

        try {
            File text = new File(filename);
            oku = new Scanner(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (oku != null && oku.hasNextLine()) {
            metin = oku.nextLine();
            String[] mystr = metin.split("[|]");

            Integer bookID = Integer.parseInt(mystr[0]);
            String bookTitle = mystr[1];
            String author = mystr[2];
            String genre = mystr[3];

            CengBook temp = new CengBook(bookID, bookTitle, author, genre);
            bookList.add(temp);
        }


        return bookList;
    }

    public static void startParsingCommandLine() throws IOException
    {
        // TODO: Start listening and parsing command line -System.in-.
        // There are 4 commands:
        // 1) quit : End the app, gracefully. Print nothing, call nothing, just break off your command line loop.
        // 2) add : Parse and create the book, and call CengBookRunner.addBook(newlyCreatedBook).
        // 3) search : Parse the bookID, and call CengBookRunner.searchBook(bookID).
        // 4) print : Print the whole tree, call CengBookRunner.printTree().

        // Commands (quit, add, search, print) are case-insensitive.

        Scanner oku = new Scanner(System.in);
        String metin;
        String task;
        Integer bookID;

        while(true) {
            metin = oku.nextLine();
            String[] line = metin.split("[|]");
            task = line[0].toLowerCase();

            switch(task) {
                case "quit":
                    return;
                case "add":
                {
                    bookID = Integer.parseInt(line[1]);
                    String bookTitle = line[2];
                    String author = line[3];
                    String genre = line[4];
                    CengBook temp = new CengBook(bookID, bookTitle, author, genre);
                    CengBookRunner.addBook(temp);
                    break;
                }
                case "search":
                {
                    bookID = Integer.parseInt(line[1]);
                    CengBookRunner.searchBook(bookID);
                    break;
                }
                case "print":
                {
                    CengBookRunner.printTree();
                    break;
                }
            }
        }



    }
}
