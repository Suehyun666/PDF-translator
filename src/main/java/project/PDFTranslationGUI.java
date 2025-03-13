package project;

import project.translator.DeepLTranslator;
import project.translator.NLLBTranslator;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PDFTranslationGUI {
    private JFrame frame;
    private JButton uploadButton;
    private JButton translateButton;
    private JTextArea textArea;
    private DefaultListModel<String> fileListModel;
    private JList<String> fileList;
    private List<File> selectedFiles;

    public PDFTranslationGUI() {
        frame = new JFrame("PDF 번역기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null); // 화면 중앙 배치

        uploadButton = new JButton("📂 PDF 파일 선택");
        translateButton = new JButton("🌍 번역 시작");
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectedFiles = new ArrayList<>();

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF 파일", "pdf"));
            fileChooser.setMultiSelectionEnabled(true); // 여러 개 선택 가능
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                for (File file : fileChooser.getSelectedFiles()) {
                    selectedFiles.add(file);
                    fileListModel.addElement(file.getName());
                }
            }
        });

        translateButton.addActionListener(e -> {
            if (!selectedFiles.isEmpty()) {
                textArea.setText("번역 중...");
                startTranslation(selectedFiles.get(0)); // 첫 번째 파일만 번역 (여러 개 번역 가능하도록 수정 가능)
            } else {
                JOptionPane.showMessageDialog(frame, "PDF 파일을 먼저 선택해주세요.");
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0;
        panel.add(uploadButton, gbc);
        gbc.gridx = 1;
        panel.add(translateButton, gbc);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.add(new JScrollPane(fileList), BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void startTranslation(File pdfFile) {
        new Thread(() -> {
            try {
                String extractedText = PDFToTextWithOCR.extractFirstPageText(pdfFile);
                //String extractedText ="Hello";
                //String translatedText = DeepLTranslator.translateText(extractedText, "KO"); // deepl
                String translatedText = NLLBTranslator.translateText(extractedText);
                textArea.setText(translatedText);
            } catch (Exception ex) {
                textArea.setText("오류 발생: " + ex.getMessage());
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PDFTranslationGUI::new);
    }
}
