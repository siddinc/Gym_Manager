import com.google.gson.Gson;
import database.models.Customer;

import java.time.LocalDate;
import java.time.Month;

class Index {

    public static void main (String [] args) {
        Customer x = new Customer ();
        x.setJoiningDate (LocalDate.of (2018, Month.SEPTEMBER, 10));
        x.setMembershipEndDate (LocalDate.of (2018, Month.DECEMBER, 25));

        Gson gson = new Gson ();

        System.out.println (gson.toJson (x));
        System.out.println (x.daysFromJoining ());
        System.out.println (x.daysTillEnd ());
    }

}