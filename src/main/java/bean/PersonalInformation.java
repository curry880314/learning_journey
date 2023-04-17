package bean;

public class PersonalInformation {
    public String id;

    public String name;
    public String password;
    public String phoneNumber;
    public String email;
    public String major;
    public String college;
    public String imageUrl;

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

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
