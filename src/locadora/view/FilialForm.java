package locadora.view;

import locadora.model.Filial;
import locadora.service.FilialService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class FilialForm extends JPanel {
    private final FilialService filialService = new FilialService();
    private JTextField cnpjField;
    private JTextField nomeField;
    private JTextField enderecoField;
    private JTextField telefoneField;
    private JTable tabelaFiliais;
    private DefaultTableModel modeloTabela;
    private String cnpjEditando = null;

    public FilialForm() {
        setLayout(new BorderLayout());

        JPanel formularioPanel = criarFormulario();
        JPanel listaPanel = criarLista();

        add(formularioPanel, BorderLayout.NORTH);
        add(listaPanel, BorderLayout.CENTER);

        carregarFiliais();
    }

    private JPanel criarFormulario() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Nova/Editar Filial"));
        panel.setPreferredSize(new Dimension(0, 150));

        panel.add(new JLabel("CNPJ:"));
        cnpjField = new JTextField();
        panel.add(cnpjField);

        panel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        panel.add(nomeField);

        panel.add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        panel.add(enderecoField);

        panel.add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        panel.add(telefoneField);

        JPanel botoesPanel = new JPanel(new FlowLayout());
        
        JButton salvarBtn = new JButton("Salvar");
        salvarBtn.addActionListener(e -> salvarFilial());
        botoesPanel.add(salvarBtn);

        JButton limparBtn = new JButton("Limpar");
        limparBtn.addActionListener(e -> limparCampos());
        botoesPanel.add(limparBtn);

        JButton deletarBtn = new JButton("Deletar");
        deletarBtn.addActionListener(e -> deletarFilial());
        botoesPanel.add(deletarBtn);

        JButton atualizarBtn = new JButton("Atualizar Lista");
        atualizarBtn.addActionListener(e -> carregarFiliais());
        botoesPanel.add(atualizarBtn);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.NORTH);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return mainPanel;
    }

    private JPanel criarLista() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Filiais Cadastradas"));

        modeloTabela = new DefaultTableModel(
            new String[]{"CNPJ", "Nome", "Endereço", "Telefone"}, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaFiliais = new JTable(modeloTabela);
        tabelaFiliais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaFiliais.getSelectionModel().addListSelectionListener(e -> selecionarLinha());
        
        JScrollPane scrollPane = new JScrollPane(tabelaFiliais);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void salvarFilial() {
        try {
            if (cnpjField.getText().isEmpty() || nomeField.getText().isEmpty() ||
                enderecoField.getText().isEmpty() || telefoneField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Filial filial = new Filial();
            filial.setCnpj(cnpjField.getText());
            filial.setNome(nomeField.getText());
            filial.setEndereco(enderecoField.getText());
            filial.setTelefone(telefoneField.getText());

            if (cnpjEditando != null) {
                filial.setCnpj(cnpjEditando);
            }
            filialService.salvar(filial);
            JOptionPane.showMessageDialog(this, cnpjEditando != null ? "Filial atualizada com sucesso!" : "Filial criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            limparCampos();
            carregarFiliais();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar filial: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarFilial() {
        if (cnpjEditando == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma filial para deletar!", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar esta filial?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                filialService.deletar(cnpjEditando);
                JOptionPane.showMessageDialog(this, "Filial deletada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarFiliais();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao deletar filial: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarFiliais() {
        modeloTabela.setRowCount(0);
        try {
            List<Filial> filiais = filialService.listarTodas();
            for (Filial filial : filiais) {
                modeloTabela.addRow(new Object[]{
                    filial.getCnpj(),
                    filial.getNome(),
                    filial.getEndereco(),
                    filial.getTelefone()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar filiais: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selecionarLinha() {
        int selectedRow = tabelaFiliais.getSelectedRow();
        if (selectedRow >= 0) {
            cnpjEditando = (String) modeloTabela.getValueAt(selectedRow, 0);
            try {
                Filial filial = filialService.buscar(cnpjEditando);
                if (filial != null) {
                    cnpjField.setText(filial.getCnpj());
                    nomeField.setText(filial.getNome());
                    enderecoField.setText(filial.getEndereco());
                    telefoneField.setText(filial.getTelefone());
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar filial: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        cnpjField.setText("");
        nomeField.setText("");
        enderecoField.setText("");
        telefoneField.setText("");
        cnpjEditando = null;
        tabelaFiliais.clearSelection();
    }
}
