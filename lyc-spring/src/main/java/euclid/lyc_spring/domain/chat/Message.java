package euclid.lyc_spring.domain.chat;

import euclid.lyc_spring.domain.enums.MessageCategory;
import euclid.lyc_spring.domain.mapping.MemberChat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "BIT DEFAULT 0")
    private Boolean isText;

    @CreatedDate
    @Column
    private LocalDateTime createdAt;

    @Setter
    @Column(columnDefinition = "BIT DEFAULT 0")
    private Boolean isChecked;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_chat_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MemberChat memberChat;

}
