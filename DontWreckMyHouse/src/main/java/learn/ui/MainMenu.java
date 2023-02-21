package learn.ui;

public enum MainMenu {
   VIEW_RESERVATIONS_FOR_HOST(1, "View Reservations for Host", false),
    MAKE_A_RESERVATION(2, "Make A Reservation", false),
    EDIT_A_RESERVATION(3, "Edit A Reservation", false),
    CANCEL_A_RESERVATION(4, "Cancel A Reservation", false),
    EXIT(5, "Exit", false);


    private int value;
    private String message;
    private boolean hidden;

    private MainMenu(int value, String message, boolean hidden) {
        this.value = value;
        this.message = message;
        this.hidden = hidden;
    }

    public static MainMenu fromValue(int value) {
        for (MainMenu option : MainMenu.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public boolean isHidden() {
        return hidden;
    }
}
