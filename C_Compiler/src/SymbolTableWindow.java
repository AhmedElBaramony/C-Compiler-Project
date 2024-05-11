import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SymbolTableWindow extends JFrame {
    private JTable symbolTable;

    public SymbolTableWindow(SymbolTable symbolTableData) {
        setTitle("Symbol Table");
        setSize(400, 300);
        setLocationRelativeTo(null);

        symbolTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(symbolTable);
        add(scrollPane);

        updateSymbolTable(symbolTableData);
    }

    private void updateSymbolTable(SymbolTable symbolTableData) {
        List<Token> tokens = symbolTableData.getSymbols();
        String[] columnNames = {"Value", "Type"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Token token : tokens) {
            String value = token.getValue();
            String type = token.getType().toString();
            model.addRow(new Object[]{value, type});
        }

        symbolTable.setModel(model);
    }
}
