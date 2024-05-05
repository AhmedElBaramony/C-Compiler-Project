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
}
