package com.biblioP7.security;

import com.biblioP7.beans.Membre;
import com.biblioP7.dao.MembreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

//classe qui vérifie la concordance des utilisateurs et de leurs rôles

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    MembreDao membreDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Membre membre = membreDao.findByEmail(username);
        if(membre == null) throw new UsernameNotFoundException((username));
//        System.out.println("role du membre = " + membre.getRole());

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(membre.getRole()));
        return new User(membre.getEmail(), membre.getPassword(), authorities);


    }
}