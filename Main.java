import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class PrincipalGUI extends JFrame {
    private static ArrayList<Pessoa> pessoas = new ArrayList<>();

    public PrincipalGUI() {
        setTitle("Sistema Escolar");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton inserirButton = new JButton("Inserir");
        JButton alterarButton = new JButton("Alterar");
        JButton removerButton = new JButton("Remover");
        JButton listarButton = new JButton("Listar");
        JButton sairButton = new JButton("Sair");

        inserirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInserirDialog();
            }
        });

        alterarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAlterarDialog();
            }
        });

        removerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRemoverDialog();
            }
        });

        listarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showListarDialog();
            }
        });

        sairButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(inserirButton);
        panel.add(alterarButton);
        panel.add(removerButton);
        panel.add(listarButton);
        panel.add(sairButton);

        add(panel);
    }

    private void showInserirDialog() {
        JDialog dialog = new JDialog(this, "Inserir Pessoa", true);
        dialog.setSize(300, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel tipoLabel = new JLabel("Tipo:");
        JComboBox<String> tipoComboBox = new JComboBox<>(new String[]{"Aluno", "Professor"});
        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField();
        JLabel cpfLabel = new JLabel("CPF:");
        JTextField cpfField = new JTextField();
        JLabel matriculaLabel = new JLabel("Matrícula:");
        JTextField matriculaField = new JTextField();
        JLabel departamentoLabel = new JLabel("Departamento:");
        JTextField departamentoField = new JTextField();
        JButton inserirButton = new JButton("Inserir");

        panel.add(tipoLabel);
        panel.add(tipoComboBox);
        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(cpfLabel);
        panel.add(cpfField);
        panel.add(matriculaLabel);
        panel.add(matriculaField);
        panel.add(departamentoLabel);
        panel.add(departamentoField);
        panel.add(new JLabel());
        panel.add(inserirButton);

        tipoComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tipoComboBox.getSelectedItem().equals("Aluno")) {
                    matriculaLabel.setVisible(true);
                    matriculaField.setVisible(true);
                    departamentoLabel.setVisible(false);
                    departamentoField.setVisible(false);
                } else {
                    matriculaLabel.setVisible(false);
                    matriculaField.setVisible(false);
                    departamentoLabel.setVisible(true);
                    departamentoField.setVisible(true);
                }
            }
        });

        inserirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String cpf = cpfField.getText();
                if (tipoComboBox.getSelectedItem().equals("Aluno")) {
                    String matricula = matriculaField.getText();
                    pessoas.add(new Aluno(nome, cpf, matricula));
                } else {
                    String departamento = departamentoField.getText();
                    pessoas.add(new Professor(nome, cpf, departamento));
                }
                dialog.dispose();
            }
        });

        tipoComboBox.setSelectedIndex(0);
        tipoComboBox.getActionListeners()[0].actionPerformed(null);  // Trigger initial visibility

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showAlterarDialog() {
        JDialog dialog = new JDialog(this, "Alterar Pessoa", true);
        dialog.setSize(300, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel cpfLabel = new JLabel("CPF:");
        JTextField cpfField = new JTextField();
        JLabel nomeLabel = new JLabel("Novo Nome:");
        JTextField nomeField = new JTextField();
        JLabel extraLabel = new JLabel();
        JTextField extraField = new JTextField();
        JButton alterarButton = new JButton("Alterar");

        panel.add(cpfLabel);
        panel.add(cpfField);
        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(extraLabel);
        panel.add(extraField);
        panel.add(new JLabel());
        panel.add(alterarButton);

        alterarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();
                Pessoa pessoa = buscarPorCpf(cpf);
                if (pessoa != null) {
                    pessoa.setNome(nomeField.getText());
                    if (pessoa instanceof Aluno) {
                        ((Aluno) pessoa).setMatricula(extraField.getText());
                    } else if (pessoa instanceof Professor) {
                        ((Professor) pessoa).setDepartamento(extraField.getText());
                    }
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Pessoa não encontrada!");
                }
            }
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showRemoverDialog() {
        JDialog dialog = new JDialog(this, "Remover Pessoa", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel cpfLabel = new JLabel("CPF:");
        JTextField cpfField = new JTextField();
        JButton removerButton = new JButton("Remover");

        panel.add(cpfLabel);
        panel.add(cpfField);
        panel.add(new JLabel());
        panel.add(removerButton);

        removerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();
                Pessoa pessoa = buscarPorCpf(cpf);
                if (pessoa != null) {
                    pessoas.remove(pessoa);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Pessoa não encontrada!");
                }
            }
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showListarDialog() {
        JDialog dialog = new JDialog(this, "Listar Pessoas", true);
        dialog.setSize(300, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        for (Pessoa pessoa : pessoas) {
            textArea.append(pessoa.toString() + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private Pessoa buscarPorCpf(String cpf) {
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getCpf().equals(cpf)) {
                return pessoa;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PrincipalGUI().setVisible(true);
            }
        });
    }
}

class Pessoa {
    private String nome;
    private String cpf;

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", CPF: " + cpf;
    }
}

class Aluno extends Pessoa {
    private String matricula;

    public Aluno(String nome, String cpf, String matricula) {
        super(nome, cpf);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return super.toString() + ", Matrícula: " + matricula;
    }
}

class Professor extends Pessoa {
    private String departamento;

    public Professor(String nome, String cpf, String departamento) {
        super(nome, cpf);
        this.departamento = departamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return super.toString() + ", Departamento: " + departamento;
    }
}
