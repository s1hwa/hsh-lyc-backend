package euclid.lyc_spring.domain.chat;

import euclid.lyc_spring.domain.mapping.MemberChat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(columnDefinition = "BIT DEFAULT 0")
    private Boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_chat_id", nullable = false)
    private MemberChat memberChat;

    @Builder
    public Message(String content, Boolean isText, Boolean isChecked, MemberChat memberChat) {
        this.content = content;
        this.isText = isText;
        this.isChecked = isChecked;
        this.memberChat = memberChat;
    }

}
