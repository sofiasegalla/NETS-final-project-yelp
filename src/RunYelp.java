import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;


public class RunYelp extends JFrame implements ActionListener {

    // JPanel
    static JPanel userInputPanel;
    static JPanel resultsPanel;

    static JTextField locationText;
    static JTextField categoriesText;
    static JTextField numText;

    // JFrame
    static JFrame frame;

    // JButtons
    static JButton findButton;
    static JButton itineraryButton;
    static JButton ratingsButton;
    static JButton resetButton;

    static ButtonGroup categories;

    static JRadioButton landmarks;
    static JRadioButton museums;
    static JRadioButton bakeries;
    static JRadioButton coffee;
    static JRadioButton restaurants;
    static JRadioButton bars;

    // labels to display text
    static JLabel locationLabel;
    static JLabel categoriesLabel;
    static JLabel numLabel;
    static JLabel line;

    static String locationInput;
    static String categoriesInput;
    static int numInput;
    static String resultsString;

    static JTextArea results;
    static JScrollPane scroll;

    public RunYelp() {
        userInputPanel = new JPanel(new GridLayout(12, 2));
        resultsPanel = new JPanel(new FlowLayout());
    }

    public static void main(String[] args) {
        // create a new frame to store text field and button

        frame = new JFrame("Going Global");
        frame.setLayout(new GridLayout(2, 1, 0, 0));

        // create a label to display text
        locationLabel = new JLabel("Location:", JLabel.LEFT);
        numLabel = new JLabel("Number of Results:", JLabel.LEFT);
        line = new JLabel("–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––" +
                "–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––" +
                "–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
        resultsString = "";


        RunYelp te = new RunYelp();

        findButton = new JButton("Find Places");
        findButton.addActionListener(te);
        itineraryButton = new JButton("Get Your Itinerary!");
        itineraryButton.addActionListener(te);
        ratingsButton = new JButton("Highest Ratings");
        ratingsButton.addActionListener(te);
        resetButton = new JButton("Reset");
        resetButton.addActionListener(te);

        results = new JTextArea(15, 50);
        results.setEditable(false);
        results.setLineWrap(true);
        scroll = new JScrollPane(results);

        categories = new ButtonGroup();
        categoriesLabel = new JLabel("Categories:");
        landmarks = new JRadioButton("landmarks");
        museums = new JRadioButton("museums");
        bakeries = new JRadioButton("bakeries");
        coffee = new JRadioButton("coffee");
        restaurants = new JRadioButton("restaurants");
        bars = new JRadioButton("bars");

        categories.add(landmarks);
        categories.add(museums);
        categories.add(bakeries);
        categories.add(coffee);
        categories.add(restaurants);
        categories.add(bars);

        landmarks.addActionListener(te);
        museums.addActionListener(te);
        bakeries.addActionListener(te);
        coffee.addActionListener(te);
        restaurants.addActionListener(te);
        bars.addActionListener(te);


        // create a object of JTextField with 16 columns
        locationText = new JTextField(16);
        categoriesText = new JTextField(16);
        numText = new JTextField(16);

        // add buttons and textfield to panel
        userInputPanel.add(locationLabel);
        userInputPanel.add(locationText);

        userInputPanel.add(categoriesLabel);
        userInputPanel.add(new JLabel());
        userInputPanel.add(landmarks);
        userInputPanel.add(museums);
        userInputPanel.add(bakeries);
        userInputPanel.add(coffee);
        userInputPanel.add(restaurants);
        userInputPanel.add(bars);

        userInputPanel.add(numLabel);
        userInputPanel.add(numText);

        userInputPanel.add(findButton, CENTER_ALIGNMENT);
        userInputPanel.add(itineraryButton, CENTER_ALIGNMENT);
        userInputPanel.add(ratingsButton, CENTER_ALIGNMENT);
        userInputPanel.add(resetButton, CENTER_ALIGNMENT);

        resultsPanel.add(scroll);

        // add panel to frame
        frame.add(userInputPanel);
        frame.add(resultsPanel);

        // set the size of frame
        frame.setSize(650, 600);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        String q = e.getActionCommand();

        //if the button is pressed, gets the answers to input into API
        if (q.equals("Find Places") || q.equals("Get Your Itinerary!") || q.equals("Highest Ratings")) {
            if (!resultsString.equals("")) {
                resultsString = "";
                results.setText(resultsString);
            }
            System.out.println(locationText.getText());
            if (locationText.getText().equals("")) {
                resultsString = "Invalid input. Must enter location.";
                results.setText(resultsString);
            } else if (numText.getText().equals("")) {
                resultsString = "Invalid input. Must enter number.";
                results.setText(resultsString);
            } else if (!landmarks.isSelected() && !museums.isSelected() && !bakeries.isSelected() && !coffee.isSelected() && !restaurants.isSelected() && !bars.isSelected()) {
                resultsString = "Invalid input. Must select a category.";
                results.setText(resultsString);
            } else {
                locationInput = locationText.getText();

                if (landmarks.isSelected()) {
                    categoriesInput = "landmarks";
                } else if (museums.isSelected()) {
                    categoriesInput = "museums";
                } else if (bakeries.isSelected()) {
                    categoriesInput = "bakeries";
                } else if (coffee.isSelected()) {
                    categoriesInput = "coffee";
                } else if (restaurants.isSelected()) {
                    categoriesInput = "restaurants";
                } else if (bars.isSelected()) {
                    categoriesInput = "bars";
                }

                System.out.println(numText.getText());
                numInput = Integer.parseInt(numText.getText().trim());

                // get the businesses based on inputs
                YelpAPI yelp = new YelpAPI();
                yelp.getUserInput(locationInput.trim(), categoriesInput, numInput);
                yelp.getResults();
                ArrayList<YelpBusiness> businesses = yelp.getBusinesses();


                // Gets any number of attractions as specified
                if (q.equals("Find Places")) {
                    for (YelpBusiness business : businesses) {
                        String lat = business.getLatitude() + "";
                        String lon = business.getLongitude() + "";

                        resultsString = resultsString + business.getName() + "\nAddress: \n" +
                                business.getAddress() + "Location: " + lat + " , " + lon + "\n\n";
                    }

                    results.setText(resultsString);

                    findButton.setEnabled(false);
                    itineraryButton.setEnabled(false);
                    ratingsButton.setEnabled(false);
                    resetButton.setEnabled(true);
                }

                //create the itinerary
                Itinerary i = new Itinerary(businesses);
                i.makeGraph();
                try {
                    i.dijkstra(i.getWorst(), i.getBest());
                    LinkedList<YelpBusiness> path = i.getPath();

                    //Gets top three places to visit using dijkstra's
                    if (q.equals("Get Your Itinerary!")) {
                        int count = 0;
                        for (YelpBusiness it : path) {
                            count++;
                            resultsString = resultsString + count + ": " + it.getName() + "\nRating: " + it.getRating()
                                    + " stars from " + it.getRc() + " reviews\n" + "Address:\n" + it.getAddress() + "\n";
                        }
                    }
                } catch (Exception p) {
                    resultsString = "No path from worst location to best location was found.";
                } finally {
                    results.setText(resultsString);
                    findButton.setEnabled(false);
                    itineraryButton.setEnabled(false);
                    ratingsButton.setEnabled(false);
                    resetButton.setEnabled(true);
                }

                // Gets the highest and lowest rated attractions from the list found
                if (q.equals("Highest Ratings")) {
                    YelpBusiness best = i.getBest();
                    YelpBusiness worst = i.getWorst();
                    resultsString = resultsString + "Highest Rated: " + best.getName() + "\nRating: " + best.getRating()
                            + " from " + best.getRc() + " reviews" +
                            "\n\n" +
                            "Lowest Rated: " + worst.getName() + "\nRating: " + worst.getRating()
                            + " from " + worst.getRc() + " reviews" + "\n";
                    results.setText(resultsString);
                    findButton.setEnabled(false);
                    itineraryButton.setEnabled(false);
                    ratingsButton.setEnabled(false);
                    resetButton.setEnabled(true);
                }
            }
        }


        if (e.getSource() == resetButton) {
            findButton.setEnabled(true);
            itineraryButton.setEnabled(true);
            ratingsButton.setEnabled(true);
            resetButton.setEnabled(false);
            locationText.setText("");
            categoriesText.setText("");
            numText.setText("");
            results.setText("");
            categories.clearSelection();
            resultsString = "";
        }
    }
}
