package locadora.view;

import locadora.model.Locacao;
import locadora.service.LocacaoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LocacaoForm extends JPanel {
    private final LocacaoService locacaoService = new LocacaoService();
    private JTextField cpfClienteField;
    private JTextField placaVeiculoField;
    private JTextField cnpjFilialRetiradaField;
    private JTextField cnpjFilialRetornoField;
    private JTextField dataInicialField;
    private JTextField dataFinalField;
    private JTextField combustInicialField;
    private JTextField combustFinalField;
    private JTextField kmPercorridoField;
    private JTable tabelaLocacoes;
    private DefaultTableModel modeloTabela;
    private Integer idLocacaoEditando = null;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public LocacaoForm() {
        setLayout(new BorderLayout());

        JPanel formularioPanel = criarFormulario();
        JPanel listaPanel = criarLista();

        add(formularioPanel, BorderLayout.NORTH);
        add(listaPanel, BorderLayout.CENTER);

        carregarLocacoes();
    }

    private JPanel criarFormulario() {
        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Nova/Editar Locação"));
        panel.setPreferredSize(new Dimension(0, 320));

        panel.add(new JLabel("CPF Cliente:"));
        cpfClienteField = new JTextField();
        panel.add(cpfClienteField);

        panel.add(new JLabel("Placa Veículo:"));
        placaVeiculoField = new JTextField();
        panel.add(placaVeiculoField);

        panel.add(new JLabel("CNPJ Filial Retirada:"));
        cnpjFilialRetiradaField = new JTextField();
        panel.add(cnpjFilialRetiradaField);

        panel.add(new JLabel("CNPJ Filial Retorno:"));
        cnpjFilialRetornoField = new JTextField();
        panel.add(cnpjFilialRetornoField);

        panel.add(new JLabel("Data Inicial (dd/MM/yyyy):"));
        dataInicialField = new JTextField();
        panel.add(dataInicialField);

        panel.add(new JLabel("Data Final (dd/MM/yyyy):"));
        dataFinalField = new JTextField();
        panel.add(dataFinalField);

        panel.add(new JLabel("Combustível Inicial:"));
        combustInicialField = new JTextField();
        panel.add(combustInicialField);

        panel.add(new JLabel("Combustível Final:"));
        combustFinalField = new JTextField();
        panel.add(combustFinalField);

        panel.add(new JLabel("KM Percorrido:"));
        kmPercorridoField = new JTextField();
        panel.add(kmPercorridoField);

        JPanel botoesPanel = new JPanel(new FlowLayout());
        
        JButton salvarBtn = new JButton("Salvar");
        salvarBtn.addActionListener(e -> salvarLocacao());
        botoesPanel.add(salvarBtn);

        JButton limparBtn = new JButton("Limpar");
        limparBtn.addActionListener(e -> limparCampos());
        botoesPanel.add(limparBtn);

        JButton deletarBtn = new JButton("Deletar");
        deletarBtn.addActionListener(e -> deletarLocacao());
        botoesPanel.add(deletarBtn);

        JButton atualizarBtn = new JButton("Atualizar Lista");
        atualizarBtn.addActionListener(e -> carregarLocacoes());
        botoesPanel.add(atualizarBtn);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.NORTH);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return mainPanel;
    }

    private JPanel criarLista() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Locações Cadastradas"));

        modeloTabela = new DefaultTableModel(
            new String[]{"ID", "Cliente", "Veículo", "Data Inicial", "Data Final", "Valor"}, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaLocacoes = new JTable(modeloTabela);
        tabelaLocacoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaLocacoes.getSelectionModel().addListSelectionListener(e -> selecionarLinha());
        
        JScrollPane scrollPane = new JScrollPane(tabelaLocacoes);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void salvarLocacao() {
        try {
            if (cpfClienteField.getText().isEmpty() || placaVeiculoField.getText().isEmpty() ||
                cnpjFilialRetiradaField.getText().isEmpty() || cnpjFilialRetornoField.getText().isEmpty() ||
                dataInicialField.getText().isEmpty() || dataFinalField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!", "Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Locacao locacao = new Locacao();
            locacao.setCpfCliente(cpfClienteField.getText());
            locacao.setPlacaVeiculo(placaVeiculoField.getText());
            locacao.setCnpjFilialRetirada(cnpjFilialRetiradaField.getText());
            locacao.setCnpjFilialRetorno(cnpjFilialRetornoField.getText());
            locacao.setDataInicial(LocalDate.parse(dataInicialField.getText(), formatter));
            locacao.setDataFinal(LocalDate.parse(dataFinalField.getText(), formatter));
            locacao.setCombustInicial(Double.parseDouble(combustInicialField.getText()));
            
            if (!combustFinalField.getText().isEmpty()) {
                locacao.setCombustFinal(Double.parseDouble(combustFinalField.getText()));
            }
            if (!kmPercorridoField.getText().isEmpty()) {
                locacao.setKmPercorrido(Double.parseDouble(kmPercorridoField.getText()));
            }

            if (idLocacaoEditando != null) {
                locacao.setIdLocacao(idLocacaoEditando);
                locacaoService.atualizar(locacao);
                JOptionPane.showMessageDialog(this, "Locação atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                locacaoService.salvar(locacao);
                JOptionPane.showMessageDialog(this, "Locação criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }

            limparCampos();
            carregarLocacoes();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar locação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarLocacao() {
        try {
            if (idLocacaoEditando == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma locação para deletar!");
                return;
            }
            locacaoService.deletar(idLocacaoEditando);
            JOptionPane.showMessageDialog(this, "Locação deletada com sucesso!");
            limparCampos();
            carregarLocacoes();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar locação: " + ex.getMessage());
        }
    }

    private void carregarLocacoes() {
        modeloTabela.setRowCount(0);
        try {
            List<Locacao> locacoes = locacaoService.listarTodos();
            for (Locacao locacao : locacoes) {
                modeloTabela.addRow(new Object[]{
                    locacao.getIdLocacao(),
                    locacao.getCpfCliente(),
                    locacao.getPlacaVeiculo(),
                    locacao.getDataInicial(),
                    locacao.getDataFinal(),
                    String.format("R$ %.2f", locacao.getValor())
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar locações: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selecionarLinha() {
        int selectedRow = tabelaLocacoes.getSelectedRow();
        if (selectedRow >= 0) {
            idLocacaoEditando = (Integer) modeloTabela.getValueAt(selectedRow, 0);
            try {
                Locacao locacao = locacaoService.buscar(idLocacaoEditando);
                if (locacao != null) {
                    cpfClienteField.setText(locacao.getCpfCliente());
                    placaVeiculoField.setText(locacao.getPlacaVeiculo());
                    cnpjFilialRetiradaField.setText(locacao.getCnpjFilialRetirada());
                    cnpjFilialRetornoField.setText(locacao.getCnpjFilialRetorno());
                    dataInicialField.setText(locacao.getDataInicial().toString());
                    dataFinalField.setText(locacao.getDataFinal().toString());
                    combustInicialField.setText(String.valueOf(locacao.getCombustInicial()));
                    combustFinalField.setText(String.valueOf(locacao.getCombustFinal() != null ? locacao.getCombustFinal() : ""));
                    kmPercorridoField.setText(String.valueOf(locacao.getKmPercorrido() != null ? locacao.getKmPercorrido() : ""));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar locação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        cpfClienteField.setText("");
        placaVeiculoField.setText("");
        cnpjFilialRetiradaField.setText("");
        cnpjFilialRetornoField.setText("");
        dataInicialField.setText("");
        dataFinalField.setText("");
        combustInicialField.setText("");
        combustFinalField.setText("");
        kmPercorridoField.setText("");
        idLocacaoEditando = null;
        tabelaLocacoes.clearSelection();
    }
}
