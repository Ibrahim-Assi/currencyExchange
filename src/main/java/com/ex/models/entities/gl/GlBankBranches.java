package com.ex.models.entities.gl;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gl_bank_branches")
//@Audited
public class GlBankBranches {

    @Id
    @Column(name = "branch_id", length = 50, nullable = false)
    private Long branchId;
    @Column(name = "bank_id", length = 50, nullable = false)
    private Long bankId;
    @Column(name = "branch_ar_name", length = 100, nullable = false)
    private String branchArName;
    @Column (name = "branch_en_name", length = 100, nullable = false)
    private String branchEnName;
    @Column (name = "branch_address", length = 200, nullable = false)
    private String branchAddress;
    @Column(name = "is_active",  nullable = false)
    private Boolean  isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({     @JoinColumn(name = "bank_id",  referencedColumnName = "bank_id",insertable = false, updatable = false)  })
    private GlBanks glBanks;

//    @Column (name = "operation" )
//    @NotNull
//    private  String operation ;
//
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
