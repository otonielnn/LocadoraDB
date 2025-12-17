package locadora.view;

import locadora.model.Veiculo;
import locadora.service.VeiculoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class VeiculoForm extends JPanel {
    private final VeiculoService veiculoService = new VeiculoService();
    private JTextField placaField;
    private JTextField tipoField;
    private JTextField modeloField;
    private JTextField anoField;
    private JTextField precoDiariaField;
    private JTextField kmAnteriorField;
    private JTextField cnpjFilialField;
    private JTextArea historicoArea;
    private JTable tabelaVeiculos;
    private DefaultTableModel modeloTabela;
    private String placaEditando = null;

    public VeiculoForm() {
        setLayout(new BorderLayout());

        JPanel formularioPanel = criarFormulario();
        JPanel listaPanel = criarLista();

        add(formularioPanel, BorderLayout.NORTH);
        add(listaPanel, BorderLayout.CENTER);

        carregarVeiculos();
    }

    private JPanel criarFormulario() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Novo/Editar Veículo"));
        panel.setPreferredSize(new Dimension(0, 250));

        panel.add(new JLabel("Placa:"));
        placaField = new JTextField();
        panel.add(placaField);

        panel.add(new JLabel("Tipo:"));
        tipoField = new JTextField();
        panel.add(tipoField);

        panel.add(new JLabel("Modelo:"));
        modeloField = new JTextField();
        panel.add(modeloField);

        panel.add(new JLabel("Ano:"));
        anoField = new JTextField();
        panel.add(anoField);

        panel.add(new JLabel("Preço Diária:"));
        precoDiariaField = new JTextField();
        panel.add(precoDiariaField);

        panel.add(new JLabel("KM Anterior:"));
        kmAnteriorField = new JTextField();
        panel.add(kmAnteriorField);

        panel.add(new JLabel("CNPJ Filial:"));
        cnpjFilialField = new JTextField();
        panel.add(cnpjFilialField);

        panel.add(new JLabel("Histórico:"));
        historicoArea = new JTextArea(3, 20);
        JScrollPane scrollHistorico = new JScrollPane(historicoArea);
        panel.add(scrollHistorico);

        JPanel botoesPanel = new JPanel(new FlowLayout());
        
        JButton salvarBtn = new JButton("Salvar");
        salvarBtn.addActionListener(e -> salvarVeiculo());
        botoesPanel.add(salvarBtn);

        JButton limparBtn = new JButton("Limpar");
        limparBtn.addActionListener(e -> limparCampos());
        botoesPanel.add(limparBtn);

        JButton deletarBtn = new JButton("Deletar");
        deletarBtn.addActionListener(e -> deletarVeiculo());
        botoesPanel.add(deletarBtn);

        JButton atualizarBtn = new JButton("Atualizar Lista");
        atualizarBtn.addActionListener(e -> carregarVeiculos());
        botoesPanel.add(atualizarBtn);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.NORTH);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return mainPanel;
    }

    private JPanel criarLista() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Veículos Cadastrados"));

        modeloTabela = new DefaultTableModel(
            new String[]{"Placa", "Tipo", "Modelo", "Ano", "Preço/Dia", "KM Anterior", "Filial"}, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaVeiculos = new JTable(modeloTabela);
        tabelaVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaVeiculos.getSelectionModel().addListSelectionListener(e -> selecionarLinha());
        
        JScrollPane scrollPane = new JScrollPane(tabelaVeiculos);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void salvarVeiculo() {
        try {
            if (!validarCampos()) {
                JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!");
                return;
            }

            String placa = placaField.getText().trim();
            String tipo = tipoField.getText().trim();
            String modelo = modeloField.getText().trim();
            Integer ano = Integer.parseInt(anoField.getText().trim());
            Double precoDiaria = Double.parseDouble(precoDiariaField.getText().trim());
            Double kmAnterior = Double.parseDouble(kmAnteriorField.getText().trim());
            String cnpjFilial = cnpjFilialField.getText().trim();
            String historico = historicoArea.getText().trim();

            Veiculo veiculo = new Veiculo(placa, tipo, modelo, ano, precoDiaria, kmAnterior, false, historico, cnpjFilial);

            if (veiculoService.validarVeiculo(veiculo)) {
                veiculoService.salvar(veiculo);
                JOptionPane.showMessageDialog(this, "Veículo salvo com sucesso!");
                limparCampos();
                carregarVeiculos();
            } else {
                JOptionPane.showMessageDialog(this, "Dados do veículo inválidos!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar veículo: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro de formato nos campos numéricos!");
        }
    }

    private void deletarVeiculo() {
        try {
            String placa = placaField.getText().trim();
            if (placa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um veículo para deletar!");
                return;
            }
            veiculoService.deletar(placa);
            JOptionPane.showMessageDialog(this, "Veículo deletado com sucesso!");
            limparCampos();
            carregarVeiculos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar veículo: " + ex.getMessage());
        }
    }

    private void carregarVeiculos() {
        try {
            modeloTabela.setRowCount(0);
            List<Veiculo> veiculos = veiculoService.listarTodos();
            for (Veiculo veiculo : veiculos) {
                modeloTabela.addRow(new Object[]{
                    veiculo.getPlaca(),
                    veiculo.getTipo(),
                    veiculo.getModelo(),
                    veiculo.getAno(),
                    veiculo.getPrecoDiaria(),
                    veiculo.getKmAnterior(),
                    veiculo.getCnpjFilial()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar veículos: " + ex.getMessage());
        }
    }
    
    private void selecionarLinha() {
        int linhaSelecionada = tabelaVeiculos.getSelectedRow();
        if (linhaSelecionada >= 0) {
            placaField.setText((String) modeloTabela.getValueAt(linhaSelecionada, 0));
            tipoField.setText((String) modeloTabela.getValueAt(linhaSelecionada, 1));
            modeloField.setText((String) modeloTabela.getValueAt(linhaSelecionada, 2));
            anoField.setText(modeloTabela.getValueAt(linhaSelecionada, 3).toString());
            precoDiariaField.setText(modeloTabela.getValueAt(linhaSelecionada, 4).toString());
            kmAnteriorField.setText(modeloTabela.getValueAt(linhaSelecionada, 5).toString());
            cnpjFilialField.setText((String) modeloTabela.getValueAt(linhaSelecionada, 6));
            placaEditando = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
        }
    }

    private void limparCampos() {
        placaField.setText("");
        tipoField.setText("");
        modeloField.setText("");
        anoField.setText("");
        precoDiariaField.setText("");
        kmAnteriorField.setText("");
        cnpjFilialField.setText("");
        historicoArea.setText("");
        placaEditando = null;
    }

    private boolean validarCampos() {
        return !placaField.getText().trim().isEmpty() &&
               !tipoField.getText().trim().isEmpty() &&
               !modeloField.getText().trim().isEmpty() &&
               !anoField.getText().trim().isEmpty() &&
               !precoDiariaField.getText().trim().isEmpty() &&
               !cnpjFilialField.getText().trim().isEmpty();
    }
}
