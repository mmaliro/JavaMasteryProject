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

import java.time.LocalDate;
import java.util.List;

@Component
public class Controller {
   // private final GuestService guestService;
   // private final HostService hostService;
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

    private void deleteReservation() {
    }

    private void updateReservation() {
    }

    private void addReservation() throws DataException {
        //1. Prompt the user for Guest Email
        //2. Prompt the user for Host Email
        //3. Display existing reservations for host
        //4. Prompt user for start and end dates
        //5. Future addition - Display summary confirmation
        //6. Show success message
        //7. Show error message for reservation not being able to be added

        String guestEmail = view.getGuestEmail();
        Guest guest = guestService.findByEmail(guestEmail);
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        List<Reservation> allReservations = reservationService.findByHost(hostEmail);
        view.displayReservations(allReservations);
        LocalDate start = view.getDates("Start (MM/dd/yyyy): ");
        LocalDate end = view.getDates("End (MM/dd/yyyy): ");
        Reservation newReservation = new Reservation();
        newReservation.setStartDate(start);
        newReservation.setEndDate(end);
        newReservation.setGuest(guest);
        newReservation.setHost(host);
        Result result = reservationService.createReservation(newReservation);
        if (result.isSuccess()) {
            view.printSuccessMessageForCreate(result.getReservation());
        }



    }

    private void viewByHostEmail() throws DataException {

        //1. Prompt the user for host email
        //2. Call the HostService class findByEmail method to see if the host exists
        //3. If host is null, display a message to the user indicating that no such host found
        //4. If there is a host, use the host to call the ReservationService findByHost method
        //5. Display the results to the user

        String hostEmail = view.getHostEmail();
        List<Reservation> allReservations = reservationService.findByHost(hostEmail);
        view.displayReservations(allReservations);
    }
}
