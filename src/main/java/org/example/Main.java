package org.example;
import org.example.dao.UsuarioDAO;
import org.example.model.Usuario;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Cria um objeto do tipo UsuarioDAO para acessar o banco de dados
        UsuarioDAO dao = new UsuarioDAO();

        try {
            int opcao;

            // Estrutura de repetição com menu interativo, executa enquanto a opção for diferente de 0
            do {
                // Mostra o menu e solicita que o usuário digite uma opção
                opcao = Integer.parseInt(JOptionPane.showInputDialog(null, """
                                                                        1. Inserir 
                                                                        2. Mostrar 
                                                                        3. Consultar
                                                                        4. Alterar
                                                                        5. Excluir                                                                       
                                                                        0. Sair
                                                                        Escolha uma opção:""",
                        "========= MENU =========", JOptionPane.QUESTION_MESSAGE));

                // Escolhe a operação de acordo com a opção digitada
                switch (opcao) {

                    case 1 -> {
                        // ======================
                        // INSERIR NOVO USUÁRIO
                        // ======================

                        Usuario novo = new Usuario("", "");

                        // Validação do nome (não pode conter números)
                        while (true) {
                            String nome = JOptionPane.showInputDialog("Digite o nome: ");
                            if (nome == null || nome.trim().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Nome não pode ser vazio.");
                                break;
                            } else if (!nome.matches("^[^\\d]+$")) {
                                JOptionPane.showMessageDialog(null, "Digite apenas letras.");
                            } else {
                                novo.setNome(nome);
                                break;
                            }
                        }

                        // Só continua se o nome for válido
                        if (novo.getNome() == null || novo.getNome().isEmpty()) {
                            break;
                        }

                        // Validação do email (não pode conter números antes do @)
                        while (true) {
                            String email = JOptionPane.showInputDialog("Digite o email: ");
                            if (email == null || email.trim().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Email não pode ser vazio.");
                                break;
                            } else if (!email.contains("@")) {
                                JOptionPane.showMessageDialog(null, "Email inválido. Deve conter '@'.");
                            } else {
                                String parteAntesArroba = email.split("@")[0];
                                if (!parteAntesArroba.matches("^[^\\d]+$")) {
                                    JOptionPane.showMessageDialog(null, "A parte antes do '@' no email não pode conter números.");
                                } else {
                                    novo.setEmail(email);
                                    break;
                                }
                            }
                        }

                        // Só insere se nome e email forem válidos
                        if (novo.getEmail() != null && !novo.getEmail().isEmpty()) {
                            dao.inserir(novo);
                            JOptionPane.showMessageDialog(null, "Inserido com sucesso!");
                        }
                    }


                    case 2 -> {
                        // ======================
                        // MOSTRAR TODOS OS USUÁRIOS
                        // ======================

                        // Busca a lista de todos os usuários no banco
                        List<Usuario> usuarios = dao.listar();

                        // Cria uma string para montar a exibição da lista
                        StringBuilder mensagem = new StringBuilder();

                        // Percorre a lista e adiciona cada usuário à mensagem
                        for (Usuario u : usuarios) {
                            mensagem.append(u.getId())     // ID
                                    .append(" - ")
                                    .append(u.getNome())   // Nome
                                    .append(" - ")
                                    .append(u.getEmail())  // Email
                                    .append("\n");         // Nova linha
                        }

                        // Mostra a lista em uma janela
                        JOptionPane.showMessageDialog(null, "           Lista de Pessoas:\n"+
                                                                                                "ID   -  Nome  -   Email\n" + mensagem.toString());
                    }

                    case 3 -> {
                        // ======================
                        // CONSULTAR USUÁRIO POR ID
                        // ======================

                        // Solicita o ID do usuário a ser consultado
                        int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID de quem deseja consultar: "));

                        // Busca o usuário com esse ID
                        Usuario u = dao.consultar(id);

                        // Verifica se encontrou o usuário
                        if (u != null) {
                            // Exibe os dados encontrados
                            JOptionPane.showMessageDialog(null,
                                    "ID: " + u.getId() +
                                            "\nNome: " + u.getNome() +
                                            "\nEmail: " + u.getEmail());
                        } else {
                            // Se não encontrar, exibe mensagem de erro
                            JOptionPane.showMessageDialog(null, "ID digitado " + id + " não encontrado.");
                        }
                    }

                    case 4 -> {
                        // ======================
                        // ALTERAR USUÁRIO EXISTENTE
                        // ======================

                        // Solicita o ID do usuário que será alterado
                        int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o Id: "));

                        String nome = "";
                        String email = "";

                        // Validação do nome (não pode conter números)
                        while (true) {
                            nome = JOptionPane.showInputDialog("Digite o nome: ");
                            if (nome == null || nome.trim().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Nome não pode ser vazio.");
                                break;
                            } else if (!nome.matches("^[^\\d]+$")) {
                                // Se houver número no nome, avisa e pede novamente
                                JOptionPane.showMessageDialog(null, "Nome não pode conter números. Digite apenas letras.");
                            } else {
                                // Nome válido
                                break;
                            }
                        }

                        // Se o nome for inválido ou cancelado, sai do case
                        if (nome == null || nome.trim().isEmpty() || !nome.matches("^[^\\d]+$")) {
                            break;
                        }

                        // Validação do email (a parte antes do @ não pode conter números)
                        while (true) {
                            email = JOptionPane.showInputDialog("Digite o email: ");
                            if (email == null || email.trim().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Email não pode ser vazio.");
                                break;
                            } else if (!email.contains("@")) {
                                // Verifica se possui o @
                                JOptionPane.showMessageDialog(null, "Email inválido. Deve conter '@'.");
                            } else {
                                String parteAntesArroba = email.split("@")[0];
                                if (!parteAntesArroba.matches("^[^\\d]+$")) {
                                    // Verifica se a parte antes do @ tem números
                                    JOptionPane.showMessageDialog(null, "A parte antes do '@' no email não pode conter números.");
                                } else {
                                    // Email válido
                                    break;
                                }
                            }
                        }

                        // Se o email for inválido ou cancelado, sai do case
                        if (email == null || email.trim().isEmpty() || !email.contains("@") || !email.split("@")[0].matches("^[^\\d]+$")) {
                            break;
                        }

                        // Cria objeto com os dados validados
                        Usuario atualizar = new Usuario(id, nome, email);

                        // Atualiza os dados do usuário no banco
                        dao.atualizar(atualizar);

                        // Confirmação
                        JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
                    }


                    case 5 -> {
                        // ======================
                        // EXCLUIR USUÁRIO POR ID
                        // ======================

                        int idExcluir = -1; // Variável para armazenar o ID a ser excluído

                        // Laço de repetição para garantir entrada válida de número inteiro
                        while (true) {
                            String entrada = JOptionPane.showInputDialog("Digite o ID de quem deseja excluir:");

                            if (entrada == null || entrada.trim().isEmpty()) {
                                // Usuário cancelou ou não digitou nada
                                JOptionPane.showMessageDialog(null, "Operação cancelada ou ID em branco.");
                                break;
                            }

                            try {
                                // Tenta converter a entrada para número inteiro
                                idExcluir = Integer.parseInt(entrada);
                                if (idExcluir <= 0) {
                                    JOptionPane.showMessageDialog(null, "ID deve ser um número positivo.");
                                } else {
                                    // Entrada válida, sai do laço
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                // Caso o usuário digite algo que não seja número
                                JOptionPane.showMessageDialog(null, "ID inválido. Digite apenas números inteiros.");
                            }
                        }

                        // Só tenta excluir se o ID for válido
                        if (idExcluir > 0) {
                            // Chama o método para deletar no banco
                            dao.deletar(idExcluir);
                            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
                        }
                    }


                    case 0 -> {
                        // ======================
                        // ENCERRAR O SISTEMA
                        // ======================

                        // Mensagem final de encerramento
                        JOptionPane.showMessageDialog(null,
                                "Encerrando sistema...",
                                "Game Over",
                                JOptionPane.WARNING_MESSAGE);
                    }

                    default -> {
                        // ======================
                        // OPÇÃO INVÁLIDA
                        // ======================

                        // Caso a opção digitada não esteja entre 0 e 5
                        JOptionPane.showMessageDialog(null,
                                "Opção inválida!",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            } while (opcao != 0); // Enquanto a opção for diferente de 0, repete o menu

            // ======================
            // MOSTRA A LISTA NO CONSOLE (após sair do menu)
            // ======================

            List<Usuario> usuarios = dao.listar();

            for (Usuario u : usuarios) {
                // Exibe cada usuário no console (útil para depuração)
                System.out.println(u.getId() + " - " + u.getNome() + " - " + u.getEmail());
            }

        } catch (Exception e) {
            // Se houver qualquer erro (ex: falha na conexão com banco), exibe o erro no console
            e.printStackTrace();
        }


    }


}