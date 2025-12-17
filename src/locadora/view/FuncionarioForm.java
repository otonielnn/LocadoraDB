package locadora.view;

import locadora.model.Funcionario;
import locadora.service.FuncionarioService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class FuncionarioForm extends JPanel {
    private final FuncionarioService funcionarioService = new FuncionarioService();
    private JTextField cpfField;
    private JTextField nomeField;
    private JTextField enderecoField;
    private JTextField salarioField;
    private JTextField cargoField;
    private JTextField cnpjFilialField;
    private JTable tabelaFuncionarios;
    private DefaultTableModel modeloTabela;
    private String cpfEditando = null;

    public FuncionarioForm() {
        setLayout(new BorderLayout());

        JPanel formularioPanel = criarFormulario();
        JPanel listaPanel = criarLista();

        add(formularioPanel, BorderLayout.NORTH);
        add(listaPanel, BorderLayout.CENTER);

        carregarFuncionarios();
    }

    private JPanel criarFormulario() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Novo/Editar Funcionário"));
        panel.setPreferredSize(new Dimension(0, 200));

        panel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        panel.add(cpfField);

        panel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        panel.add(nomeField);

        panel.add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        panel.add(enderecoField);

        panel.add(new JLabel("Salário:"));
        salarioField = new JTextField();
        panel.add(salarioField);

        panel.add(new JLabel("Cargo:"));
        cargoField = new JTextField();
        panel.add(cargoField);

        panel.add(new JLabel("CNPJ Filial:"));
        cnpjFilialField = new JTextField();
        panel.add(cnpjFilialField);

        JPanel botoesPanel = new JPanel(new FlowLayout());
        
        JButton salvarBtn = new JButton("Salvar");
        salvarBtn.addActionListener(e -> salvarFuncionario());
        botoesPanel.add(salvarBtn);

        JButton limparBtn = new JButton("Limpar");
        limparBtn.addActionListener(e -> limparCampos());
        botoesPanel.add(limparBtn);

        JButton deletarBtn = new JButton("Deletar");
        deletarBtn.addActionListener(e -> deletarFuncionario());
        botoesPanel.add(deletarBtn);

        JButton atualizarBtn = new JButton("Atualizar Lista");
        atualizarBtn.addActionListener(e -> carregarFuncionarios());
        botoesPanel.add(atualizarBtn);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.NORTH);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return mainPanel;
    }

    private JPanel criarLista() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Funcionários Cadastrados"));

        modeloTabela = new DefaultTableModel(
            new String[]{"CPF", "Nome", "Endereço", "Salário", "Cargo", "Filial"}, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaFuncionarios = new JTable(modeloTabela);
        tabelaFuncionarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaFuncionarios.getSelectionModel().addListSelectionListener(e -> selecionarLinha());
        
        JScrollPane scrollPane = new JScrollPane(tabelaFuncionarios);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void salvarFuncionario() {
        try {
            if (cpfField.getText().isEmpty() || nomeField.getText().isEmpty() ||
                enderecoField.getText().isEmpty() || salarioField.getText().isEmpty() ||
                cargoField.getText().isEmpty() || cnpjFilialField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Funcionario funcionario = new Funcionario();
            funcionario.setCpf(cpfField.getText());
            funcionario.setNome(nomeField.getText());
            funcionario.setEndereco(enderecoField.getText());
            funcionario.setSalario(Double.parseDouble(salarioField.getText()));
            funcionario.setCargo(cargoField.getText());
            funcionario.setCnpjFilial(cnpjFilialField.getText());

            if (cpfEditando != null) {
                funcionario.setCpf(cpfEditando);
            }
            funcionarioService.salvar(funcionario);
            JOptionPane.showMessageDialog(this, cpfEditando != null ? "Funcionário atualizado com sucesso!" : "Funcionário criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            limparCampos();
            carregarFuncionarios();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro de formato no campo de salário!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar funcionário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarFuncionario() {
        if (cpfEditando == null) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário para deletar!", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este funcionário?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                funcionarioService.deletar(cpfEditando);
                JOptionPane.showMessageDialog(this, "Funcionário deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarFuncionarios();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao deletar funcionário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarFuncionarios() {
        modeloTabela.setRowCount(0);
        try {
            List<Funcionario> funcionarios = funcionarioService.listarTodos();
            for (Funcionario funcionario : funcionarios) {
                modeloTabela.addRow(new Object[]{
                    funcionario.getCpf(),
                    funcionario.getNome(),
                    funcionario.getEndereco(),
                    String.format("R$ %.2f", funcionario.getSalario()),
                    funcionario.getCargo(),
                    funcionario.getCnpjFilial()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar funcionários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selecionarLinha() {
        int selectedRow = tabelaFuncionarios.getSelectedRow();
        if (selectedRow >= 0) {
            cpfEditando = (String) modeloTabela.getValueAt(selectedRow, 0);
            try {
                Funcionario funcionario = funcionarioService.buscar(cpfEditando);
                if (funcionario != null) {
                    cpfField.setText(funcionario.getCpf());
                    nomeField.setText(funcionario.getNome());
                    enderecoField.setText(funcionario.getEndereco());
                    salarioField.setText(String.valueOf(funcionario.getSalario()));
                    cargoField.setText(funcionario.getCargo());
                    cnpjFilialField.setText(funcionario.getCnpjFilial());
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar funcionário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        cpfField.setText("");
        nomeField.setText("");
        enderecoField.setText("");
        salarioField.setText("");
        cargoField.setText("");
        cnpjFilialField.setText("");
        cpfEditando = null;
        tabelaFuncionarios.clearSelection();
    }
}
