package learn.data;

import learn.models.Guest;

import java.util.List;

public class GuestRepositoryDouble implements GuestRepository{
    public static Guest GUEST_1 = new Guest(1, "Sullivan", "Lomas",
            "slomas0@mediafire.com", "(702) 7768761", "NV");

    public static Guest GUEST_2 = new Guest(2, "Olympie", "Gecks",
            "ogecks1@dagondesign.com", "(202) 2528316", "DC");

    public static Guest GUEST_3 = new Guest(3, "Tremain", "Carncross",
            "tcarncross2@japanpost.jp", "(313) 2245034", "MI");

    private List<Guest> guests = List.of(GUEST_1, GUEST_2, GUEST_3);

    @Override
    public Guest findById(int guest_id) throws DataException {
        return findAll().stream()
                .filter(guest -> guest.getGuest_id() == guest_id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Guest> findAll() throws DataException {
        return guests;
    }

    @Override
    public Guest findByEmail(String guestEmail) throws DataException {
        return guests.stream()
                .filter(guest -> guest.getGuestEmail().equalsIgnoreCase(guestEmail))
                .findFirst()
                .orElse(null);
    }
}
