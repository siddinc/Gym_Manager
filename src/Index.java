import com.google.gson.Gson;
import database.models.Customer;

import java.time.LocalDate;
import java.time.Month;

class Index {

    public static void main (String [] args) {
        Customer x = new Customer ();
        x.setFirstName ("Vikrant");
        x.setLastName ("Gajria");
        x.setGender (Customer.Gender.MALE);
        x.setJoiningDate (LocalDate.of (2018, Month.SEPTEMBER, 20));

        Gson gson = new Gson ();

        System.out.println (gson.toJson (x));
        System.out.println (x.calculateDaysFromJoining ());
        System.out.println (x.calculateDaysTillEnd ());

    }

}