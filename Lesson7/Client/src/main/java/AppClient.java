import javax.swing.*;

public class AppClient {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientUI::new);
    }
}
