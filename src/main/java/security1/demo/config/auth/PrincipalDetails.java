package security1.demo.config.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료되면 시큐리티 session을 만들어준다. (Security ContextHolder)
// Security ContextHolder 여기에 들어갈 수 있는 오브젝트 타입이 정해져 있는데, Authentication 타입의 객체가 들어가야한다.
// Authentication 안에는 User 정보가 있어야 되고,
// User 오브젝트 타입은 UserDetails 타입 객체

// Security Session 여기에 세션 정보를 저장하는데, 여기에 들어갈 수 있는 객체가 Authentication 객체
// 그리고 Authentication 객체 안에 user 정보를 저장하는데 이때 객체 타입은 UserDetails 타입이어야 한다.
// 유저 오브젝트에 접근 하려면 Security Session 이 세션에서 Authentication 객체를 꺼내고, 거기서 UserDetails 를 통해서 유저 오브젝트에 접근.
// Security Session => Authentication => UserDetails(PrincipalDetails)

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import security1.demo.model.User;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 User의 권한을 리턴하는 곳.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        // 우리 사이트에서 1년 동안 회원이 로그인을 안하면 휴면 계정으로 하기로 했다면
        // user.getLoginDate 를 가져와서 현재시간 - 로그인 시간 => 1년을 초과하면 return false;

        return true;
    }
}
