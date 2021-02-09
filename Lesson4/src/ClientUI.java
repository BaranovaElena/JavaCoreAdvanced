import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientUI extends JFrame {
    JButton btnSend;
    JTextField fieldMessage;
    JTextArea areaChat;

    public ClientUI() {
        setTitle("Чатик");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //создаем кнопку и строку
        btnSend = new JButton("Отправить сообщение");
        fieldMessage = new JTextField();

        //создаем компоновщик для поля и строки
        JPanel panelLineBtn = new JPanel();
        GridLayout layoutTextFields = new GridLayout(2,1);
        panelLineBtn.setLayout(layoutTextFields);

        //помещаем кнопку и строку в низ окна
        panelLineBtn.add(fieldMessage);
        panelLineBtn.add(btnSend);
        add(panelLineBtn, BorderLayout.SOUTH);

        //оставшееся место заполняем текстовым полем
        areaChat = new JTextArea();
        areaChat.setEditable(false);
        areaChat.setBackground(new Color(135, 206, 250));
        areaChat.setLineWrap(true); //перенос длинной строки на след.строку
        JScrollPane scroll = new JScrollPane(areaChat,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);

        setSendingMessage();

        setVisible(true);
    }

    private void setSendingMessage() {
        //событие для переноса текста
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaChat.append(fieldMessage.getText()+"\n\n");
                fieldMessage.setText("");
            }
        };

        btnSend.addActionListener(actionListener);  //на кнопку
        fieldMessage.addActionListener(actionListener); //на Enter
    }

    public static void main(String[] args) {
        new ClientUI();
    }
}
