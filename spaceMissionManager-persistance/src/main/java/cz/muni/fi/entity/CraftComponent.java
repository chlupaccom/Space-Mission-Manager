package cz.muni.fi.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * Created by jcizmar
 *
 * @author jcizmar
 */
@Entity
public class CraftComponent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private boolean readyToUse;

	@Column(unique = true)
	@NotNull
	private String name;

    @ManyToOne(fetch = FetchType.LAZY)
	private Spacecraft spacecraft;

	private ZonedDateTime readyDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isReadyToUse() {
		return readyToUse;
	}

	public void setReadyToUse(boolean readyToUse) {
		this.readyToUse = readyToUse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ZonedDateTime getReadyDate() {
		return readyDate;
	}

	public void setReadyDate(ZonedDateTime readyDate) {
		this.readyDate = readyDate;
	}

	public Spacecraft getSpacecraft() {
		return spacecraft;
	}

	public void setSpacecraft(Spacecraft spacecraft) {
		if(this.spacecraft != null && this.spacecraft.equals(spacecraft)) return;
		this.spacecraft = spacecraft;
		spacecraft.addComponent(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof CraftComponent)) return false;

		CraftComponent that = (CraftComponent) o;

		return getName().equals(that.getName());
	}

	@Override
	public int hashCode() {
		return getName() != null ? getName().hashCode() : 0;
	}


	@Override
	public String toString() {
		return "CraftComponent{" +
				", readyToUse=" + readyToUse +
				", name='" + name + '\'' +
				", readyDate=" + readyDate +
				'}';
	}
}