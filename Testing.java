package com.example.firstfxproj;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.*;
import java.sql.*;


public class Testing extends Application implements EventHandler<ActionEvent>{
    Stage window;
    Scene scene1,scene2;
    Button button1,button2;
    String ans0;
    int ans1,ans2,ans3,ans4,ans5;
    TextField a0,a1,a2,a3,a4,a5;
    String url ="jdbc:mysql://localhost:3306/db1?useSSL=true";
    String user="root";
    String pass="Aman9889156878";
    Connection conn;
    Label label6,label7,label8,label9,label10,label61;


    public void start(Stage primaryStage) {

        window = primaryStage;
        Label label = new Label("Movie Review System:");
        label.setFont(new Font("Arial",20));
        Label label0 = new Label("Enter the name of the movie");
        Label label1 = new Label("Rate the story of the movie");
        Label label2 = new Label("Rate the casting of the movie");
        Label label3 = new Label("Rate the direction of the movie");
        Label label4 = new Label("Rate the production of the movie");
        Label label5 = new Label("Rate the acting in the movie");

        label61 = new Label("Movies:\tReviews:");
        label61.setFont(new Font("Arial",20));

        label6 = new Label();
        label7 = new Label();
        label8 = new Label();
        label9 = new Label();
        label10 = new Label();
        label6.setFont(new Font("Verdana",15));
        label7.setFont(new Font("Verdana",15));
        label8.setFont(new Font("Verdana",15));
        label9.setFont(new Font("Verdana",15));
        label10.setFont(new Font("Verdana",15));

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");//fix this
             conn = DriverManager.getConnection(url,user,pass);
            System.out.println("Established Connection");
        }catch(Exception e){
            System.out.println(e);
        }

         a0 =new TextField();
         a1 =new TextField();
         a2 =new TextField();
         a3 =new TextField();
         a4 =new TextField();
         a5 =new TextField();

        button1 = new Button("Submit");
        button1.setOnAction(this);

        VBox layout1= new VBox(10);
        layout1.setPadding(new Insets(10,50,50,50));
        layout1.getChildren().addAll(label,label0,a0,label1,a1,label2,a2,label3,a3,label4,a4,label5,a5,button1);
        scene1= new Scene(layout1,400,600);

        button2 = new Button("Let's rate a new Movie");
        button2.setOnAction(this);

        VBox layout2 = new VBox(10);
        layout2.setPadding(new Insets(10,50,50,50));
        layout2.getChildren().addAll(label61,label6,label8,label7,label9,label10,button2);
        scene2 = new Scene(layout2,600,300);

        window.setScene(scene2);
        window.setTitle("Movie Review System");
        window.show();


    }

    @Override
    public void handle(ActionEvent event){
        if(event.getSource()==button1){
            System.out.println("button1 clicked");
            ans0 =a0.getText();
            ans1 =Integer.parseInt(a1.getText());
            ans2 =Integer.parseInt(a2.getText());
            ans3 =Integer.parseInt(a3.getText());
            ans4 =Integer.parseInt(a4.getText());
            ans5 =Integer.parseInt(a5.getText());
            String title=ans0;

            int average= ans1+ans2+ans3+ans4+ans5;
            average= average/5;
            try (Connection conn =DriverManager.getConnection(url,user,pass);
                Statement stmt = conn.createStatement();
                ){
                //
                String sql="INSERT INTO reviews VALUES ('"+title+"','"+ans1+"',"+ans2+","+ans3+","+ans4+","+ans5+","+average+")";
                stmt.executeUpdate(sql);
            }
            catch (SQLException e){
                e.printStackTrace();
            }

            System.out.println(ans0+ans1+ans2+ans3+ans4+ans5);
            System.out.println("AVG:"+average);
            window.setScene(scene2);

            try (Connection conn =DriverManager.getConnection(url,user,pass);
                 Statement stmt = conn.createStatement();
            ){
                String sql="SELECT name,avg FROM  reviews";
                ResultSet rs=stmt.executeQuery(sql);
                int c=0;
                String s="label";
                while (rs.next()) {
                   System.out.print("\nName:" + rs.getString("name"));
                   System.out.print("\tavg:" + rs.getInt("avg"));
                    if(c==0)
                        label6.setText( rs.getString("name")+"\t\t"+(String)rs.getString("avg"));
                   if(c==1)
                       label7.setText( rs.getString("name")+"\t\t\t"+(String)rs.getString("avg"));
                   if(c==2)
                       label8.setText( rs.getString("name")+"\t\t\t"+(String)rs.getString("avg"));
                   if(c==3)
                       label9.setText( rs.getString("name")+"\t\t"+(String)rs.getString("avg"));
                   if(c==4)
                       label10.setText( rs.getString("name")+"\t\t\t"+(String)rs.getString("avg"));
                c++;
               }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(event.getSource()==button2){
            System.out.println("button2 clicked");
            window.setScene(scene1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}