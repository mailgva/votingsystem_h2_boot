package com.voting.desktopapp.form;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.voting.to.DailyMenuTo;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainGUIForm extends JFrame{
    private JPanel rootPanel;
    private JButton btnEnter;
    private JTextArea txtArea;


    public MainGUIForm() throws HeadlessException {
        setSize(800,800);
        setLocationRelativeTo(null);
        setTitle("Voting");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(rootPanel);
        rootPanel.setLayout(null);

        btnEnter.addActionListener(e ->
                        txtArea.setText(getDailyMenu())
                //JOptionPane.showMessageDialog(null, "Ok")
        );
    }

    private void createUIComponents() {
        btnEnter = new JButton();
        btnEnter.setBounds(20,20,100,20);

        txtArea = new JTextArea();
        txtArea.setBounds(5,50,770,700);
        txtArea.setWrapStyleWord(true);
        txtArea.setLineWrap(true);
        txtArea.setText("Start text");

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        txtArea.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));

    }


    public String getDailyMenu() {
        try {
            String access_token="";
            String url = "https://localhost:8443/rest/voting/?date=2019-03-22";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");


            String user_name="user@yandex.ru";
            String password="password";

            String userCredentials = user_name+":"+password;
            String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
            con.setRequestProperty ("Authorization", basicAuth);
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:27.0) Gecko/20100101 Firefox/27.0.2 Waterfox/27.0");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //String urlParameters = "";



            int responseCode = con.getResponseCode();
            //System.out.println("\nSending 'GET' request to URL : " + url);
            //System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            StringBuilder result = new StringBuilder();
            JSONArray jsonA = new JSONArray(response.toString());
            for (int i = 0; i < jsonA.length(); i++) {
                JSONObject jsonObj = jsonA.getJSONObject(i);
                //System.out.println(jsonObj.getString("id"));
                //System.out.println(jsonObj.getString("name"));
                ObjectMapper m = new ObjectMapper();
                m.registerModule(new JavaTimeModule());
                DailyMenuTo dailyMenuTo = m.readValue(jsonObj.toString(), DailyMenuTo.class);
                result.append(dailyMenuTo + "\n");
                result.append("\n");
            }


            return result.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }
}
