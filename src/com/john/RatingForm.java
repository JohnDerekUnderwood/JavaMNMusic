/*
*John Underwood
*The class extends JFrame using javax.swing
* I also used java.awt (AWT: Abstract Window Toolkit)
* Also ActionEvent, ActionListener, ArrayList and List classes.
* */

package com.john;

//Import classes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



//Implementing RatingForm class that extends JFrame class
public class RatingForm extends JFrame{

    //Create components for JFrame
    private JButton clickMeButton;
    private JPanel rootPanel;
    private JTextField Venue;
    private JTextField City;
    private JComboBox Rating;
    private JTextArea textArea1;

    //Create RatingForm Method
    public RatingForm(){
        super("MN Music Venues");

        //setContentPane to rootPanel as its automatically created in form designer.
        setContentPane(rootPanel);
        updateRatingList();
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        //Creating ActionListner class & instance.
        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(RatingForm.this, "Thank you for your rating!");
                Rating rating = new Rating();
                for (Component c : rootPanel.getComponents()) {
                    if (((JComponent)c).getToolTipText() != null) {
                        if (((JComponent) c).getToolTipText().equals("Venue")) {
                            rating.setVenue(((JTextField) c).getText());
                        } else if (((JComponent) c).getToolTipText().equals("City")) {
                            rating.setCity(((JTextField) c).getText());
                        } else if (((JComponent) c).getToolTipText().equals("Rating")) {
                            rating.setRating(Integer.parseInt(((JComboBox<Number>) c).getSelectedItem().toString()));
                        }
                    }
                }
                addRating(rating);
                updateRatingList();

            }
        });
        setVisible(true);



    }

    //Add elements to JtextArea
    protected  void updateRatingList() {
        for (Component c: rootPanel.getComponents()) {
            if (c.getClass().getName().equals("javax.swing.JTextArea")) {
                JTextArea text = (JTextArea) c;
                text.setText(null);
                for (Rating r : getRatings()) {
                    text.append(r.getVenue() + " " + r.getCity() + " " + r.getRating() + "\n");
                }
            }
        }
    }


    public List<Rating> getRatings() {
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from VenueInfo");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet != null) {
                while (resultSet.next()) {
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    Rating rating = new Rating(resultSet.getString("Venue"), resultSet.getString("City"), resultSet.getInt("Rating"));
                    ratings.add(rating);
                }
            }
        }
        catch (Exception e) {

            //Instance of StackTrace - keeping track of methods
            e.printStackTrace();
        }
        return ratings;
    }

    //Connecting to SQL Management Studio through JDBC API (Java Database Connectivity)
    //Created new user: john
    //Code:  https://docs.microsoft.com/en-us/sql/connect/jdbc/step-3-proof-of-concept-connecting-to-sql-using-java?view=sql-server-2017
    private Connection getConnection () {
        String connectionString =
                "jdbc:sqlserver://127.0.0.1:56649;"
                        + "database=MNVenues;"
                        + "user=john;"
                        + "password=test1;"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "hostNameInCertificate=*.database.windows.net;"
                        + "loginTimeout=30;";

        // Declare the JDBC objects.
        Connection connection = null;


        //Code:  https://stackoverflow.com/questions/21024224/fetch-an-entire-row-with-jdbc-as-a-list
        try {
            connection = DriverManager.getConnection(connectionString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            //if (connection != null) try { connection.close(); } catch(Exception e) {}
        }
        return connection;
    }

    //Method calling rating objects
    protected void addRating(Rating rating) {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into VenueInfo values (?,?,?)");
            statement.setString(1, rating.getVenue());
            statement.setString(2, rating.getCity());
            statement.setInt(3, rating.getRating());
            statement.execute();

            //Future ideas: Add button to clear (
            // call a truncate statement or to delete individual rows)
            //under different user permissions.
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
