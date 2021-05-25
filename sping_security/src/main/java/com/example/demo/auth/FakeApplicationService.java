package com.example.demo.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.security.ApplicatonUserRole;
import com.google.common.collect.Lists;

@Repository("fake")
public class FakeApplicationService implements ApplicationUserDao {

	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public FakeApplicationService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	private List<ApplicationUser> getApplicationUsers(){
		List<ApplicationUser> applicationUser = Lists.newArrayList(
				new ApplicationUser(
						"nam",
						passwordEncoder.encode("nam12345"),
						ApplicatonUserRole.STUDENT.getGrantedAuthorities(),
						true, true, true, true ),
				new ApplicationUser(
						"admin",
						passwordEncoder.encode("nam12345"),
						ApplicatonUserRole.ADMIN.getGrantedAuthorities(),
						true, true, true, true ),
				new ApplicationUser(
						"admintrain",
						passwordEncoder.encode("nam12345"),
						ApplicatonUserRole.ADMINTRAINEE.getGrantedAuthorities(),
						true, true, true, true )
				);
		return applicationUser;
	}
	
	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		return getApplicationUsers()
				.stream()
				.filter(applicationUser -> username.equals(applicationUser.getUsername()))
				.findFirst();
	}
	
}
