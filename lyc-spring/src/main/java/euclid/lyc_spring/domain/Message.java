package euclid.lyc_spring.domain;

import euclid.lyc_spring.domain.mapping.MemberChat;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Getter
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @CreatedDate
    @Column
    private Date createdAt;

    @Column(columnDefinition = "BIT DEFAULT 0")
    private Boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_chat_id", nullable = false)
    private MemberChat memberChat;
}
