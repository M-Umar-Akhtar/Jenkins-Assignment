

import java.util.Stack;

class TreeNode {

    String value;
    TreeNode left;
    TreeNode right;

    public TreeNode(String value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public TreeNode(String value, TreeNode left, TreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public TreeNode getLeft() {
        return this.left;
    }

    public TreeNode getRight() {
        return this.right;
    }

    public String getValue() {
        return value;
    }

    public String preOrder(TreeNode node) {
        String s = "";
        Stack<TreeNode> nodeStack = new Stack<>();
        nodeStack.push(node);
        while (nodeStack.empty() == false) {
            TreeNode current = nodeStack.peek();
            s += (current.value + ",");
            nodeStack.pop();
            if (current.right != null) {
                nodeStack.push(current.right);
            }
            if (current.left != null) {
                nodeStack.push(current.left);
            }
        }
        return s;
    }

    public boolean isLeaf() {
        if (getLeft() == null && getRight() == null) {
            return true;
        } else {
            return false;
        }
    }
}
