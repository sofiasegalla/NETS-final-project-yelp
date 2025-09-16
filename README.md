# NETS 150 Final Project Summary

## Description

We created a project called **Going Global**, which is an interactive Java program that outputs a travel itinerary given an input location and category such as landmarks, museums, restaurants, etc. Our program returns a number of results based on your inputs, as gathered from the Yelp API, and also allows you to plan a trip from the worst rated place in the results to the best rated place in the results. Additionally, our program also gives the user the highest rated and lowest rated place based on their inputs, along with their rating and number of reviews. We utilized **Graph Algorithms** (Dijkstra’s implementation) and **Information Networks** (API call using `URLGetter` and `HTTPConnection`) from class to create our project.

## Changes

Initially, we planned on using the Google Maps API to find places and plan an itinerary, but realized that this API does not have strong support for Java implementations. Therefore, we decided to pivot by using the Yelp API instead, which offered better support in Java and allowed us to expand our project to also include other categories such as coffee shops, restaurants, and bars, so that the user can utilize our project to do a “crawl” of a particular category of places from the worst place in that category to the best.

## Work Breakdown

### Marissa Almonte
Our project TA recommended we implement Dijkstra’s to output an order to visit the attractions. I implemented the graph by creating an adjacency list for each `YelpBusiness` returned in the `Itinerary.java` class. Edges were connected depending on the endpoints’ rating, so that the directed edge would go from the lower-rated attraction to the higher-rated one. The edge weight was calculated using the distance between each point, which was calculated with the coordinates. I encountered the most difficulty thinking about how to connect the graph to produce a path of length >2 because the Yelp API did not provide specific directions from one point to another. I implemented Dijkstra’s using the `Pair` class and a `PriorityQueue` to follow the algorithm we learned in class. I took the lead on designing our user manual with the help of Tasneem and Sofia’s input.

### Tasneem Pathan
For our project, I worked on the backend API calls and pre-processing to get the results based on the user’s input. I created the `YelpAPI.java`, `YelpBusiness.java`, and `YelpTester.java` classes, and also used the `URLGetter.java` class that Swap gave us in order to connect to the Yelp API and make a request for the information. Since the API returns a JSON object, which is difficult to work with, I created the `YelpBusiness` class to make it easier for the rest of my collaborators to get access to the information they needed without having to process the data from the JSON object each time. I also modified the GUI to add a reset button and radio buttons for the category options to make it easier for the user to interact with our project and limit exceptions from invalid input.

### Sofia Segalla
For our itinerary, we really wanted to make the interaction between user and code as smooth as possible. We chose to create a GUI that could take in the specific parameters the user wanted in their search and then output places to visit that matched those parameters. I started the GUI by building a `JFrame` with 3 textboxes for location, type of attraction, and number of places. I added getter and setter methods based on Tasneem and Marissa’s work. The GUI allows three types of output: (1) a list of attractions, (2) an itinerary of 3 places using Dijkstra’s algorithm, and (3) the highest and lowest rated places. I displayed each place’s name, address, latitude/longitude, and Yelp rating. Later, we redesigned the interface to use radio buttons instead of text input for attraction type and added a reset button to allow repeated use and minimize errors.

---

# Homework 5: Theory  
**Date:** 5/1/22

## Prisoner’s Dilemma

### 1. Hiring a Lawyer Game

|             | Hire     | Don't Hire |
|-------------|----------|------------|
| **Hire**    | 30, 30   | 45, 35     |
| **Don't**   | 35, 45   | 50, 50     |

- Yes, each party is better off **not hiring** the lawyer because it is the **dominant strategy**.
- Although hiring gives a bigger reward, the cost reduces the net payoff.
- Not hiring gives 35 or 50, rather than 30 or 45 respectively.

---

## Exercise 6.11 – Ya and Ub

### Payoff Matrix

|       | L    | M    | R    |
|-------|------|------|------|
| **t** | 0,③  | ⑨,2  | 1,1  |
| **m** | 2,③  | 0,1  | ⑦,0  |
| **b** | ⑤,③  | 4,2  | 3,1  |

- Player B has a **strongly dominant strategy** of playing **L**.
- No matter what Player A chooses, **L** always gives Player B the highest payoff.
- **Nash Equilibrium**: (**b**, **L**)

---

## Exercise 6.11 – 8A and 8B

### Payoff Matrix

|       | L    | R    |
|-------|------|------|
| **U** | ④,①  | 0,0  |
| **D** | 0,0  | ④,⑥  |

- **Nash Equilibria**: (U, L) and (D, R)
- Mixed strategy solution:  
  - (p, g) = (⅗, ⅗)

---

## Exercise 6.11 – IEC

### Entry Game

|       | A        | B        |
|-------|----------|----------|
| **N** | -10,-10  | 15, 0    |
| **B** | 5,5      | 0,0      |

- This argument is valid. By entering and producing B, the worst you can do is same profit, best is 30.
- Not entering gives $0 regardless.
- Strategy **B** is the best response for both players.
- **Nash Equilibria**: (A, B) and (B, A)
- Yes, merging is a good idea — both firms can make more informed production decisions and maximize profit.

---

## IEDS (Iterated Elimination of Dominated Strategies)

### Game Table

|       | L        | C        | R        |
|-------|----------|----------|----------|
| **U** | 4, ?     | ?        | ?        |
| **D** | 1, 2     | -1, ?    | -4, ?    |

- No dominant strategy
- Dominated strategies:
  - R is **weakly dominated** by C
  - U is **strongly dominated** by M and D
  - C is **weakly dominated** by L
- Final IEDS solution: **(D, L)**
