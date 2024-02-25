import java.util.ArrayList;

public class CengTree {
    public CengTreeNode root;

    // Any extra attributes...

    public CengTree(Integer order) {
        CengTreeNode.order = order;

        root = new CengTreeNodeLeaf(null);
        // TODO: Initialize the class

    }

    public void addBook(CengBook book) {
        // TODO: Insert Book to Tree

        CengTreeNodeLeaf newNode = ((CengTreeNodeLeaf) findLoc(book));

        InsertNode(newNode, book);

    }

    public ArrayList<CengTreeNode> searchBook(Integer bookID) {

        // TODO: Search within whole Tree, return visited nodes.
        // Return null if not found.
        ArrayList<CengTreeNode> bag = new ArrayList<CengTreeNode>();
        CengTreeNode temp = root;
        int mylevel = 0;

        boolean bool = recursive_find(temp, bookID, bag);
        if (bool == false) {
            System.out.println("Could not find " + bookID + ".");
            return null;
        } else {
            search_tree(mylevel, bookID, bag);
        }

        return bag;
    }

    public void printTree() {
        // TODO: Print the whole tree to console
        CengTreeNode temp = root;
        int mylevel = 0;
        print_tree(root, mylevel);

    }

    // Any extra functions...

    private CengTreeNode findLoc(CengBook curBook) {

        CengTreeNode temp = root;

        if (temp.getType() != CengNodeType.Leaf) {
            temp = findHome(temp, curBook);
        }
        return temp;
    }

    public CengTreeNode findHome(CengTreeNode temp, CengBook curBook) {

        if (temp.getType() == CengNodeType.Leaf || temp == null) {
            return temp;
        } else {
            int size = ((CengTreeNodeInternal) temp).keyCount();
            int flag = 0;

            for (int i = 0; i < size; i++) {
                int value = ((CengTreeNodeInternal) temp).keyAtIndex(i);

                if (curBook.getBookID() < value) {
                    CengTreeNode next = ((CengTreeNodeInternal) temp).ChildatIndex(i);
                    flag = 1;
                    if (next.getType() == CengNodeType.Leaf) {
                        return next;
                    } else {
                        return findHome(next, curBook);
                    }
                }
            }
            if (flag == 0) {
                CengTreeNode next = ((CengTreeNodeInternal) temp).ChildatIndex(size);
                if (next.getType() == CengNodeType.Leaf) {
                    return next;
                } else {
                    return findHome(next, curBook);
                }
            }
        }
        return null;
    }

    public void InsertNode(CengTreeNodeLeaf newNode, CengBook iBook) {

        int size = newNode.bookCount();
        int id = iBook.getBookID();
        int flag = 0;

        for (int i = 0; i < size; i++) {
            if (id < newNode.bookKeyAtIndex(i)) {
                newNode.newAdd(i, iBook);
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            newNode.newAddSpecial(iBook);
        }
        size = newNode.bookCount();

        if (size > newNode.order * 2) {
            select(newNode, iBook);
        }

    }

    public void select(CengTreeNode victim, CengBook iBook) {

        if (victim.getType() == CengNodeType.Leaf) {

            split_leaf((CengTreeNodeLeaf) victim, iBook);
        } else split_inter((CengTreeNodeInternal) victim);
    }

    public void split_leaf(CengTreeNodeLeaf victim, CengBook iBook) {

        CengTreeNodeLeaf born = new CengTreeNodeLeaf(null);

        int size = victim.bookCount();
        int d = CengTreeNode.order;

        for (int i = d; i < size; i++) {
            CengBook temp = victim.getBook(i);
            born.newAddSpecial(temp);
        }

        for (int i = d; i < size; i++) {
            victim.deleteBook(d);
        }

        if (victim.getParent() == null) {
            CengBook tempBook = born.getBook(0);
            CengTreeNodeInternal created = new CengTreeNodeInternal(null);
            created.addKey(0, tempBook.getBookID());
            created.setChild(victim, 0);
            created.setChild(born, 1);
            victim.setParent(created);
            born.setParent(created);
            root = created;
        } else {
            CengTreeNodeInternal temp2 = (CengTreeNodeInternal) victim.getParent();
            copyUp(born.bookKeyAtIndex(0), temp2, victim, born);
        }

    }

    public void copyUp(Integer key, CengTreeNodeInternal baba, CengTreeNode left, CengTreeNode right) {

        int size = baba.keyCount();
        int flag = 0;

        baba.findplace(key, left, right);
        left.setParent(baba);
        right.setParent(baba);
        if (baba.keyCount() > CengTreeNode.order * 2) {
            split_inter(baba);
        }

    }

    public void split_inter(CengTreeNodeInternal inter) {

        int size = inter.keyCount();
        int d = CengTreeNode.order;

        int pushValue = inter.keyAtIndex(d);

        CengTreeNodeInternal left = new CengTreeNodeInternal(null);

        for (int i = 0; i < d; i++) {

            int key = inter.keyAtIndex(i);
            left.addKey(i, key);

            CengTreeNode temp = inter.ChildatIndex(i);
            left.setChild(temp, i);
            temp.setParent(left);

        }

        CengTreeNode orta = inter.ChildatIndex(d);
        left.setChild(orta, d);
        orta.setParent(left);

        CengTreeNodeInternal right = new CengTreeNodeInternal(null);

        for (int i = d + 1; i < size; i++) {

            int key = inter.keyAtIndex(i);
            right.addKey(i - d - 1, key);

            CengTreeNode temp = inter.ChildatIndex(i);
            right.setChild(temp, i - d - 1);
            temp.setParent(right);

        }

        CengTreeNode son = inter.ChildatIndex(size);
        right.setChild(son, size - d - 1);
        son.setParent(right);

        if (inter.getParent() == null) {
            CengTreeNodeInternal newRoot = new CengTreeNodeInternal(null);
            left.setParent(newRoot);
            right.setParent(newRoot);
            newRoot.setChild(left, 0);
            newRoot.setChild(right, 1);
            newRoot.addKey(0, pushValue);
            root = newRoot;
        } else {
            pushUp(pushValue, (CengTreeNodeInternal) inter.getParent(), left, right);
        }

        /*if(inter.getParent() == null){
            CengTreeNodeInternal top = new CengTreeNodeInternal(null);
            top.findplace(pushValue, inter, second);
            inter.setParent(top);
            second.setParent(top);
            root = top;
        }
        else{
            CengTreeNodeInternal temp2 = (CengTreeNodeInternal) inter.getParent();
            pushUp(pushValue, temp2);
        }*/

    }

    public void pushUp(Integer key, CengTreeNodeInternal baba, CengTreeNodeInternal left, CengTreeNodeInternal right) {

        int size = baba.keyCount();
        int flag = 0;
        left.setParent(baba);
        right.setParent(baba);

        for (int i = 0; i < size; i++) {
            if (key < baba.keyAtIndex(i)) {
                baba.addKey(i, key);
                flag = 1;
                baba.changeChild(i, left);
                baba.setChild(right, i + 1);
                break;
            }
        }
        if (flag == 0) {
            baba.addKey(size, key);
            baba.changeChild(size, left);
            baba.setChild(right, size + 1);
        }
        if (baba.keyCount() > CengTreeNode.order * 2) {
            split_inter(baba);
        }

    }

    public void print_tree(CengTreeNode begin, int mylevel) {

        String s = "";
        for (int i = 0; i < mylevel; i++) {
            s = s + "\t";
        }
        if (begin.getType() == CengNodeType.Leaf) {
            print_node(begin, mylevel);
        } else {
            ArrayList<CengTreeNode> cocuk = ((CengTreeNodeInternal) begin).getAllChildren();

            System.out.println(s + "<index>");

            for (int j = 0; j < ((CengTreeNodeInternal) begin).keyCount(); j++) {
                System.out.println(s + ((CengTreeNodeInternal) begin).keyAtIndex(j));
            }
            System.out.println(s + "</index>");
            mylevel++;

            int size = cocuk.size();
            for (int i = 0; i < size; i++) {
                print_tree(cocuk.get(i), mylevel);
            }
        }
    }

    public void print_node(CengTreeNode begin, int mylevel) {
        String s = "";
        for (int i = 0; i < mylevel; i++) {
            s = s + "\t";
        }
        System.out.println(s + "<data>");
        int size = ((CengTreeNodeLeaf) begin).bookCount();
        for (int i = 0; i < size; i++) {
            CengBook temp = ((CengTreeNodeLeaf) begin).getBook(i);

            System.out.print(s + "<record>");
            System.out.print(temp.getBookID());
            System.out.print("|");
            System.out.print(temp.getBookTitle());
            System.out.print("|");
            System.out.print(temp.getAuthor());
            System.out.print("|");
            System.out.print(temp.getGenre());
            System.out.print("</record>");
            System.out.print("\n");
        }
        System.out.println(s + "</data>");
    }

    public void search_tree(int mylevel, int id, ArrayList<CengTreeNode> bag) {
        String s = "";
        for (int i = 0; i < bag.size(); i++) {

            CengTreeNode naber = bag.get(i);
            if (naber.getType() == CengNodeType.Leaf) {
                for (int j = 0; j < ((CengTreeNodeLeaf) naber).bookCount(); j++) {

                    CengBook arr = ((CengTreeNodeLeaf) naber).getBook(j);


                    if (arr.getBookID() == id) {
                        System.out.print(s + "<record>");
                        System.out.print(arr.getBookID());
                        System.out.print("|");
                        System.out.print(arr.getBookTitle());
                        System.out.print("|");
                        System.out.print(arr.getAuthor());
                        System.out.print("|");
                        System.out.print(arr.getGenre());
                        System.out.print("</record>");
                        System.out.print("\n");
                        break;
                    }
                }
            } else {
                System.out.println(s + "<index>");

                for (int j = 0; j < ((CengTreeNodeInternal) naber).keyCount(); j++) {
                    System.out.println(s + ((CengTreeNodeInternal) naber).keyAtIndex(j));
                }
                System.out.println(s + "</index>");
            }
            s = s + "\t";
        }

    }

    public boolean recursive_find(CengTreeNode temp, int id, ArrayList<CengTreeNode> bag) {
        bag.add(temp);
        if (temp == null) {
            return false;
        }
        if (temp.getType() == CengNodeType.Leaf) {
            for (int i = 0; i < ((CengTreeNodeLeaf) temp).bookCount(); i++) {
                CengBook arr = ((CengTreeNodeLeaf) temp).getBook(i);
                if (arr.getBookID() == id) {
                    return true;
                }
            }
        }
        else {
            CengTreeNode lazım = ((CengTreeNodeInternal)temp).findChild(id);
            return recursive_find(lazım, id, bag);

        }
        return false;
    }

}
