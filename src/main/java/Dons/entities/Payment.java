package Dons.entities;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;

public class Payment {
    public static void main(String[] args) {
        // Set your secret key here
        Stripe.apiKey = "sk_test_51OnN2iI3dJx1TucUSLXejDmbjPPLTqFb4Q5btmlmfliYiDOHXlHrzLaq5OOCMJv9PRXDewithRQCah0lv1VXi21000LhIpIGIG";

        try {
            // Retrieve your account information
            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());
            // Print other account information as needed
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}
