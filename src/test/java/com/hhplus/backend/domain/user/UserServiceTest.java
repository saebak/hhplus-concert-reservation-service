package com.hhplus.backend.domain.user;

import com.hhplus.backend.domain.exception.NotEnoughPointException;
import com.hhplus.backend.domain.exception.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("사용자 조회 및 포인트관련 테스트")
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    private User basicUser;
    private UserPoint basicUserPoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // 기본 객체 세팅
        long id = 1;
        long userId = 1;
        int point = 2000;
        this.basicUser = new User(userId, "박새미");
        this.basicUserPoint = new UserPoint(id, userId, point);
    }

    @Test
    @DisplayName("포인트 조회 테스트")
    public void getUserPointTest() {
        // given
        long userId = 1;
        given(userRepository.getUser(anyLong())).willReturn(basicUser);
        given(userRepository.getUserPoint(anyLong())).willReturn(basicUserPoint);

        // when
        UserPoint userPoint = userService.getUserPoint(userId);

        // then
        assertThat(userPoint).isNotNull();
        assertThat(userPoint.getId()).isEqualTo(basicUser.getId());
        assertThat(userPoint.getUserId()).isEqualTo(basicUserPoint.getUserId());
        assertThat(userPoint.getPoint()).isEqualTo(basicUserPoint.getPoint());
    }

    @Test
    @DisplayName("포인트 조회하는데 유저 정보가 없을 경우 테스트")
    public void getUserPointIsNullTest() {
        // given
        long userId = 1;
        given(userRepository.getUser(anyLong())).willReturn(null);
        given(userRepository.getUserPoint(anyLong())).willReturn(basicUserPoint);

        // when
        // then
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(()->{
                    userService.getUserPoint(userId);
                }).withMessage("사용자가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("포인트 충전 테스트")
    public void chargePointTest() throws Exception {
        // given
        long userId = 1;
        int amount = 20000;
        UserPoint newUserPoint = new UserPoint(basicUserPoint.getId(), userId, basicUserPoint.getPoint()+amount);
        given(userRepository.getUser(anyLong())).willReturn(basicUser);
        given(userRepository.getUserPoint(anyLong())).willReturn(basicUserPoint);
        given(userRepository.updateUserPoint(any())).willReturn(newUserPoint);

        // when
        UserPoint userPoint = userService.chargePoint(userId, amount);

        // then
        assertThat(userPoint).isNotNull();
        assertThat(userPoint.getId()).isEqualTo(basicUserPoint.getId());
        assertThat(userPoint.getUserId()).isEqualTo(basicUserPoint.getUserId());
        assertThat(userPoint.getPoint()).isEqualTo(basicUserPoint.getPoint());
    }

    @Test
    @DisplayName("포인트 사용 테스트")
    public void usePointTest() throws Exception {
        // given
        long userId = 1;
        int usePoint = 1000;
        UserPoint newUserPoint = new UserPoint(basicUserPoint.getId(), userId, basicUserPoint.getPoint()-usePoint);
        given(userRepository.getUser(anyLong())).willReturn(basicUser);
        given(userRepository.getUserPoint(anyLong())).willReturn(basicUserPoint);
        given(userRepository.updateUserPoint(any())).willReturn(newUserPoint);

        // when
        UserPoint userPoint = userService.usePoint(userId, usePoint);

        // then
        assertThat(userPoint).isNotNull();
        assertThat(userPoint.getId()).isEqualTo(basicUserPoint.getId());
        assertThat(userPoint.getUserId()).isEqualTo(basicUserPoint.getUserId());
        assertThat(userPoint.getPoint()).isEqualTo(basicUserPoint.getPoint());
    }

    @Test
    @DisplayName("포인트 사용 테스트 - 포인트가 부족할시 exception 발생")
    public void usePointNotEnoughTest() throws Exception {
        // given
        long userId = 1;
        int usePoint = 5000;
        UserPoint newUserPoint = new UserPoint(basicUserPoint.getId(), userId, basicUserPoint.getPoint()-usePoint);
        given(userRepository.getUser(anyLong())).willReturn(basicUser);
        given(userRepository.getUserPoint(anyLong())).willReturn(basicUserPoint);
        given(userRepository.updateUserPoint(any())).willReturn(newUserPoint);

        // when
        // given
        assertThatExceptionOfType(NotEnoughPointException.class)
                .isThrownBy(()->{
                    userService.usePoint(userId, usePoint);
                }).withMessage("포인트가 부족합니다.");
    }
    
}
