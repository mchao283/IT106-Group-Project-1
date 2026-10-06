//File made by Mindy Chao
import javax.swing.JOptionPane;

public class BudgetCalorieMealPlanner {
    public static void main(String[] args) {
    
        //constants for meal cost and calorie value, and recommended daily intake (2000 for women, 2500 for men)
        final double BREAKFAST_COST = 5.00;
        final int BREAKFAST_CALORIES = 400;
        
        final double LUNCH_COST = 10.00;
        final int LUNCH_CALORIES = 600;
        
        final double DINNER_COST = 15.00;
        final int DINNER_CALORIES = 900;

        final int RECOMMENDED_CALORIES_WOMEN = 2000;
        final int RECOMMENDED_CALORIES_MEN = 2500;

        //accept inputs for: meal budget, gender(m/f), meal option(breakfast, lunch, dinner)        
        double mealBudget = 0;
        boolean isValid = false;
        while (!isValid) {
            String budgetInput = JOptionPane.showInputDialog(null, "Enter your daily meal budget: ", "Budget Input", JOptionPane.QUESTION_MESSAGE);   //get input from user for their meal budget
            
            if (budgetInput == null) { //for if user exits the pane
               JOptionPane.showMessageDialog(null, "Input canceled.");
               continue;
            }
            
            
            //error accepting inputs that are not a number, results in Exception in thread "main" java.lang.NumberFormatException: For input string: "e"
            try {
                mealBudget = Double.parseDouble(budgetInput);
                if (mealBudget > 0) { //checks that the budget entered is greater than 0, exits the loop
                    isValid = true; //exits loop
                } else {
                    JOptionPane.showMessageDialog(null, "Budget must be a number greater than 0.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                //if the user inputs text or symbols instead of a number
                JOptionPane.showMessageDialog(null, "Budget must be a number greater than 0.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }       
        
        
        } //end of while loop
       
        String[] genderOptions = {"Female", "Male"};
        int genderChoice = JOptionPane.showOptionDialog(null, "Select your gender for calorie recommendations:", "Gender Selection", //select between female and male for gender
                  JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, genderOptions, genderOptions[0]);
        int recommendedCalories = 0;
        
        if (genderChoice == 0) { //0 is the integer for selecting female, otherwise it is male, sets the recommended calories to the gender that was selected.
            recommendedCalories = RECOMMENDED_CALORIES_WOMEN;
        }
        else {
            recommendedCalories = RECOMMENDED_CALORIES_MEN;
        }
                
        
        boolean programRunning = true; 
        int mealCalories = 0;
        double mealCost = 0;
        int caloriesConsumed = 0;
        
        //selecting meal options will be a switch case to select 1. breakfast, 2. lunch, 3. dinner or 4. to exit (any other number will be an invalid input)
        while (programRunning) { // this will loop until Finish Day is selected, if the budget is still greater than the cost for breakfast (further below)
            
            String[] mealOptions = {"Breakfast", "Lunch", "Dinner", "Finish Day"};
            int mealChoice = JOptionPane.showOptionDialog(null, String.format("Current Status:\n- Remaining Budget: $%.2f\n- Calories Consumed: %d / %d kcal\n\nSelect a meal option: \n Breakfast - $5.00, 450 calories \n Lunch - $10.00, 600 calories \n Dinner - $15.00, 900 calories", 
                     mealBudget, caloriesConsumed, recommendedCalories), "Meal Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, mealOptions, mealOptions[0]);
              
            switch (mealChoice) {
               case 0:  //breakfast
                  mealCost = BREAKFAST_COST;
                  mealCalories = BREAKFAST_CALORIES;
                  break;
               case 1:  //lunch
                  mealCost = LUNCH_COST;
                  mealCalories = LUNCH_CALORIES;
                  break;
               case 2:  //dinner
                  mealCost = DINNER_COST;
                  mealCalories = DINNER_CALORIES;
                  break;
               case 3:  //finish planning
                  programRunning = false; //stops the loop
                  continue;
            }
                        
            
            if (mealBudget < BREAKFAST_COST) {  //checks if the budget is lower than the meal cost of breakfast, meaning the budget is less than all of the meal options.
               JOptionPane.showMessageDialog(null, String.format("You cannot afford this meal.", "Insufficient Funds", JOptionPane.WARNING_MESSAGE));
               programRunning = false; //stops the loop
            }
            else if (mealBudget < mealCost) {    //checks if the budget is lower than the meal cost of the meal option selected.
               JOptionPane.showMessageDialog(null, String.format("You cannot afford this meal.", "Insufficient Funds", JOptionPane.WARNING_MESSAGE)); //the loop still continues since there is a cheaper option able to be chosen.
            }
            else {   //if the budget is greater than the meal cost, it can be subtracted
               mealBudget = mealBudget - mealCost; //subtracts the cost of the meal selected from the budget
               caloriesConsumed = caloriesConsumed + mealCalories;   //adds the calories from the meal selected to the calories consumed (starting from 0).
            }
            
            
        }   // end of while loop for meal options.
              
        int calorieDifference = Math.abs(recommendedCalories - caloriesConsumed); //takes the absolute value of recommendedCalories minus caloriesConsumed to find the excess amount of calories, if any.
        double joggingHours = (double) calorieDifference/100;
        
        if (caloriesConsumed > recommendedCalories) { //checks if the calories consumed is over the recommended calorie intake
            JOptionPane.showMessageDialog(null, String.format("You are over the recommended daily calorie intake by " + calorieDifference + " calories.\n Recommended: Jog for " + joggingHours + " hours to burn the excess calories.", "Final Result", JOptionPane.INFORMATION_MESSAGE, calorieDifference));
        }
        else if (caloriesConsumed == recommendedCalories) { //checks if calories consumed is exactly the same amount as the calorie intake
            JOptionPane.showMessageDialog(null, "Congratulations! You are at the exact recommended daily calorie intake!", "Final Result", JOptionPane.INFORMATION_MESSAGE);
        }
        else { //checks if calories consumed is less than the recommended calorie intake
            JOptionPane.showMessageDialog(null, "Congratulations! You have met the recommended daily calorie intake!", "Final Result", JOptionPane.INFORMATION_MESSAGE);
        }
        
        System.exit(0);
        
    }
}