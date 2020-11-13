package ro.sd.a2.entity;

import javax.persistence.*;

@Entity
@Table(name = "exchange")
public class Exchange{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "valuteFrom_id")
    private Valute valuteFrom;

    @ManyToOne
    @JoinColumn(name = "valuteTo_id")
    private Valute valuteTo;

    @Column
    private float ratio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Valute getValuteFrom() {
        return valuteFrom;
    }

    public void setValuteFrom(Valute valuteFrom) {
        this.valuteFrom = valuteFrom;
    }

    public Valute getValuteTo() {
        return valuteTo;
    }

    public void setValuteTo(Valute valuteTo) {
        this.valuteTo = valuteTo;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
}
