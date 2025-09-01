
package tcc.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import tcc.application.form.ControllerPrincipal;


public class FormImpressão extends javax.swing.JPanel implements Printable {
   
    private ControllerPrincipal app; // Cuidado: app não está sendo inicializado no código fornecido.
    private JPanel panelImpressao;
    private JTextArea areaTexto;
    private JButton btnImprimir = new JButton("Imprimir");
    private JComboBox<String> comboTipoDocumento;

    /**
     * Template para a Receita Médica.
     */
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

    /**
     * Novo template para o Atestado Médico.
     */
    private static final String ATESTADO_TEMPLATE = """
            ATESTADO MÉDICO

            Atesto para os devidos fins que o(a) Sr(a). ____________________________________,
            portador(a) do RG ____________________, necessita de ____ (_______) dias de
            afastamento de suas atividades laborais/escolares a partir desta data,
            por motivos de doença (CID: ________).

            Cidade, _____ de __________________ de ________.

            ----------------------------------------------
            Observações:
            ______________________________________________
            ______________________________________________
            ______________________________________________

            Dr(a). _______________________________________
            CRM: _____________ UF: _____

            Assinatura e carimbo do médico:
            ______________________________________________
            """;

    public FormImpressão() {
        initComponents();
        // A linha abaixo pode causar NullPointerException se 'app' não for inicializado
        // app.setI(0); 
        configurarLayout();
    }

    /**
     * Configura o layout da interface, incluindo o seletor de tipo de documento.
     */
    public void configurarLayout() {
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        panelImpressao = new JPanel(new BorderLayout(0, 15)); // Adicionado espaçamento vertical
        panelImpressao.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelImpressao.putClientProperty("FlatLaf.style", "arc:25");

        // --- PAINEL DE CONTROLES (NOVO) ---
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelControles.add(new JLabel("Tipo de Documento:"));

        String[] tipos = {"Receita Médica", "Atestado Médico"};
        comboTipoDocumento = new JComboBox<>(tipos);
        comboTipoDocumento.addActionListener(e -> atualizarTemplate()); // Adiciona o listener
        panelControles.add(comboTipoDocumento);
        // --- FIM PAINEL DE CONTROLES ---

        // Configuração da área de texto
        areaTexto = new JTextArea(RECEITA_TEMPLATE); // Inicia com a receita
        areaTexto.setWrapStyleWord(true);
        areaTexto.setLineWrap(true);
        areaTexto.setFont(new Font("Serif", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(areaTexto);

        // Painel de botões
        JPanel panelBotoes = new JPanel();
        btnImprimir.addActionListener(e -> imprimirDireto());
        panelBotoes.add(btnImprimir);

        // Adiciona os componentes ao painel de impressão
        panelImpressao.add(panelControles, BorderLayout.NORTH); // Adiciona o seletor na parte superior
        panelImpressao.add(scrollPane, BorderLayout.CENTER);
        panelImpressao.add(panelBotoes, BorderLayout.SOUTH);

        painelPrincipal.add(panelImpressao, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(painelPrincipal, BorderLayout.CENTER);
    }
    
    /**
     * Atualiza o conteúdo da área de texto com base na seleção do JComboBox.
     */
    private void atualizarTemplate() {
        String tipoSelecionado = (String) comboTipoDocumento.getSelectedItem();
        if ("Atestado Médico".equals(tipoSelecionado)) {
            areaTexto.setText(ATESTADO_TEMPLATE);
        } else {
            areaTexto.setText(RECEITA_TEMPLATE);
        }
    }

    private void imprimirDireto() {
        imprimirAreaTextoComoPDF(areaTexto);
    }

    /**
     * Gera um PDF a partir do texto e o envia para impressão.
     * @param areaTexto O JTextArea contendo o texto a ser impresso.
     */
    public static void imprimirAreaTextoComoPDF(JTextArea areaTexto) {
        if (areaTexto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há texto para imprimir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750); // Posição Y ajustada para margem superior
                float leading = 14.5f;

                String[] lines = areaTexto.getText().split("\n");
                for (String line : lines) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -leading);
                }
                contentStream.endText();
            }

            File tempFile = File.createTempFile("impressao", ".pdf");
            document.save(tempFile);
            printPDF(tempFile);
            tempFile.deleteOnExit();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao criar PDF: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Tenta imprimir ou abrir um arquivo PDF.
     * @param pdfFile O arquivo PDF a ser impresso.
     */
    private static void printPDF(File pdfFile) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.PRINT)) {
                    desktop.print(pdfFile);
                } else if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(pdfFile);
                    JOptionPane.showMessageDialog(null,
                        "Por favor, imprima o documento que será aberto.",
                        "Imprimir Manualmente", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    throw new IOException("Nenhuma operação de impressão ou abertura suportada.");
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
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        this.printAll(graphics);
        return Printable.PAGE_EXISTS;
    }

   
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