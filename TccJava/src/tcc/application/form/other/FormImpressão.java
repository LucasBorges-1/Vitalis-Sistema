
package tcc.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.print.*;
import java.io.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.print.PrinterException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;


public class FormImpressão extends javax.swing.JPanel implements Printable {
    private tcc.application.form.ControllerPrincipal app;
    private JPanel panelImpressao;
    private static JTextArea areaTexto;
    private JButton btnImprimir = new JButton("Imprimir");
     private static final String RECEITA_TEMPLATE = """
        RECEITA MÉDICA

        Dr(a). _______________________________________
        CRM: _____________ UF: _____

        Paciente: ____________________________________
        Idade: _______    Sexo: _______
        Data: ___/___/_____

        ----------------------------------------------
        Prescrição:

        1. ___________________________________________
        Posologia: ________________________________

        2. ___________________________________________
        Posologia: ________________________________

        3. ___________________________________________
        Posologia: ________________________________

        ----------------------------------------------
        Observações:
        ______________________________________________
        ______________________________________________

        Assinatura e carimbo do médico:
        ______________________________________________
        """;

    public FormImpressão() {
        initComponents();

        app.setI(0);
        configurarLayout();
    }

    public void configurarLayout() {
        
         JPanel painelPrincipal = new JPanel(new BorderLayout());
         painelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        panelImpressao = new JPanel(new BorderLayout());
        panelImpressao.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelImpressao.putClientProperty("FlatLaf.style", "arc:25");
        areaTexto = new JTextArea(RECEITA_TEMPLATE);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setLineWrap(true);
        areaTexto.setFont(new Font("Serif", Font.PLAIN, 16));
        
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel();
        
        
        
        btnImprimir.addActionListener(e -> imprimirDireto());
        
        
       
        panelBotoes.add(btnImprimir);
        
        
        
        
        
        panelImpressao.add(scrollPane, BorderLayout.CENTER);
        panelImpressao.add(panelBotoes, BorderLayout.SOUTH);

        painelPrincipal.add(panelImpressao, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(painelPrincipal, BorderLayout.CENTER);
    }

   

  


    private void imprimirDireto() {
        imprimirAreaTextoComoPDF(areaTexto); 
    }

   
    
    
       public static void imprimirAreaTextoComoPDF(JTextArea areaTexto) {
        if (areaTexto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há texto para imprimir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            
            // Configuração do fluxo de conteúdo
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Definir a fonte ANTES de começar o texto
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 12);
                
                // Iniciar o bloco de texto
                contentStream.beginText();
                
                // Posição inicial (x, y)
                contentStream.newLineAtOffset(50, 700);
                
                // Espaçamento entre linhas
                float leading = 14.5f;
                
                // Dividir o texto em linhas
                String[] lines = areaTexto.getText().split("\n");
                
                // Escrever cada linha
                for (String line : lines) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -leading);
                }
                
                // Finalizar o bloco de texto (IMPORTANTE!)
                contentStream.endText();
            } // O try-with-resources fecha automaticamente o contentStream
            
            // Salvar PDF temporário
            File tempFile = File.createTempFile("impressao", ".pdf");
            document.save(tempFile);
            
            // Imprimir o PDF
            printPDF(tempFile);
            
            // Opcional: excluir o arquivo temporário após a impressão
            tempFile.deleteOnExit();
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao criar PDF: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private static void printPDF(File pdfFile) {
    try {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            
            // Primeiro tenta imprimir diretamente
            if (desktop.isSupported(Desktop.Action.PRINT)) {
                desktop.print(pdfFile);
            } 
            // Se não conseguir, tenta abrir para impressão manual
            else if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(pdfFile);
                // O usuário precisará imprimir manualmente
                JOptionPane.showMessageDialog(null, 
                    "Por favor, imprima o documento que será aberto", 
                    "Imprimir Manualmente", JOptionPane.INFORMATION_MESSAGE);
            } else {
                throw new IOException("Nenhuma operação de impressão ou abertura suportada");
            }
        } else {
            JOptionPane.showMessageDialog(null, 
                "Não foi possível acessar o recurso de impressão do sistema.", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, 
            "Erro ao imprimir: " + e.getMessage() + 
            "\nCertifique-se de ter um leitor de PDF instalado.", 
            "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
     @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return Printable.NO_SUCH_PAGE;
        }
        
        // Ajusta as coordenadas para considerar as margens
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        
        // Desenha o conteúdo do painel
        this.printAll(graphics);
        
        return Printable.PAGE_EXISTS;
    }
    



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 806, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}

