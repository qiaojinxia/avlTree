import java.util.ArrayList;

/**
 * Created by caomaoboy 2019-11-15
 **/

public class AVLTree<K extends Comparable<K>, V> implements Map<K,V> {

    private class Node{
        public K key;
        public V value;
        public Node left, right;
        public int height;

        public Node(K key, V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            height =1;
        }
    }

    private Node root;
    private int size;

    public AVLTree(){
        root = null;
        size = 0;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public String getmAlgorithmName() {
        return "AVLTree";
    }

    // 向二分搜索树中添加新的元素(key, value)
    public void add(K key, V value){
        root = add(root, key, value);
    }

    // 向以node为根的二分搜索树中插入元素(key, value)，递归算法
    // 返回插入新节点后二分搜索树的根
    private Node add(Node node, K key, V value){

        if(node == null){
            size ++;
            return new Node(key, value);
        }

        if(key.compareTo(node.key) < 0)
            node.left = add(node.left, key, value);
        else if(key.compareTo(node.key) > 0)
            node.right = add(node.right, key, value);
        else // key.compareTo(node.key) == 0
            node.value = value;
        //回溯时根据左右 子孩子更新节点的高度
        node.height = 1 + Math.max(getNodeHeight(node.left) ,getNodeHeight(node.right));
        int balanceFactor = getBalanceFactor(node);
//        if(Math.abs(balanceFactor)>1)
//            System.out.println("unblanced" + balanceFactor);
        // 因为是左边 减右边 所以当 平衡因子为2时 并且是左边的左边这种情况 因为 左边大于 右边高度 的话 平衡因子就大于等于0
        if(balanceFactor > 1 && getBalanceFactor(node.left)>=0)
            return rightRotate(node);
        //rr 这种情况 就是右边的数大于左边这种情况 并且插入的 节点是右边的右边
        if(balanceFactor < -1 && getBalanceFactor(node.right)<=0)
            return leftRotate(node);
        //左右旋lr
        //为什么 getBalanceFactor(node.left) <0 不加 = 因为 加了等于相当于做节点和有节点是平衡二叉树就没必要再去旋转左节点了
        if(balanceFactor > 1 && getBalanceFactor(node.left) <0) {
            //将节点下面的节点先右旋 转化为ll的情况
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        //rl 插入左边的右边
        if(balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }


        return node;
    }
    /**
     * 对节点y进行右旋操作,返回旋转后新的根节点x
     *
     *     //          y                       x
     *     //         / \                    /    \
     *     //        x   T4     右旋(y)      z      y
     *     //       / \       ========>    /  \    / \
     *     //      z   T3                 T1   T2 T3  T4
     *     //     / \
     *     //    T1  T2
     * @param y 传入的是要更新的平衡因子不对的node 也就是↑y
     * @return
     */
    private Node rightRotate(Node y){
        //取出元素x
        Node x = y.left;
        //取出变量T3
        Node T3 = x.right;
        //将x 的有节点设置为 y
        x.right = y;
        //将y的左节点设置为 T3
        y.left = T3;
        y.height = 1 + Math.max(getNodeHeight(y.left) ,getNodeHeight(y.right));
        x.height = 1 + Math.max(getNodeHeight(x.left),getNodeHeight(x.right));
        return x;

    }


    public boolean isBalanced(){
        return isBalanced(root);
    }

    // 判断以Node为根的二叉树是否是一棵平衡二叉树，递归算法
    private boolean isBalanced(Node node){
        if(node == null)
            return true;

        int balanceFactor = getBalanceFactor(node);
        if(Math.abs(balanceFactor) > 1)
            return false;
        return isBalanced(node.left) && isBalanced(node.right);
    }

    /**
     * 对节点y进行右旋操作,返回旋转后新的根节点x
     *
     *     //          y                        x
     *     //         /  \                     /   \
     *     //        T4   x     左旋(y)       z    y
     *     //            / \    ======>     /  \ /  \
     *     //           T3  z             T1   T2 T3  T4
     *     //              / \
     *     //            T1  T2
     * @param y 传入的是要更新的平衡因子不对的node 也就是↑y
     * @return
     */
    private Node leftRotate(Node y){
        //拿出x
        Node x = y.right;
        //将x的做节点 拿出
        Node T3 = x.left;
        //x的左节点 设置为y
        x.left = y;
        //将y的 右节点设置为 T3
        y.right = T3;
        //更新左右的高度
        y.height = 1 + Math.max(getNodeHeight(y.left),getNodeHeight(y.right));
        x.height = 1 + Math.max(getNodeHeight(x.left),getNodeHeight(x.right));
        return x;
    }

    private int getNodeHeight(Node node){
        if(node ==null)
            return 0;
        else
            return node.height;
    }
    private int getBalanceFactor(Node node){
        if(node == null)
            return 0;
        //左边减右边 计算平衡因子
        return getNodeHeight(node.left) - getNodeHeight(node.right);
    }

    // 返回以node为根节点的二分搜索树中，key所在的节点
    private Node getNode(Node node, K key){

        if(node == null)
            return null;

        if(key.equals(node.key))
            return node;
        else if(key.compareTo(node.key) < 0)
            return getNode(node.left, key);
        else // if(key.compareTo(node.key) > 0)
            return getNode(node.right, key);
    }

    public boolean contains(K key){
        return getNode(root, key) != null;
    }

    public V get(K key){

        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue){
        Node node = getNode(root, key);
        if(node == null)
            throw new IllegalArgumentException(key + " doesn't exist!");

        node.value = newValue;
    }

    // 返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node){
        if(node.left == null)
            return node;
        return minimum(node.left);
    }


    // 从AVL二分搜索树中删除键为key的节点
    public V remove(K key){
        Node node = getNode(root, key);
        if(node != null){
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    public boolean isBST(){
        ArrayList<K> keys =new ArrayList<K>();
        //中序遍历
        intorder(root,keys);
        for(int i=1;i<keys.size();i++)
            if(keys.get(i-1).compareTo(keys.get(i))>0)
                return false;
        return true;
    }

    private void intorder(Node node, ArrayList<K> keys) {
        if(node ==null)
            return;
        intorder(node.left,keys);
        keys.add(node.key);
        intorder(node.right,keys);
    }

    private Node remove(Node node, K key){
        Node retNode;
        if( node == null )
            return null;
        if( key.compareTo(node.key) < 0 ){
            node.left = remove(node.left , key);
            retNode = node;
        }
        else if(key.compareTo(node.key) > 0 ){
            node.right = remove(node.right, key);
            retNode = node;
        }
        else{   // key.compareTo(node.key) == 0

            // 待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                retNode = rightNode;
            }else  if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                retNode = leftNode;
            }else{
                // 待删除节点左右子树均不为空的情况

                // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
                // 用这个节点顶替待删除节点的位置
                Node successor = minimum(node.right);
                successor.right = remove(node.right,successor.key);
                successor.left = node.left;
                node.left = node.right = null;

                retNode=  successor;
            }
        }
        if(retNode == null)
            return null;

        // 更新height
        retNode.height = 1 + Math.max(getNodeHeight(retNode.left), getNodeHeight(retNode.right));

        // 计算平衡因子
        int balanceFactor = getBalanceFactor(retNode);

        // 平衡维护
        // LL
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) >= 0)
            return rightRotate(retNode);

        // RR
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) <= 0)
            return leftRotate(retNode);

        // LR
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) < 0) {
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }

        // RL
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) > 0) {
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }

        return retNode;

    }
    public static void main(String[] args){

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            AVLTree<String, Integer> map = new AVLTree<>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));

            System.out.println("is BST : " + map.isBST());
            System.out.println("is Balanced : " + map.isBalanced());

            for(String word: words){
                map.remove(word);
                if(!map.isBST() || !map.isBalanced())
                    throw new RuntimeException();
            }
        }

        System.out.println();
    }
}
