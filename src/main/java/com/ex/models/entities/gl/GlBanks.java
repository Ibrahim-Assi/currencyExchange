package com.ex.models.entities.gl;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Audited
@Table(name = "gl_banks")
public class GlBanks {

    @Id
    @Column(name = "bank_id", length = 50, nullable = false)
    private Long bankId;
    @Column(name = "bank_ar_name", length = 100, nullable = false, unique = true)
    private String bankArName;
    @Column(name = "bank_en_name", length = 100, nullable = false, unique = true)
    private String bankEnName;


//    @NotAudited
    @OneToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY, mappedBy = "glBanks")
    private List<GlBankBranches> glBankBranches = new ArrayList<>();



//    @Column (name = "operation", nullable = false )
//    private  String operation ;
//    @Column(name= "timestamp")
//    private  long timestamp  ;
//
//    @PrePersist
//    public void  onPresist(){audit("INSERT");}
//    @PreUpdate
//    public void  onPreUpdate(){audit("UPDATE");}
//    @PreRemove
//    public void  onPreRemove(){audit("DELETE");}
//
//    private void audit(String operation) {
//        setOperation(operation);
//        setTimestamp((new Date()).getTime());
//    }

}
