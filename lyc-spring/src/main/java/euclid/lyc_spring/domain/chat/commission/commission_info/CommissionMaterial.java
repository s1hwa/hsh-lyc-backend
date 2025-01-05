package euclid.lyc_spring.domain.chat.commission.commission_info;

import euclid.lyc_spring.domain.chat.commission.Commission;
import euclid.lyc_spring.domain.enums.Material;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommissionMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Material material;

    @Column(nullable = false)
    private Boolean isPrefer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commission_id", nullable = false)
    private Commission commission;

}
