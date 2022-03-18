package nz.co.app.db;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty(message = "Title can not be empty")
    private String title;

    @Column(length = 1024)
    @Size(min = 1, max = 1024, message = "Description: Length should be between 1 to 1024")
    private String description;

    private Date due_date;

    @Column(length = 10)
    @Size(min = 1, max = 10, message = "Status: Length should be between 1 to 10")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_date;

    //*****************************************************************************

    @PrePersist
    private void onCreate() {
        creation_date = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Tasks() {}

    public Tasks(String title, String description, Date due_date, String status) {
        this.title = title;
        this.description = description;
        this.due_date = due_date;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", due_date=" + due_date +
                ", status='" + status + '\'' +
                ", creation_date=" + creation_date +
                '}';
    }
}
