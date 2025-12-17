package locadora.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("Sistema de Locadora de Veículos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true);

        tabbedPane = new JTabbedPane();
        
        // Adicionar abas
        tabbedPane.addTab("Clientes", new ClienteForm());
        tabbedPane.addTab("Veículos", new VeiculoForm());
        tabbedPane.addTab("Locações", new LocacaoForm());
        
        add(tabbedPane);

        // Menu bar
        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu arquivoMenu = new JMenu("Arquivo");
        JMenuItem sairItem = new JMenuItem("Sair");
        sairItem.addActionListener(e -> System.exit(0));
        arquivoMenu.add(sairItem);

        JMenu ajudaMenu = new JMenu("Ajuda");
        JMenuItem sobreItem = new JMenuItem("Sobre");
        sobreItem.addActionListener(e -> mostrarSobre());
        ajudaMenu.add(sobreItem);

        menuBar.add(arquivoMenu);
        menuBar.add(ajudaMenu);

        setJMenuBar(menuBar);
    }

    private void mostrarSobre() {
        JOptionPane.showMessageDialog(this, 
            "Sistema de Locadora de Veículos\nVersão 1.0\n© 2025", 
            "Sobre", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
