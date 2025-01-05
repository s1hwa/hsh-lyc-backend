package euclid.lyc_spring.domain;

import euclid.lyc_spring.domain.clothes.Clothes;
import euclid.lyc_spring.domain.chat.commission.Commission;
import euclid.lyc_spring.domain.enums.Role;
import euclid.lyc_spring.domain.info.Info;
import euclid.lyc_spring.domain.mapping.LikedPosting;
import euclid.lyc_spring.domain.mapping.MemberChat;
import euclid.lyc_spring.domain.mapping.SavedPosting;
import euclid.lyc_spring.domain.posting.Posting;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String loginPw;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column(length = 30, nullable = false)
    private String phone;

    @Column(length = 10, nullable = false)
    private String nickname;

    @Column(columnDefinition = "text")
    private String introduction;

    @Column(columnDefinition = "text")
    private String profileImage;

    @Column(nullable = false, columnDefinition = "SMALLINT DEFAULT 0")
    private Short stampNo;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    @Setter
    private LocalDateTime inactive;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Integer point;

    @Setter
    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long follower;

    @Setter
    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long following;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Info info;

    @Setter
    @Column(columnDefinition = "BIT DEFAULT 1")
    private Boolean isPublic; // 저장한 코디 공개 여부

    // search용
    @Setter
    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long popularity;

    @Column
    @Setter
    private LocalDateTime lastLoginAt;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private PushSet pushSet;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Notification> notificationList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Attendance> attendanceList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberChat> memberChatList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PointUsage> pointUsageList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<LikedPosting> likedPostingList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<SavedPosting> savedPostingList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Clothes> clothesList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follow> followerList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL)
    private List<Follow> followingList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "blockedMember", cascade = CascadeType.ALL)
    private List<BlockMember> blockMemberList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "director", cascade = CascadeType.ALL)
    private List<Commission> commissionList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "fromMember", cascade = CascadeType.ALL)
    private List<Posting> fromPostingList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "toMember", cascade = CascadeType.ALL)
    private List<Posting> toPostingList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<Posting> postingList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Report> reportList = new ArrayList<>();

/*-------------------------------------------------- 연관관계 메서드 --------------------------------------------------*/

    public void addPushSet(PushSet pushSet) {
        this.pushSet = pushSet;
        pushSet.setMember(this);
    }

    public void addNotification(Notification notification) {
        notificationList.add(notification);
        notification.setMember(this);
    }

    public void addAttendance(Attendance attendance) {
        attendanceList.add(attendance);
    }

    public void addMemberChat(MemberChat memberChat) {
        memberChatList.add(memberChat);
        memberChat.setMember(this);
    }

    public void addPointUsage(PointUsage pointUsage) {
        pointUsageList.add(pointUsage);
        pointUsage.setMember(this);
    }

    public void addLikedPosting(LikedPosting likedPosting) {
        likedPostingList.add(likedPosting);
        likedPosting.setMember(this);
    }

    public void addSavedPosting(SavedPosting savedPosting) {
        savedPostingList.add(savedPosting);
        savedPosting.setMember(this);
    }

    public void addClothes(Clothes clothes) {
        clothesList.add(clothes);
        clothes.setMember(this);
    }

    public void addFollower(Follow follower) {
        followerList.add(follower);
    }

    public void addFollowing(Follow following) {
        followingList.add(following);
    }

    public void addBlockMember(BlockMember blockMember) {
        blockMemberList.add(blockMember);
    }

    public void addCommission(Commission commission) {
        commissionList.add(commission);
        commission.setDirector(this);
    }

    public void addFromPosting(Posting posting) {
        fromPostingList.add(posting);
        posting.setFromMember(this);
    }

    public void addToPosting(Posting posting) {
        toPostingList.add(posting);
        posting.setToMember(this);
    }

    public void addPosting(Posting posting) {
        postingList.add(posting);
        posting.setWriter(this);
    }

    public void addPoints(Integer points) {
        if (points != null) {
            point += points;
        }
    }

    public void addReport(Report report) { this.reportList.add(report); }

    //=== remove Methods ===//

    public void removePosting(Posting posting) {
        fromPostingList.remove(posting);
        toPostingList.remove(posting);
        postingList.remove(posting);
    }

    public void removeSavedPosting(SavedPosting savedPosting) {
        savedPostingList.remove(savedPosting);
    }

    //=== reload Methods ===//

    public void reloadFollowing(Long following) {
        this.following = following;
    }

    public void reloadFollower(Long follower) {
        this.follower = follower;
    }

    public void reloadNickname(String nickname) { this.nickname = nickname; }

    public void reloadIntroduction(String introduction) { this.introduction = introduction; }

    public void reloadLoginId(String loginId) { this.loginId = loginId; }

    public void reloadProfileImage(String profileImage) { this.profileImage = profileImage; }

    public void reloadPopularity(Long popularity) {this.popularity = popularity;}

    //=== change Methods ===//

    public void changeLoginPw(String password, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.loginPw = bCryptPasswordEncoder.encode(password);
    }
}