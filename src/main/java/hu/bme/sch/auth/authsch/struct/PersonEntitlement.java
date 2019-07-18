package hu.bme.sch.auth.authsch.struct;

import java.io.Serializable;

public class PersonEntitlement implements Serializable {

    private static final long serialVersionUID = -2904767686389619156L;
    
    private final long id;
    private final String name;
    private final String status;
    private final String start;
    private final String end;

    public PersonEntitlement(long id, String name, String status, String start, String end) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.start = start;
        this.end = end;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    /**
     * Date Format: YYYY-MM-DD
     * @return
     */
    public String getStart() {
        return start;
    }

    /**
     * Date Format: YYYY-MM-DD
     * @warning It can be null!
     * @return
     */
    public String getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "PersonEntitlement [id=" + id + ", name=" + name + ", status=" + status + ", start=" + start + ", end="
                + end + "]";
    }

}
