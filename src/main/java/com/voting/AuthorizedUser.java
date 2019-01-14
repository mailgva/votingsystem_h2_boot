package com.voting;

import com.voting.model.User;
import com.voting.to.UserTo;
import com.voting.util.UserUtil;
import lombok.Data;

@Data
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public int getId() {
        return userTo.getId();
    }

    public void update(UserTo newTo) {
        userTo = newTo;
    }


    @Override
    public String toString() {
        return userTo.toString();
    }
}