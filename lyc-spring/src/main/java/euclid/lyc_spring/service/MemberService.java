package euclid.lyc_spring.service;

import euclid.lyc_spring.apiPayload.code.status.ErrorStatus;
import euclid.lyc_spring.apiPayload.exception.handler.MemberHandler;
import euclid.lyc_spring.domain.BlockMember;
import euclid.lyc_spring.domain.Follow;
import euclid.lyc_spring.domain.Member;
import euclid.lyc_spring.repository.BlockMemberRepository;
import euclid.lyc_spring.repository.FollowRepository;
import euclid.lyc_spring.repository.MemberRepository;
import euclid.lyc_spring.dto.response.MemberDTO.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final BlockMemberRepository blockMemberRepository;


    public List<TodayDirectorDTO> getTodayDirectorList() {
        return memberRepository.findAll().stream()
                .sorted(Comparator.comparing(Member::getFollower).reversed())
                .map(TodayDirectorDTO::toDTO)
                .limit(10)
                .toList();

    }

    public MemberInfoDTO getMemberInfoDTO(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        return MemberInfoDTO.toDTO(Objects.requireNonNull(member));
    }

    // FOLLOW

    // 팔로우하기
    public MemberInfoDTO followMember( Long myId,  Long memberId) {
        Member following = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (Objects.equals(myId, memberId)){
            throw new RuntimeException("본인은 팔로우할 수 없습니다.");
        } else if (followRepository.findByFollowerIdAndFollowingId(myId,memberId).isPresent()) {
            throw new RuntimeException("이미 팔로우 중입니다.");
        } else if (blockMemberRepository.findByMemberIdAndBlockMemberId(myId, memberId).isPresent()) {
            throw new RuntimeException("차단 중인 멤버입니다.");
        }

        Member follower = memberRepository.findById(myId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Follow follow = new Follow(follower,following);

        followRepository.save(follow);

        return MemberInfoDTO.toDTO(following);
    }

    // 언팔로우하기
    public MemberInfoDTO unfollowMember( Long myId,  Long memberId) {

        Member followingMember = memberRepository.findById(memberId).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Follow follow = followRepository.findByFollowerIdAndFollowingId(myId,memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.NOT_FOLLOWING));

        followRepository.delete(follow);
        return MemberInfoDTO.toDTO(followingMember);
    }

    public void unfollow_member( Long myId,  Long memberId){
        Member followingMember = memberRepository.findById(memberId).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Follow follow = followRepository.findByFollowerIdAndFollowingId(myId,memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.NOT_FOLLOWING));

        followRepository.delete(follow);

    }

    // 팔로워 목록 불러오기(member를 팔로우하는 사람들)
    public List<FollowDTO> getFollowerList( Long memberId) {

        memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        List<Follow> followers = followRepository.findByFollowingId(memberId);
        return followers.stream()
                .map(Follow::getFollower)
                .map(FollowDTO::toDTO)
                .toList();
    }

    // 팔로잉 목록 불러오기(member가 팔로우하는 사람들)
    public List<FollowDTO> getFollowingList( Long memberId) {

        memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        List<Follow> followers = followRepository.findByFollowerId(memberId);
        return followers.stream()
                .map(Follow::getFollowing)
                .map(FollowDTO::toDTO)
                .toList();
    }

    // BLOCKED

    // 차단하기
    public MemberInfoDTO blockMember( Long memberId,  Long blockMemberId) {

        Member blockMember = memberRepository.findById(blockMemberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (blockMemberRepository.findByMemberIdAndBlockMemberId(memberId,blockMemberId).isPresent()) {
            throw new MemberHandler(ErrorStatus.MEMBER_ALREADY_BLOCKED);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        BlockMember blockMemberRelation = new BlockMember(member, blockMember);
        blockMemberRepository.save(blockMemberRelation);

        return MemberInfoDTO.toDTO(blockMember);
    }

    // 차단 해제하기
    public MemberInfoDTO unblockMember( Long memberId, Long blockMemberId) {
        Member blockMember = memberRepository.findById(blockMemberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        BlockMember blockMemberRelation = blockMemberRepository.findByMemberIdAndBlockMemberId(memberId, blockMemberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_BLOCKED));

        blockMemberRepository.delete(blockMemberRelation);

        return MemberInfoDTO.toDTO(blockMember);
    }

    public void searchBlockMember( Long memberId, Long blockMemberId) {
        Member blockMember = memberRepository.findById(blockMemberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }
}