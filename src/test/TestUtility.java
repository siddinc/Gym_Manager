import database.models.Customer;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class TestUtility {
    public static Faker faker = new Faker ();

    public static Customer createCustomer () {
        Customer tester = new Customer ();

        tester.setFirstName (faker.name ().firstName ());
        tester.setLastName  (faker.name ().lastName ());

        int     gender = faker.number ().numberBetween (0, 6);
        if      (gender % 3 == 0) tester.setGender (Customer.Gender.MALE);
        else if (gender % 2 == 0) tester.setGender (Customer.Gender.FEMALE);

        tester.setBirthDate         (convertDateToLocalDate (faker.date ().birthday (14, 60)));
        tester.setJoiningDate       (convertDateToLocalDate (faker.date ().past   (365, TimeUnit.DAYS)));
        tester.setMembershipEndDate (convertDateToLocalDate (faker.date ().future (365, TimeUnit.DAYS)));

        return tester;
    }

    // -----

    public static LocalDate convertDateToLocalDate (Date d) {
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
