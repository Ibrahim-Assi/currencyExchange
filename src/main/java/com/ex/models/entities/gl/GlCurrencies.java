package com.ex.models.entities.gl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Audited
@Table(name = "gl_currencies")
public class GlCurrencies {
    @Id
    @Column(name = "curr_code", length = 10, nullable = false)
    private String currCode;
    @Column(name = "curr_ar_name", length = 20, nullable = false, unique = true)
    private String currArName;
    @Column(name = "curr_en_name", length = 20, nullable = false, unique = true)
    private String currEnName;
    @Column(name = "curr_part_en_name", length = 10, nullable = false)
    private String currPartEnName;
    @Column(name = "curr_part_ar_name", length = 10, nullable = false)
    private String currPartArName;

    @Column(name = "min_rate", length = 50, nullable = false)
    private Double minRate;
    @Column(name = "max_rate", length = 8, nullable = false)
    private Double maxRate;

    public GlCurrencies( String currArName, String currEnName,String currCode, String currPartEnName,
                        String currPartArName, Double minRate, Double maxRate) {
        this.currCode = currCode;
        this.currArName = currArName;
        this.currEnName = currEnName;
        this.currPartEnName = currPartEnName;
        this.currPartArName = currPartArName;
        this.minRate = minRate;
        this.maxRate = maxRate;
    }

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_curr_code", referencedColumnName = "curr_code",insertable = false,updatable = false)
    private List<GlCurrencyRates> currencyRatesSet;

    @Column(name = "opreation")
    private String opreation;
    @Column(name = "timestamp")
    private Long timestamp;

    @PrePersist
    public void onPrePersist() {
        audit("INSERT");
    }
    @PreUpdate
    public void onPreUpdate() {
        audit("UPDATE");
    }
    @PreRemove
    public void onPreRemove() {
        audit("REMOVE");
    }
    private void audit(String opreation) {
        setOpreation(opreation);
        setTimestamp((new Date()).getTime());
    }


}
