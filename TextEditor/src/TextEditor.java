import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class TextEditor implements ActionListener {
    JFrame frame;
    JMenuBar menuBar;
    JMenu file,edit;
    JMenuItem newFile,openFile,saveFile,cut,copy,paste,selectAll,close;
    JTextArea textArea;
    TextEditor(){
        //Initialize all components
        frame = new JFrame();
        menuBar = new JMenuBar();
        textArea = new JTextArea();
        file = new JMenu("File");
        edit = new JMenu("Edit");

        //Initializing Menu Items and their action listeners
        newFile = new JMenuItem("New Window");
        openFile = new JMenuItem("Open File");
        saveFile = new JMenuItem("Save File");
        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);

        //Adding the menu items to respective menu
        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);

        //Initializing Menu Items and their action listeners
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        selectAll = new JMenuItem("Select All");
        close = new JMenuItem("Close");
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        close.addActionListener(this);

        //Adding the menu items to respective menu
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        edit.add(close);

        //Adding menus to the menu bar and then menu bar to the frame
        menuBar.add(file);
        menuBar.add(edit);
        frame.setJMenuBar(menuBar);

        //Creating a text area with vertical and horizontal scroll
        textArea.setMargin(new Insets(10,10,10,10));
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5,5,5,5));
        panel.setLayout(new BorderLayout(0,0));
        panel.add(textArea,BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane);
        frame.add(panel);

        //Prompting save when exit button is clicked
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //read contents of text area
                String data=textArea.getText().trim();

                //Check if text area is empty
                if(!data.equals("")) {//As text area is not empty we prompt for save
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    int result = JOptionPane.showConfirmDialog(null, "Do you want to save the file?");
                    switch (result) {
                        case JOptionPane.YES_OPTION:
                        {JFileChooser fileChooser = new JFileChooser("C:");
                            int chooseOption = fileChooser.showSaveDialog(null);
                            if (chooseOption == JFileChooser.APPROVE_OPTION) {
                                String path = fileChooser.getSelectedFile().getAbsolutePath();
                                path = path.replace(".txt","");
                                File file = new File(path+".txt");

                                try{
                                    FileWriter fileWriter = new FileWriter(file);
                                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                    textArea.write(bufferedWriter);
                                    bufferedWriter.close();
                                }
                                catch(IOException ioException){
                                    ioException.printStackTrace();
                                }
                            }
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        }
                        break;
                        case JOptionPane.NO_OPTION:
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            break;
                    }
                }
                else{// Just exit window without saving
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });

        frame.setTitle("Text Editor");
        frame.setBounds(0,0,900,500);
        frame.setVisible(true);
        frame.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(actionEvent.getSource() == cut){
            textArea.cut();
        }

        if(actionEvent.getSource() == copy){
            textArea.copy();
        }

        if(actionEvent.getSource() == paste){
            textArea.paste();
        }

        if(actionEvent.getSource() == selectAll){
            textArea.selectAll();
        }

        if(actionEvent.getSource() == close){
            System.exit(0);
        }

        if(actionEvent.getSource() == newFile){
            TextEditor newTextEditor = new TextEditor();
        }

        if(actionEvent.getSource() == openFile){
            String data=textArea.getText().trim();

            //Check if text area is empty
            if(!data.equals("")) {//As text area is not empty we prompt for save
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                int result = JOptionPane.showConfirmDialog(null, "Do you want to save the file?");
                switch (result) {
                    case JOptionPane.YES_OPTION:
                    {JFileChooser fileChooser = new JFileChooser("C:");
                        int chooseOption = fileChooser.showSaveDialog(null);
                        if (chooseOption == JFileChooser.APPROVE_OPTION) {
                            String path = fileChooser.getSelectedFile().getAbsolutePath();
                            path = path.replace(".txt","");
                            File file = new File(path+".txt");

                            try{
                                FileWriter fileWriter = new FileWriter(file);
                                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                textArea.write(bufferedWriter);
                                bufferedWriter.close();
                            }
                            catch(IOException ioException){
                                ioException.printStackTrace();
                            }
                        }
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    }
                    break;
                    case JOptionPane.NO_OPTION:
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        break;
                }
            }
            else{// Just exit window without saving
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }

            JFileChooser fileChooser = new JFileChooser("C:");
            int chooseOption = fileChooser.showOpenDialog(null);
            if (chooseOption == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getPath();

                try{
                    FileReader fileReader = new FileReader(filePath);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String intermediate = "", output = "";

                    while((intermediate = bufferedReader.readLine())!=null){
                        output += intermediate+"\n";
                    }

                    textArea.setText(output);
                }
                catch(IOException fileNotFoundException){
                    fileNotFoundException.printStackTrace();
                }
            }
        }

        if(actionEvent.getSource() == saveFile){
            JFileChooser fileChooser = new JFileChooser("C:");
            int chooseOption = fileChooser.showSaveDialog(null);
            if (chooseOption == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                path = path.replace(".txt","");
                File file = new File(path+".txt");

                try{
                    FileWriter fileWriter = new FileWriter(file);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    textArea.write(bufferedWriter);
                    bufferedWriter.close();
                }
                catch(IOException ioException){
                    ioException.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        TextEditor textEditor = new TextEditor();
    }
}