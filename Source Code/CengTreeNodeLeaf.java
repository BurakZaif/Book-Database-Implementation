import java.util.ArrayList;

public class CengTreeNodeLeaf extends CengTreeNode
{
    private ArrayList<CengBook> books;

    // TODO: Any extra attributes

    public CengTreeNodeLeaf(CengTreeNode parent)
    {
        super(parent);

        books = new ArrayList<CengBook>();
        this.type = CengNodeType.Leaf;
        // TODO: Extra initializations
    }

    // GUI Methods - Do not modify
    public int bookCount()
    {
        return books.size();
    }
    public Integer bookKeyAtIndex(Integer index)
    {
        if(index >= this.bookCount()) {
            return -1;
        } else {
            CengBook book = this.books.get(index);

            return book.getBookID();
        }
    }

    // Extra Functions

    public void newAdd(Integer index, CengBook iBook){
        books.add(index, iBook);
    }

    public void newAddSpecial(CengBook iBook){
        books.add(iBook);
    }

    public CengBook getBook(Integer index){
        return books.get(index);
    }

    public void deleteBook(int index){
        books.remove(index);
    }


}
