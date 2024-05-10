import java.util.ArrayList;
import java.util.List;

public class TreeNode
{
    private Token token;
    private TreeNode parent;
    private List<TreeNode> children;

    public TreeNode(Token token) {
        this.token = token;
        this.children = new ArrayList<>();
    }

    public Token getToken() {
        return token;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void addChild(TreeNode child) {
        child.setParent(this);
        children.add(child);
    }

    public List<TreeNode> getChildren() {
        return children;
    }
    public void printTree() {
        printTreeRecursive(this, 0);
    }

    private void printTreeRecursive(TreeNode node, int depth) {
        // Print the node's data with indentation based on its depth in the tree
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.println(node.getToken().getValue());

        // Recursively print the children of the current node
        for (TreeNode child : node.getChildren()) {
            printTreeRecursive(child, depth + 1);
        }
    }
}


