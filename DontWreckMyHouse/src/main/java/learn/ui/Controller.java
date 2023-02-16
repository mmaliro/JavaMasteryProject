package learn.ui;

import learn.data.DataException;
import learn.domain.GuestService;
import learn.domain.HostService;
import learn.domain.ReservationService;
import learn.models.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Controller {
   // private final GuestService guestService;
   // private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(ReservationService reservationService, View view) {
      //  this.guestService = guestService;
     //   this.hostService = hostService;
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

    private void addReservation() {
    }

    private void viewByHostEmail() throws DataException {

        //1. Prompt the user for host email
        //2. Call the HostService class findByEmail method to see if the host exists
        //3. If host is null, display a message to the user indicating that no such host found
        //4. If there is a host, use the host to call the ReservationService findByHost method
        //5. Display the results to the user

        String hostEmail = view.getHostEmail();
        System.out.println("The email you entered is: " + hostEmail);
        String tempHostId = "2e72f86c-b8fe-4265-b4f1-304dea8762db";
        List<Reservation> allReservations = reservationService.findByHost(tempHostId);
        view.displayReservations(allReservations);
    }
}
