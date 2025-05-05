package tcc.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.JFileChooser;

public class FormImpressão extends javax.swing.JPanel implements Printable {

    private tcc.application.form.ControllerPrincipal app;
    private JPanel panelImpressao;
    private JTextArea areaTexto;
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
        JButton btnVisualizar = new JButton("Visualizar Impressão");
        btnVisualizar.addActionListener(e -> visualizarImpressao());
        
        JButton btnImprimir = new JButton("Imprimir Direto");
        btnImprimir.addActionListener(e -> imprimirDireto());
        
        JButton btnExportarPDF = new JButton("Exportar para PDF");
        btnExportarPDF.addActionListener(e -> exportarParaPDF());
        
        panelBotoes.add(btnVisualizar);
        panelBotoes.add(btnImprimir);
        panelBotoes.add(btnExportarPDF);
        
        
        
        
        panelImpressao.add(scrollPane, BorderLayout.CENTER);
        panelImpressao.add(panelBotoes, BorderLayout.SOUTH);

        painelPrincipal.add(panelImpressao, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(painelPrincipal, BorderLayout.CENTER);
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

       
        String[] lines = areaTexto.getText().split("\n");
        Font font = new Font("Serif", Font.PLAIN, 10);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        
        int y = 50;
        for (String line : lines) {
            g2d.drawString(line, 50, y);
            y += fm.getHeight();
        }

        return PAGE_EXISTS;
    }

    private void visualizarImpressao() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        
       
        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        attributes.add(MediaSizeName.ISO_A4);
        
        if (job.printDialog(attributes)) {
            try {
              
                Book book = new Book();
                book.append(this, job.defaultPage());
                job.setPageable(book);
                
               
                job.pageDialog(attributes);
            } catch (Exception s) {
                JOptionPane.showMessageDialog(this, "Erro ao visualizar impressão " , 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void imprimirDireto() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        
        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        attributes.add(MediaSizeName.ISO_A4);
        
        if (job.printDialog(attributes)) {
            try {
                job.print(attributes);
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao imprimir: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportarParaPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar como PDF");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF Files", "pdf"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".pdf")) {
                file = new File(file.getParentFile(), file.getName() + ".pdf");
            }
            
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);
                
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                   
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, 700);
                    
                    String[] lines = areaTexto.getText().split("\n");
                    for (String line : lines) {
                        contentStream.showText(line);
                        contentStream.newLineAtOffset(0, -15);
                    }
                    
                    contentStream.endText();
                }
                
                document.save(file);
                JOptionPane.showMessageDialog(this, "PDF salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao gerar PDF: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
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
