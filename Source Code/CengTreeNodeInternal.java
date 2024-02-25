import java.util.ArrayList;

public class CengTreeNodeInternal extends CengTreeNode
{
    private ArrayList<Integer> keys;
    private ArrayList<CengTreeNode> children;

    public CengTreeNodeInternal(CengTreeNode parent)
    {
        super(parent);

        keys = new ArrayList<Integer>();
        children = new ArrayList<CengTreeNode>();
        this.type = CengNodeType.Internal;

        // TODO: Extra initializations, if necessary.
    }

    // GUI Methods - Do not modify
    public ArrayList<CengTreeNode> getAllChildren()
    {
        return this.children;
    }
    public Integer keyCount()
    {
        return this.keys.size();
    }
    public Integer keyAtIndex(Integer index)
    {
        if(index >= this.keyCount() || index < 0)
        {
            return -1;
        }
        else
        {
            return this.keys.get(index);
        }
    }

    // Extra Functions

    public CengTreeNode ChildatIndex(Integer index){
        return children.get(index);
    };

    public void addKey(Integer index, Integer key){
        keys.add(index, key);
    }

    public void deleteKey(int index){
        keys.remove(index);
    }

    public void deleteChild(int index){
        children.remove(index);
    }


    public void setChild(CengTreeNode node, Integer index){
        children.add(index, node);

    }

    public void changeChild( Integer index, CengTreeNodeInternal node){
        children.set(index, node);
    }

    public CengTreeNode findChild(int id){
        int size = this.keyCount();
        int flag = 0;
        for (int i = 0; i < size; i++) {
            if (id < this.keyAtIndex(i)) {
                flag = 1;
                CengTreeNode lazım = this.ChildatIndex(i);
                return lazım;
            }
        }
        if (flag == 0) {
            CengTreeNode lazım = this.ChildatIndex(size);
            return lazım;
        }
        return null;
    }

    public void findplace(Integer key, CengTreeNode left, CengTreeNode right){
        int size = this.keyCount();
        int flag = 0;

        for(int i=0; i<size; i++){
            if(key<this.keyAtIndex(i)){
                flag = 1;
                keys.add(i, key);
                children.add(i+1, right);
                break;
            }
        }
        if(flag == 0){
            keys.add(size, key);
            children.add(size+1, right);
        }
    }

}
