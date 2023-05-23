package bean;

/**
 * Represents personal information of an individual.
 */
public class PersonalInformation {

    /**
     * The ID of the person.
     */
    public String id;

    /**
     * The name of the person.
     */
    public String name;

    /**
     * The password associated with the person.
     */
    public String password;

    /**
     * The phone number of the person.
     */
    public String phoneNumber;

    /**
     * The email address of the person.
     */
    public String email;

    /**
     * The major of the person.
     */
    public String major;

    /**
     * The college of the person.
     */
    public String college;

    /**
     * The URL of the person's image.
     */
    public String imageUrl;

    /**
     * Constructs a PersonalInformation object with the specified details.
     *
     * @param id          the ID of the person
     * @param name        the name of the person
     * @param password    the password associated with the person
     * @param phoneNumber the phone number of the person
     * @param email       the email address of the person
     * @param major       the major of the person
     * @param college     the college of the person
     * @param imageUrl    the URL of the person's image
     */
    public PersonalInformation(String id, String name, String password, String phoneNumber, String email, String major, String college, String imageUrl) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.major = major;
        this.college = college;
        this.imageUrl = imageUrl;
    }

    /**
     * Returns the ID of the person.
     *
     * @return the ID of the person
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the person.
     *
     * @param id the ID of the person
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the name of the person.
     *
     * @return the name of the person
     */
    public String getName() {
        return name;
    }


    /**
     * Returns a string representation of the PersonalInformation object.
     *
     * @return a string representation of the PersonalInformation object
     */
    @Override
    public String toString() {
        return "PersonalInformation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", major='" + major + '\'' +
                ", college='" + college + '\'' +
                '}';
    }
}

