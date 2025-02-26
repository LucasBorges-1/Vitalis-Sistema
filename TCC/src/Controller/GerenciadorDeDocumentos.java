/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Controller.ControlladorPrincipal;
import Wiew.TelaDeRelatorio;
import Wiew.TelaPrincipal;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 *
 * @author Borges
 */
public class GerenciadorDeDocumentos {
   
    private TelaPrincipal telaPrincipal;
    private  Wiew.TelaDeRelatorio telaDeRelatorio=new TelaDeRelatorio(null, true);
    
    private static final String DOCUMENTS_FOLDER ="documentos";
    
    
    public GerenciadorDeDocumentos() {
        telaPrincipal=new TelaPrincipal();
        telaDeRelatorio=new TelaDeRelatorio(telaPrincipal, true);
        inicializarComponentes();
       
    }
    
   public void inicializarComponentes(){ 
       telaDeRelatorio.btSalvarDocks.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               try{
                   
                   //Verificando se o titulo foi inserido.
                   String titulo = telaDeRelatorio.edTitulo.getText().trim();
                   if (titulo.isEmpty()) {
                        JOptionPane.showMessageDialog(telaDeRelatorio, "Por favor, insira um título para o documento.");
                        return;
                    }
                   
                   //Verificando se o conteudo foi digitado.
                   String conteudo=telaDeRelatorio.TxArea.getText().trim();
                   if (conteudo.isEmpty()) {                       
                        JOptionPane.showMessageDialog(telaDeRelatorio, "O documento está vazio. Nada para salvar.");
                        return;
                   }
                   
                   //Alterar quando houver conexão com o banco.
                   //Criando pasta documentos se nao existir
                   File folder=new File(DOCUMENTS_FOLDER);
                   if (!folder.exists()) {
                       //o (!) significa (NÃO),neste contexto inverte o valor booleano
                       folder.mkdir();
                   }
                   
                   //Criando o arquivo
                   File file =new File(folder,titulo + ".txt");
                   try(FileWriter writer=new FileWriter(file)){
                       writer.write(conteudo);
                   }
                   String Caminho=file.getAbsolutePath();

                   //Imprime a mensagem e limpa a tela
                   JOptionPane.showMessageDialog(telaPrincipal,"Documento salvo com sucesso! " + Caminho);
                   telaDeRelatorio.TxArea.setText("");
                     telaDeRelatorio.edTitulo.setText("");
                    
               }catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(telaDeRelatorio, "Erro ao salvar o arquivo.");
                }
               
               
               
               
               
               
           }
       });
       
       
       //Botão limpar
       telaDeRelatorio.btLimparDocks.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               telaDeRelatorio.TxArea.setText("");
               telaDeRelatorio.edTitulo.setText("");
           }
       }) ;
   }
    
    public void abrirRelatorio(){
        telaDeRelatorio.setVisible(true);
    }
    
    
    
    
}
