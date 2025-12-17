package locadora.view;

import locadora.model.Cliente;
import locadora.service.ClienteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClienteForm extends JPanel {
    private final ClienteService clienteService = new ClienteService();
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField cnhField;
    private JTextField dataNascimentoField;
    private JSpinner avaliacaoSpinner;
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;
    private String cpfEditando = null;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ClienteForm() {
        setLayout(new BorderLayout());

        // Painel de formulário
        JPanel formularioPanel = criarFormulario();
        
        // Painel de lista
        JPanel listaPanel = criarLista();

        add(formularioPanel, BorderLayout.NORTH);
        add(listaPanel, BorderLayout.CENTER);

        carregarClientes();
    }

    private JPanel criarFormulario() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Novo/Editar Cliente"));
        panel.setPreferredSize(new Dimension(0, 200));

        panel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        panel.add(cpfField);

        panel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        panel.add(nomeField);

        panel.add(new JLabel("Data de Nascimento (dd/MM/yyyy):"));
        dataNascimentoField = new JTextField();
        panel.add(dataNascimentoField);

        panel.add(new JLabel("CNH:"));
        cnhField = new JTextField();
        panel.add(cnhField);

        panel.add(new JLabel("Avaliação:"));
        avaliacaoSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 5.0, 0.1));
        panel.add(avaliacaoSpinner);

        // Painel de botões
        JPanel botoesPanel = new JPanel(new FlowLayout());
        
        JButton salvarBtn = new JButton("Salvar");
        salvarBtn.addActionListener(e -> salvarCliente());
        botoesPanel.add(salvarBtn);

        JButton limparBtn = new JButton("Limpar");
        limparBtn.addActionListener(e -> limparCampos());
        botoesPanel.add(limparBtn);

        JButton atualizarBtn = new JButton("Atualizar Lista");
        atualizarBtn.addActionListener(e -> carregarClientes());
        botoesPanel.add(atualizarBtn);

        JButton deletarBtn = new JButton("Deletar");
        deletarBtn.addActionListener(e -> deletarCliente());
        botoesPanel.add(deletarBtn);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.NORTH);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return mainPanel;
    }

    private JPanel criarLista() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Clientes Cadastrados"));

        // Criar modelo da tabela
        modeloTabela = new DefaultTableModel(
            new String[]{"CPF", "Nome", "Data Nascimento", "CNH", "Avaliação"}, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável
            }
        };

        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.getSelectionModel().addListSelectionListener(e -> selecionarLinha());
        
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void salvarCliente() {
        try {
            if (!validarCampos()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            String cpf = cpfField.getText().trim();
            String nome = nomeField.getText().trim();
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoField.getText().trim(), formatter);
            String cnh = cnhField.getText().trim();
            Double avaliacao = (Double) avaliacaoSpinner.getValue();

            Cliente cliente = new Cliente(cpf, nome, dataNascimento, cnh, avaliacao);

            if (clienteService.validarCliente(cliente)) {
                clienteService.salvar(cliente);
                JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
                limparCampos();
                carregarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Dados do cliente inválidos!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cliente: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao processar dados: " + ex.getMessage());
        }
    }

    private void deletarCliente() {
        try {
            String cpf = cpfField.getText().trim();
            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para deletar!");
                return;
            }
            clienteService.deletar(cpf);
            JOptionPane.showMessageDialog(this, "Cliente deletado com sucesso!");
            limparCampos();
            carregarClientes();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar cliente: " + ex.getMessage());
        }
    }

    private void carregarClientes() {
        try {
            modeloTabela.setRowCount(0); // Limpar tabela
            List<Cliente> clientes = clienteService.listarTodos();
            
            for (Cliente cliente : clientes) {
                modeloTabela.addRow(new Object[]{
                    cliente.getCpf(),
                    cliente.getNome(),
                    cliente.getDataNascimento(),
                    cliente.getCnh(),
                    cliente.getAvaliacao()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage());
        }
    }
    
    private void selecionarLinha() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada >= 0) {
            cpfField.setText((String) modeloTabela.getValueAt(linhaSelecionada, 0));
            nomeField.setText((String) modeloTabela.getValueAt(linhaSelecionada, 1));
            dataNascimentoField.setText(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
            cnhField.setText((String) modeloTabela.getValueAt(linhaSelecionada, 3));
            avaliacaoSpinner.setValue((Double) modeloTabela.getValueAt(linhaSelecionada, 4));
            cpfEditando = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
        }
    }

    private void limparCampos() {
        cpfField.setText("");
        nomeField.setText("");
        dataNascimentoField.setText("");
        cnhField.setText("");
        avaliacaoSpinner.setValue(0.0);
        cpfEditando = null;
    }

    private boolean validarCampos() {
        return !cpfField.getText().trim().isEmpty() &&
               !nomeField.getText().trim().isEmpty() &&
               !cnhField.getText().trim().isEmpty() &&
               !dataNascimentoField.getText().trim().isEmpty();
    }
}
