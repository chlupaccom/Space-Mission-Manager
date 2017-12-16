package cz.muni.fi.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * Created by jsmadis
 *
 * @author jsmadis
 */

@Entity
//In Derby, its forbiden to 'USER' is reserved keyword, we need to rename table
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate birthDate;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = ".+@.+\\....?")
    @NotNull
    private String email;

    @NotNull
    private String password;

    private boolean isManager;

    private int experienceLevel;


    private boolean acceptedMission;

    private String explanation;

    @ManyToOne(fetch = FetchType.LAZY)
    private Mission mission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public boolean getAcceptedMission() {
        return acceptedMission;
    }

    public void setAcceptedMission(boolean acceptedMission) {
        this.acceptedMission = acceptedMission;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        if(this.mission != null && this.mission.equals(mission)) return;
        this.mission = mission;
        if (mission != null) mission.addAstronaut(this);
    }

    public boolean missionStatusPending(){
        return mission != null && !getAcceptedMission() && ((getExplanation() == null) || getExplanation().isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return getEmail() != null ? getEmail().equals(user.getEmail()) : user.getEmail() == null;
    }

    @Override
    public int hashCode() {
        return getEmail() != null ? getEmail().hashCode() : 0;
    }


    @Override
    public String toString() {
        return "User{" +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", experienceLevel=" + experienceLevel +
                '}';
    }
}