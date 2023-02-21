package learn.ui;

import learn.data.DataException;
import learn.domain.GuestService;
import learn.domain.HostService;
import learn.domain.ReservationService;
import learn.domain.Result;
import learn.models.Guest;
import learn.models.Host;
import learn.models.Reservation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class Controller {
    Scanner console = new Scanner(System.in);
    private final ReservationService reservationService;
    private final View view;
    private final HostService hostService;
    private final GuestService guestService;

    public Controller(ReservationService reservationService, View view, GuestService guestService, HostService hostService) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Don't Wreck My House");
        try {
            runApp();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runApp() throws DataException {
        MainMenu option;
        do {
            option = view.selectMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS_FOR_HOST:
                    viewByHostEmail();
                    view.enterToContinue();
                    break;
                case MAKE_A_RESERVATION:
                    addReservation();
                    break;
                case EDIT_A_RESERVATION:
                    updateReservation();
                    break;
                case CANCEL_A_RESERVATION:
                    deleteReservation();
                    break;

            }
        } while (option != MainMenu.EXIT);
    }

    private void deleteReservation() throws DataException {
        view.displayHeader("Cancel Reservation");
        Reservation reservation = new Reservation();
        String guestEmail = view.getGuestEmail();
        Guest guest = guestService.findByEmail(guestEmail);
        if (guest == null) {
            view.printString("Guest email not found.");
            return;
        }
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        if (host == null) {
            view.printString("Host email not found.");
            return;
        }

        List<Reservation> allReservations = reservationService.findByHost(hostEmail);

        if (allReservations.size() == 0) {
            view.displayReservations(allReservations, host);
            return;
        } else {
            view.displayReservations(allReservations, host);
        }

        reservation.setRes_id(view.getResId());
        reservation.setHost(host);


        boolean result = reservationService.cancelReservation(reservation);

        if (result) {
            view.printString("Reservation cancelled");
        }






    }

    private void updateReservation() throws DataException {
        view.displayHeader("Edit Reservation");
        Reservation reservation = new Reservation();
        String guestEmail = view.getGuestEmail();
        Guest guest = guestService.findByEmail(guestEmail);
        String input;
        if (guest == null) {
            view.printString("Guest email not found.");
            return;
        }
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        if (host == null) {
            view.printString("Host email not found.");
            return;
        }

        List<Reservation> allReservations = reservationService.findByHost(hostEmail);

       if (allReservations.size() == 0) {
           view.displayReservations(allReservations, host);
            return;
        } else {
           view.displayReservations(allReservations, host);
       }

        int reservationId = view.getResId();

        view.printString("Editing reservation " + reservationId);

        LocalDate start = view.getDates("Start (MM/dd/yyyy): ");
        LocalDate end = view.getDates("End (MM/dd/yyyy): ");
        Reservation editReservation = new Reservation();
        editReservation.setStartDate(start);
        editReservation.setEndDate(end);
        editReservation.setGuest(guest);
        editReservation.setHost(host);
        editReservation.setRes_id(reservationId);
        view.printString("Summary");
        view.printString("=======");
        view.printString("Start: " + start);
        view.printString("End: " + end);
        view.printString("Total: $" + reservationService.calculate(editReservation));
        view.printString("Is this okay? [y/n]");
        input = console.next();

        Result result = reservationService.editReservation(editReservation);



       while (!(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n"))) {
            view.printString("Invalid input. Please enter 'y' or 'n'.");
            view.printString("Is this okay? [y/n]");
            input = console.next();
        }

        if (input.equalsIgnoreCase("y") && result.isSuccess()) {
            view.printString("Success! Reservation " + reservationId + " updated");
        } else if (input.equalsIgnoreCase("n")) {
            reservationService.cancelReservation(editReservation);
            view.printString("Reservation cancelled. You can start over.");

        }  else {
        view.displayError(result);
       }





        System.out.println();


    }


    private void addReservation() throws DataException {
        view.displayHeader("Add Reservation");
        String guestEmail = view.getGuestEmail();
        Guest guest = guestService.findByEmail(guestEmail);
        String input;
        if (guest == null) {
            view.printString("Guest email not found.");
            return;
        }
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        if (host == null) {
            view.printString("Host email not found.");
            return;
        }

        List<Reservation> allReservations = reservationService.findByHost(hostEmail);
        view.displayReservations(allReservations, host);
        LocalDate start = view.getDates("Start (MM/dd/yyyy): ");
        LocalDate end = view.getDates("End (MM/dd/yyyy): ");
        Reservation newReservation = new Reservation();
        newReservation.setStartDate(start);
        newReservation.setEndDate(end);
        newReservation.setGuest(guest);
        newReservation.setHost(host);
        view.printString("Summary");
        view.printString("=======");
        view.printString("Start: " + start);
        view.printString("End: " + end);
        view.printString("Total: $" + reservationService.calculate(newReservation));
        view.printString("Is this okay? [y/n]");
        input = console.next();
        Result result = reservationService.createReservation(newReservation);

        while (!(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n"))) {
            view.printString("Invalid input. Please enter 'y' or 'n'.");
            view.printString("Is this okay? [y/n]");
            input = console.next();
        }

            if (input.equalsIgnoreCase("y") && result.isSuccess()) {
                view.printSuccessMessageForCreate(result.getReservation());
            } else if (input.equalsIgnoreCase("n")) {
                reservationService.cancelReservation(newReservation);
                view.printString("Reservation cancelled");
            } else {
                view.displayError(result);
            }


        System.out.println();



    }

    private void viewByHostEmail() throws DataException {
        Host host = getHost();

        if (host == null) {
            view.printString("Host does not exist.");
            return;
        }


        List<Reservation> allReservations = reservationService.findByHost(host.getHostEmail());
        view.displayReservations(allReservations, host);




    }

    private Guest getGuest() throws DataException {
        String guestEmail = view.getGuestEmail();
        return guestService.findByEmail(guestEmail);
    }

    private Host getHost() throws DataException {
        String hostEmail = view.getHostEmail();
        return hostService.findByEmail(hostEmail);

    }


}

