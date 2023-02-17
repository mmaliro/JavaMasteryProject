package learn.ui;

import learn.data.DataException;
import learn.domain.Result;
import learn.models.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenu selectMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenu option : MainMenu.values()) {
            if (!option.isHidden()) {
                io.printf("%s. %s%n", option.getValue(), option.getMessage());
            }
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max);
        return MainMenu.fromValue(io.readInt(message, min, max));
    }

    public void displayHeader(String header) {
        io.println("");
        io.println(header);
        io.println("=".repeat(header.length()));
    }


    public void displayException(DataException ex) {
        displayHeader("An error has occurred:");
        io.println(ex.getMessage());
    }

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }

    public String getHostEmail() {
        return io.readString("Host Email: ");

    }

    public String getGuestEmail() {
        return io.readString("Guest Email: ");
    }

    public void displayReservations(List<Reservation> results) {
        if (results.size() == 0) {
            io.println("This host does not have any reservations.");
        } else {
            for (Reservation res : results) {
             //   io.println(res.toString());
                io.printf("ID: %s, %s - %s, Guest: %s, %s, Email: %s%n" , res.getRes_id(), io.formatter.format(res.getStartDate()), io.formatter.format(res.getEndDate()), res.getGuest().getLastName(), res.getGuest().getFirstName(), res.getGuest().getGuestEmail());
            }
        }
    }

    public LocalDate getDates(String prompt) {
        return io.readLocalDate(prompt);
    }

    public void printSuccessMessageForCreate(Reservation res) {
        io.printf("Success! Reservation %s added", res.getRes_id());
    }
}

