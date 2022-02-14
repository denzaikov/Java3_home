package ru.geekbrains.march.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;
    private boolean hasLogin;

    public String getUsername() {
        return username;
    }

    public ClientHandler(Server server, Socket socket, ExecutorService threadPool) {
        this.server = server;
        this.socket = socket;
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            threadPool.execute(() -> {
                try {
                    while (!Thread.interrupted()) {
                        authentication();
                        if (hasLogin) communicate();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    threadPool.shutdown();
                    disconnect();
                }
            });
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void authentication() throws IOException {
        while (!hasLogin) { // Цикл авторизации
            String msg = in.readUTF();
            if (msg.startsWith("/login ")) {
                String[] tokens = msg.split("\\s+");
                if (tokens.length != 3) {
                    sendMessage("/login_failed Введите имя пользователя и пароль");
                    continue;
                }
                String login = tokens[1];
                String password = tokens[2];

                String userNickname = server.getAuthenticationProvider().getNicknameByLoginAndPassword(login, password);
                if (userNickname == null) {
                    sendMessage("/login_failed Введен некорретный логин/пароль");
                    continue;
                }
                if (server.isUserOnline(userNickname)) {
                    sendMessage("/login_failed Учетная запись уже используется");
                    continue;
                }
                username = userNickname;
                hasLogin = true;
                sendMessage("/login_ok " + login + " " + username);
                server.subscribe(this);
                break;
            }
        }
    }

    private void communicate() throws IOException {
        while (true) { // Цикл общения с клиентом
            String msg = in.readUTF();
            if (msg.startsWith("/")) {
                executeCommand(msg);
                continue;
            }
            server.broadcastMessage(username + ": " + msg);
        }
    }

    private void executeCommand(String cmd) {
        // /w Bob Hello, Bob!!!
        if (cmd.startsWith("/w ")) {
            String[] tokens = cmd.split("\\s+", 3);
            if (tokens.length != 3) {
                sendMessage("Server: Введена некорректная команда");
                return;
            }
            server.sendPrivateMessage(this, tokens[1], tokens[2]);
            return;
        }
        // /change_nick myNewNickname
        if (cmd.startsWith("/change_nick ")) {
            String[] tokens = cmd.split("\\s+");
            if (tokens.length != 2) {
                sendMessage("Server: Введена некорректная команда");
                return;
            }
            String newNickname = tokens[1];
            if (server.getAuthenticationProvider().isNickBusy(newNickname)) {
                sendMessage("Server: Такой никнейм уже занят");
                return;
            }
            server.getAuthenticationProvider().changeNickname(username, newNickname);
            username = newNickname;
            sendMessage("Server: Вы изменили никнейм на " + newNickname);
            server.broadcastClientsList();
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            disconnect();
        }
    }

    public void disconnect() {
        hasLogin = false;
        server.unsubscribe(this);
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
