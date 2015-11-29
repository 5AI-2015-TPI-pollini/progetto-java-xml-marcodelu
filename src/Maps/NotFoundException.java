package Maps;

/**
 * Exception throwed when an address isn't found in Google Maps database.
 *
 * @author Marco De Lucchi
 */
public class NotFoundException extends Exception {

    private final String address;

    /**
     * Inizialize exception
     *
     * @param address address that throwed exception
     */
    public NotFoundException(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Can't find this address: " + address;
    }

    /**
     * Get the address that throwed the exception
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }
}
